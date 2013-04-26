package marina.matrix;

import static org.junit.Assert.assertTrue;

import matrix.ContingencyMatrixCell;

import org.junit.Test;

public class ContingencyMatrixCellTest {
	
	/**
	 * Assert that the value at X and G is at coordinates (0, 0)
	 * */
	@Test
	public void testXAndGIndices() {
		assertTrue(ContingencyMatrixCell.X_AND_G.getRow() == 0 &&
				ContingencyMatrixCell.X_AND_G.getColumn() == 0);
	}
	
	/**
	 * Assert that the value at X and not-G is at coordinates (0, 1)
	 * */
	@Test
	public void testXAndNotGIndices() {
		assertTrue(ContingencyMatrixCell.X_AND_NOT_G.getRow() == 0 &&
				ContingencyMatrixCell.X_AND_NOT_G.getColumn() == 1);
	}
	
	/**
	 * Assert that the value at not-X and not-G is at coordinates (1, 1)
	 * */
	@Test
	public void testNotXAndNotGIndices() {
		assertTrue(ContingencyMatrixCell.NOT_X_AND_NOT_G.getRow() == 1 &&
				ContingencyMatrixCell.NOT_X_AND_NOT_G.getColumn() == 1);
	}
	
	/**
	 * Assert that the value at not-X and G is at coordinates (1, 0)
	 * */
	@Test
	public void testNotXAndGIndices() {
		assertTrue(ContingencyMatrixCell.NOT_X_AND_G.getRow() == 1 &&
				ContingencyMatrixCell.NOT_X_AND_G.getColumn() == 0);
	}
	
	/**
	 * Test that a contingency matrix has four cell-values.
	 * */
	@Test
	public void testFourCellValues() {
		assertTrue(ContingencyMatrixCell.values().length == 4);
	}

}
