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
	
	public PositionWeightMatrix(double[][] data) {
		super(data);
		this.jitter();
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
	public PositionWeightMatrix buildInformation() throws IOException {
		double[][] origFreqMatrix = this.copyData();
		double[][] infoData = new double[this.getHeight()][this.getWidth()];
		// compute matrix information content given the original matrix.
		for (int r = 0; r < this.getHeight(); r++) {
			for (int c = 0; c < this.getWidth(); c++) {
				double currValue = origFreqMatrix[r][c];
				double currColSum = this.columnSums()[c];
				// feed data into the information-function
				double infoValue = (currValue / currColSum) * 
						PositionWeightMatrix.information(currValue / currColSum);
				infoData[r][c] = infoValue;
			}
		}
		PositionWeightMatrix infoMat = new PositionWeightMatrix(infoData);
		infoMat.setRows(this.getRows());
		infoMat.setColumns(this.getColumns());
		infoMat.setName(this.getName());
		double[] columnSums = infoMat.columnSums();
		for (int r = 0; r < infoMat.getHeight(); r++) {
			for (int c = 0; c < infoMat.getWidth(); c++) {
				double weighted = origFreqMatrix[r][c] * 
						columnSums[c] * 0.25; // base-specific weights
				infoMat.getData()[r][c] = weighted;
			}
		}
		return infoMat;
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
		return Math.log(4*val);
	}
	
	/**
	 * Return a string-representation of the current PWM.
	 * */
	@Override
	public String toString() {
		return this.getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if(obj instanceof PositionWeightMatrix) { // equality based on name
			PositionWeightMatrix other = (PositionWeightMatrix)obj;
			return this.getName().equals(other.getName());
		}
		return false;
	}
	
	@Override
	public int hashCode() { // Gene name is unique.
		return this.getName().hashCode();
	}
}
