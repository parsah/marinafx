package marina.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import marina.bindingsite.BindingSite;
import marina.bindingsite.PositionWeightMatrix;

import org.junit.Before;
import org.junit.Test;

public class PositionWeightMatrixTest {
	private PositionWeightMatrix pwm;
	
	@Before
	public void setUp() throws Exception {
		// provide a known and valid PWM
		double[][] data = new double[][]{
				{215, 40, 313, 0, 0, 0, 0},
				{407, 100, 4, 0, 0, 0, 0},
				{353, 0, 2, 999, 999, 999, 541},
				{24, 859, 679, 0, 0, 0, 458}};
		this.pwm = new PositionWeightMatrix(data);
	}
	
	/**
	 * A valid PWM must have four rows.
	 * */
	@Test
	public void testHeightEqualsFour() {
		assertTrue(this.pwm.getHeight() == 4);
	}
	
	/**
	 * Recomputing matrix sums must equal the apriori-computed sum.
	 * */
	@Test
	public void testSumComputation() {
		double oldSum = this.pwm.getSum();
		this.pwm.updateMatrixSum();
		assertTrue(oldSum == this.pwm.getSum());
	}
	
	/**
	 * Test the information function works given an even value.
	 * */
	@Test
	public void testEvenValueInfoFunction() throws IOException {
		double knownInformation = 11.0903;
		assertEquals(knownInformation, PositionWeightMatrix.information(4), 1);
	}
	
	/**
	 * Test the information function works given an odd value.
	 * @throws IOException 
	 * */
	@Test
	public void testOddValueInfoFunction() throws IOException {
		double knownOutput = 23.3254;
		assertEquals(knownOutput, PositionWeightMatrix.information(7), 1);
	}
	
	/**
	 * Test the information function works given a decimal value.
	 * @throws IOException 
	 * */
	@Test
	public void testDecimalValueInfoFunction() throws IOException {
		double knownOutput = -0.0446;
		assertEquals(knownOutput, PositionWeightMatrix.information(0.2), 1);
	}
	
	/**
	 * Test that a negative value in information function throws exception.
	 * @throws IOException 
	 * */
	@Test(expected=IOException.class)
	public void testNonZeroInfoFunctionException() throws IOException {
		PositionWeightMatrix.information(-2); // negative
	}
	
	/**
	 * Test that the PWM is a BindingSite object
	 * */
	@Test
	public void testIsBindingSite() {
		assertTrue(this.pwm instanceof BindingSite);
	}
	
	/**
	 * Test that the column sums, when summed-together, equal the matrix sum.
	 * */
	@Test
	public void testColumnSumsEqualMatrixSum() {
		this.pwm.updateColumnSums(); // compute column sums
		double matrixSum = Matrix.summation(this.pwm.getColumnSums());
		assertTrue(matrixSum == this.pwm.getSum());
	}
	
	/**
	 * Test that the column-sum length equals matrix width.
	 * */
	@Test
	public void testColumnSumLength() {
		this.pwm.updateColumnSums();
		assertTrue(this.pwm.getColumnSums().length == this.pwm.getWidth());
	}
	
	/**
	 * Test that matrix-jitter removes all zero-values.
	 * */
	@Test
	public void testJitterRemovedZeros() {
		this.pwm.jitter();
		assertFalse(this.pwm.contains(0));
	}
	
	/**
	 * Test that column sums of a weighted matrix sum to entire matrix sum.
	 * @throws IOException 
	 * */
	@Test
	public void testWeightedSumEquality() throws IOException {
		this.pwm.jitter();
		this.pwm.updateColumnSums(); // compute column sums
		this.pwm.information();
		this.pwm.updateMatrixSum();
		double matrixSum = Matrix.summation(this.pwm.getColumnSums());
		assertEquals(matrixSum, this.pwm.getSum(), 1);
	}

}
