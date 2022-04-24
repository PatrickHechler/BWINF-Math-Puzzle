package de.hechler.patrick.puzzles.mathpuzzles.objects;

import static de.hechler.patrick.zeugs.check.Assert.assertEqual;
import static de.hechler.patrick.zeugs.check.Assert.assertEquals;
import static de.hechler.patrick.zeugs.check.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.Iterator;

import de.hechler.patrick.puzzles.mathpuzzles.interfaces.GleichungsGenerator;
import de.hechler.patrick.zeugs.check.anotations.Check;
import de.hechler.patrick.zeugs.check.anotations.CheckClass;
import de.hechler.patrick.zeugs.check.anotations.End;
import de.hechler.patrick.zeugs.check.anotations.MethodParam;
import de.hechler.patrick.zeugs.check.anotations.ResultParam;
import de.hechler.patrick.zeugs.check.anotations.Start;
import de.hechler.patrick.zeugs.check.objects.Result;

@CheckClass(disabled = true)
public class CheckedGleichungsGeneratorChecker {
	
	protected GleichungsGenerator gg;
	protected long                cnt;
	
	@Start
	private void init(@MethodParam Method met) {
		System.out.println("start check of class: " + getClass().getName());
		System.out.println("    method: " + met);
	}
	
	@Start
	protected void start() {
		gg = new CheckedGleichungsGenerator(new SimpleGleichungsGenerator(10L));
	}
	
	@End
	private void end(@ResultParam Result res) {
		if (res.goodResult()) {
			Object obj = res.getResult();
			if (obj instanceof Gleichung) {
				Gleichung gl = (Gleichung) obj;
				assertNotNull(gl);
				assertEqual(count(gl), cnt);
			}
		}
		gg = null;
	}
	
	@Check
	private Gleichung check1Num() {
		cnt = 1L;
		return gg.generiere(1);
	}
	
	@Check
	private Gleichung check2Num() {
		cnt = 4L;
		return gg.generiere(2);
	}
	
	@Check
	private Gleichung check3Num() {
		cnt = 4L * 4L;
		return gg.generiere(3);
	}
	
	@Check
	private Gleichung check4Num() {
		cnt = 4L * 4L * 4L;
		return gg.generiere(4);
	}
	
	@Check
	private Gleichung check5Num() {
		cnt = 4L * 4L * 4L * 4L;
		return gg.generiere(5);
	}
	
	@Check
	private Gleichung check6Num() {
		cnt = 4L * 4L * 4L * 4L * 4L;
		return gg.generiere(6);
	}
	
	@Check
	private Gleichung check7Num() {
		cnt = 4L * 4L * 4L * 4L * 4L * 4L;
		return gg.generiere(7);
	}
	
	@Check
	private Gleichung check8Num() {
		cnt = 4L * 4L * 4L * 4L * 4L * 4L * 4L;
		return gg.generiere(8);
	}
	
	@Check
	private Gleichung check9Num() {
		cnt = 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L;
		return gg.generiere(9);
	}
	
	@Check
	private Gleichung check10Num() {
		cnt = 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L;
		return gg.generiere(10);
	}
	
	@Check
	private Gleichung check11Num() {
		cnt = 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L * 4L;
		return gg.generiere(11);
	}
	
	protected long count(Gleichung gl) {
		long cnt = 0L;
		for (Iterator <Rechnung> iter = gl.rechnung.iterator(); iter.hasNext();) {
			Rechnung rech = iter.next();
			cnt ++ ;
			try {
				long calc = rech.calc();
				if (gl.ergebnis == calc) {
					assertEquals(gl.rechnung, rech);
				}
			} catch (ArithmeticException e) {}
			// System.out.println("cnt=" + cnt + " next='" + n + '\'');
		}
		return cnt;
	}
	
}
