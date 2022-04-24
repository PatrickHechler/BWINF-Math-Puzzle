package de.hechler.patrick.puzzles.mathpuzzles.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;

import de.hechler.patrick.puzzles.mathpuzzles.enums.Operator;

public class RechnungStringIterator implements Iterator <String> {
	
	private final Operator[] ops  = Operator.values();
	private final String[]   str;
	private int[]            is;
	private String           next = null;
	
	
	
	public RechnungStringIterator(Rechnung rech) {
		this(rech.toHiddenString().split("\\s*\\?\\s*"));
	}
	
	public RechnungStringIterator(String str) {
		this(str.split("\\s*\\?\\s*"));
	}
	
	public RechnungStringIterator(String[] str) {
		this.str = str;
		this.is = new int[str.length - 1];
	}
	
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
	public String next() {
		String n = next;
		if (n != null) {
			next = null;
			return n;
		} else if (is == null) {
			throw new NoSuchElementException("no more elements!");
		}
		StringBuilder build = new StringBuilder(str[0]);
		for (int i = 0; i < is.length; i ++ ) {
			build.append(ops[is[i]]).append(str[i + 1]);
		}
		// Rechnung rech = SimpleGleichungsGenerator.generateRechnungFromString(build.toString());
		for (int i = 0; i < is.length; i ++ ) {
			is[i] ++ ;
			if (is[i] >= ops.length) {
				is[i] = 0;
				continue;
			}
			return build.toString();
		}
		is = null;
		return build.toString();
	}
	
}
