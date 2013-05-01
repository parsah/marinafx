package quantification;

import java.math.BigDecimal;
import java.math.MathContext;

import matrix.ContingencyMatrix;
import matrix.ContingencyMatrixCell;

/**
 * A class for modeling all metric-related functionality, including the
 * actual name of the metric and its respective equation.
 * @author Parsa Hosseini
 * */
public abstract class Metric {

	/**
	 * Return the Laplace (LP) correction value for the respective matrix.
	 * @return double representing Laplace correction.
	 * */
	public static double laplace(ContingencyMatrix cm) {
		return (cm.getFrequency(ContingencyMatrixCell.X_AND_G) + 1) /
				(Statistic.summation(cm.getX()) + 2);
	}

	/**
	 * Computes confidence (CI) measure for the current contingency matrix.
	 * @return double representing the confidence measure.
	 * */
	public static double confidence(ContingencyMatrix cm) {
		return cm.getProbability(ContingencyMatrixCell.X_AND_G) /
				Statistic.summation(cm.toProbability().getX());

	}

	/**
	 * Computes the lift (LI) measure for the current contingency matrix.
	 * @return double representing lift measure.
	 * */
	public static double lift(ContingencyMatrix cm) {
		ContingencyMatrix prob = cm.toProbability();
		double numer = cm.getProbability(ContingencyMatrixCell.X_AND_G);
		double denom = Statistic.summation(prob.getX()) * 
				Statistic.summation(prob.getG());
		return numer / denom;
	}

	/**
	 * Computes the Cosine (CO) measure for the current contingency matrix.
	 * @return double representing the cosine measure.
	 * */
	public static double cosine(ContingencyMatrix cm) {			        		
		ContingencyMatrix prob = cm.toProbability();
		double top = cm.getProbability(ContingencyMatrixCell.X_AND_G);
		double bottom = Math.sqrt(Statistic.summation(prob.getX()) *
				Statistic.summation(prob.getG()));
		return top / bottom;
	}

	/**
	 * Computes the Jaccard (JAC) measure for the current contingency matrix.
	 * @return double representing the Jaccard measure.
	 * */
	public static double jaccard(ContingencyMatrix cm) {
		ContingencyMatrix prob = cm.toProbability();
		double numer = cm.getProbability(ContingencyMatrixCell.X_AND_G);
		double denom = Statistic.summation(prob.getX()) +
				Statistic.summation(prob.getG()) -
				cm.getProbability(ContingencyMatrixCell.X_AND_G);
		return numer / denom;
	}

	/**
	 * Computes the Kappa coefficient (K) for the current contingency matrix.
	 * @return double representing the Kappa coefficient measure.
	 * */
	public static double kappa(ContingencyMatrix cm) {
		ContingencyMatrix prob = cm.toProbability();
		double numer = cm.getProbability(ContingencyMatrixCell.X_AND_G) +
				cm.getProbability(ContingencyMatrixCell.NOT_X_AND_NOT_G) -
				Statistic.summation(prob.getX()) *
				Statistic.summation(prob.getG()) -
				Statistic.summation(prob.getNotX()) *
				Statistic.summation(prob.getNotG());
		double denom = 1 - Statistic.summation(prob.getX()) *
				Statistic.summation(prob.getG()) -
				Statistic.summation(prob.getNotX()) *
				Statistic.summation(prob.getNotG());
		return numer / denom;
	}

	/**
	 * Computes the Phi coefficient (PHI) for the current contingency matrix.
	 * @return double representing the Phi coefficient measure.
	 * */
	public static double phi(ContingencyMatrix cm) {
		ContingencyMatrix prob = cm.toProbability();
		double top = cm.getProbability(ContingencyMatrixCell.X_AND_G) -
				Statistic.summation(prob.getX()) *
				Statistic.summation(prob.getG());
		double bottom = Statistic.summation(prob.getX()) *
				Statistic.summation(prob.getG()) *
				(1 - Statistic.summation(prob.getX())) *
				(1 - Statistic.summation(prob.getG()));
		return top / Math.sqrt(bottom);
	}
	
	/**
	 * Computes a p-value for the provided ContingencyMatrix object.
	 * @param cm A ContingencyMatrix object.
	 * @return double representing the hypergeometric p-value.
	 * */
	public static double computePvalue(ContingencyMatrix cm) {
		int x = (int)cm.getFrequency(ContingencyMatrixCell.X_AND_G);
		int N = (int)cm.sum();
		int M = (int)Statistic.summation(cm.getX());
		int n = (int)cm.getFrequency(ContingencyMatrixCell.X_AND_G);
		BigDecimal m_give_x = new BigDecimal(Statistic.combinatorial(M, x));
		BigDecimal nmGiveNx = new BigDecimal(Statistic.combinatorial(N - M, n - x));
		BigDecimal nGiveN = new BigDecimal(Statistic.combinatorial(N, n));
		BigDecimal pval = (m_give_x.multiply(nmGiveNx)).divide(nGiveN, MathContext.DECIMAL32);
		cm.setPvalue(pval.doubleValue());
		return cm.getPvalue();
	}

	/**
	 * Computes the abundance of a TFBS in the baseline group.
	 * @return double representing the baseline binding site abundance.
	 * */
	public static double mumBaseline(ContingencyMatrix cm) {
		return cm.getFrequency(ContingencyMatrixCell.X_AND_NOT_G);
	}

	/**
	 * Computes the abundance of a TFBS in the query group.
	 * @return double representing the query binding site abundance.
	 * */
	public static double numQuery(ContingencyMatrix cm) {
		return cm.getFrequency(ContingencyMatrixCell.X_AND_G);
	}

	public enum Name {
		LAPLACE("Laplace", "laplace", true),
		CONFIDENCE("Confidence", "confidence", true),
		LIFT("Lift", "lift", true),
		COSINE("Cosine", "cosine", true),
		JACCARD("Jaccard", "jaccard", true),
		KAPPA("Kappa", "kappa", true),
		PHI("Phi-Coefficient", "phi", true),
		// these metrics are useful but they are neither ranked nor sorted.
		PVALUE("p-value", "pvalue", false),
		NUM_QUERY("#/query", "numQuery", false),
		NUM_BASELINE("#/baseline", "numBaseline",false);

		private String row;
		private String value; // references the bean state.
		private boolean isStat;

		private Name(String row, String value, boolean isStat) {
			this.setRow(row);
			this.setStat(isStat);
			this.setValue(value);
		}

		/**
		 * @return the row
		 */
		public String get() {
			return row;
		}

		/**
		 * @param row the row to set
		 */
		private void setRow(String row) {
			this.row = row;
		}

		/**
		 * @return whether the metric is statistical or not.
		 */
		public boolean isStat() {
			return isStat;
		}

		/**
		 * @param bool represents whether the metric is statistical.
		 */
		private void setStat(boolean bool) {
			this.isStat = bool;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value the value to set
		 */
		private void setValue(String value) {
			this.value = value;
		}
	}
}
