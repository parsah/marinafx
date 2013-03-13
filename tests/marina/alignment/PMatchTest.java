package marina.alignment;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import marina.bindingsite.FASTAFragment;
import marina.bindingsite.PositionWeightMatrix;
import marina.group.FASTASequence;
import marina.parser.PWMParser;

import org.junit.Before;
import org.junit.Test;

public class PMatchTest {
	private PMatch pmatch;
	private PositionWeightMatrix pwm;
	private FASTAFragment frag;

	@Before
	public void setUp() throws Exception {
		PWMParser parser = new PWMParser(new File("./demo/unittest_pwm.txt"));
		parser.parse(); // contains only 1x PWM, hence get index 0.
		this.pwm = parser.getMatrices().get(0).buildInformation();
		// a parent FASTA sequence being to one of its sub-sequences.
		FASTASequence seq = new FASTASequence("header", "TGACTGATGTACCTTGACG");
		this.frag = new FASTAFragment(seq, "header", "ATGTACCT", 6);
		// this PWM is known to map to this sub-sequence only once.
		this.pmatch = new PMatch(this.frag, this.pwm);
	}

	/**
	 * Assert that the fragment is the same length as the PWM.
	 * */
	@Test
	public void testPWMWidthEqualsFragmentLength() {
		assertTrue(this.frag.getLength() == this.pwm.getWidth());
	}

	/**
	 * If a PWM maps to a FASTA fragment given the default PWM cutoff
	 * of 0.80 and that PWM is not found in the parent-FASTA mapping-set,
	 * a new key is added.
	 * */
	@Test
	public void testPWMAddsMapKey() throws IOException {
		this.pmatch.extrapolate(); // map the PWM onto the fragment
		assertTrue(this.frag.getParent().getMappings().size() == 1);
	}

	/**
	 * If a PWM maps to a FASTA fragment given the default PWM cutoff
	 * of 0.80 and that PWM is not found in the parent-FASTA mapping-set,
	 * a new key is added.
	 * */
	@Test
	public void testMappedKeyReferencesIndex() throws IOException {
		this.pmatch.extrapolate(); // map the PWM onto the fragment
		int numHit = this.frag.getParent().getMappings().get(this.pwm).size();
		// if there's 1x PWM mapping to 1x fragment, 1x offset will be saved.
		assertTrue(numHit == 1);
	}

	/**
	 * More often than not, the fragment can yield an alignment
	 * which does not pass the default threshold. In doing so, the
	 * specific PWM does not map to the fragment.
	 * */
	@Test
	public void testFragmentsCanHaveNoHits() throws IOException {
		this.frag.setSequence("TTTTACCT"); // different to original sequence
		this.pmatch.extrapolate();
		assertTrue(this.frag.getParent().getMappings().size() == 0);
	}

	/**
	 * The upper-boundary of the PWM alignment is the total-sum of
	 * the entire information matrix. The lower-boundary however
	 * is the sum of each-column's minimum values.
	 * */
	@Test
	public void testProbabilityBoundaries() throws IOException {
		this.pmatch.extrapolate();
		assertTrue(this.pwm.sum() > this.pwm.sumColumnMins());

	}

	/**
	 * Tests that a relatively-moderate score yields an equally
	 * proportional probability.
	 * */
	@Test
	public void testModerateScoreToProb() throws IOException {
		this.pmatch.extrapolate();
		double prob = (111 - this.pwm.sumColumnMins()) /
				(this.pwm.sum() - this.pwm.sumColumnMins());
		assertEquals(prob, 0.8261, 1);
	}
	
	/**
	 * Tests that a very-high score yields an equally
	 * proportional probability.
	 * */
	@Test
	public void testHighScoreToProb() throws IOException {
		this.pmatch.extrapolate();
		double prob = (130 - this.pwm.sumColumnMins()) /
				(this.pwm.sum() - this.pwm.sumColumnMins());
		assertEquals(prob, 0.970, 1);
	}
	
	/**
	 * Tests that a low score yields an equally proportional 
	 * probability.
	 * */
	@Test
	public void testLowScoreToProb() throws IOException {
		this.pmatch.extrapolate();
		double prob = (3 - this.pwm.sumColumnMins()) /
				(this.pwm.sum() - this.pwm.sumColumnMins());
		assertEquals(prob, 0.004979, 1);
	}
}
