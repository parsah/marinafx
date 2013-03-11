package marina.quantification;

public final class Statistic {
	
	/**
	 * Computes the factorial for a given integer. To enable factorial
	 * computation of large numbers, log-scaling summation is performed, with
	 * the resultant exponential of the value being returned.
	 * @param integer representing number to compute factorial of.
	 * @return factorial result.
	 * */
	public static long factorial(int num) {
		double sum = 0;
		for (int i=1; i < num+1; i++) {
			sum += Math.log(i);
		}
		return Math.round(Math.exp(sum));
	}
	
	/**
	 * Computes the combinatorial value given n-choose-r integers.
	 * @param n Total size
	 * @param r Selection size
	 * @return combinatorial given n-choose-r.
	 * */
	public static long combinatorial(int n, int r) {
		long numerator = Statistic.factorial(n);
		long denominator = Statistic.factorial(r) * Statistic.factorial(n-r);
		return numerator / denominator;
	}
}
