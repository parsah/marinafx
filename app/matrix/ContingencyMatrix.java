package matrix;

import java.io.IOException;

import quantification.Metric;

import bindingsite.BindingSite;



/**
 * A Contingency Matrix is a Matrix used to model abundances across various
 * groups. Often, such a matrix is of 2x2 dimensions; each row is a variable
 * and each column is a categorical group.
 * @author Parsa Hosseini
 * */
public class ContingencyMatrix extends Matrix {
	private BindingSite bindingSite; // binding site representing the matrix.

	/**
	 * A Contingency Matrix is a 2 by 2 Matrix, used for computing magnitude
	 * of statistical significance across various categorical groups.
	 * */
	public ContingencyMatrix() {
		super(new double[2][2]); // each contingency matrix is 2x2 in size.
	}

	/**
	 * Create a Contingency Matrix given a pre-constructed multi-dimensional
	 * matrix.
	 * */
	public ContingencyMatrix(double[][] data) {
		super(data);
	}

	/**
	 * Return the row representing the variable X across groups G and not-G.
	 * @return double-array for discrete variable X.
	 * */
	public double[] getX() {
		return this.getData()[0];
	}

	/**
	 * Return the row representing the variable not-X given groups G and not-G.
	 * @return double-array representing discrete variable not-X.
	 * */
	public double[] getNotX() {
		return this.getData()[1];
	}

	/**
	 * Return the column representing the group G across variables X and not-X.
	 * @return double-array representing values for group G.
	 * */
	public double[] getG() {
		return this.getColumn(0);
	}

	/**
	 * Return the column representing the group not-G across X and not-X.
	 * @return double-array representing values for group not-G.
	 * */
	public double[] getNotG() {
		return this.getColumn(1);
	}

	/**
	 * A Contingency Matrix represents frequency counts. Such a 
	 * representation is beneficial, however oftentimes, a specific segment
	 * of the matrix is to be represented as a probability. By having a
	 * ContingencyMatrixCell enumeration, the locations of each variable as 
	 * well as its respective group are preset. As a result, such indices can 
	 * be easily retrieved and represented as frequencies or probabilities. 
	 * This ensures an additional class is not created to solely work with
	 * probabilities.
	 * @param ContingencyMatrixCell a value in the contingency matrix.
	 * @return double representing probability of contingency matrix index.
	 * */
	public double getProbability(ContingencyMatrixCell idx) {
		double freq = this.getData()[idx.getRow()][idx.getColumn()];
		return freq / this.sum();
	}

	/**
	 * A Contingency Matrix contains frequency-counts, however retrieving
	 * multiple probabilities can be difficult to track. To aide such efforts,
	 * the current contingency matrix is converted to its equivalent 
	 * probability-matrix.
	 * @return ContingencyMatrix object where counts are probabilities.
	 * */
	public ContingencyMatrix toProbability() {
		double[][] data = new double[this.getHeight()][this.getWidth()];
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				data[i][j] = this.getData()[i][j] / this.sum();
			}
		}
		ContingencyMatrix prob = new ContingencyMatrix(data);
		prob.setName(this.getName());
		prob.setBindingSite(this.getBindingSite());
		return prob;
	}

	/**
	 * Retrieve the actual value in the contingency matrix as-is and without
	 * any pre-computed probability.
	 * @param ContingencyMatrixCell a value in the contingency matrix.
	 * @return double representing the contingency matrix value.
	 * */
	public double getFrequency(ContingencyMatrixCell idx) {
		return this.getData()[idx.getRow()][idx.getColumn()];
	}


	/**
	 * Support is the probability as to how many times the variable, X, is
	 * found in the group, G. This probability is then multiplied by 100. 
	 * @return double representing contingency matrix support.
	 * */
	public double getSupport() {
		double suppG = this.getProbability(
				ContingencyMatrixCell.X_AND_G) * 100;
		double suppNotG = this.getProbability(
				ContingencyMatrixCell.X_AND_NOT_G) * 100;
		return Math.max(suppG, suppNotG);
	}
	
	/**
	 * Difference is the absolute-value when you subtract the frequencies 
	 * of a variable, X, given its presence in both groups G and not-G.
	 * @return difference of variable X across groups G and not-G (!G).
	 * */
	public double getDifference() {
		return Math.abs(this.getFrequency(ContingencyMatrixCell.X_AND_G) - 
				this.getFrequency(ContingencyMatrixCell.X_AND_NOT_G));
	}

	/**
	 * Computes in-place Iterative Proportional Fitting (IPF). Given a 
	 * matrix with cells f(0, 0), f(1, 0), f(0, 1) and f(1, 1), 
	 * cells f(0, 0) and f(1, 1) will have the same value, x. Values 
	 * for f(1, 0) and f(0, 1) will however be (N/2) - x.
	 * */
	public void ipf() {		
		// create variables to perform IPF normalization
		double oldSum = this.sum();
		double numer = oldSum * Math.sqrt(
				this.getFrequency(ContingencyMatrixCell.X_AND_G) * 
				this.getFrequency(ContingencyMatrixCell.NOT_X_AND_NOT_G));
		double denom = 2 * (Math.sqrt(this.getFrequency(ContingencyMatrixCell.NOT_X_AND_NOT_G) * 
				this.getFrequency(ContingencyMatrixCell.X_AND_G)) + 
				Math.sqrt(this.getFrequency(ContingencyMatrixCell.X_AND_NOT_G) * 
						this.getFrequency(ContingencyMatrixCell.NOT_X_AND_G)));
		// adjust matrix counts for f(0, 0) and f(1, 1)
		this.getData()[1][1] = (numer / denom);
		this.getData()[0][0] = (numer / denom);
		// adjust matrix counts for f(0, 1) and f(1, 0)
		double diagValue = (oldSum / 2) - this.getFrequency(ContingencyMatrixCell.X_AND_G);
		this.getData()[1][0] = diagValue;
		this.getData()[0][1] = diagValue;
		this.sum();
	}

	/**
	 * Converts a contingency matrix into log-base(n) format; useful in
	 * instances where cells are considerably large and downstream
	 * computations yield outputs unable to be assigned to primitive 
	 * data-types.
	 * @return 
	 * */
	public ContingencyMatrix log(int base) {
		double[][] data = new double[this.getHeight()][this.getWidth()];
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				double val = Math.log(this.getData()[i][j]) / Math.log(base);
				data[i][j] = val;
			}
		}
		ContingencyMatrix cm = new ContingencyMatrix(data);
		cm.setColumns(this.getColumns());
		cm.setName(this.getName());
		cm.setRows(this.getRows());
		return cm;
	}

	public double[] metricValues() throws IOException {
		double[] values = new double[Metric.Name.values().length];
		for (int i = 0; i < values.length; i++) {
			Metric.Name metric = Metric.Name.values()[i];
			switch (metric) {
			case PVALUE:
				values[i] = Metric.pValue(this.log(2));
				break;
			case CONFIDENCE:
				values[i] = Metric.confidence(this);
				break;
			case COSINE:
				values[i] = Metric.cosine(this);
				break;
			case JACCARD:
				values[i] = Metric.jaccard(this);
				break;
			case KAPPA:
				values[i] = Metric.kappa(this);
				break;
			case LAPLACE:
				values[i] = Metric.laplace(this);
				break;
			case LIFT:
				values[i] = Metric.lift(this);
				break;
			case NUM_BASELINE:
				values[i] = Metric.mumBaseline(this);
				break;
			case NUM_QUERY:
				values[i] = Metric.numQuery(this);
				break;
			case PHI:
				values[i] = Metric.phi(this);
				break;
			}
		}
		return values;
	}

	/**
	 * Trivial String function for the contingency matrix.
	 * */
	@Override
	public String toString() {
		return this.getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if(obj instanceof ContingencyMatrix) { // equality on matrix name
			ContingencyMatrix other = (ContingencyMatrix)obj;
			return this.getName().equals(other.getName());
		}
		return false;
	}
	
	@Override
	public int hashCode() { // ContingencyMatrix name is unique
		return this.getName().hashCode();
	}

	/**
	 * @return the bindingSite
	 */
	public BindingSite getBindingSite() {
		return bindingSite;
	}

	/**
	 * @param tfbs the bindingSite to set
	 */
	public void setBindingSite(BindingSite tfbs) {
		this.bindingSite = tfbs;
	}
}
