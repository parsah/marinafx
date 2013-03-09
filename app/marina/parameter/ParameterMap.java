package marina.parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import marina.group.Group;
import marina.parser.DNAMotifParser;
import marina.parser.PWMParser;

/**
 * Contains as well as runtime arguments used for analysis throughout Marina.
 * Each parameter is referenced by a string which points to a Parameter object.
 * This object contains the parameter long-name as well as specific 
 * data-type.
 * @author Parsa Hosseini
 * */
public class ParameterMap extends LinkedHashMap<String, Parameter>{
	private static final long serialVersionUID = 1L;
	private Group queryGroup;
	private Group baselineGroup;
	private DNAMotifParser motifParser;
	private PWMParser pwmParser;
	private boolean alignmentSuccess;
	
	public ParameterMap() {
		super();
		this.mapDefaultArguments();
		this.setBaseline(null);
		this.setQuery(null);
		this.setMotifParser(null);
		this.setPWMParser(null);
		this.setAlignmentSuccess(false);
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
				pVal, worker, ipf, weights); // add parameters to global-set
		for (Parameter p: paramSet) {
			this.put(p.getName(), p);
		}
	}
	
	/**
	 * Determines if all the weight-sums equal to one (1). If this sum does
	 * not equal to one, the user cannot progress. By default, all four
	 * weights are assigned a weight of 0.25 each.
	 * @return boolean whether all four weights sum to 1.0.
	 * */
	public boolean hasValidWeights() {
		BaseWeightParameter w = (BaseWeightParameter)this.get("Weights");
		double sum = 0;
		for (DoubleParameter np: w.getArguments()) {
			sum += np.getArgument();
		}
		if (sum == 1.0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determine if enough arguments are provided to enable subsequent
	 * execution. In order to make this possible, 2x FASTA files must
	 * be provided (1x for the baseline, 1x for the query) as well as
	 * either a DNA motifs and/or PWMs.
	 * @return boolean whether execution can go on.
	 * */
	public boolean canRun() {
		boolean hasParser = (this.getMotifParser() != null || 
				this.getPWMParser() != null );
		boolean hasFASTA = (this.getQuery() != null && 
				this.getBaseline() != null);
		return hasFASTA && hasParser; // both clauses must be met to continue
	}

	/**
	 * @return the queryGroup
	 */
	public Group getQuery() {
		return queryGroup;
	}
	
	/**
	 * @param queryGroup the queryGroup to set
	 */
	public void setQuery(Group queryGroup) {
		this.queryGroup = queryGroup;
	}

	/**
	 * @return the baselineGroup
	 */
	public Group getBaseline() {
		return baselineGroup;
	}

	/**
	 * @param baselineGroup the baselineGroup to set
	 */
	public void setBaseline(Group baselineGroup) {
		this.baselineGroup = baselineGroup;
	}

	/**
	 * @return the motifParser
	 */
	public DNAMotifParser getMotifParser() {
		return motifParser;
	}

	/**
	 * @param motifParser the motifParser to set
	 */
	public void setMotifParser(DNAMotifParser motifParser) {
		this.motifParser = motifParser;
	}

	/**
	 * @return the pwmParser
	 */
	public PWMParser getPWMParser() {
		return pwmParser;
	}

	/**
	 * @param pwmParser the pwmParser to set
	 */
	public void setPWMParser(PWMParser pwmParser) {
		this.pwmParser = pwmParser;
	}

	/**
	 * @return the alignmentSuccess
	 */
	public boolean isAlignmentSuccess() {
		return alignmentSuccess;
	}

	/**
	 * @param state whether alignment was a success or not
	 */
	public void setAlignmentSuccess(boolean state) {
		this.alignmentSuccess = state;
	}
}
