package marina.bindingsite;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LinearDNAMotifTest {
	private LinearDNAMotif motif;
	
	@Before
	public void setUp() throws Exception {
		this.motif = new LinearDNAMotif("family", "gene", "GTACGTGTAC");
	}

	/**
	 * Any string renders the motif family valid.
	 * */
	@Test
	public void testGivenFamily() {
		assertNotNull(this.motif.getFamily());
	}
	
	/**
	 * An empty family is still rendered valid.
	 * */
	@Test
	public void testEmptyFamily() {
		this.motif.setFamily("");
		assertNotNull(this.motif.getFamily());
	}
	
	/**
	 * Test that a valid sequence is provided
	 * */
	@Test
	public void testNonZeroSequence() {
		assertEquals(this.motif.getLength(), 10);
	}
	
	/**
	 * Test that sequence length is the same as in-built motif length
	 * */
	@Test
	public void testMotifEqualsSequenceLength() {
		assertEquals(this.motif.getLength(), this.motif.getSequence().length());
	}
	
	/**
	 * Test that two motifs are not equal to one another.
	 * */
	@Test
	public void testEqualityWithDifferentSequences() {
		LinearDNAMotif other = new LinearDNAMotif("family", "gene", "ACGT");
		assertNotSame(this.motif, other);
	}
	
	/**
	 * Test that two motifs are not equal to one another.
	 * */
	@Test
	public void testEqualityWithSameSequences() {
		LinearDNAMotif other = new LinearDNAMotif("family", "gene", "GTACGTGTAC");
		assertEquals(this.motif, other);
	}
	
	/**
	 * Test that two motifs are not equal to one another based on gene name.
	 * */
	@Test
	public void testEqualityWithDifferentGene() {
		LinearDNAMotif other = new LinearDNAMotif("family", "diff", "GTACGTGTAC");
		assertNotSame(this.motif, other);
	}
	
	/**
	 * Test that two motifs are equal to one another based on gene name.
	 * */
	@Test
	public void testEqualityWithSameGene() {
		LinearDNAMotif other = new LinearDNAMotif("family", "gene", "GTACGTGTAC");
		assertEquals(this.motif, other);
	}
	
	/**
	 * Test that an invalid number of motif-states throws exception
	 * */
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testFewStatesThrowsException() {
		new LinearDNAMotif(new String[]{"family", "gene"});
	}
	
	/**
	 * Test that too many states yields no exception
	 * */
	@Test
	public void testManyStatesThrowsNothing() {
		new LinearDNAMotif(new String[]{"family", "gene", "GTACTG", "other"});
	}
	
	/**
	 * Test that a DNA motif is a BindingSite object
	 * */
	@Test
	public void testIsBindingSite() {
		assertTrue(this.motif instanceof BindingSite);
	}

}
