package marina.bindingsite;

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
	public boolean sanityCheck() {
		// TODO Auto-generated method stub
		return false;
	}

}
