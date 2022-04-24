package de.hechler.patrick.puzzles.mathpuzzles.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;

import de.hechler.patrick.puzzles.mathpuzzles.enums.Operator;
import de.hechler.patrick.puzzles.mathpuzzles.exceptions.IllegalCalculationException;

public class MultiRechnung extends Rechnung {
	
	public final Rechnung a;
	public final Operator op;
	public final Rechnung b;
	
	
	public MultiRechnung(Rechnung a, Operator op, Rechnung b) {
		this.a = a;
		this.op = op;
		this.b = b;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(a);
		builder.append(' ');
		builder.append(op);
		builder.append(' ');
		builder.append(b);
		return builder.toString();
	}
	
	public String toString(Operator op) {
		StringBuilder builder = new StringBuilder();
		builder.append(a);
		builder.append(' ');
		builder.append(op);
		builder.append(' ');
		builder.append(b);
		return builder.toString();
	}
	
	@Override
	public String toHiddenString() {
		StringBuilder builder = new StringBuilder();
		builder.append(a.toHiddenString());
		builder.append(" ? ");
		builder.append(b.toHiddenString());
		return builder.toString();
	}
	
	@Override
	protected long calc() throws ArithmeticException {
		long ac = a.calc(),
			bc = b.calc();
		switch (op) {
		case geteilt: {
			long res = ac / bc;
			long acCheck = Math.multiplyExact(res, bc);
			if (ac != acCheck) {
				throw new IllegalCalculationException("ac/bc: ac=" + ac + " bc=" + bc + " res=" + res + " acCheck=" + acCheck);
			}
			return res;
		}
		case mal:
			return Math.multiplyExact(ac, bc);
		case minus:
			return Math.subtractExact(ac, bc);
		case plus:
			return Math.addExact(ac, bc);
		}
		throw new InternalError("unknown op: " + op.name());
	}
	
	
	@Override
	public Iterator <Rechnung> iterator() {
		return new Iterator <Rechnung>() {
			
			Operator[] ops  = Operator.values();
			Rechnung   next = null;
			String[]   str  = toHiddenString().split("\\s*\\?\\s*");
			int[]      is   = new int[str.length - 1];
			
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
			public Rechnung next() {
				Rechnung n = next;
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
				Rechnung rech = SimpleGleichungsGenerator.generateRechnungFromString(build.toString());
				for (int i = 0; i < is.length; i ++ ) {
					is[i] ++ ;
					if (is[i] >= ops.length) {
						is[i] = 0;
						continue;
					}
					return rech;
				}
				is = null;
				return rech;
			}
			
		};
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + a.hashCode();
		result = prime * result + b.hashCode();
		result = prime * result + op.hashCode();
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if ( ! (obj instanceof MultiRechnung)) return false;
		MultiRechnung other = (MultiRechnung) obj;
		if (a == null) {
			if (other.a != null) return false;
		} else if ( !a.equals(other.a)) return false;
		if (b == null) {
			if (other.b != null) return false;
		} else if ( !b.equals(other.b)) return false;
		if (op != other.op) return false;
		return true;
	}
	
}
