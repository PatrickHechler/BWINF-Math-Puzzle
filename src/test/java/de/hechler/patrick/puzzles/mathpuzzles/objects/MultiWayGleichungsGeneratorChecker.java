package de.hechler.patrick.puzzles.mathpuzzles.objects;

import static de.hechler.patrick.zeugs.check.Assert.assertEqual;
import static de.hechler.patrick.zeugs.check.Assert.assertEquals;
import static de.hechler.patrick.zeugs.check.Assert.assertNotEquals;
import static de.hechler.patrick.zeugs.check.Assert.assertNotNull;
import static de.hechler.patrick.zeugs.check.Assert.assertNull;

import java.util.Random;

import de.hechler.patrick.puzzles.mathpuzzles.enums.Operator;
import de.hechler.patrick.puzzles.mathpuzzles.objects.MultiWayGleichungsGenerator.RechnungswegRechnung;
import de.hechler.patrick.zeugs.check.anotations.Check;
import de.hechler.patrick.zeugs.check.anotations.CheckClass;

@CheckClass
public class MultiWayGleichungsGeneratorChecker extends CheckedGleichungsGeneratorChecker {
	
	@Override
	protected void start() {
		gg = new MultiWayGleichungsGenerator(new Random(10L));
	}
	
	@Check
	private Gleichung check15Num() {
		cnt = cnt(15);
		return gg.generiere(15);
	}
	
	@Check
	private Gleichung check14Num() {
		cnt = cnt(14);
		return gg.generiere(14);
	}
	
	@Check
	private Gleichung check13Num() {
		cnt = cnt(13);
		return gg.generiere(13);
	}
	
	@Check
	private Gleichung check12Num() {
		cnt = cnt(12);
		return gg.generiere(12);
	}
	
	@Check
	private Gleichung check11Num() {
		cnt = cnt(11);
		return gg.generiere(11);
	}
	
	@Check
	private String check11Num100Mal() {
		long time = 0L,
			min = Long.MAX_VALUE,
			max = 0L;
		for (int i = 0; i < 100; i ++ ) {
			long start = System.currentTimeMillis();
			Gleichung gl = gg.generiere(11);
			long end = System.currentTimeMillis();
			long thisTime = end - start;
			System.out.println(i + ": needed time: " + thisTime);
			assertNotNull(gl);
			assertEqual(count(gl), 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L);
			time += thisTime;
			if (thisTime < min) {
				min = thisTime;
			} else if (thisTime > max) {
				max = thisTime;
			}
			System.out.println("validated");
		}
		return "~" + (time / 100L) + "ms total: " + time + "ms min: " + min + "ms max: " + max + "ms";
	}
	
	@Check
	private void simpleCheck() {// 6 * 2 + 2 = 14
		Rechnung[] gen = ((MultiWayGleichungsGenerator) gg).generiereAlle(0, 0, 2);
		assertNull(gen.length);
	}
	
	@Check
	private void simpleCheck2() {// 3 * 7 * 6 - 7 - 8 * 8 + 9 is invalid
		Rechnung[] gen = ((MultiWayGleichungsGenerator) gg).generiereAlle(3, 7, 6, 7, 8, 8, 9);
		RechnungswegRechnung invalid = new RechnungswegRechnung(new int[] {3, 7, 6, 7, 8, 8, 9 }, new Operator[] {Operator.mal, Operator.mal, Operator.minus, Operator.minus, Operator.mal, Operator.plus }, 64L);
		for (Rechnung rech : gen) {
			assertNotEquals(invalid, rech);
		}
		for (Rechnung rech : gen) {
			long calc = rech.calc();
			for (Rechnung r : rech) {
				try {
					long res = r.calc();
					if (res == calc) {
						assertEquals(rech, r);
					}
				} catch (ArithmeticException e) {}
			}
		}
	}
	
	@Check
	private void simpleCheck3() {// 3,0,3
		Rechnung[] gen = ((MultiWayGleichungsGenerator) gg).generiereAlle(3, 0, 3);
		for (Rechnung rech : gen) {
			long calc = rech.calc();
			for (Rechnung r : rech) {
				try {
					long res = r.calc();
					if (res == calc) {
						assertEquals(rech, r);
					}
				} catch (ArithmeticException e) {}
			}
		}
	}
	
	private long cnt(int len) {
		long cnt = 1L;
		for (int i = 0; i < len; i ++ ) {
			cnt *= 4L;
		}
		return cnt;
	}
	
}
