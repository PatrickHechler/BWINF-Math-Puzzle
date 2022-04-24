package de.hechler.patrick.puzzles.mathpuzzles.objects;

import de.hechler.patrick.puzzles.mathpuzzles.interfaces.GleichungsGenerator;

public class CheckedGleichungsGenerator implements GleichungsGenerator {
	
	private final GleichungsGenerator gg;
	
	public CheckedGleichungsGenerator(GleichungsGenerator gg) {
		this.gg = gg;
	}
	
	@Override
	public Gleichung generiere(int zahlen) {
		Gleichung gl;
		boolean cont;
		do {
			cont = false;
			while (true) {
				try {
					gl = gg.generiere(zahlen);
					if (gl.ergebnis >= 0L) {
						break;
					}
				} catch (ArithmeticException e) {}
			}
			boolean hit = false;
			for (Gleichung g : gl) {
				if (g.ergebnis == gl.ergebnis) {
					if (hit) {
						cont = true;
						break;
					} else {
						hit = true;
					}
				}
			}
		} while (cont);
		return gl;
	}
	
}
