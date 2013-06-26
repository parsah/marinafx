package marina.quantification;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import group.FASTASequence;
import group.Group;

import java.io.File;

import matrix.ContingencyMatrix;

import org.junit.Before;
import org.junit.Test;

import parser.DNAMotifParser;
import parser.FASTAParser;
import quantification.AbundanceInference;
import quantification.CandidateMatrixBuilder;
import alignment.RabinKarp;
import bindingsite.LinearDNAMotif;

public class AbundanceInferenceTest {
	private AbundanceInference inference;
	private ContingencyMatrix cm; // for testing abundance functions.

	@Before
	public void setUp() throws Exception {
		this.cm = new ContingencyMatrix(new double[][]{
				{55.0,  65.0},  
				{38.0,  59.0}}); // create a known, sound matrix.
		FASTAParser induced = new FASTAParser(new File("./demo/most_induced.fasta"));	
		FASTAParser suppressed = new FASTAParser(new File("./demo/most_suppressed.fasta"));	
		Group query = new Group(induced);
		Group baseline = new Group(suppressed);
		DNAMotifParser motifs = new DNAMotifParser(new File("./demo/sample_motifs.txt"));
		motifs.parse();
		induced.parse();
		suppressed.parse();
		// to save time, use group subset; larger subset increases test-time.
		for (Group g: new Group[]{baseline, query}) {
			for (FASTASequence seq: g.getParser().getSequences().subList(0, 75)) {
				for (LinearDNAMotif motif: motifs.getLinearMotifs()) {
					RabinKarp rk = new RabinKarp();
					rk.align(seq, motif);
				}
			}
		}
		CandidateMatrixBuilder builder = new CandidateMatrixBuilder(query, baseline);
		this.inference = new AbundanceInference(builder);
	}
	
	/**
	 * Assert that a known contingency matrix can pass the 
	 * AbundanceInference difference-cutoff.
	 * */
	@Test
	public void testRepresentedMatrixPassesDifference() {
		assertTrue(this.inference.isPassDifference(this.cm));
	}
	
	/**
	 * Assert that a known contingency matrix can pass the 
	 * AbundanceInference Laplace-cutoff.
	 * */
	@Test
	public void testRepresentedMatrixPassesLaplace() {
		assertTrue(this.inference.isPassLaplace(this.cm));
	}
	
	/**
	 * Assert that a known contingency matrix can pass the 
	 * AbundanceInference P-value cutoff.
	 * */
	@Test
	public void testRepresentedMatrixPassesPValue() {
		assertTrue(this.inference.isPassPValue(this.cm));
	}
	
	/**
	 * Assert that a known contingency matrix can pass the 
	 * AbundanceInference Support-cutoff.
	 * */
	@Test
	public void testRepresentedMatrixPassesSupport() {
		assertTrue(this.inference.isPassSupport(this.cm));
	}
	
	/**
	 * Assert that a matrix which with a low difference can fail the
	 * difference-cutoff.
	 * */
	@Test
	public void testUnRepresentedMatrixFailedDifference() {
		double[][] data = new double[][]{
				{55.0,  56.0},  
				{38.0,  59.0}};
		ContingencyMatrix cm = new ContingencyMatrix(data);
		assertFalse(this.inference.isPassDifference(cm));
	}
	
	/**
	 * Assert that a matrix which with a low difference can fail the
	 * difference-cutoff.
	 * */
	@Test
	public void testUnRepresentedMatrixFailedLaplace() {
		double[][] data = new double[][]{
				{0.0,  12.0},
				{2.0,  3.0}};
		ContingencyMatrix cm = new ContingencyMatrix(data);
		assertFalse(this.inference.isPassLaplace(cm));
	}
	
	/**
	 * Test that the rank-ordered matrix is initially null.
	 * */
	@Test
	public void testInitiallyNullOrderedMatrix() {
		assertNull(this.inference.getOrderedMatrix());
	}
	
	/**
	 * Test that the rank-unordered matrix is initially null.
	 * */
	@Test
	public void testInitiallyNullUnOrderedMatrix() {
		assertNull(this.inference.getUnOrderedMatrix());
	}
}
