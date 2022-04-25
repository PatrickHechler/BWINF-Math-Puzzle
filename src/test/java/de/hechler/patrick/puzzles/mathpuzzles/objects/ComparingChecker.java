package de.hechler.patrick.puzzles.mathpuzzles.objects;

import static de.hechler.patrick.zeugs.check.Assert.assertArrayEquals;

import java.lang.reflect.Method;
import java.util.Arrays;

import de.hechler.patrick.zeugs.check.anotations.Check;
import de.hechler.patrick.zeugs.check.anotations.MethodParam;
import de.hechler.patrick.zeugs.check.anotations.Start;

public class ComparingChecker {
	
	private MultiWayGleichungsGenerator       mwgg;
	private MultiThreadingGleichungsGenerator mtgg;
	
	@Start
	private void init(@MethodParam Method met) {
		System.out.println("start check of class: " + getClass().getName());
		System.out.println("    method: " + met);
		mwgg = new MultiWayGleichungsGenerator();
		mtgg = new MultiThreadingGleichungsGenerator(new SimpleGleichungsGenerator(10L));
	}
	
	@Check
	private void compareCheck() {
		for (int i = 0; i < 100; i ++ ) {
			Gleichung[] mtgls = mtgg.generiereMehrere(5);
			int[] nums = extractNums(mtgls);
			Gleichung[] mwgls = mwgg.generiereAlle(nums);
			Arrays.sort(mtgls, (a, b) -> Long.compare(a.ergebnis, b.ergebnis));
			Arrays.sort(mwgls, (a, b) -> Long.compare(a.ergebnis, b.ergebnis));
			assertArrayEquals(mtgls, mwgls);
		}
		for (int i = 0; i < 10; i ++ ) {
			Gleichung[] mtgls = mtgg.generiereMehrere(10);
			int[] nums = extractNums(mtgls);
			Gleichung[] mwgls = mwgg.generiereAlle(nums);
			Arrays.sort(mtgls, (a, b) -> Long.compare(a.ergebnis, b.ergebnis));
			Arrays.sort(mwgls, (a, b) -> Long.compare(a.ergebnis, b.ergebnis));
			assertArrayEquals(mtgls, mwgls);
		}
		Gleichung[] mtgls = mtgg.generiereMehrere(15);
		int[] nums = extractNums(mtgls);
		Gleichung[] mwgls = mwgg.generiereAlle(nums);
		Arrays.sort(mtgls, (a, b) -> Long.compare(a.ergebnis, b.ergebnis));
		Arrays.sort(mwgls, (a, b) -> Long.compare(a.ergebnis, b.ergebnis));
		assertArrayEquals(mtgls, mwgls);
	}
	
	private int[] extractNums(Gleichung[] mtgls) {
		String[] strs = mtgls[0].rechnung.toHiddenString().split("\\s*\\?\\s*");
		int[] nums = new int[strs.length];
		for (int i = 0; i < nums.length; i ++ ) {
			nums[i] = Integer.parseInt(strs[i]);
		}
		return nums;
	}
	
}
