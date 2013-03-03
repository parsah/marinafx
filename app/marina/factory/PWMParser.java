package marina.factory;

import java.io.File;
import java.io.IOException;

import marina.bindingsite.PositionWeightMatrix;

/**
 * Position Weight Matrices (PWMs) are matrices which represent frequencies
 * as-to likelihood of specific TFBSs being a regulatory element. Parsing
 * such a matrix is necessary as it must pass both length requirements as well
 * as various checks apart of the PWM.
 * 
 * @author Parsa Hosseini
 * @see PositionWeightMatrix
 * */
public class PWMParser extends Parser {

	public PWMParser(File file) {
		super(file);
	}
	
	public PWMParser() {
		super(null);
	}

	@Override
	public void parse() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void filter(int minLen) {
		// TODO Auto-generated method stub
		
	}
}
