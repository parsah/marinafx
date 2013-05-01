package quantification;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;


public final class Statistic {

	/**
	 * Computes the factorial for a given integer. To enable factorial
	 * computation of large numbers, Stirling's Approximation is performed.
	 * @param integer representing number to compute factorial of.
	 * @return BigInteger result.
	 * */
	public static BigInteger factorial(int n) {
		if (n == 0) {
			return new BigInteger("1");
		}
		// computes sqrt(2 * PI * n); used by Stirling's Approximation
		BigDecimal radicand = new BigDecimal(
				Math.sqrt(2 * Math.PI * n));
		// compute (n / Math.E) ^ n
		BigDecimal fact = new BigDecimal(n / Math.E);
		fact = fact.pow(n);
		// multiply the value raised to the power by the radicand.
		fact = fact.multiply(radicand);
		return fact.toBigInteger();
	}

	/**
	 * Computes the combinatorial value given n-choose-r integers.
	 * @param n Total size
	 * @param r Selection size
	 * @return combinatorial given n-choose-r.
	 * */
	public static BigInteger combinatorial(int n, int r) {
		BigInteger numerator = Statistic.factorial(n);
		BigInteger denominator = Statistic.factorial(r).
				multiply(Statistic.factorial(n-r));
		return numerator.divide(denominator);
	}

	/**
	 * Orders the predefined column array such that larger values are
	 * closed to 1 whereas smaller values are closed to N; N references
	 * the upper-bound array size.
	 * @param array of numerical values.
	 * @return array referencing integer-ranks for all column values.
	 * */
	public static int[] rank(double[] column) {
		// sort the array and get its index
		double[] sorted = Statistic.sort(column);
		int[] ranks = new int[column.length];
		for (int i = 0; i < column.length; i++) {
			int rank = 0;
			for (int j=0; j < sorted.length; j++) {
				if (sorted[j] == column[i]) {
					rank = j;
					break;
				}
			}
			// larger measures have higher ranks versus smaller measures.
			ranks[i] = Math.abs(column.length - rank);
		}
		return ranks;
	}

	/**
	 * Sorts a predefined column and returns the result as a new list.
	 * @param array of numerical values.
	 * @return array referencing sorted values given a prior array.
	 * */
	public static double[] sort(double[] column) {
		double[] sorted = new double[column.length];
		System.arraycopy(column, 0, sorted, 0, column.length );
		Arrays.sort(sorted);
		return sorted;
	}

	/**
	 * Compute sum of a given array; useful in cases where a column or row
	 * is returned.
	 * @param array of values.
	 * @return sum of the provided list.
	 * */
	public static double summation(double[] vals) {
		double sum = 0;
		for (int i = 0; i < vals.length; i++) {
			sum += vals[i];
		}
		return sum;
	}

	/**
	 * Identifies the minimum value in a list of numerical values.
	 * @param vals a list of numerical values.
	 * @return double minimum value in the list.
	 * */	
	public static double minimum(double[] vals) {
		double minValue = Double.MAX_VALUE;
		for (int i=0; i < vals.length; i++) {
			if (vals[i] <= minValue) {
				minValue = vals[i];
			}
		}
		return minValue;
	}

	/**
	 * Identifies the maximum value in a list of numerical values.
	 * @param vals a list of numerical values.
	 * @return double maximum value in the list.
	 * */
	public static double maximum(double[] vals) {
		double maxValue = Double.MIN_VALUE;
		for (int i=0; i < vals.length; i++) {
			if (vals[i] >= maxValue) {
				maxValue = vals[i];
			}
		}
		return maxValue;
	}
}
