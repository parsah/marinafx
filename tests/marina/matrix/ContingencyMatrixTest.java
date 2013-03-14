package marina.matrix;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class ContingencyMatrixTest {
	private ContingencyMatrix cm; // references a valid matrix

	@Before
	public void setUp() throws Exception {
		double[][] data = new double[][]{{33, 15}, {1843, 1891}};
		this.cm = new ContingencyMatrix(data);
	}
	
	/**
	 * By default, no binding site is provided
	 * */
	@Test
	public void testNoDefaultBindingSite() {
		assertNull(this.cm.getBindingSite());
	}
	
	/**
	 * Test that a ContingencyMatrix is a sub-class of the Matrix class.
	 * */
	@Test
	public void testIsMatrix() {
		assertTrue(this.cm instanceof Matrix);
	}
	
	/**
	 * By default, no column names are provided; often not needed.
	 * */
	@Test
	public void testNoDefaultColumns() {
		assertNull(this.cm.getColumns());
	}
	
	/**
	 * By default, no row names are provided; often not needed.
	 * */
	@Test
	public void testNoDefaultRowNames() {
		assertNull(this.cm.getRows());
	}
	
	/**
	 * Test the matrix sums to the correct known value
	 * */
	@Test
	public void testMatrixSummation() {
		assertEquals(this.cm.sum(), 3782, 0); 
	}
	
	/**
	 * Test that recomputing matrix sum without prior modifications
	 * yields the same sum as before.
	 * */
	@Test
	public void testRecomputingSumEquality() {
		double oldSum = this.cm.sum();
		assertEquals(oldSum, this.cm.sum(), 0);
	}
	
	/**
	 * Test that the difference of the matrix is sound.
	 * */
	@Test
	public void testCorrectDifferenceReference() {
		assertEquals(this.cm.getDifference(), 
				this.cm.getFrequency(ContingencyMatrixCell.X_AND_G) -
				this.cm.getFrequency(ContingencyMatrixCell.X_AND_NOT_G), 
				0);
	}
	
	/**
	 * Test that matrix support references the correct cells.
	 * */
	@Test
	public void testCorrectSupportReference() {
		assertEquals(this.cm.getSupport(), 
				this.cm.getProbability(ContingencyMatrixCell.X_AND_G)*100, 
				0);
	}
	
	/**
	 * Test that the known matrix yields the correct difference.
	 * */
	@Test
	public void testCorrectDifferenceValue() {
		
		assertEquals(this.cm.getDifference(), 18, 0);
	}
	
	/**
	 * Test that the known matrix yields the correct support (represented
	 * as a percentage)
	 * */
	@Test
	public void testCorrectSupportValue() {
		// indeed this specific matrix yields poor support (is percentage)
		assertEquals(this.cm.getSupport(), 0.87255, 1);
	}
	
	/**
	 * Contingency matrices have a width of 2 columns only.
	 * */
	@Test
	public void testWidth() {
		assertTrue(this.cm.getWidth() == 2);
	}
	
	/**
	 * Contingency matrices have a height of 2 rows only.
	 * */
	@Test
	public void testHeight() {
		assertTrue(this.cm.getHeight() == 2);
	}
	
	/**
	 * Test that the not-variable X row is correctly identified.
	 * */
	@Test
	public void testXRowEquality() {
		double[] x = this.cm.getData()[0];
		assertArrayEquals(x, this.cm.getX(), 0);
	}
	
	/**
	 * Test that the variable X row is correctly identified.
	 * */
	@Test
	public void testNotXRowEquality() {
		double[] notX = this.cm.getData()[1];
		assertArrayEquals(notX, this.cm.getNotX(), 0);
	}

	/**
	 * Test that the Group G column is correctly identified.
	 * */
	@Test
	public void testGroupGColumnEquality() {
		double[] groupG = this.cm.getColumn(0);
		assertArrayEquals(groupG, this.cm.getG(), 0);
	}
	
	/**
	 * Test that the not-Group G column is correctly identified.
	 * */
	@Test
	public void testNotGroupGColumnEquality() {
		double[] notGroupG = this.cm.getColumn(1);
		assertArrayEquals(notGroupG, this.cm.getNotG(), 0);
	}
	
	/**
	 * Test the frequency at (0, 0) references X and group G
	 * */
	@Test
	public void testXAndGFrequency() {
		assertTrue(
				this.cm.getFrequency(ContingencyMatrixCell.X_AND_G) ==
				this.cm.getData()[0][0]);
	}
	
	/**
	 * Test the frequency at (0, 1) references X and not-group G
	 * */	
	@Test
	public void testXAndNotGFrequency() {
		assertTrue(
				this.cm.getFrequency(ContingencyMatrixCell.X_AND_NOT_G) ==
				this.cm.getData()[0][1]);
	}
	
	/**
	 * Test the frequency at (1, 0) references not-X and group G
	 * */
	
	@Test
	public void testNotXAndGFrequency() {
		assertTrue(
				this.cm.getFrequency(ContingencyMatrixCell.NOT_X_AND_G) ==
				this.cm.getData()[1][0]);
	}
	
	/**
	 * Test the frequency at (1, 1) references not-X and not-group G
	 * */
	@Test
	public void testNotXAndNotGFrequency() {
		assertTrue(
				this.cm.getFrequency(ContingencyMatrixCell.NOT_X_AND_NOT_G) ==
				this.cm.getData()[1][1]);
	}
	
	/**
	 * Test the probability at (0, 0) references X and group G
	 * */
	@Test
	public void testXAndGProbability() {
		assertTrue(
				this.cm.getProbability(ContingencyMatrixCell.X_AND_G) ==
				this.cm.getData()[0][0] / this.cm.sum());
	}
	
	/**
	 * Test the probability at (0, 1) references X and not-group G
	 * */	
	@Test
	public void testXAndNotGProbability() {
		assertTrue(
				this.cm.getProbability(ContingencyMatrixCell.X_AND_NOT_G) ==
				this.cm.getData()[0][1] / this.cm.sum());
	}
	
	/**
	 * Test the probability at (1, 0) references not-X and group G
	 * */
	
	@Test
	public void testNotXAndGProbability() {
		assertTrue(
				this.cm.getProbability(ContingencyMatrixCell.NOT_X_AND_G) ==
				this.cm.getData()[1][0] / this.cm.sum());
	}
	
	/**
	 * Test the frequency at (1, 1) references not-X and not-group G
	 * */
	@Test
	public void testNotXAndNotGProbability() {
		assertTrue(
				this.cm.getProbability(ContingencyMatrixCell.NOT_X_AND_NOT_G) ==
				this.cm.getData()[1][1] / this.cm.sum());
	}
	
	/**
	 * Assert that sum-frequencies of entire matrix sums to pre-computed sum.
	 * */
	@Test
	public void testFrequencySumsToMatrixSum() {
		double sumProbability = 
				this.cm.getFrequency(ContingencyMatrixCell.NOT_X_AND_G) +
				this.cm.getFrequency(ContingencyMatrixCell.NOT_X_AND_NOT_G) +
				this.cm.getFrequency(ContingencyMatrixCell.X_AND_G) +
				this.cm.getFrequency(ContingencyMatrixCell.X_AND_NOT_G);
		assertTrue(sumProbability == this.cm.sum());
	}
	
	/**
	 * Assert that sum-probabilities of the entire matrix sums to 1.0
	 * */
	@Test
	public void testProbabilitySumToOne() {
		double sumProbability = 
				this.cm.getProbability(ContingencyMatrixCell.NOT_X_AND_G) +
				this.cm.getProbability(ContingencyMatrixCell.NOT_X_AND_NOT_G) +
				this.cm.getProbability(ContingencyMatrixCell.X_AND_G) +
				this.cm.getProbability(ContingencyMatrixCell.X_AND_NOT_G);
		assertTrue(sumProbability == 1);
	}
	
	/**
	 * The known Laplace correction was derived apriori; ensure valid output.
	 * */
	@Test
	public void testIsCorrectLaplace() {
		double lapl = (this.cm.getFrequency(
				ContingencyMatrixCell.X_AND_G) + 1) / 
				(Matrix.summation(this.cm.getX()) + 2);
		assertEquals(lapl, 0.5399, 1);
	}
	
	/**
	 * The known lift was derived apriori; ensure valid output.
	 * */
	@Test
	public void testIsCorrectLift() {
		assertEquals(this.cm.getLift(), 1.38599, 0.1);
	}
	
	/**
	 * The known cosine was derived apriori; ensure valid output.
	 * */
	@Test
	public void testIsCorrectCosine() {
		assertEquals(this.cm.getCosine(), 0.10997, 0.1);
	}
	
	/**
	 * The known Jaccard measure was derived apriori; ensure valid output.
	 * */
	@Test
	public void testIsCorrectJaccard() {
		assertEquals(this.cm.getJaccard(), 0.01745108, 0.1);
	}
	
	/**
	 * The known confidence was derived apriori; ensure valid output.
	 * */
	@Test
	public void testIsCorrectConfidence() {
		assertEquals(this.cm.getConfidence(), 0.6875, 0.1);
	}
	
	/**
	 * The known Phi-coefficient was derived apriori; ensure valid output.
	 * */
	@Test
	public void testIsCorrectPhiCoefficient() {
		assertEquals(this.cm.getPhi(), 0.0434179, 0.1);
	}

	/**
	 * The known Kappa coefficient was derived apriori; ensure valid output.
	 * */
	@Test
	public void testIsCorrectKappaCoefficient() {
		assertEquals(this.cm.getKappa(), 0.00979, 0.1);
	}
	
	/**
	 * Sometimes, the hypergeometric p-value is so small that it
	 * is difficult to model. In such a case, return the 
	 * smallest-possible value instead.
	 * */
	@Test
	public void testIsCorrectPValueProduced() {
		assertTrue(this.cm.log(2).getPValue() <= 1.0);
	}
	
	/**
	 * Test that when IPF is performed, column values in the
	 * contingency matrix are identical to one another.
	 * */
	@Test
	public void testIPFYieldsEqualColumnValues() {
		this.cm.ipf();
		double[] col1 = this.cm.getColumn(0);
		double[] col2 = this.cm.getColumn(1);
		Arrays.sort(col1); // IPF values are reciprocal of eachother.
		assertEquals(Matrix.summation(col1), Matrix.summation(col2), 0);
	}
	
	/**
	 * Test that upon IPF, row marginals equal column marignals.
	 * */
	@Test
	public void testRowSumsFromIPF() {
		this.cm.ipf();
		assertEquals(
				Matrix.summation(this.cm.getX()),
				Matrix.summation(this.cm.getNotX()), 0);
	}
	
	/**
	 * Test that upon IPF, column sums equal one another.
	 * */
	@Test
	public void testColumnSumsFromIPF() {
		this.cm.ipf();
		assertEquals(
				Matrix.summation(this.cm.getG()),
				Matrix.summation(this.cm.getNotG()), 0);
	}
	
	/**
	 * Test that upon IPF, column sums equal one another.
	 * */
	@Test
	public void testProbabilitySumToOnePostIPF() {
		this.cm.ipf();
		double sumProbability = 
				this.cm.getProbability(ContingencyMatrixCell.NOT_X_AND_G) +
				this.cm.getProbability(ContingencyMatrixCell.NOT_X_AND_NOT_G) +
				this.cm.getProbability(ContingencyMatrixCell.X_AND_G) +
				this.cm.getProbability(ContingencyMatrixCell.X_AND_NOT_G);
		// IPF may represent 1.0 as 0.99999999999, hence set delta.
		assertEquals(sumProbability, 1, 0.5);
	}

	/**
	 * Test a not-null object is produced given a valid contingency matrix.
	 * */
	@Test
	public void testToProbabilityNotNull() {
		assertNotNull(this.cm.toProbability());
	}
	
	/**
	 * Test a produced probability-matrix sums to 1.0. 
	 * */
	@Test
	public void testProbMatrixSumsToOne() {
		assertEquals(this.cm.toProbability().sum(), 1.0, 0);
	}
	
	/**
	 * Test a probability-matrix has the same height as those which are not.
	 * */
	@Test
	public void testProbMatrixSameHeight() {
		assertTrue(this.cm.toProbability().getHeight() == this.cm.getHeight());
	}
	
	/**
	 * Test a probability-matrix has the same width as those which are not.
	 * */
	@Test
	public void testProbMatrixSameWidth() {
		assertTrue(this.cm.toProbability().getWidth() == this.cm.getWidth());
	}
}
