package marina.quantification;

import static org.junit.Assert.*;

import org.junit.Test;

public class StatisticTest {
	
	/**
	 * Given an even integer, test the correct factorial is produced.
	 * */
	@Test
	public void testEvenFactorial() {
		assertEquals(Statistic.factorial(6), 720);
	}
	
	/**
	 * Given an odd integer, test the correct factorial is produced.
	 * */
	@Test
	public void testOddFactorial() {
		assertEquals(Statistic.factorial(5), 120);
	}
	
	/**
	 * Test that a zero factorial (0!) equals 1.
	 * */
	@Test
	public void testZeroFactorial() {
		assertEquals(Statistic.factorial(0), 1);
	}
	
	/**
	 * Test that a factorial of 1 (1!) equals 1.
	 * */
	@Test
	public void testOneFactorial() {
		assertEquals(Statistic.factorial(1), 1);
	}
	
	/**
	 * Test that the combinatorial function works given an odd
	 * size (n)
	 * */
	@Test
	public void testCombinatorialOddN() {
		assertEquals(Statistic.combinatorial(17, 3), 680);
	}
	
	/**
	 * Test that the combinatorial function works given an even
	 * size (n)
	 * */
	@Test
	public void testCombinatorialEvenN() {
		assertEquals(Statistic.combinatorial(16, 3), 560);
	}
	
	/**
	 * Test that the combinatorial function works given size of 0
	 * */
	@Test
	public void testCombinatorialSizeZero() {
		assertEquals(Statistic.combinatorial(0, 3), 0);
	}
	
	/**
	 * Test that the combinatorial function works given n-choose-0
	 * */
	@Test
	public void testCombinatorialChooseZero() {
		assertEquals(Statistic.combinatorial(5, 0), 1);
	}
}
