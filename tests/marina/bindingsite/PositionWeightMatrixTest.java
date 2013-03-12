package marina.bindingsite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import marina.matrix.Matrix;

import org.junit.Before;
import org.junit.Test;

public class PositionWeightMatrixTest {
	private PositionWeightMatrix pwm;
	
	@Before
	public void setUp() throws Exception {
		// provide a known and valid PWM
		double[][] data = new double[][]{
				{70,1,	0,	31,	86,	1,	5,	3},
				{6,	21,	1,	12,	0,	49,	61,	8},
				{6,	0,	86,	3,	1,	2,	13,	4},
				{5,	65,	0,	41,	0,	35,	8,	72}};
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
		double matrixSum = Matrix.summation(this.pwm.columnSums());
		assertEquals(matrixSum, this.pwm.sum(), 1);
	}
	
	/**
	 * Test that the column-sum length equals matrix width.
	 * */
	@Test
	public void testColumnSumLength() {
		assertTrue(this.pwm.columnSums().length == this.pwm.getWidth());
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
	 * Test that the minimum matrix value is less than the maximal value.
	 * */
	@Test
	public void testMinLessThanMax() {
		this.pwm.jitter();
		assertTrue(this.pwm.min() <= this.pwm.max());
	}
	
	/**
	 * Assert that the matrix-sum is greater than both the min and max 
	 * value.
	 * */
	@Test
	public void testSumGreaterThanMinMax() {
		assertTrue(this.pwm.sum() >= this.pwm.max() && 
				this.pwm.sum() >= this.pwm.min());
	}
	
	/**
	 * Summation of minimal-values per column must be greater than the 
	 * entire matrix-minimal value.
	 * */
	@Test
	public void testSumMinsGreaterThanMatrixMin() {
		assertTrue(Matrix.summation(this.pwm.columnSums()) > this.pwm.min());
	}
		
	/**
	 * Once the information-function has been applied to the matrix,
	 * minimal values will have smaller weights than their original
	 * original frequency-matrix counterpart.
	 * */
	@Test
	public void testSmallerMinOnceInfoBuilt() throws IOException {
		double oldMin = this.pwm.min();
		this.pwm.jitter();
		this.pwm.buildInformation();
		assertTrue(this.pwm.min() <= oldMin);
	}
	
	/**
	 * If a PWM is valid, so too must its PWM.
	 * */
	@Test
	public void testInfoMatrixBuilt() throws IOException {
		this.pwm.jitter();
		assertNotNull(this.pwm.buildInformation());
	}
	
	/**
	 * Information matrices must have the same height as a PWM
	 * */
	@Test
	public void testInfoAndPWMHeight() throws IOException {
		PositionWeightMatrix infoMatrix = this.pwm.buildInformation();
		assertEquals(infoMatrix.getHeight(), this.pwm.getHeight(), 0);
	}
	
	/**
	 * Information matrices must have the same width as a PWM
	 * */
	@Test
	public void testInfoAndPWMWidth() throws IOException {
		PositionWeightMatrix infoMatrix = this.pwm.buildInformation();
		assertEquals(infoMatrix.getWidth(), this.pwm.getWidth(), 0);
	}
	
	/**
	 * An information matrix must not have the same sum as a PWM
	 * */
	@Test
	public void testInfoAndPWMSumsUnequal() throws IOException {
		PositionWeightMatrix infoMatrix = this.pwm.buildInformation();
		assertNotSame(infoMatrix.sum(), this.pwm.sum());
	}
}