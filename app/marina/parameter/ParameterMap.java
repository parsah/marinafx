package marina.parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

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
		int numWorkers = Runtime.getRuntime().availableProcessors();
		IntegerParameter supp = new IntegerParameter("Support", 0, 0, 100);
		IntegerParameter diff = new IntegerParameter("Difference", 4, 0, 100);
		IntegerParameter len = new IntegerParameter("Length", 6, 0, 100);
		IntegerParameter count = new IntegerParameter("Count", 3, 0, 100);
		DoubleParameter pwm = new DoubleParameter("PWM cutoff", 0.8, 0, 1.0);
		DoubleParameter lapl = new DoubleParameter("Laplace", 0.3, 0, 1.0);
		DoubleParameter pVal = new DoubleParameter("p-value", 0.05, 0, 1.0);
		IntegerParameter worker = new IntegerParameter("#/workers", 1, 1, numWorkers);
		BooleanParameter ipf = new BooleanParameter("IPF standardize", false);
		BaseWeightParameter weights = new BaseWeightParameter("Weights");
		List<Parameter> paramSet = new ArrayList<Parameter>();
		Collections.addAll(paramSet, supp, diff, len, count, pwm, lapl, 
				pVal, worker, ipf, weights); // add parameters to global set
		for (Parameter p: paramSet) {
			this.put(p.getName(), p);
		}
	}

}
