package marina.quantification;

public final class Statistic {
	
	/**
	 * Computes the factorial for a given integer. To enable factorial
	 * computation of large numbers, log-scaling summation is performed, with
	 * the resultant exponential of the value being returned.
	 * @param integer representing number to compute factorial of.
	 * @return factorial result.
	 * */
	public static double factorial(double num) {
		double sum = 0;
		for (int i=1; i < num+1; i++) {
			sum += Math.log(i);
		}
		return Math.exp(sum);
	}
	
	/**
	 * Computes the combinatorial value given n-choose-r integers.
	 * @param n Total size
	 * @param r Selection size
	 * @return combinatorial given n-choose-r.
	 * */
	public static double combinatorial(double n, double r) {
		double numerator = Statistic.factorial(n);
		double denominator = Statistic.factorial(r) * Statistic.factorial(n-r);
		return numerator / denominator;
	}
}
