package marina.matrix;

import static org.junit.Assert.*;

import java.util.Arrays;

import marina.quantification.MetricName;

import org.junit.Before;
import org.junit.Test;

public class RankedAbundanceMatrixTest {
	private RankedAbundanceMatrix matrix;

	@Before
	public void setUp() throws Exception {
		double[][] data = new double[][]{
				{0.55, 0.55, 1.19, 0.15, 0.04,  0.01, 0.035, 0.001,   49.0, 39.0},  
				{0.44, 0.43, 0.93, 0.08, 0.01, -0.01, -0.00, 0.003,   18.0, 23.0},  
				{0.42, 0.42, 0.91, 0.11, 0.02, -0.01, -0.01, 0.002,   32.0, 43.0},  
				{0.46, 0.45, 0.98, 0.50, 0.33, -0.02, -0.01, 5.1E-4, 609.0, 715.0},  
				{0.46, 0.46, 0.98, 0.33, 0.18, -0.01, -0.00, 5.0E-4, 266.0, 311.0}};
		this.matrix = new RankedAbundanceMatrix(data);
		this.matrix.setColumns(MetricName.values());
	}

	/**
	 * Test the matrix references the correct-sized column.
	 * */
	@Test
	public void testReferencesValidColumn() {
		assertEquals(this.matrix.getWidth(), 10, 0);
	}

	/**
	 * Assert that matrix-sorting feature sorts the provided column.
	 * */
	@Test
	public void testColumnIsSorted() {
		double[] column = new double[]{0.332, 0.112, -0.782, 1.192};
		double[] values = this.matrix.sort(column);
		boolean isSorted = false;
		for (int i=1; i < values.length; i++) {
			if (values[i-1] <= values[i]) {
				isSorted = true;
			}
		}
		assertTrue(isSorted);
	}

	/**
	 * Assert that without sorting or not, column sums are the same.
	 * */
	@Test
	public void testSortingDoesNotChangeSum() {
		double sumSorted = Matrix.summation(this.matrix.sort(this.matrix.getColumn(0)));
		double sumUnsorted = Matrix.summation(this.matrix.getColumn(0));
		assertTrue(sumSorted == sumUnsorted);
	}

	/**
	 * Assert that sorting yields the same array size as the original array.
	 * */
	@Test
	public void testSortingYieldsSameColumnSize() {
		double[] column = new double[]{0.332, 0.112, -0.782, 1.192};
		assertTrue(this.matrix.sort(column).length == column.length);
	}

	/**
	 * The minimum rank must always be one (1).
	 * */
	@Test
	public void testRankMinIsOne() {
		double[] column = new double[]{0.332, 0.112, -0.782, 1.192};
		int[] ranks = this.matrix.rank(column);
		Arrays.sort(ranks); // sorting to make min-identification easier.
		assertTrue(ranks[0] == 1);
	}
	
	/**
	 * The maximum rank is that of the number of rows in the column.
	 * */
	@Test
	public void testRankMaxIsColumnLength() {
		double[] column = new double[]{0.332, 0.112, -0.782, 1.192};
		int[] ranks = this.matrix.rank(column);
		Arrays.sort(ranks); // sorting to make min-identification easier.
		assertTrue(ranks[ranks.length-1] == ranks.length);
	}
	
	/**
	 * Test that ranks from a specified column yield pre-derived ranks.
	 * */
	@Test
	public void testColumnRankEquality() {
		double[] column = new double[]{1.19, 0.93, 0.91, 0.98, 0.98};
		int[] ranked = this.matrix.rank(column);
		assertArrayEquals(ranked, new int[]{1, 4, 5, 3, 3});
	}
	
}
