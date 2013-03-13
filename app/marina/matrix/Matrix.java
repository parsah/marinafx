package marina.matrix;

import java.text.DecimalFormat;
import java.util.Map;

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
	private int[] columns;
	private Map<String, Integer> rows; // row name references row number.
	private String name; // name to identify the matrix by.

	/**
	 * Create a Matrix object given a pre-populated array of values.
	 * */
	public Matrix(double[][] data) {
		this.setData(data);
		this.setColumns(null);
		this.setRows(null);
		this.sum();
	}

	/**
	 * @return the data
	 */
	public double[][] getData() {
		return data;
	}
	
	/**
	 * Compute sum of all the matrix values.
	 * @return double representing Matrix sum.
	 * */
	public double sum() {
		double sum = 0;
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				sum += this.getData()[i][j];
			}
		}
		return sum;
	}
	
	public void round() {
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				DecimalFormat f = new DecimalFormat("#.####");
				String val = f.format(this.getData()[i][j]);
				this.getData()[i][j] = Double.valueOf(val).doubleValue();
			}
		}
	}
	
	/**
	 * Identification of matrix-wide minimum value
	 * */
	public double min() {
		double minValue = Double.MAX_VALUE;
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				double currValue = this.getData()[i][j]; 
				if (currValue <= minValue) {
					minValue = currValue;
				}
			}
		}
		return minValue;
	}
	
	/**
	 * Identification of matrix-wide maximum value
	 * */
	public double max() {
		double maxValue = Double.MIN_VALUE;
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				double currValue = this.getData()[i][j]; 
				if (currValue >= maxValue) {
					maxValue = currValue;
				}
			}
		}
		return maxValue;
	}
	
	/**
	 * Compute column-sums for each matrix.
	 * */
	public double[] columnSums() {
		double[] sums = new double[this.getWidth()];
		for (int i=0; i < this.getWidth(); i++) {
			sums[i] = Matrix.summation(this.getColumn(i));
		}
		return sums;
	}
	
	public double sumColumnMins() {
		double[] mins = new double[this.getWidth()];
		for (int col=0; col < this.getWidth(); col++) {
			double min = Matrix.minimum(this.getColumn(col));
			mins[col] = min;
		}
		return Matrix.summation(mins);
	}
	
	/**
	 * Compute sum of a given array; useful in cases where a column or row
	 * is returned.
	 * @param array of values.
	 * @return sum of the provided list.
	 * */
	public static double summation(double[] vals) {
		double sum = 0;
		for (int i = 0; i < vals.length; i++) {
			sum += vals[i];
		}
		return sum;
	}
	
	public static double minimum(double[] vals) {
		double minValue = Double.MAX_VALUE;
		for (int i=0; i < vals.length; i++) {
			if (vals[i] <= minValue) {
				minValue = vals[i];
			}
		}
		return minValue;
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
	 * Determines whether a specific value is found in the matrix.
	 * @param double representing value to find.
	 * @return boolean whether the value is present in the matrix.
	 * */
	public boolean contains(double value) {
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				if (value == this.getData()[i][j]) {
					return true;
				}
			}
		}
		return false;
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
	public int[] getColumns() {
		return columns;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(int[] columns) {
		this.columns = columns;
	}

	/**
	 * @return the rows
	 */
	public Map<String, Integer> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(Map<String, Integer> rows) {
		this.rows = rows;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
