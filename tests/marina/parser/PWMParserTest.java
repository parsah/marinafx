package marina.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import parser.PWMParser;

public class PWMParserTest {
	private PWMParser parser;
	
	@Before
	public void setUp() throws Exception {
		this.parser = new PWMParser(new File("./demo/sample_pwms.txt"));
	}
	
	/**
	 * Test that a known PWM file is parsed and the expected number of
	 * PWMs are produced.
	 * */
	@Test
	public void testPWMParsingYieldsKnownPWMs() throws IOException {
		this.parser.parse();
		assertTrue(this.parser.getMatrices().size() == 3);
	}
	
	/**
	 * Assert that initially, no PWMs are parsed.
	 * */
	@Test
	public void testNoParsingInitially() {
		assertTrue(this.parser.getMatrices().size() == 0);
	}
	
	/**
	 * Test that parsing an invalid file throws an exception.
	 * */
	@Test(expected=IOException.class)
	public void testWrongFileThrowsException() throws IOException {
		this.parser = new PWMParser(new File("./demo/sample_motifs.txt"));
		this.parser.parse();
	}
}
