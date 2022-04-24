package de.hechler.patrick.puzzles.mathpuzzles.objects;

import java.util.Arrays;
import java.util.Iterator;

public class ZahlRechnung extends Rechnung {
	
	public final long zahl;
	
	public ZahlRechnung(long zahl) {
		this.zahl = zahl;
	}
	
	@Override
	public String toString() {
		return Long.toString(zahl);
	}
	
	@Override
	public String toHiddenString() {
		return Long.toString(zahl);
	}
	
	@Override
	protected long calc() {
		return zahl;
	}
	
	@Override
	public Iterator <Rechnung> iterator() {
		return Arrays.asList((Rechnung) this).iterator();
	}
	
	@Override
	public int hashCode() {
		return (int) (zahl ^ (zahl >>> 32));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if ( ! (obj instanceof ZahlRechnung)) return false;
		ZahlRechnung other = (ZahlRechnung) obj;
		if (zahl != other.zahl) return false;
		return true;
	}
	
}
