package marina.parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * A ListParameter object is anything which has multiple arguments. A good
 * example of such a parameter is nucleotide-specific weights. Each weight,
 * for instance, is assigned a unique weight for-which its sum must equal
 * a value of 1.0. By having a list parameter, one can easily model all
 * associated parameters.
 * @author Parsa Hosseini
 * */
public class BaseWeightParameter extends Parameter {
	private List<DoubleParameter> arguments;

	public BaseWeightParameter(ParameterName name) {
		super(name);
		this.setArguments(new ArrayList<DoubleParameter>());
		this.assignDefaultWeights();
	}
	
	private void assignDefaultWeights() {
		this.getArguments().add(new DoubleParameter(ParameterName.A, 
				0.25, 0, 1));
		this.getArguments().add(new DoubleParameter(ParameterName.T, 
				0.25, 0, 1));
		this.getArguments().add(new DoubleParameter(ParameterName.G, 
				0.25, 0, 1));
		this.getArguments().add(new DoubleParameter(ParameterName.C, 
				0.25, 0, 1));
	}

	/**
	 * @return the arguments
	 */
	public List<DoubleParameter> getArguments() {
		return arguments;
	}

	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(List<DoubleParameter> arguments) {
		this.arguments = arguments;
	}

}
