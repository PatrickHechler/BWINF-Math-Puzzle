package de.hechler.patrick.puzzles.mathpuzzles.enums;


public enum Operator {
	
	plus, minus, mal, geteilt
	
	;
	
	@Override
	public String toString() {
		switch (this) {
		case geteilt:
			return ":";
		case mal:
			return "*";
		case minus:
			return "-";
		case plus:
			return "+";
		}
		throw new InternalError("unknown operator: " + name());
	}
	
}
