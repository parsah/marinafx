package marina.quantification;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import group.FASTASequence;
import group.Group;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import matrix.ContingencyMatrix;

import org.junit.Before;
import org.junit.Test;

import parser.DNAMotifParser;
import parser.FASTAParser;

import quantification.CandidateMatrixBuilder;

import bindingsite.BindingSite;
import bindingsite.LinearDNAMotif;

import alignment.RabinKarp;

public class CandidateMatrixBuilderTest {
	private CandidateMatrixBuilder builder;
	private Group query;
	private Group baseline;

	@Before
	public void setUp() throws Exception {
		FASTAParser induced = new FASTAParser(new File("./demo/most_induced.fasta"));	
		FASTAParser suppressed = new FASTAParser(new File("./demo/most_suppressed.fasta"));	
		this.query = new Group(induced);
		this.baseline = new Group(suppressed);
		DNAMotifParser motifs = new DNAMotifParser(new File("./demo/sample_motifs.txt"));
		motifs.parse();
		induced.parse();
		suppressed.parse();
		// to save time, use group subset; larger subset increases test-time.
		for (Group g: new Group[]{this.baseline, this.query}) {
			for (FASTASequence seq: g.getParser().getSequences().subList(0, 50)) {
				for (LinearDNAMotif motif: motifs.getLinearMotifs()) {
					RabinKarp rk = new RabinKarp();
					rk.align(seq, motif);
				}
			}
		}
		this.builder = new CandidateMatrixBuilder(this.query, this.baseline);
	}
	
	/**
	 * Assert that only two groups can be provided.
	 * */
	@Test
	public void testGroupLengthEqualsTwo() {
		assertTrue(this.builder.groups().length == 2);
	}
	
	/**
	 * Test that the CandidateMatrixBuilder object can derive 
	 * over-represented binding sites. The current provided groups do
	 * yield over-represented binding sites (represented as contingency
	 * matrices).
	 * @throws IOException 
	 * */
	@Test
	public void testRepresentedMatricesDerived() throws IOException {
		assertTrue(this.builder.build().size() > 0);
	}
	
	/**
	 * Assert that all ContingencyMatrix sums are the same. Certainly
	 * their cell-values are different but their sums must be the same.
	 * */
	@Test
	public void testEqualMatrixSums() throws IOException {
		List<ContingencyMatrix> cms = this.builder.build();
		Set<Double> sums = new TreeSet<Double>();
		for (int i=0; i < cms.size(); i++) {
			sums.add(cms.get(i).sum());
		}
		// if only unique sums are stored, list size must be size 1.
		assertTrue(sums.size() == 1);
	}
	
	/**
	 * Test that binding-sites shared between two groups are of the same
	 * length as the number of likely over-represented contingency matrices.
	 * */
	@Test
	public void testSharedTFBSsEqualNumMatrices() throws IOException {
		assertTrue(this.builder.union().size() == 
				this.builder.build().size());
	}
	
	/**
	 * Test that all shared binding sites are valid and not-null.
	 * */
	@Test
	public void testSharedTFBSsNotNull() {
		boolean isSharedSitesValid = true;
		for (BindingSite tfbs: this.builder.union()) {
			if (tfbs == null) {
				isSharedSitesValid = false;
			}
		}
		assertTrue(isSharedSitesValid);
	}
	
	/**
	 * If a TFBS is created and is over-represented, this object 
	 * must be found in list referencing over-represented binding-sites.
	 * */
	@Test
	public void testSingleMotifFound() throws IOException {
		LinearDNAMotif motif = new LinearDNAMotif("ARF family", "ARF1", "seq");
		ContingencyMatrix arfCM = this.builder.build(motif, 
				this.query.mappingWrapper(), this.baseline.mappingWrapper());
		assertTrue(this.builder.build().contains(arfCM));
	}
	
	/**
	 * If an invalid TFBS is created and is over-represented, this object 
	 * must be found in list referencing over-represented binding-sites.
	 * */
	@Test
	public void testInvalidMotifNotFound() throws IOException {
		LinearDNAMotif motif = new LinearDNAMotif("ARF family", "ARF_X", "seq");
		ContingencyMatrix arfCM = this.builder.build(motif, 
				this.query.mappingWrapper(), this.baseline.mappingWrapper());
		assertFalse(this.builder.build().contains(arfCM));
	}

}
