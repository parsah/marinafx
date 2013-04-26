package quantification;

import gui.MarinaGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import parameter.DoubleParameter;
import parameter.IntegerParameter;
import parameter.Parameter;
import parameter.ParameterMap;
import parameter.ParameterName;

import matrix.ContingencyMatrix;
import matrix.Matrix;

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
	private Matrix unOrderedMatrix;
	private Matrix orderedMatrix;
	
	public AbundanceInference(CandidateMatrixBuilder builder) {
		this.setCandidateBuilder(builder);
		this.setOrderedMatrix(null);
		this.setUnOrderedMatrix(null);
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
		if (Metric.laplace(cm) >= userLaplace) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determines whether a contingency matrix passes the user-defined
	 * p-value cutoff.
	 * @param ContingencyMatrix object.
	 * @return boolean whether a matrix passes the p-value cutoff.
	 * */
	public boolean isPassPValue(ContingencyMatrix cm) {
		Parameter p = MarinaGUI.get().parameterMap().get(ParameterName.P_VALUE);
		double userPValue = ((DoubleParameter)(p)).getArgument();
		if (Metric.pValue(cm.log(2)) <= userPValue) {
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
		List<ContingencyMatrix> cms = new ArrayList<ContingencyMatrix>();
		for (ContingencyMatrix cm: this.getCandidateBuilder().build()) {
			boolean passDiff = this.isPassDifference(cm);
			boolean passLaplace = this.isPassLaplace(cm);
			boolean passSupport = this.isPassSupport(cm);
			boolean passPValue = this.isPassPValue(cm);
			if (passDiff && passLaplace && passSupport && passPValue) {
				if (ParameterMap.toBoolean(ParameterName.IPF)) {
					cm.ipf(); // IPF-standardize matrix if selected.
				}
				cms.add(cm);
			}
		}
		if (cms.size() == 0) {
			String msg = "No binding sites rendered over-represented.";
			throw new IOException(msg);
		}
		return cms; // return a list of over-represented binding-sites
	}
	
	/**
	 * Each contingency matrix is fed into a collection of various 
	 * statistical metrics. In doing so, magnitude of abundance for the
	 * contingency matrix can be ranked and made easier to interpret. 
	 * To make this possible, output per statistical metric is saved into 
	 * a centralized matrix object. This object can then be ordered 
	 * so that most over-represented contingency matrices are ranked 
	 * close to 1.0 and least over-represented are ranked farthest from 1.0.
	 * An unordered matrix is also present if such ranking is not desired.
	 * @throws IOException 
	 * */
	public void bindAbundances() throws IOException {
		List<ContingencyMatrix> overReps = this.representedMatrices();
		double[][] data = new double[overReps.size()][Metric.Name.values().length];
		Map<Object, Integer> rowNames = new LinkedHashMap<Object, Integer>();
		for (int i=0; i < overReps.size(); i++) {
			ContingencyMatrix cm = overReps.get(i);
			data[i] = cm.metricValues();
			rowNames.put(cm, i); // reference entire matrix and binding site
		}
		// java states enumerations are returned in declaration order.
		Matrix m = new Matrix(data);
		m.setColumns(Metric.Name.values());
		m.setRows(rowNames);
		this.setUnOrderedMatrix(new Matrix(m));
		this.setOrderedMatrix(new Matrix(m));
		this.orderMatrix(); // rank matrix so it can be from 1 to N.
	}
	
	/**
	 * Ranks and sorts a matrix such that only those columns having
	 * statistical metrics are processed. All other columns are not ranked.
	 * @return 
	 * */
	private void orderMatrix() {
		Metric.Name[] metrics = Metric.Name.values();
		for (int colNum = 0; colNum < this.getUnOrderedMatrix().getWidth(); colNum++) {
			double[] theColumn = this.getUnOrderedMatrix().getColumn(colNum);
			Metric.Name col = metrics[colNum];
			if (col.isStat()) {
				int[] ranks = Statistic.rank(theColumn);
				for (int rowNum = 0; rowNum < theColumn.length; rowNum++) {
					this.getOrderedMatrix().getData()[rowNum][colNum] = 
							ranks[rowNum];
				}
			}
		}
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

	/**
	 * @return the unOrderedMatrix
	 */
	public Matrix getUnOrderedMatrix() {
		return unOrderedMatrix;
	}

	/**
	 * @param unOrderedMatrix the unOrderedMatrix to set
	 */
	private void setUnOrderedMatrix(Matrix unOrderedMatrix) {
		this.unOrderedMatrix = unOrderedMatrix;
	}

	/**
	 * @return the orderedMatrix
	 */
	public Matrix getOrderedMatrix() {
		return orderedMatrix;
	}

	/**
	 * @param orderedMatrix the orderedMatrix to set
	 */
	private void setOrderedMatrix(Matrix orderedMatrix) {
		this.orderedMatrix = orderedMatrix;
	}
}
