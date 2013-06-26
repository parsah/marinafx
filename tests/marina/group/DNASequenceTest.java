package marina.group;

import static org.junit.Assert.*;
import group.FASTASequence;

import org.junit.Before;
import org.junit.Test;

public class DNASequenceTest {
	private FASTASequence fasta; // testing DNASequence behaviors on a FASTA

	@Before
	public void setUp() throws Exception {
		// generate random sequence
		this.fasta = new FASTASequence("head", "TGACGTTGACTGTGATGTGTGCATGG");
	}
	
	/**
	 * Test that upon reversing a sequence, sequence length remains the same.
	 * */
	@Test
	public void testReverseLengthEquality() {
		int origLength = this.fasta.getLength();
		this.fasta.reverse(); // next, reverse the sequence
		assertEquals(this.fasta.getLength(), origLength);
	}
	
	/**
	 * Test that sequence complement is empirically correct.
	 * */
	@Test
	public void testCorrectComplement() {
		this.fasta.complement();
		assertEquals("ACTGCAACTGACACTACACACGTACC", this.fasta.getSequence());
	}
	
	/**
	 * Getting the complement of a complement cancels-out and produces the
	 * original sequence.
	 * */
	@Test
	public void testComplementCancellation() {
		String origSeq = this.fasta.getSequence();
		this.fasta.complement();
		this.fasta.complement();
		assertEquals(origSeq, this.fasta.getSequence());
	}
	
	/**
	 * Test that the reverse complement of a sequence is empirically correct.
	 * */
	@Test
	public void testReverseComplementEquality() {
		this.fasta.reverseComplement();
		assertEquals("CCATGCACACATCACAGTCAACGTCA", this.fasta.getSequence());
	}
	
	/**
	 * Test that reverse complement of a palindromic sequence yields the
	 * exact same sequence as the original.
	 * */
	@Test
	public void testPalindromeReverse() {
		String palindrome = "GGCC";
		this.fasta.setSequence(palindrome); // palindromic sequence
		this.fasta.reverseComplement();
		assertEquals(palindrome, this.fasta.getSequence());
	}
}
