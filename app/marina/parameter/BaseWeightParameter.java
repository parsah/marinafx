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
	private List<NumericParameter> arguments;

	public BaseWeightParameter(String name) {
		super(name);
		this.setArguments(new ArrayList<NumericParameter>());
		this.assignDefaultWeights();
	}
	
	private void assignDefaultWeights() {
		this.getArguments().add(new NumericParameter("A", 0.25, 0, 1));
		this.getArguments().add(new NumericParameter("T", 0.25, 0, 1));
		this.getArguments().add(new NumericParameter("G", 0.25, 0, 1));
		this.getArguments().add(new NumericParameter("C", 0.25, 0, 1));
	}

	/**
	 * @return the arguments
	 */
	public List<NumericParameter> getArguments() {
		return arguments;
	}

	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(List<NumericParameter> arguments) {
		this.arguments = arguments;
	}

}
