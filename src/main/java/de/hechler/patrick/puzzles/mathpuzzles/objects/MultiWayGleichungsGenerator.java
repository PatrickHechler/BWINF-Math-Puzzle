package de.hechler.patrick.puzzles.mathpuzzles.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Map;

import de.hechler.patrick.puzzles.mathpuzzles.enums.Operator;
import de.hechler.patrick.puzzles.mathpuzzles.interfaces.GleichungsGenerator;

public class MultiWayGleichungsGenerator implements GleichungsGenerator {
	
	private final Random rnd;
	
	public MultiWayGleichungsGenerator() {
		this(new Random());
	}
	
	public MultiWayGleichungsGenerator(Random random) {
		this.rnd = random;
	}
	
	@Override
	public Gleichung generiere(int zahlen) {
		Gleichung[] gls = generiereMehrere(zahlen);
		return gls[0];
	}
	
	public Gleichung[] generiereMehrere(int zahlen) {
		while (true) {
			int[] nums = new int[zahlen];
			for (int i = 0; i < nums.length; i ++ ) {
				nums[i] = rnd.nextInt(10);
			}
			Gleichung[] result = generiereAlle(nums);
			if (result.length > 0) {
				return result;
			}
		}
	}
	
	public Gleichung[] generiereAlle(int... nums) {
		Map <Rechnungsweg, Boolean> wege = new HashMap <>();
		wege.put(new Rechnungsweg(nums[0], 0L, new Operator[nums.length - 1]), Boolean.TRUE);
		for (int i = 1; i < nums.length; i ++ ) {
			final int finali = i;
			Map <Rechnungsweg, Boolean> neueWege = new HashMap <>();
			wege.forEach((weg, bool) -> {
				Operator[] ops;
				long pw, sw;
				Rechnungsweg rw;
				try {
					pw = nums[finali];
					sw = Math.addExact(weg.punktWert, weg.strichWert);
					ops = weg.ops;
					ops[finali - 1] = Operator.plus;
					rw = new Rechnungsweg(pw, sw, ops);
					merge(neueWege, bool, rw);
				} catch (ArithmeticException e) {}
				try {
					pw = -nums[finali];
					sw = Math.addExact(weg.punktWert, weg.strichWert);
					ops = weg.ops.clone();
					ops[finali - 1] = Operator.minus;
					rw = new Rechnungsweg(pw, sw, ops);
					merge(neueWege, bool, rw);
				} catch (ArithmeticException e) {}
				try {
					pw = Math.multiplyExact(weg.punktWert, nums[finali]);
					sw = weg.strichWert;
					ops = weg.ops.clone();
					ops[finali - 1] = Operator.mal;
					rw = new Rechnungsweg(pw, sw, ops);
					merge(neueWege, bool, rw);
				} catch (ArithmeticException e) {}
				try {
					pw = weg.punktWert / nums[finali];
					sw = weg.strichWert;
					if (weg.punktWert == Math.multiplyExact(pw, nums[finali])) {
						ops = weg.ops.clone();
						ops[finali - 1] = Operator.geteilt;
						rw = new Rechnungsweg(pw, sw, ops);
						merge(neueWege, bool, rw);
					}
				} catch (ArithmeticException e) {}
			});
			wege = neueWege;
//			System.out.println("[MultiWayLog]: wege: " + wege);
		}
		Map <Rechnungsweg, Boolean> neueWege = new HashMap <>();
//		System.out.println("[MultiWayLog]: start last nums: " + Arrays.toString(nums));
//		System.out.println("[MultiWayLog]:            ways: " + wege);
		wege.forEach((weg, bool) -> {
			long res = weg.strichWert + weg.punktWert;
			if (res < 0L) {
				return;
			}
			Rechnungsweg add = new Rechnungsweg(0L, res, weg.ops);
			merge(neueWege, bool, add);
		});
		List <Gleichung> resList = new ArrayList <>();
		neueWege.forEach((weg, bool) -> {
			if (bool) {
				RechnungswegRechnung rech = new RechnungswegRechnung(nums, weg.ops, weg.strichWert);
				Gleichung gl = new Gleichung(rech, weg.strichWert);
				resList.add(gl);
			}
		});
		Gleichung[] result = resList.toArray(new Gleichung[resList.size()]);
//		System.out.println("[MultiWayLog]: finish result: " + Arrays.toString(result));
		return result;
	}
	
	private void merge(Map <Rechnungsweg, Boolean> neueWege, Boolean bool, Rechnungsweg rw) {
		neueWege.merge(rw, bool, (k, v) -> {
//			System.out.println("[MultiWayLog]: merge: invalid rw=" + rw);
			return Boolean.FALSE;
		});
	}
	
	public static class Rechnungsweg {
		
		public final long       punktWert;
		public final long       strichWert;
		public final Operator[] ops;
		
		public Rechnungsweg(long punktWert, long strichWert, Operator[] ops) {
			this.punktWert = punktWert;
			this.strichWert = strichWert;
			this.ops = ops;
		}
		
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (punktWert ^ (punktWert >>> 32));
			result = prime * result + (int) (strichWert ^ (strichWert >>> 32));
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if ( ! (obj instanceof Rechnungsweg)) return false;
			Rechnungsweg other = (Rechnungsweg) obj;
			if (punktWert != other.punktWert) return false;
			if (strichWert != other.strichWert) return false;
			return true;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Rechnungsweg [punktWert=");
			builder.append(punktWert);
			builder.append(", strichWert=");
			builder.append(strichWert);
			builder.append(", ops=");
			builder.append(Arrays.toString(ops));
			builder.append("]");
			return builder.toString();
		}
		
	}
	
	public static class RechnungswegRechnung extends Rechnung {
		
		private final int[]      nums;
		private final Operator[] ops;
		private final long       res;
		
		public RechnungswegRechnung(int[] nums, Operator[] ops, long res) {
			this.nums = nums;
			this.ops = ops;
			this.res = res;
		}
		
		@Override
		public Iterator <Rechnung> iterator() {
			String[] strs = toHiddenString().split("\\s*\\?\\s*");
			RechnungStringIterator iter = new RechnungStringIterator(strs);
			return new Iterator <Rechnung>() {
				
				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}
				
				@Override
				public Rechnung next() {
					String next = iter.next();
					Rechnung result = SimpleGleichungsGenerator.generateRechnungFromString(next);
					return result;
				}
				
			};
		}
		
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder(nums.length * 4 - 3);
			result.append((char) (nums[0] + '0'));
			for (int i = 1; i < nums.length; i ++ ) {
				result.append(' ');
				result.append(ops[i - 1]);
				result.append(' ');
				result.append((char) (nums[i] + '0'));
			}
			return result.toString();
		}
		
		public String toSmallString() {
			StringBuilder result = new StringBuilder(nums.length * 2 - 1);
			result.append((char) (nums[0] + '0'));
			for (int i = 1; i < nums.length; i ++ ) {
				result.append(ops[i - 1]);
				result.append((char) (nums[i] + '0'));
			}
			return result.toString();
		}
		
		@Override
		public String toHiddenString() {
			StringBuilder result = new StringBuilder(nums.length * 4 - 3);
			result.append((char) (nums[0] + '0'));
			for (int i = 1; i < nums.length; i ++ ) {
				result.append(" ? ");
				result.append((char) (nums[i] + '0'));
			}
			return result.toString();
		}
		
		@Override
		protected long calc() throws ArithmeticException {
			return res;
		}
		
		@Override
		public int hashCode() {
			return (int) (res | res >> 32);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if ( ! (obj instanceof RechnungswegRechnung)) {
				String myStr = toSmallString();
				String otherStr = obj.toString().replaceAll("\\s+", "");
				return myStr.equals(otherStr);
			}
			RechnungswegRechnung other = (RechnungswegRechnung) obj;
			if ( !Arrays.equals(nums, other.nums)) return false;
			if ( !Arrays.equals(ops, other.ops)) return false;
			if (res != other.res) return false;
			return true;
		}
		
	}
	
}
