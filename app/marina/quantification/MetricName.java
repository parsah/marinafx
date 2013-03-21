//package marina.quantification;
//
//public enum MetricName {
//	LAPLACE("Laplace", "laplace", true),
//	CONFIDENCE("Confidence", "confidence", true),
//	LIFT("Lift", "lift", true),
//	COSINE("Cosine", "cosine", true),
//	JACCARD("Jaccard", "jaccard", true),
//	KAPPA("Kappa", "kappa", true),
//	PHI("Phi-Coefficient", "phi", true),
//	// these metrics are useful but they are neither ranked nor sorted.
//	PVALUE("p-value", "pvalue", false),
//	NUM_QUERY("#/query", "numQuery", false),
//	NUM_BASELINE("#/baseline", "numBaseline",false);
//	
//	private String row;
//	private String value; // references the bean state.
//	private boolean isStat;
//	
//	private MetricName(String row, String value, boolean isStat) {
//		this.setRow(row);
//		this.setStat(isStat);
//		this.setValue(value);
//	}
//	
//	/**
//	 * @return the row
//	 */
//	public String get() {
//		return row;
//	}
//
//	/**
//	 * @param row the row to set
//	 */
//	private void setRow(String row) {
//		this.row = row;
//	}
//
//	/**
//	 * @return whether the metric is statistical or not.
//	 */
//	public boolean isStat() {
//		return isStat;
//	}
//
//	/**
//	 * @param bool represents whether the metric is statistical.
//	 */
//	private void setStat(boolean bool) {
//		this.isStat = bool;
//	}
//
//	/**
//	 * @return the value
//	 */
//	public String getValue() {
//		return value;
//	}
//
//	/**
//	 * @param value the value to set
//	 */
//	private void setValue(String value) {
//		this.value = value;
//	}
//	
//}
