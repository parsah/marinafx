package marina.quantification;

import marina.gui.MarinaGUI;

public enum MetricName {
	LAPLACE("LP", true),
	CONFIDENCE("CF", true),
	LIFT("LI", true),
	COSINE("CO", true),
	JACCARD("JAC", true),
	KAPPA("KA", true),
	PHI("PHI", true),
	// these metrics are useful but they are neither ranked nor sorted.
	HYPER("log2(PVAL)", false),
	NUM_QUERY("n(" + MarinaGUI.get().parameterMap().getQuery().getBasename()+")", false),
	NUM_BASELINE("n(" + MarinaGUI.get().parameterMap().getBaseline().getBasename()+")", false);
	
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
