package marina.parser;
import static org.junit.Assert.assertTrue;

import java.io.File;

import marina.parser.FASTAParser;

import org.junit.Before;
import org.junit.Test;


public class FASTAParserTest {
	private FASTAParser parser;

	@Before
	public void setUp() throws Exception {
		// provide a known FASTA file
		this.parser = new FASTAParser(new File("./demo/most_suppressed.fasta"));
		this.parser.parse();
	}
	
	/**
	 * The FASTA file must be valid.
	 * */
	@Test
	public void testFASTAIsValid() {
		assertTrue(this.parser.getFile().exists());
	}
	
	/**
	 * Test that all sequences in the file can be parsed.
	 * */
	@Test
	public void testCanParse() {
		assertTrue(this.parser.getSequences().size() == 585);
	}
}
