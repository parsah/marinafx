package marina.bindingsite;

import java.io.IOException;

import marina.matrix.Matrix;

/**
 * A Position Weight Matrix (PWM) is a special type of Matrix in-that it is
 * used for modeling base-specific affinities of nucleotides being a
 * regulatory element. As-is, a PWM means very little since it must be 
 * massaged given an information function. Ultimately, this PWM produces
 * a probability as-to the likelihood a specific DNA sequence has of being
 * a regulatory element.
 * 
 * @author Parsa Hosseini
 * */
public class PositionWeightMatrix extends Matrix implements BindingSite {
	private double columnMins;
	
	public PositionWeightMatrix(double[][] data) {
		super(data);
		this.jitter();
		this.updateColumnSums();
		this.updateMatrixSum();
		this.updateMax();
		this.updateMin();
	}

	@Override
	public boolean sanityCheck() throws IOException{
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Jitter represents the process of replacing zero-values with that
	 * close to zero (i.e. 1e-4).
	 * */
	public void jitter() {
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				double currValue = this.getData()[i][j]; 
				if (currValue == 0) {
					this.getData()[i][j] = 1e-4;
				}
			}
		}
	}
	
	public void updateColumnMins() {
		double[] mins = new double[this.getWidth()];
		for (int col=0; col < this.getWidth(); col++) {
			double min = Matrix.minimum(this.getColumn(col));
			mins[col] = min;
		}
		this.setColumnMins(Matrix.summation(mins));
	}
	
	private double[][] copyData() {
		double[][] data = new double[this.getHeight()][this.getWidth()];
		for (int r = 0; r < data.length; r++) {
			for (int c = 0; c < data[r].length; c++) {
				data[r][c] = this.getData()[r][c];
			}
		}
		return data;
	}
	
	/**
	 * Given base-specific weights, the matrix is to be put into an
	 * information function. In doing so, the matrix becomes one which is
	 * weighted rather than frequency-based.
	 * @throws IOException 
	 * */
	public void information() throws IOException {
		double[][] origFreqMatrix = this.copyData();
		// compute matrix information content given the original matrix.
		for (int r = 0; r < this.getHeight(); r++) {
			for (int c = 0; c < this.getWidth(); c++) {
				double value = origFreqMatrix[r][c] / this.getColumnSums()[r];
				this.getData()[r][c] = PositionWeightMatrix.information(value);
			}
		}
		this.updateColumnSums(); // update matrix-sum after information added.
		// next, apply base-specific weights onto the information matrix
		for (int r = 0; r < this.getHeight(); r++) {
			for (int c = 0; c < this.getWidth(); c++) {
				double weighted = origFreqMatrix[r][c] * 
						this.getColumnSums()[c] * 0.25;
				this.getData()[r][c] = weighted;
			}
		}
		this.updateColumnMins();
		this.updateMatrixSum();
	}

	/**
	 * The P-MATCH information function.
	 * @param double representing PWM weight
	 * @return double representing information content
	 * @throws IOException 
	 * */
	public static final double information(double val) throws IOException {
		if (val <= 0) {
			String m = "PWM information f(x) requires positive matrix values.";
			throw new IOException(m);
		}
		return val * Math.log(4*val);
	}

	/**
	 * @return the columnMins
	 */
	public double getColumnMins() {
		return columnMins;
	}

	/**
	 * @param columnMins the columnMins to set
	 */
	public void setColumnMins(double columnMins) {
		this.columnMins = columnMins;
	}
}
