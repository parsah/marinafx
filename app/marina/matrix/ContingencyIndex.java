package marina.matrix;

public enum ContingencyIndex {
	X_AND_G(0, 0), // i.e. n(X, G)
	X_AND_NOT_G(0, 1), // i.e. n(X, !G)
	NOT_X_AND_G(1, 0), // i.e. n(!X, G)
	NOT_X_AND_NOT_G(1, 1); // i.e. n(!X, !G)
	
	private int row;
	private int column;
	
	private ContingencyIndex(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
}
