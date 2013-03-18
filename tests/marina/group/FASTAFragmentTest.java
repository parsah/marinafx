package marina.group;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FASTAFragmentTest {
	private FASTAFragment frag;

	@Before
	public void setUp() throws Exception {
		FASTASequence parent = new FASTASequence(">parent", "GTACGTACTGGTCA");
		this.frag = new FASTAFragment(parent, ">header", "ACGTACT", 3);
	}
	
	/**
	 * Test that the fragment is the expected size.
	 * */
	@Test
	public void testFragmentLengthIsExpected() {
		assertTrue(this.frag.getLength() == 7);
	}
	
	/**
	 * Test that fragment length equals sequence length.
	 * */
	@Test
	public void testFragmentLengthEqualsSequenceLength() {
		assertTrue(this.frag.getLength() == this.frag.getSequence().length());
	}
	
	/**
	 * A fragment has no initial mappings.
	 * */
	@Test
	public void testFragmentsHaveNoInitialMappings() {
		assertTrue(this.frag.getMappings().size() == 0);
	}
	
	/**
	 * Assert that getting a specific base the expected nucleotide.
	 * */
	@Test
	public void testBaseIdentificationEquality() {
		int idx = 0;
		String base = this.frag.getBase(idx);
		String other = String.valueOf(this.frag.getSequence().charAt(idx));
		assertTrue(base.equals(other));
	}
	
	/**
	 * Assert that a fragment's parent is not null as it must not be.
	 * */
	@Test
	public void testParentSequenceNotNull() {
		assertNotNull(this.frag.getParent());
	}
	
	/**
	 * Assert that a FASTAFragment object is also a DNASequence object.
	 * */
	@Test
	public void testFragmentIsDNASequence() {
		assertTrue(this.frag instanceof DNASequence);
	}
	
	/**
	 * Assert that building fragments of an known fragment yields only 
	 * 1 fragment.
	 * */
	@Test
	public void testBuildingFragmentsYieldsLengthOne() {
		assertTrue(this.frag.toFragments(this.frag.getLength()).size() == 1);
	}
	
	/**
	 * Assert that building fragments of an known fragment can yield many
	 * fragments if the desired size is small.
	 * */
	@Test
	public void testBuildingShortFragmentsYieldsMultiple() {
		int size = 1; // build fragments of size 1.
		assertTrue(this.frag.toFragments(size).size() == this.frag.getLength());
	}

}
