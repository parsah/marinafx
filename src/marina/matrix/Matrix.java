package marina.matrix;

import java.util.HashMap;

/**
 * The core Marina data-structure associated with storing and 
 * manipulating values is a matrix. Matrix variants exist, such as
 * Contingency Matrices and Position Weight Matrices.
 * <p>
 * Each Matrix is a multi-dimensional array whereby its specific
 * elements are populated given user-provided inputs.
 * @author Parsa Hosseini
 * */
public class Matrix {
	
	private double[][] data;
	private String[] columns;
	private HashMap<String, Integer> rows; // row name references row number.
	
	/**
	 * Create a bare-bones Matrix object where no states are provided.
	 * */
	public Matrix() {
		this.setData(null);
		this.setColumns(null);
		this.setRows(null);
	}
	
	/**
	 * Create a Matrix object given a pre-populated array of values.
	 * */
	public Matrix(double[][] data) {
		this.setData(data);
		this.setColumns(null);
		this.setRows(null);
	}

	/**
	 * @return the data
	 */
	public double[][] getData() {
		return data;
	}
	
	/**
	 * Get a specific row from the Matrix given its row index.
	 * @return entire matrix row
	 * */
	public double[] getRow(int row) {
		return this.getData()[row];
	}
	
	/**
	 * Get a specific column from the matrix given a column index.
	 * @return entire column
	 * */
	public double[] getColumn(int column) {
		double[] col = new double[this.getHeight()];
		for (int i = 0; i < this.getHeight(); i++) {
			col[i] = this.getData()[i][column];
		}
		return col;
	}
	
	/**
	 * Get the width of the current matrix (number of columns).
	 * @return number of columns in the matrix
	 * */
	public int getWidth() {
		return this.getData()[0].length;
	}
	
	/**
	 * Get the height of the current matrix (number of rows).
	 * @return number of rows in the matrix.
	 * */
	public int getHeight() {
		return this.getData().length;
	}
	
	/**
	 * Helper-method for iterating through a matrix and printing each of its
	 * respective variables.
	 * */
	public void debug() {
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				System.out.print(this.getData()[i][j] + "  ");
			}
			System.out.println();
		}
	}

	/**
	 * @param data the data to set
	 */
	private void setData(double[][] data) {
		this.data = data;
	}

	/**
	 * @return the columns
	 */
	public String[] getColumns() {
		return columns;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	/**
	 * @return the rows
	 */
	public HashMap<String, Integer> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(HashMap<String, Integer> rows) {
		this.rows = rows;
	}

}
