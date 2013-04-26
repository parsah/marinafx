package marina.matrix;

import static org.junit.Assert.*;

import matrix.Matrix;

import org.junit.Before;
import org.junit.Test;

import quantification.Statistic;

public class MatrixTest {
	private Matrix matrix;
	
	@Before
	public void setUp() {
		double[][] data = new double[][]{
				{0.22, 0.92, 0.112},
				{1.00, 0.00, 0.212},
				{6.01, -1.01, 2.12}};
		this.matrix = new Matrix(data);
	}
	
	/**
	 * Test that the matrix sum equals the known empirical value.
	 * */
	@Test
	public void testSumEqualsEmpirical() {
		assertTrue(this.matrix.sum() == 9.584);
	}
	
	/**
	 * Test that the matrix minimum equals the known empirical value.
	 * */
	@Test
	public void testMinEqualsEmpirical() {
		assertTrue(this.matrix.min() == -1.01);
	}
	
	/**
	 * Test that the matrix maximum equals the known empirical value.
	 * */
	@Test
	public void testMaxEqualsEmpirical() {
		assertTrue(this.matrix.max() == 6.01);
	}

	/**
	 * Assert width of the matrix.
	 * */
	@Test
	public void testValidWidth() {
		assertTrue(this.matrix.getWidth() == 3);
	}
	
	/**
	 * Assert height of the matrix.
	 * */
	@Test
	public void testValidHeight() {
		assertTrue(this.matrix.getWidth() == 3);
	}
	
	/**
	 * Column-sums list-length must equal width of that respective matrix.
	 * */
	@Test
	public void testColumnSumsLengthEqualsWidth() {
		assertTrue(this.matrix.columnSums().length == this.matrix.getWidth());
	}
	
	/**
	 * The sum of the entire matrix-columns must equal the entire matrix.
	 * */
	@Test
	public void testColumnSumsEqualMatrixSum() {
		assertTrue(Statistic.summation(this.matrix.columnSums()) == 
				this.matrix.sum());
	}
	
	/**
	 * Assert that column-sums at a specific column yields emprical sum.
	 * */
	@Test
	public void testColumnSumsEqualEmpiricalSum() {
		int index = 1; // sum column at index 1
		double sum = Statistic.summation(this.matrix.getColumn(index));
		assertTrue(this.matrix.columnSums()[index] == sum);
	}
	
	/**
	 * Summing the minimum values within per column must be larger than the
	 * singular minimum in the entire matrix (unless matrix is all zeros).
	 * */
	@Test
	public void sumColumnMinsGreaterThanMatrixMin() {
		assertTrue(this.matrix.sumColumnMins() >= this.matrix.min());
	}
	
	/**
	 * Summing the minimum values within per column must be smaller than the
	 * singular maximum in the entire matrix (unless matrix is all zeros).
	 * */
	@Test
	public void sumColumnMinsLessThanMatrixMin() {
		assertTrue(this.matrix.sumColumnMins() <= this.matrix.max());
	}
	
	/**
	 * Test that when a row is identified, it equals the matrix width.
	 * */
	@Test
	public void testGetRowYieldsMatrixWidth() {
		// take the first row as an example
		assertTrue(this.matrix.getRow(0).length == this.matrix.getWidth());
	}
	
	/**
	 * Test that when a column is identified, it equals the matrix height.
	 * */
	@Test
	public void testGetColumnYieldsMatrixHeight() {
		// take the first column as an example
		assertTrue(this.matrix.getColumn(0).length == this.matrix.getHeight());
	}
	
	/**
	 * Test identification of an unknown valid in the matrix.
	 * */
	@Test
	public void testDoesNotContainValue() {
		assertFalse(this.matrix.contains(1002101)); // deliberately not in.
	}
	
	/**
	 * Test identification of a known valid in the matrix.
	 * */
	@Test
	public void testContainsKnownValue() {
		assertTrue(this.matrix.contains(0.212)); // deliberately in matrix.
	}
	
	/**
	 * Test that matrix width equals matrix row length.
	 * */
	@Test
	public void testWidthEqualsRowLength() {
		int length = this.matrix.getRow(1).length; // get row 1 for example.
		assertTrue(length == this.matrix.getWidth());
	}
	
	/**
	 * Test that matrix height equals matrix column length.
	 * */
	@Test
	public void testHeightEqualsColumnLength() {
		int length = this.matrix.getColumn(1).length; // column 1 for example.
		assertTrue(length == this.matrix.getHeight());
	}
	
	/**
	 * Test that no default row names are initially provided.
	 * */
	@Test
	public void testNoDefaultRowNames() {
		assertNull(this.matrix.getRows());
	}
	
	/**
	 * Test that no default column names are initially provided.
	 * */
	@Test
	public void testNoDefaultColumnNames() {
		assertNull(this.matrix.getColumns());
	}
	
	/**
	 * Test that no default matrix name is initially provided.
	 * */
	@Test
	public void testNoDefaultName() {
		assertNull(this.matrix.getName());
	}
}
