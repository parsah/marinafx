package marina.matrix;

import marina.bindingsite.BindingSite;


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
		return this.getProbability(ContingencyMatrixCell.X_AND_G) * 100;
	}

	/**
	 * Computes in-place Iterative Proportional Fitting (IPF). Given a 
	 * matrix with cells f(0, 0), f(1, 0), f(0, 1) and f(1, 1), 
	 * cells f(0, 0) and f(1, 1) will have the same value, x. Values 
	 * for f(1, 0) and f(0, 1) will however be (N/2) - x.
	 * */
	public void ipf() {
		double xAndG = this.getFrequency(ContingencyMatrixCell.X_AND_G);
		double notXNotG = this.getFrequency(ContingencyMatrixCell.NOT_X_AND_NOT_G);
		double notXAndG = this.getFrequency(ContingencyMatrixCell.NOT_X_AND_G);
		double xAndNotG = this.getFrequency(ContingencyMatrixCell.X_AND_NOT_G);
		// create variables to perform IPF normalization
		double numer = this.sum() * Math.sqrt(xAndG * notXNotG);
		double denom = 2 * (Math.sqrt(notXAndG * xAndG) + 
				Math.sqrt(xAndNotG * notXAndG));
		// adjust matrix counts for f(0, 0) and f(1, 1)
		this.getData()[1][1] = this.getData()[0][0] = (numer / denom);
		// adjust matrix counts for f(0, 1) and f(1, 0)
		double diagValue = (this.sum() / 2) - xAndG;
		this.getData()[1][0] = this.getData()[0][1] = diagValue;
		this.sum();
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
	 * Return the Laplace correction value for the respective matrix.
	 * @return Laplace correction.
	 * */
	public double getLaplace() {
		return (this.getFrequency(ContingencyMatrixCell.X_AND_G) + 1) /
				(Matrix.summation(this.getX()) + 2);
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
