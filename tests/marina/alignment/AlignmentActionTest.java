package marina.alignment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AlignmentActionTest {
	private AlignmentAction action;
	
	@Before
	public void setUp() throws Exception {
		this.action = new AlignmentAction();
	}
	
	/**
	 * Assert that the thread can successfully join before closing.
	 * */
	@Test
	public void testThreadCloses() throws InterruptedException {
		Thread t = new Thread(this.action);
		t.start();
		t.join();
		assertFalse(t.isAlive());
	}
	
	/**
	 * Assert that no DNA motif parser is designated by-default.
	 * */
	@Test
	public void testNoDefaultMotifParser() {
		assertFalse(this.action.isUsingMotifs());
	}
	/**
	 * Assert that no PWM parser is designated by-default.
	 * */
	@Test
	public void testNoDefaultPWMParser() {
		assertFalse(this.action.isUsingPWMs());
	}

}
