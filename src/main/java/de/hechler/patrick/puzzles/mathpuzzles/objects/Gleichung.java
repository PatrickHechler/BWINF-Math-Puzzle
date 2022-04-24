package de.hechler.patrick.puzzles.mathpuzzles.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Gleichung implements Iterable <Gleichung> {
	
	public final Rechnung rechnung;
	public final long     ergebnis;
	
	public Gleichung(Rechnung rechnung, long ergebnis) {
		this.rechnung = rechnung;
		this.ergebnis = ergebnis;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(rechnung);
		builder.append(" = ");
		builder.append(ergebnis);
		return builder.toString();
	}
	
	public String toHiddenString() {
		StringBuilder builder = new StringBuilder();
		builder.append(rechnung.toHiddenString());
		builder.append(" = ");
		builder.append(ergebnis);
		return builder.toString();
	}
	
	@Override
	public Iterator <Gleichung> iterator() {
		return new Iterator <Gleichung>() {
			
			Iterator <Rechnung> iter = rechnung.iterator();
			Gleichung           next;
			
			@Override
			public boolean hasNext() {
				try {
					next = next();
					return true;
				} catch (NoSuchElementException e) {
					return false;
				}
			}
			
			@Override
			public Gleichung next() throws NoSuchElementException {
				Gleichung n = next;
				if (n != null) {
					next = null;
					return n;
				}
				while (true) {
					try {
						Rechnung rech = iter.next();
						long res = rech.calc();
						return new Gleichung(rech, res);
					} catch (ArithmeticException e) {}
				}
			}
			
		};
	}
	
	@Override
	public int hashCode() {
		return (int) (ergebnis ^ (ergebnis >>> 32));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if ( ! (obj instanceof Gleichung)) return false;
		Gleichung other = (Gleichung) obj;
		if (ergebnis != other.ergebnis) return false;
		if (rechnung == null) {
			if (other.rechnung != null) return false;
		} else if ( !rechnung.equals(other.rechnung)) return false;
		return true;
	}
	
}
