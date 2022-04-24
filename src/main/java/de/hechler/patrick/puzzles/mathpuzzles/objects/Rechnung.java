package de.hechler.patrick.puzzles.mathpuzzles.objects;

import de.hechler.patrick.puzzles.mathpuzzles.enums.Operator;

public abstract class Rechnung implements Iterable <Rechnung> {
	
	public Rechnung append(Operator op, Rechnung dann) {
		return new MultiRechnung(this, op, dann);
	}
	
	@Override
	public abstract String toString();
	
	public abstract String toHiddenString();
	
	protected abstract long calc() throws ArithmeticException;
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object obj);
	
}
