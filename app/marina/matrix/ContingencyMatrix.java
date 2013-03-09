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
	 * ContingencyIndex enumeration, the locations of each variable as well
	 * as its respective group are preset. As a result, such indices can be
	 * easily retrieved and represented as frequencies or probabilities. This
	 * ensures an additional class is not created to solely work with
	 * probabilities.
	 * @param ContingencyIndex enumeration which represents a specific index.
	 * @return double representing probability of contingency matrix index.
	 * */
	public double getProbability(ContingencyIndex idx) {
		double freq = this.getData()[idx.getRow()][idx.getColumn()];
		return freq / this.getSum();
	}
	
	/**
	 * Retrieve the actual value in the contingency matrix as-is and without
	 * any pre-computed probability.
	 * @return double representing the contingency matrix value.
	 * */
	public double getFrequency(ContingencyIndex idx) {
		return this.getData()[idx.getRow()][idx.getColumn()];
	}
	
	public double getSupport() {
		// TODO implement support
		return 0;
	}
	
	public double getDifference() {
		// TODO implement difference
		return 0;
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
