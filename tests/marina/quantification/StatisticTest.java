package marina.quantification;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import quantification.Statistic;

public class StatisticTest {
	
	/**
	 * Given an even integer, test the correct factorial is produced.
	 * */
	@Test
	public void testEvenCombinatorial() {
		assertEquals(Statistic.combinatorial(6, 3).intValue(), 20, 0);
	}
	
	/**
	 * Given an odd integer, test the correct factorial is produced.
	 * */
	@Test
	public void testOddCombinatorial() {
		assertEquals(Statistic.combinatorial(30, 18).intValue(), 86493225, 0);
	}
	
	/**
	 * Test that a zero factorial (0!) equals 1.
	 * */
	@Test
	public void testZeroCombinatorial() {
		assertEquals(Statistic.combinatorial(0, 0).intValue(), 1, 0);
	}
	
	/**
	 * Test that a factorial of 1 (1!) equals 1.
	 * */
	@Test
	public void testOneCombinatorial() {
		assertEquals(Statistic.combinatorial(1, 1).intValue(), 1, 0);
	}
		
	/**
	 * Test that vector summation works when array-values are all even
	 * */
	@Test
	public void testSummationEvenNumbers() {
		double[] vals = new double[]{2, 4, 6, 8, 10}; // known sum is 30
		assertTrue(Statistic.summation(vals) == 30);
	}

	/**
	 * Test that vector summation works when array-values are all odd
	 * */
	@Test
	public void testSummationOddNumbers() {
		double[] vals = new double[]{1, 3, 7, 9, 5}; // known sum is 25
		assertTrue(Statistic.summation(vals) == 25);
	}

	/**
	 * Test vector summation works when there is only one odd value.
	 * */
	@Test
	public void testSummationOneOddNumber() {
		double[] vals = new double[]{121}; // known sum is 121
		assertTrue(Statistic.summation(vals) == 121);
	}

	/**
	 * Test vector summation works when there is only one even value.
	 * */
	@Test
	public void testSummationOneEvenNumber() {
		double[] vals = new double[]{628}; // known sum is 628
		assertTrue(Statistic.summation(vals) == 628);
	}

	/**
	 * Test vector summation when array-values have mixed-signage.
	 * */
	@Test
	public void testSummationMixedSign() {
		double[] vals = new double[]{-2, -10, 7, 4}; // known sum is -1
		assertTrue(Statistic.summation(vals) == -1);
	}

	/**
	 * Test that vector summation works when array-values are all odd
	 * */
	@Test
	public void testSummationNoNumbers() {
		double[] vals = new double[]{}; // no numbers
		assertTrue(Statistic.summation(vals) == 0);
	}

	/**
	 * Test that vector minimum works when array-values are all even
	 * */
	@Test
	public void testMinimumEvenNumbers() {
		double[] vals = new double[]{2, 4, 6, 8, 10}; // known min is 2
		assertTrue(Statistic.minimum(vals) == 2);
	}

	/**
	 * Test that vector minimum works when array-values are all odd
	 * */
	@Test
	public void testMinimumOddNumbers() {
		double[] vals = new double[]{1, 3, 7, 9, 5}; // known min is 1
		assertTrue(Statistic.minimum(vals) == 1);
	}

	/**
	 * Test vector minimum works when there is only one odd value.
	 * */
	@Test
	public void testMinimumOneOddNumber() {
		double[] vals = new double[]{7};
		assertTrue(Statistic.minimum(vals) == 7);
	}

	/**
	 * Test vector summation works when there is only one even value.
	 * */
	@Test
	public void testMinimumOneEvenNumber() {
		double[] vals = new double[]{16};
		assertTrue(Statistic.minimum(vals) == 16);
	}

	/**
	 * Test vector maximum when array-values have mixed signs.
	 * */
	@Test
	public void testMinimumMixedSign() {
		double[] vals = new double[]{-7, -22, 16, 3}; // minimum is -22
		assertTrue(Statistic.minimum(vals) == -22);
	}
		
	/**
	 * Test that vector maximum works when array-values are all even.
	 * */
	@Test
	public void testMaximumEvenNumbers() {
		double[] vals = new double[]{2, 4, 6, 8, 10}; // known max is 10
		assertTrue(Statistic.maximum(vals) == 10);
	}

	/**
	 * Test that vector maximum works when array-values are all odd.
	 * */
	@Test
	public void testMaximumOddNumbers() {
		double[] vals = new double[]{1, 3, 7, 9, 5}; // known max is 9
		assertTrue(Statistic.maximum(vals) == 9);
	}

	/**
	 * Test vector maximum works when there is only one odd value.
	 * */
	@Test
	public void testMaximumOneOddNumber() {
		double[] vals = new double[]{7};
		assertTrue(Statistic.maximum(vals) == 7);
	}

	/**
	 * Test vector maximum works when there is only one even value.
	 * */
	@Test
	public void testMaximumOneEvenNumber() {
		double[] vals = new double[]{16};
		assertTrue(Statistic.maximum(vals) == 16);
	}

	/**
	 * Test vector maximum when array-values have mixed signs.
	 * */
	@Test
	public void testMaximumMixedSign() {
		double[] vals = new double[]{-7, -22, 16, 3}; // maximum is 16
		assertTrue(Statistic.maximum(vals) == 16);
	}
	
	/**
	 * Assert that sorting an array yields the correct sorting order.
	 * */
	@Test
	public void testSortingMixedSignNumbers() {
		double[] vals = new double[]{-7, -22, 16, 3};
		double[] sorted = Statistic.sort(vals);
		assertArrayEquals(sorted, new double[]{-22, -7, 3, 16}, 0);
	}
	
	/**
	 * Assert sorting an array with one number yields the original array.
	 * */
	@Test
	public void testSortingOneNumber() {
		double[] vals = new double[]{12};
		double[] sorted = Statistic.sort(vals);
		assertArrayEquals(sorted, new double[]{12}, 0);
	}
	
	/**
	 * Assert sorting an array of the same values yields the original array.
	 * */
	@Test
	public void testSortingSameNumbers() {
		double[] vals = new double[]{2, 2, 2, 2, 2, 2};
		double[] sorted = Statistic.sort(vals);
		assertArrayEquals(sorted, vals, 0);
	}
	
	/**
	 * Assert list-ranking given a mixed-sign array.
	 * */
	@Test
	public void testRankGivenMixedSign() {
		double[] vals = new double[]{-7, -22, 16, 3};
		int[] ranked = Statistic.rank(vals);
		assertArrayEquals(ranked, new int[]{3, 4 , 1 , 2});
		
	}
	
	/**
	 * Assert list-ranking given an array with the same value.
	 * */
	@Test
	public void testRankGivenSameValues() {
		double[] vals = new double[]{2,2,2,2,2};
		int[] ranked = Statistic.rank(vals);
		assertArrayEquals(ranked, new int[]{5,5,5,5,5});
	}
	
	/**
	 * Assert that list-ranking works even when all values are positive.
	 * */
	@Test
	public void testRanksGivenPositiveValues() {
		double[] vals = new double[]{2, 1, 6, 10, 1}; // numbers 1 tie.
		int[] ranked = Statistic.rank(vals);
		assertArrayEquals(ranked, new int[]{3, 5, 2, 1, 5});
	}
	
	/**
	 * Assert that list-ranked length equals list-sorted length.
	 * */
	@Test
	public void testRankLengthEqualsSortedLength() {
		double[] vals = new double[]{2, 1, 6, 10, 1}; // numbers 1 tie.
		int[] ranked = Statistic.rank(vals);
		double[] sorted = Statistic.sort(vals);
		assertTrue(ranked.length == sorted.length);
	}
}
