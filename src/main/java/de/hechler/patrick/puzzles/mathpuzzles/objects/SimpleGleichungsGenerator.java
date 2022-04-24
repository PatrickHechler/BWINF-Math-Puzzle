package de.hechler.patrick.puzzles.mathpuzzles.objects;

import java.util.Random;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;

import de.hechler.patrick.codesprachen.methodensprache.antlr4.RechnungLexer;
import de.hechler.patrick.codesprachen.methodensprache.antlr4.RechnungParser;
import de.hechler.patrick.codesprachen.methodensprache.antlr4.RechnungParser.RechnungContext;
import de.hechler.patrick.puzzles.mathpuzzles.enums.Operator;
import de.hechler.patrick.puzzles.mathpuzzles.interfaces.GleichungsGenerator;

public class SimpleGleichungsGenerator implements GleichungsGenerator {
	
	private final Random rnd;
	private final long   bound;
	
	public SimpleGleichungsGenerator(long bound) {
		this(new Random(), bound);
	}
	
	public SimpleGleichungsGenerator(Random rnd, long bound) {
		this.rnd = rnd;
		this.bound = bound;
	}
	
	@Override
	public Gleichung generiere(int zahlen) {
		Rechnung rech = nextRechnung();
		Operator[] ops = Operator.values();
		for (zahlen -- ; zahlen > 0; zahlen -- ) {
			rech = rech.append(ops[rnd.nextInt(ops.length)], nextRechnung());
		}
		rech = generateRechnungFromString(rech.toString());
		return new Gleichung(rech, rech.calc());
	}
	
	public static Rechnung generateRechnungFromString(String str) {
		ANTLRInputStream antlrin = new ANTLRInputStream(str);
		RechnungLexer lexer = new RechnungLexer(antlrin);
		CommonTokenStream tok = new CommonTokenStream(lexer);
		RechnungParser parser = new RechnungParser(tok);
		parser.setErrorHandler(new BailErrorStrategy());
		RechnungContext context = parser.rechnung();
		return context.gl;
	}
	
	private ZahlRechnung nextRechnung() {
		return new ZahlRechnung( (rnd.nextLong() & 0x7FFFFFFFFFFFFFFFL) % bound);
	}
	
}
