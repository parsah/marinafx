package marina.quantification;

public enum MetricName {
	LAPLACE("LP", true),
	CONFIDENCE("CF", true),
	LIFT("LI", true),
	COSINE("CO", true),
	JACCARD("JAC", true),
	KAPPA("KA", true),
	PHI("PHI", true),
	// these metrics are useful but they are neither ranked nor sorted.
	PVALUE("PVALUE", false),
	NUM_QUERY("NUM_QUERY", false),
	NUM_BASELINE("NUM_BASELINE", false);
	
	private String row;
	private boolean isStat;
	
	private MetricName(String row, boolean isStat) {
		this.setRow(row);
		this.setStat(isStat);
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
	
}
