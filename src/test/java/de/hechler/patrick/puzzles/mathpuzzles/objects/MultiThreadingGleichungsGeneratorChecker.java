package de.hechler.patrick.puzzles.mathpuzzles.objects;

import static de.hechler.patrick.zeugs.check.Assert.assertEqual;
import static de.hechler.patrick.zeugs.check.Assert.assertNotNull;

import de.hechler.patrick.zeugs.check.anotations.Check;
import de.hechler.patrick.zeugs.check.anotations.CheckClass;

@CheckClass(singleThread = true)
public class MultiThreadingGleichungsGeneratorChecker extends CheckedGleichungsGeneratorChecker {
	
	private static final boolean DISABLE_SLOW      = true;
	private static final boolean DISABLE_VERY_SLOW = true;
	
	protected void start() {
		gg = new MultiThreadingGleichungsGenerator(new SimpleGleichungsGenerator(10L));
	}
	
	@Check(disabled = DISABLE_VERY_SLOW)
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
	
	@Check(disabled = DISABLE_SLOW)
	private Gleichung check10Num() {
		cnt = 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L;
		return gg.generiere(10);
	}
	
	@Check(disabled = DISABLE_SLOW)
	private Gleichung check11Num() {
		cnt = 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L;
		return gg.generiere(11);
	}
	
	@Check(disabled = DISABLE_SLOW)
	private Gleichung check12Num() {
		cnt = 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L;
		return gg.generiere(12);
	}
	
	@Check(disabled = DISABLE_SLOW)
	private Gleichung check13Num() {
		cnt = 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L;
		return gg.generiere(13);
	}
	
	@Check(disabled = DISABLE_VERY_SLOW)
	private Gleichung check14Num() {
		cnt = 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L;
		return gg.generiere(14);
	}
	
	@Check(disabled = DISABLE_VERY_SLOW)
	private Gleichung check15Num() {
		cnt = 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L;
		return gg.generiere(15);
	}
	
	@Check
	private Gleichung[] checkM10() {
		Gleichung[] gls = ((MultiThreadingGleichungsGenerator) gg).generiereMehrere(10);
		System.out.println("gg(10): " + gls.length);
		return gls;
	}
	
	@Check
	private Gleichung[] checkM9() {
		Gleichung[] gls = ((MultiThreadingGleichungsGenerator) gg).generiereMehrere(9);
		System.out.println("gg(9): " + gls.length);
		return gls;
	}
	
	@Check
	private Gleichung[] checkM8() {
		Gleichung[] gls = ((MultiThreadingGleichungsGenerator) gg).generiereMehrere(8);
		System.out.println("gg(8): " + gls.length);
		return gls;
	}
	
	@Check
	private Gleichung[] checkM7() {
		Gleichung[] gls = ((MultiThreadingGleichungsGenerator) gg).generiereMehrere(7);
		System.out.println("gg(7): " + gls.length);
		return gls;
	}
	
	@Check
	private Gleichung[] checkM6() {
		Gleichung[] gls = ((MultiThreadingGleichungsGenerator) gg).generiereMehrere(6);
		System.out.println("gg(6): " + gls.length);
		return gls;
	}
	
	@Check
	private Gleichung[] checkM5() {
		Gleichung[] gls = ((MultiThreadingGleichungsGenerator) gg).generiereMehrere(5);
		System.out.println("gg(5): " + gls.length);
		return gls;
	}
	
	@Check
	private Gleichung[] checkM4() {
		Gleichung[] gls = ((MultiThreadingGleichungsGenerator) gg).generiereMehrere(4);
		System.out.println("gg(4): " + gls.length);
		return gls;
	}
	
	@Check
	private Gleichung[] checkM3() {
		Gleichung[] gls = ((MultiThreadingGleichungsGenerator) gg).generiereMehrere(3);
		System.out.println("gg(3): " + gls.length);
		return gls;
	}
	
	@Check
	private Gleichung[] checkM2() {
		Gleichung[] gls = ((MultiThreadingGleichungsGenerator) gg).generiereMehrere(2);
		System.out.println("gg(2): " + gls.length);
		return gls;
	}
	
	@Check
	private Gleichung[] checkM1() {
		Gleichung[] gls = ((MultiThreadingGleichungsGenerator) gg).generiereMehrere(1);
		System.out.println("gg(1): " + gls.length);
		return gls;
	}
	
}
