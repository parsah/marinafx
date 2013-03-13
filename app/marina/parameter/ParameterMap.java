package marina.parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import marina.group.Group;
import marina.gui.MarinaGUI;
import marina.parser.DNAMotifParser;
import marina.parser.PWMParser;

/**
 * Contains as well as runtime arguments used for analysis throughout Marina.
 * Each parameter is referenced by a string which points to a Parameter object.
 * This object contains the parameter long-name as well as specific 
 * data-type.
 * @author Parsa Hosseini
 * */
public class ParameterMap extends LinkedHashMap<ParameterName, Parameter>{
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
		IntegerParameter diff = new IntegerParameter(ParameterName.DIFF, 4, 0, 100);
		IntegerParameter len = new IntegerParameter(ParameterName.LENGTH, 6, 0, 100);
		IntegerParameter count = new IntegerParameter(ParameterName.COUNT, 0, 0, 100);
		DoubleParameter supp = new DoubleParameter(ParameterName.SUPPORT, 0, 0, 100);
		DoubleParameter pwm = new DoubleParameter(ParameterName.PWM_CUTOFF, 0.80, 0, 1.0);
		DoubleParameter lapl = new DoubleParameter(ParameterName.LAPL, 0.3, 0, 1.0);
		DoubleParameter pVal = new DoubleParameter(ParameterName.P_VALUE, 0.05, 0, 1.0);
		BooleanParameter ipf = new BooleanParameter(ParameterName.IPF, false);
		List<Parameter> paramSet = new ArrayList<Parameter>();
		// add parameters to global-set
		Collections.addAll(paramSet, diff, len, count, supp, pwm, lapl, 
				pVal, ipf); 
		for (Parameter p: paramSet) {
			this.put(p.getName(), p);
		}
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
	 * Converts a specific parameter to type-double.
	 * @return double representing the specific parameter
	 * */
	public static double toDouble(ParameterName name) {
		ParameterMap paramMap = MarinaGUI.get().parameterMap();
		DoubleParameter param = (DoubleParameter)paramMap.get(name);
		return param.getArgument();
	}
	
	/**
	 * Converts a specific parameter to type-integer.
	 * @return integer representing the specific parameter
	 * */
	public static int toInteger(ParameterName name) {
		ParameterMap paramMap = MarinaGUI.get().parameterMap();
		IntegerParameter param = (IntegerParameter)paramMap.get(name);
		return param.getArgument();
	}
	
	/**
	 * Converts a specific parameter to type-boolean.
	 * @return boolean representing the specific parameter
	 * */
	public static boolean toBoolean(ParameterName name) {
		ParameterMap paramMap = MarinaGUI.get().parameterMap();
		BooleanParameter param = (BooleanParameter)paramMap.get(name);
		return param.getArgument();
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
	 * @param parser the pwmParser to set
	 */
	public void setPWMParser(PWMParser parser) {
		this.pwmParser = parser;
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
