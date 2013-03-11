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
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean sanityCheck() throws IOException{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * The P-MATCH information function.
	 * @param double representing PWM weight
	 * @return double representing information content
	 * @throws IOException 
	 * */
	public static double information(double value) throws IOException {
		if (value <= 0) {
			String m = "PWM information f(x) requires positive matrix values.";
			throw new IOException(m);
		}
		return value * Math.log(4*value);
	}
}
