package marina.alignment;

import static org.junit.Assert.*;

import group.FASTASequence;

import java.io.IOException;


import org.junit.Before;
import org.junit.Test;

import bindingsite.LinearDNAMotif;

import alignment.RabinKarp;

public class RabinKarpTest {
	private RabinKarp rabinKarp;
	private FASTASequence seq; // valid FASTA object
	private LinearDNAMotif motif; // valid DNA motif

	@Before
	public void setUp() throws Exception {
		this.rabinKarp = new RabinKarp();
		this.seq = new FASTASequence("header", "GTACGTATCTGTGACGTTGACGTTGCAG");
		this.motif = new LinearDNAMotif("family", "gene", "AG");
	}
	
	/**
	 * Test that a motif only found once by-eye can be found so.
	 * */
	@Test
	public void testMotifSingleMapping() throws IOException {
		this.rabinKarp.align(this.seq, this.motif); // 1 mapping found
		assertEquals(this.seq.getMappings().keySet().size(), 1); 
	}
	
	/**
	 * More often that not, a single motif can map multiple times.
	 * Test that a motif which does map multiple times does so.
	 * */
	@Test
	public void testMotifWithManyMaps() throws IOException {
		this.motif.setSequence("GT"); // known to be found 5 times
		this.rabinKarp.align(this.seq, this.motif);
		assertEquals(this.seq.getMappings().get(this.motif).size(), 5); 
	}
	
	/**
	 * Test that if motifs from the same gene-family map to the query
	 * sequence, their cumulative sum represents the number of mappings
	 * for that respective motif.
	 * */
	@Test
	public void testMotifCopiesBothMap() throws IOException {
		this.motif.setSequence("GT"); // known to be found 5 times
		LinearDNAMotif other = new LinearDNAMotif("family", "gene", "AC");
		this.rabinKarp.align(this.seq, this.motif);
		this.rabinKarp.align(this.seq, other); // found 3 times
		assertEquals(this.seq.getMappings().get(this.motif).size(), 8);
	}
	
	/**
	 * A motif having a query sequence exactly the same length as the
	 * query is allowed.
	 * */
	@Test
	public void testMotifLenEqualQueryAllowed() throws IOException {
		// longer than the sequence
		this.motif.setSequence("GTACGTATCTGTGACGTTGACGTTGCAG");
		this.rabinKarp.align(this.seq, this.motif);
	}
	
	/**
	 * Test that if the motif is greater than the query (length-wise),
	 * an exception is thrown.
	 * */
	@Test(expected=IOException.class)
	public void testMotifGreaterThanQueryException() throws IOException {
		// longer than the sequence
		this.motif.setSequence("GTACGTATCTGTGACGTTGACGTTGCAGAAAA");
		this.rabinKarp.align(this.seq, this.motif);
	}
	
	/**
	 * Test that if a motif is not found in the sequence, null-list is set.
	 * */
	@Test
	public void testMotifLackingMapIndices() throws IOException {
		// longer than the sequence
		this.motif.setSequence("AAAAAAAA"); // known to not be in sequence
		this.rabinKarp.align(this.seq, this.motif);
		assertNull(this.seq.getMappings().get(this.motif));
	}
	
	/**
	 * Test that a motif is not found in the mapping-set if it has no hits.
	 * */
	@Test
	public void testNoKeyIfNoMap() throws IOException {
		// longer than the sequence
		this.motif.setSequence("AAAAAAAA"); // known to not be in sequence
		this.rabinKarp.align(this.seq, this.motif);
		assertFalse(this.seq.getMappings().containsKey(this.motif));
	}
	
	/**
	 * Test that if one gene copy maps with another does not, the motif
	 * which maps is still kept. This is an important case because the motif
	 * which does not map yields a map-list of null.
	 * */
	@Test
	public void testMotifsWithMappingVariance() throws IOException {
		LinearDNAMotif other = new LinearDNAMotif("family", "gene", "AAAAAA");
		this.rabinKarp.align(this.seq, this.motif);
		this.rabinKarp.align(this.seq, other);
		assertEquals(this.seq.getMappings().get(this.motif).size(), 1);
	}
	
	/**
	 * Test the modular-power function given a known base, exponent
	 * and power.
	 * */
	@Test
	public void testPositiveBaseModularPower() {
		assertEquals(this.rabinKarp.modularPower(5, 4, 2), 1);
	}

	/**
	 * Test the modular-power function given a negative base, exponent
	 * and power.
	 * */
	@Test
	public void testNegativeBaseModularPower() {
		assertEquals(this.rabinKarp.modularPower(-115, 4, 2), 1);
	}
	
	/**
	 * Test the modular-power function given an odd base, exponent
	 * and but even power.
	 * */
	@Test
	public void testOddExponentModularPower() {
		assertEquals(this.rabinKarp.modularPower(3, 7, 6), 3);
	}
}
