package de.hechler.patrick.puzzles.mathpuzzles;

import de.hechler.patrick.zeugs.check.objects.BigCheckResult;
import de.hechler.patrick.zeugs.check.objects.BigChecker;

public class Test {
	
	public void testname() throws Exception {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		BigCheckResult bcr = BigChecker.tryCheckAll(true, Test.class.getPackage(), Test.class.getClassLoader());
		bcr.detailedPrint();
		if (bcr.wentUnexpected()) {
			bcr.detailedPrintUnexpected(System.out);
			throw new Error(bcr.toString());
		}
	}
	
}
