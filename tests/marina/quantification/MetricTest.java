package marina.quantification;

import static org.junit.Assert.*;

import matrix.ContingencyMatrix;

import org.junit.Before;
import org.junit.Test;

import quantification.Metric;

public class MetricTest {
	private ContingencyMatrix cm;

	@Before
	public void setUp() throws Exception {
		// randomly generate some values
		double[][] data = new double[][]{
				{(int)(Math.random()*100), (int)(Math.random()*100)}, 
				{(int)(Math.random()*100), (int)(Math.random()*100)}};
		this.cm = new ContingencyMatrix(data);
	}
	
	/**
	 * Assert that confidence is within the accepted range.
	 * */
	@Test
	public void testConfidenceWithinRange() {
		double upperBound = 1;
		double lowerBound = 0;
		double val = Metric.confidence(this.cm);
		assertTrue(val >= lowerBound && val <= upperBound);
	}
	
	/**
	 * Assert that Laplace correction is within the accepted range.
	 * */
	@Test
	public void testLaplaceWithinRange() {
		double upperBound = 1;
		double lowerBound = 0;
		double val = Metric.laplace(this.cm);
		assertTrue(val >= lowerBound && val <= upperBound);
	}
	
	/**
	 * Assert that the cosine metric is within the accepted range.
	 * */
	@Test
	public void testCosineWithinRange() {
		double upperBound = 1;
		double lowerBound = 0;
		double val = Metric.cosine(this.cm);
		assertTrue(val >= lowerBound && val <= upperBound);
	}
	
	/**
	 * Assert that the Jaccard index is within the accepted range.
	 * */
	@Test
	public void testJaccardWithinRange() {
		double upperBound = 1;
		double lowerBound = 0;
		double val = Metric.jaccard(this.cm);
		assertTrue(val >= lowerBound && val <= upperBound);
	}
	
	/**
	 * Assert that the Kappa coefficient is within the accepted range.
	 * */
	@Test
	public void testKappaWithinRange() {
		double upperBound = 1;
		double lowerBound = -1;
		double val = Metric.kappa(this.cm);
		assertTrue(val >= lowerBound && val <= upperBound);
	}
	
	/**
	 * Assert that the lift metric is within the accepted range.
	 * */
	@Test
	public void testLiftWithinRange() {
		double upperBound = Double.MAX_VALUE;
		double lowerBound = 0;
		double val = Metric.lift(this.cm);
		assertTrue(val >= lowerBound && val <= upperBound);
	}
	
	/**
	 * Assert that the Phi coefficient is within the accepted range.
	 * */
	@Test
	public void testPhiWithinRange() {
		double upperBound = 1;
		double lowerBound = -1;
		double val = Metric.phi(this.cm);
		assertTrue(val >= lowerBound && val <= upperBound);
	}

}
