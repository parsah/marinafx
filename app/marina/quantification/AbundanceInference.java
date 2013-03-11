package marina.quantification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import marina.gui.MarinaGUI;
import marina.matrix.ContingencyMatrix;
import marina.parameter.DoubleParameter;
import marina.parameter.IntegerParameter;
import marina.parameter.Parameter;
import marina.parameter.ParameterName;

/**
 * Determining which binding sites are potentially over-represented is made
 * possible by representing their counts in a contingency matrices. The actual
 * counts in these matrices do not say much as to the abundance of the 
 * corresponding binding site. As a result, abundance must be ranked given
 * output from several pre-implemented statistical metrics.
 * @author Parsa Hosseini
 * */
public class AbundanceInference {
	private CandidateMatrixBuilder builder;
	
	public AbundanceInference(CandidateMatrixBuilder builder) {
		this.setCandidateBuilder(builder);
	}

	/**
	 * Determines whether a candidate contingency matrix passes the user-set
	 * support cutoff
	 * @param ContingencyMatrix object.
	 * @return boolean whether the matrix passes the support cutoff.
	 * */
	public boolean isPassSupport(ContingencyMatrix cm) {
		Parameter p = MarinaGUI.get().parameterMap().get(ParameterName.SUPPORT);
		double userSupport = ((DoubleParameter)(p)).getArgument();
		if (cm.getSupport() >= userSupport) {
			return true;
		}
		return false;
	}

	/**
	 * Determines whether a candidate contingency matrix passes the user-set
	 * difference cutoff
	 * @param ContingencyMatrix object.
	 * @return boolean whether the matrix passes the difference cutoff.
	 * */
	public boolean isPassDifference(ContingencyMatrix cm) {
		Parameter p = MarinaGUI.get().parameterMap().get(ParameterName.DIFF);
		int userDiff = ((IntegerParameter)(p)).getArgument();
		if (cm.getDifference() >= userDiff) {
			return true;
		}
		return false;
	}

	/**
	 * Determines whether a candidate contingency matrix passes the user-set
	 * Laplace correction cutoff
	 * @param ContingencyMatrix object.
	 * @return boolean whether a matrix passes the Laplace correction cutoff.
	 * */
	public boolean isPassLaplace(ContingencyMatrix cm) {
		Parameter p = MarinaGUI.get().parameterMap().get(ParameterName.LAPL);
		double userLaplace = ((DoubleParameter)(p)).getArgument();
		if (cm.getLaplace() >= userLaplace) {
			return true;
		}
		return false;
	}
	
	/**
	 * Feed all candidate contingency matrices through a set of filters
	 * as defined from the Options dialog. These options determine whether a
	 * contingency matrix (and resultant binding site) will be rendered 
	 * over-represented or not. Those contingency matrices which remain
	 * will take the place of the candidate binding sites as they are now
	 * viable for being over-represented.
	 * @return 
	 * @return whether over-represented binding-sites were identified.
	 * @throws IOException 
	 * */
	public List<ContingencyMatrix> representedMatrices() throws IOException {
		List<ContingencyMatrix> valid = new ArrayList<ContingencyMatrix>();
		for (ContingencyMatrix cm: this.getCandidateBuilder().build()) {
			boolean passDiff = this.isPassDifference(cm);
			boolean passLaplace = this.isPassLaplace(cm);
			boolean passSupport = this.isPassSupport(cm);
			if (passDiff && passLaplace && passSupport) {
				valid.add(cm);
			}
		}
		if (valid.size() == 0) {
			String msg = "No binding sites rendered over-represented.";
			throw new IOException(msg);
		}
		return valid;
	}

	/**
	 * @return the candidateBuilder
	 */
	private CandidateMatrixBuilder getCandidateBuilder() {
		return builder;
	}

	/**
	 * @param builder the candidateBuilder to set
	 */
	private void setCandidateBuilder(CandidateMatrixBuilder builder) {
		this.builder = builder;
	}
}
