package marina.quantification;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import marina.alignment.RabinKarp;
import marina.bindingsite.LinearDNAMotif;
import marina.group.FASTASequence;
import marina.group.Group;
import marina.matrix.ContingencyMatrix;
import marina.parser.DNAMotifParser;
import marina.parser.FASTAParser;

import org.junit.Before;
import org.junit.Test;

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
	 * Test that truly over-represented binding sites can be produced.
	 * */
	@Test
	public void testRepresentedSitesProduced() throws IOException {
		assertTrue(this.inference.representedMatrices().size() >= 0);
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
	
	/**
	 * Test that upon binding represented contingency matrices to
	 * respective ranks, a rank-ordered matrix is instantiated. 
	 * */
	@Test
	public void testBindingYieldsOrderedMatrix() throws IOException {
		this.inference.bindAbundances();
		assertNotNull(this.inference.getOrderedMatrix());
	}
	
	/**
	 * Test that upon binding represented contingency matrices to
	 * respective ranks, a rank-unordered matrix is instantiated. 
	 * */
	@Test
	public void testBindingYieldsUnOrderedMatrix() throws IOException {
		this.inference.bindAbundances();
		assertNotNull(this.inference.getUnOrderedMatrix());
	}
	
	/**
	 * Upon binding contingency matrices, test that ordered and 
	 * unordered matrices share the same width.
	 * */
	@Test
	public void testOrderedUnOrderedMatrixShareWidth() throws IOException {
		this.inference.bindAbundances();
		assertTrue(this.inference.getOrderedMatrix().getWidth() ==
				this.inference.getUnOrderedMatrix().getWidth());
	}
	
	/**
	 * Upon binding contingency matrices, test that ordered and 
	 * unordered matrices share the same height.
	 * */
	@Test
	public void testOrderedUnOrderedMatrixShareHeight() throws IOException {
		this.inference.bindAbundances();
		assertTrue(this.inference.getOrderedMatrix().getHeight() ==
				this.inference.getUnOrderedMatrix().getHeight());
	}
	
	/**
	 * Test that upon binding, ordered and unordered matrices are
	 * completely different references.
	 * */
	@Test
	public void testOrderedUnOrderedMatrixDifferent() throws IOException {
		this.inference.bindAbundances();
		assertTrue(this.inference.getOrderedMatrix().hashCode() != 
				this.inference.getUnOrderedMatrix().hashCode());
	}
	
	/**
	 * Test that both ordered and unordered matrices share the same row names.
	 * */
	@Test
	public void testOrderedUnOrderedMatrixShareSameRows() throws IOException {
		this.inference.bindAbundances();
		assertTrue(this.inference.getOrderedMatrix().getRows() == 
				this.inference.getUnOrderedMatrix().getRows());
	}
	
	/**
	 * Test that both ordered and unordered matrices share the same columns.
	 * */
	@Test
	public void testOrderedUnOrderedMatrixShareSameColumns() throws IOException {
		this.inference.bindAbundances();
		Object[] orderedCols = this.inference.getOrderedMatrix().getColumns();
		Object[] unOrderedCols = this.inference.getUnOrderedMatrix().getColumns();
		assertArrayEquals(orderedCols, unOrderedCols);
	}
	
	/**
	 * Test that ranking a column from the unordered matrix yields
	 * the same column in the ordered matrix.
	 * @throws IOException 
	 * */
	@Test
	public void testRankingColumnOfOrderedMatrix() throws IOException {
		int num = 1;
		this.inference.bindAbundances();
		double[] unordered = this.inference.getUnOrderedMatrix().getColumn(num);
		int[] ranks = Statistic.rank(unordered);
		double[] ordered = this.inference.getOrderedMatrix().getColumn(num);
		boolean hasSameRanks = false;
		for (int i = 0; i < ordered.length; i++) {
			if (ranks[i] == ordered[i]) {
				hasSameRanks = true;
			}
		}
		assertTrue(hasSameRanks);
	}

}
