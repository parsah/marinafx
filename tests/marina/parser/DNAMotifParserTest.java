package marina.parser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class DNAMotifParserTest {
	private DNAMotifParser parser;
	
	@Before
	public void setUp() throws Exception {
		this.parser = new DNAMotifParser(new File("./demo/sample_motifs.txt"));
	}

	/**
	 * Test that parsing a valid file yields multiple parsed motifs.
	 * */
	@Test
	public void test() throws IOException {
		this.parser.parse();
		assertTrue(this.parser.getLinearMotifs().size() == 16);
	}
	
	/**
	 * The DNA motifs file must be valid.
	 * */
	@Test
	public void testMotifsFileIsValid() {
		assertTrue(this.parser.getFile().exists());
	}
	
	/**
	 * The DNA motifs file must be valid.
	 * */
	@Test
	public void testMotifFileIsValid() {
		assertTrue(this.parser.getFile().exists());
	}
	
	/**
	 * Assert that initially, no DNA motif entries were parsed.
	 * */
	@Test
	public void testNoParsingInitially() {
		assertTrue(this.parser.getLinearMotifs().size() == 0);
	}

}
