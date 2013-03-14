package marina.quantification;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatisticTest {
	
	/**
	 * Given an even integer, test the correct factorial is produced.
	 * */
	@Test
	public void testEvenFactorial() {
		assertEquals(Statistic.factorial(6), 720, 1);
	}
	
	/**
	 * Given an odd integer, test the correct factorial is produced.
	 * */
	@Test
	public void testOddFactorial() {
		assertEquals(Statistic.factorial(5), 120, 1);
	}
	
	/**
	 * Test that a zero factorial (0!) equals 1.
	 * */
	@Test
	public void testZeroFactorial() {
		assertEquals(Statistic.factorial(0), 1, 1);
	}
	
	/**
	 * Test that a factorial of 1 (1!) equals 1.
	 * */
	@Test
	public void testOneFactorial() {
		assertEquals(Statistic.factorial(1), 1, 1);
	}
	
	/**
	 * Test that combinatorial derivation works with small n and r sizes.
	 * */
	@Test
	public void testSmallSizeSmallChooseCombinatorial() {
		assertEquals(Statistic.combinatorial(6, 2), 15, 1);
	}
	
	/**
	 * Test that combinatorial derivation works with large n.
	 * */
	@Test
	public void testLargeSizeSmallChooseCombinatorial() {
		assertEquals(Statistic.combinatorial(6, 2), 15, 1);
	}
	
	
}
