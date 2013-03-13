package marina.matrix;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixTest {

	/**
	 * Test that vector summation works when array-values are all even
	 * */
	@Test
	public void testSummationEvenNumbers() {
		double[] vals = new double[]{2, 4, 6, 8, 10}; // known sum is 30
		assertTrue(Matrix.summation(vals) == 30);
	}

	/**
	 * Test that vector summation works when array-values are all odd
	 * */
	@Test
	public void testSummationOddNumbers() {
		double[] vals = new double[]{1, 3, 7, 9, 5}; // known sum is 25
		assertTrue(Matrix.summation(vals) == 25);
	}

	/**
	 * Test vector summation works when there is only one odd value.
	 * */
	@Test
	public void testSummationOneOddNumber() {
		double[] vals = new double[]{121}; // known sum is 121
		assertTrue(Matrix.summation(vals) == 121);
	}

	/**
	 * Test vector summation works when there is only one even value.
	 * */
	@Test
	public void testSummationOneEvenNumber() {
		double[] vals = new double[]{628}; // known sum is 628
		assertTrue(Matrix.summation(vals) == 628);
	}

	/**
	 * Test vector summation when array-values have mixed-signage.
	 * */
	@Test
	public void testSummationMixedSign() {
		double[] vals = new double[]{-2, -10, 7, 4}; // known sum is -1
		assertTrue(Matrix.summation(vals) == -1);
	}

	/**
	 * Test that vector summation works when array-values are all odd
	 * */
	@Test
	public void testSummationNoNumbers() {
		double[] vals = new double[]{}; // no numbers
		assertTrue(Matrix.summation(vals) == 0);
	}

	// TODO from here
	/**
	 * Test that vector minimum works when array-values are all even
	 * */
	@Test
	public void testMinimumEvenNumbers() {
		double[] vals = new double[]{2, 4, 6, 8, 10}; // known min is 2
		assertTrue(Matrix.minimum(vals) == 2);
	}

	/**
	 * Test that vector minimum works when array-values are all odd
	 * */
	@Test
	public void testMinimumOddNumbers() {
		double[] vals = new double[]{1, 3, 7, 9, 5}; // known min is 1
		assertTrue(Matrix.minimum(vals) == 1);
	}

	/**
	 * Test vector minimum works when there is only one odd value.
	 * */
	@Test
	public void testMinimumOneOddNumber() {
		double[] vals = new double[]{7};
		assertTrue(Matrix.minimum(vals) == 7);
	}

	/**
	 * Test vector summation works when there is only one even value.
	 * */
	@Test
	public void testMinimumOneEvenNumber() {
		double[] vals = new double[]{16};
		assertTrue(Matrix.minimum(vals) == 16);
	}

	/**
	 * Test vector minimum when array-values have mixed-signage.
	 * */
	@Test
	public void testMinimumMixedSign() {
		double[] vals = new double[]{-7, -22, 16, 3}; // minimum is -22
		assertTrue(Matrix.minimum(vals) == -22);
	}
}
