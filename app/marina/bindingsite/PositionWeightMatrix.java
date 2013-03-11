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
