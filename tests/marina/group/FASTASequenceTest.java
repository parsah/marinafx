package marina.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import marina.bindingsite.LinearDNAMotif;

import org.junit.Before;
import org.junit.Test;

public class FASTASequenceTest {
	private FASTASequence fasta;

	@Before
	public void setUp() throws Exception {
		this.fasta = new FASTASequence("header", "GTACGTGTACGTCAGTCA");
	}
	
	/**
	 * Test the FASTA entry length is valid.
	 * */
	@Test
	public void testSequenceLength() {
		assertEquals(this.fasta.getLength(), this.fasta.getSequence().length());
	}
	
	/**
	 * Test that the FASTA entry initially has no mappings.
	 * */
	@Test
	public void testNoInitialMappings() {
		assertEquals(this.fasta.getMappings().size(), 0);
	}
	
	/**
	 * Changing the FASTA sequence must change its sequence length.
	 * */
	@Test
	public void testCanUpdateSequenceLength() {
		this.fasta.setSequence("TACGTGTCA");
		assertEquals(this.fasta.getLength(), 9);
	}
	
	/**
	 * Test that the FASTA object is of type DNASequence
	 * */
	@Test
	public void testIsBindingSite() {
		assertTrue(this.fasta instanceof DNASequence);
	}
	
	/**
	 * Test the FASTA entry has several motifs present
	 * */
	@Test
	public void testGenesMapOnlyOnce() {
		LinearDNAMotif motifB = new LinearDNAMotif("fam", "geneA", "TACTTAGT");
		LinearDNAMotif motifC = new LinearDNAMotif("fam", "geneB", "ACGTAC");
		LinearDNAMotif motifD = new LinearDNAMotif("fam", "geneC", "AACGTGTACT");
		this.fasta.getMappings().put(motifB, new ArrayList<Integer>());
		this.fasta.getMappings().put(motifC, new ArrayList<Integer>());
		this.fasta.getMappings().put(motifD, new ArrayList<Integer>());
		assertEquals(this.fasta.getMappings().size(), 3);
	}
	
	/**
	 * Test that multiple motif maps are consolidated as one. In other words,
	 * if a motif has multiple gene names, each gene is consolidated as one 
	 * entry and not as distinct mappings. This centralizes their mappings.
	 * */
	@Test
	public void testDuplicateMapsConsolidated() {
		LinearDNAMotif motifB = new LinearDNAMotif("fam", "geneA", "TACTTAGT");
		LinearDNAMotif motifC = new LinearDNAMotif("fam", "geneA", "ACGTAC");
		LinearDNAMotif motifD = new LinearDNAMotif("fam", "geneC", "AACGTGTACT");
		this.fasta.getMappings().put(motifB, new ArrayList<Integer>());
		this.fasta.getMappings().put(motifC, new ArrayList<Integer>());
		this.fasta.getMappings().put(motifD, new ArrayList<Integer>());
		assertEquals(this.fasta.getMappings().size(), 2);
	}
	
	/**
	 * Test the FASTA entry has a basic motif mapped to it.
	 * */
	@Test
	public void testMapPresence() {
		LinearDNAMotif motifA = new LinearDNAMotif("fam", "geneA", "TACTGCAGT");
		this.fasta.getMappings().put(motifA, new ArrayList<Integer>());
		assertTrue(this.fasta.getMappings().containsKey(motifA));
	}
	
	/**
	 * Test equality if headers and sequence do not equal
	 * */
	@Test
	public void testDiffHeaderAndSequence() {
		FASTASequence other = new FASTASequence("head", "GTAGTCTGAC");
		assertNotSame(this.fasta, other);
	}
	
	/**
	 * Test equality if headers and sequence are equal
	 * */
	@Test
	public void testSameHeadersAndSequences() {
		FASTASequence other = new FASTASequence("header", "GTACGTGTACGTCAGTCA");
		assertEquals(this.fasta, other);
	}
}
