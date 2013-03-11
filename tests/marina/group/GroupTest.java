package marina.group;

import static org.junit.Assert.*;

import java.io.File;

import marina.parser.FASTAParser;

import org.junit.Before;
import org.junit.Test;

public class GroupTest {
	private Group group;

	@Before
	public void setUp() throws Exception {
		// provide a known and valid group.
		FASTAParser p = new FASTAParser(new File("./demo/most_induced.fasta"));
		p.parse();
		this.group = new Group(p);

	}

	/**
	 * Given a known group, verify its group size equals its known count.
	 * */
	@Test
	public void testGroupSizeEqualsKnownCount() {
		assertTrue(this.group.getSize() == 556); // 556 sequences in file.
	}

	/**
	 * Test that the computer base-name equals the known base-name.
	 * */
	@Test
	public void testBasenameValidity() {
		assertEquals("\"most_induced.fasta\"", this.group.getBasename());
	}

	/**
	 * Test that the group-parser is an object of type FASTAParser
	 * */
	@Test
	public void testValidFASTAParser() {
		assertTrue(this.group.getParser() instanceof FASTAParser);
	}

	/**
	 * Test that the group size equals its respective FASTA parser size.
	 * */
	@Test
	public void testGroupSizeParserSizeEqual() {
		assertEquals(this.group.getParser().getSequences().size(), 
				this.group.getSize());
	}

	/**
	 * Immediately after parsing a group, there should be no mapping performed.
	 * */
	@Test
	public void testNoMapsAfterParsing() {
		assertEquals(this.group.mappingWrapper().getNumMappings(), 
				this.group.mappingWrapper().getMaps().size());
	}
	
	/**
	 * Group names must be flanked with double-quotations.
	 * */
	@Test
	public void testQuotesFlankGroupName() {
		boolean hasFlanks = this.group.getBasename().startsWith("\"") && 
				this.group.getBasename().endsWith("\"");
		assertTrue(hasFlanks);
	}
}
