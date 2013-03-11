package marina.bindingsite;

import java.io.IOException;

import marina.gui.MarinaGUI;
import marina.matrix.Matrix;
import marina.parameter.BaseWeightParameter;
import marina.parameter.DoubleParameter;
import marina.parameter.ParameterName;

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
	
	/**
	 * Given base-specific weights, the matrix is to be put into an
	 * information function. In doing so, the matrix becomes one which is
	 * weighted rather than frequency-based.
	 * @throws IOException 
	 * */
	public void information() throws IOException {
		double[][] origFreqMatrix = this.getData().clone();
		// compute matrix information content given the original matrix.
		for (int c = 0; c < this.getHeight(); c++) {
			for (int r = 0; r < this.getWidth(); r++) {
				double value = origFreqMatrix[c][r] / this.getColumnSums()[c];
				this.getData()[c][r] = PositionWeightMatrix.information(value);
			}
		}
		this.updateColumnSums(); // update matrix-sum after information added.
		// next, apply base-specific weights onto the information matrix
		for (int c = 0; c < this.getHeight(); c++) {
			for (int r = 0; r < this.getWidth(); r++) {
//				double noWeight = origFreqMatrix[c][r] * this.getColumnSums()[r];
//				this.getRows()this.getColumnSums()[c];
			}
		}
		
//		for (DoubleParameter p: ((BaseWeightParameter)MarinaGUI.get().parameterMap().get(ParameterName.WEIGHTS)).getArguments()) {
//			System.out.println(p.getName().get()+"\t"+p.getArgument());
//		}
		
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
}
