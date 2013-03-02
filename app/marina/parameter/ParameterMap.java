package marina.parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import marina.gui.Marina;

/**
 * Contains as well as runtime arguments used for analysis throughout Marina.
 * Each parameter is referenced by a string which points to a Parameter object.
 * This object contains the parameter long-name as well as specific 
 * data-type.
 * @author Parsa Hosseini
 * */
public class ParameterMap extends LinkedHashMap<String, Parameter>{
	private static final long serialVersionUID = 1L;
	
	public ParameterMap() {
		super();
		this.mapDefaultArguments();
	}
	
	/**
	 * Helper-method which maps parameters to their default arguments. 
	 * Modifying these values are only made possible through the GUI, hence
	 * why this process is not made public.
	 * */
	private void mapDefaultArguments() {
		int numWorkers = Marina.getNumWorkers();
		NumericParameter supp = new NumericParameter("Support", 0, 0, 100);
		NumericParameter diff = new NumericParameter("Difference", 4, 0, 100);
		NumericParameter len = new NumericParameter("Length", 6, 0, 100);
		NumericParameter count = new NumericParameter("Count", 3, 0, 100);
		NumericParameter pwm = new NumericParameter("PWM cutoff", 0.8, 0, 1.0);
		NumericParameter lapl = new NumericParameter("Laplace", 0.3, 0, 1.0);
		NumericParameter num = new NumericParameter("#/workers", 1, 1, numWorkers);
		NumericParameter pVal = new NumericParameter("p-value", 0.05, 0, 1.0);
		BooleanParameter ipf = new BooleanParameter("IPF standardize", false);
		BaseWeightParameter weights = new BaseWeightParameter("Weights");
		List<Parameter> paramSet = new ArrayList<Parameter>();
		Collections.addAll(paramSet, supp, diff, len, count, pwm, lapl, num, 
				pVal, ipf, weights); // add all parameters to our global-set
		for (Parameter p: paramSet) {
			this.put(p.getName(), p);
		}
	}

}
