package marina.matrix;

/**
 * A ContingencyMatrixCell is used to reference indices for each cell in
 * a contingency matrix. Such a matrix is 2 rows and 2 columns in size.
 * These values can however be represented as either raw-frequencies or
 * probabilities. To prevent storing the second data-type in an additional
 * matrix, all four contingency matrix positions (cells) reference their
 * respective row and column. In doing so, matrix raw-frequencies and/or
 * probabilities can be retrieved on-the-fly without instantiating a new
 * contingency matrix.
 * @author Parsa Hosseini
 * */
public enum ContingencyMatrixCell {
	X_AND_G(0, 0), // i.e. n(X, G)
	X_AND_NOT_G(0, 1), // i.e. n(X, !G)
	NOT_X_AND_G(1, 0), // i.e. n(!X, G)
	NOT_X_AND_NOT_G(1, 1); // i.e. n(!X, !G)
	
	private int row;
	private int column;
	
	private ContingencyMatrixCell(int row, int column) {
		this.setRow(row);
		this.setColumn(column);
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	private void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	private void setColumn(int column) {
		this.column = column;
	}
}
