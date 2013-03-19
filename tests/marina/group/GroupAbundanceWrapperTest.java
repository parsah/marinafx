package marina.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import marina.alignment.RabinKarp;
import marina.bindingsite.BindingSite;
import marina.bindingsite.LinearDNAMotif;
import marina.parser.DNAMotifParser;
import marina.parser.FASTAParser;

import org.junit.Before;
import org.junit.Test;

public class GroupAbundanceWrapperTest {
	private GroupAbundanceWrapper wrapper;
	private DNAMotifParser motifParser; // DNA motifs mapping to map to group

	@Before
	public void setUp() throws Exception {
		RabinKarp rk = new RabinKarp();
		this.motifParser = new DNAMotifParser(new File("./demo/sample_motifs.txt"));
		this.motifParser.parse();
		FASTAParser parser = new FASTAParser(new File("./demo/most_induced.fasta"));
		parser.parse();
		Group group = new Group(parser);
		// only map sequences to the first 2 motifs (for time-purposes).
		for (FASTASequence seq: group.getParser().getSequences().subList(0, 20)) {
			for (LinearDNAMotif motif: this.motifParser.getLinearMotifs()) {
				rk.align(seq, motif);
			}
		}
		
		this.wrapper = group.mappingWrapper();
	}
	
	/**
	 * We know that TFBSs must map, hence multiple TFBS entries must exist.
	 * */
	@Test
	public void testWrapperContainsAllMappings() {
		assertTrue(this.wrapper.getMaps().size() > 0);
	}
	
	/**
	 * The total number of times a TFBS maps to a group is summed and
	 * added to the group-sum. Ensure that the group sum equals the total
	 * number of TFBS mappings.
	 * */
	@Test
	public void testNumMapsEqualTotalNumTFBSMappings() {
		int sum = 0;
		for (Integer i: this.wrapper.getMaps().values()) {
			sum += i;
		}
		assertTrue(sum == this.wrapper.getNumMappings());
	}
	
	/**
	 * Assert that all keys in the abundance wrapper have positive counts.
	 * */
	@Test
	public void testAllMapsHavePositiveAbundance() {
		boolean hasZeroCount = false;
		for (Integer i: this.wrapper.getMaps().values()) {
			if (i == 0) {
				hasZeroCount = true;
			}
		}
		assertFalse(hasZeroCount);
	}
	
	/**
	 * Assert that all mapped motifs were parsed from original TFBS parser.
	 * */
	@Test
	public void testMappedMotifsWereParser() {
		boolean hasTFBS = false;
		for (BindingSite tfbs: this.wrapper.getMaps().keySet()) {
			hasTFBS = this.motifParser.getLinearMotifs().contains(tfbs);
		}
		assertTrue(hasTFBS);
	}
}
