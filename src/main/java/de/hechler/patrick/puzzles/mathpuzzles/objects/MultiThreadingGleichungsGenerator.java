package de.hechler.patrick.puzzles.mathpuzzles.objects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import de.hechler.patrick.puzzles.mathpuzzles.interfaces.GleichungsGenerator;

public class MultiThreadingGleichungsGenerator implements GleichungsGenerator {
	
	private static final Gleichung INVALID = new Gleichung(null, 0L);
	
	private final GleichungsGenerator   gg;
	private final Map <Long, Gleichung> gls;
	private volatile int                workers;
	
	public MultiThreadingGleichungsGenerator(GleichungsGenerator gg) {
		this.gg = gg;
		this.gls = new HashMap <>();
	}
	
	public Gleichung[] generiereMehrere(int zahlen) {
		generiereGleihungen(zahlen);
		return this.gls.values().toArray(new Gleichung[this.gls.size()]);
	}
	
	@Override
	public Gleichung generiere(int zahlen) {
		generiereGleihungen(zahlen);
		return this.gls.values().iterator().next();
	}
	
	private void generiereGleihungen(int zahlen) {
		while (true) {
			Gleichung gl;
			while (true) {
				try {
					gl = gg.generiere(zahlen);
					if (gl.ergebnis >= 0L) {
						break;
					}
				} catch (ArithmeticException e) {}
			}
			String[] strs = gl.rechnung.toHiddenString().split("\\s*\\?\\s*");
			if (strs.length < 3) {
				for (Gleichung g : gl) {
					Long res = (Long) g.ergebnis;
					Gleichung old = this.gls.put(res, g);
					if (old != null) {
						this.gls.put(res, INVALID);
					}
				}
			} else {
				String[] worker = new String[strs.length - 1];
				System.arraycopy(strs, 0, worker, 0, worker.length - 1);
				String[] last = new String[2];
				System.arraycopy(strs, worker.length - 1, last, 0, 2);
				for (Iterator <String> iter = new RechnungStringIterator(last); iter.hasNext();) {
					worker[worker.length - 1] = iter.next();
					final String[] ws = worker.clone();
					synchronized (this) {
						this.workers ++ ;
					}
					new Thread(() -> {
						try {
							for (Iterator <String> wsiter = new RechnungStringIterator(ws); wsiter.hasNext();) {
								Rechnung rech = SimpleGleichungsGenerator.generateRechnungFromString(wsiter.next());
								try {
									long res = rech.calc();
									Long calc = (Long) res;
									Gleichung g = new Gleichung(rech, res);
									synchronized (MultiThreadingGleichungsGenerator.this) {
										gls.merge(calc, g, (a, b) -> INVALID);
									}
								} catch (ArithmeticException e) {}
							}
						} finally {
							synchronized (this) {
								this.workers -- ;
								notify();
							}
						}
					}).start();
				}
				while (this.workers > 0) {
					synchronized (this) {
						try {
							wait(10L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			for (Iterator <Entry <Long, Gleichung>> iter = this.gls.entrySet().iterator(); iter.hasNext();) {
				Map.Entry <Long, Gleichung> entry = iter.next();
				if (entry.getValue() == INVALID) {
					iter.remove();
				}
			}
			if ( !this.gls.isEmpty()) {
				return;
			}
		}
	}
	
}
