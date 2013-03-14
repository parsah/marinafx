package marina.quantification;

import marina.gui.MarinaGUI;

public enum MetricName {
	LAPLACE("LP"),
	CONFIDENCE("CF"),
	LIFT("LI"),
	COSINE("CO"),
	JACCARD("JAC"),
	KAPPA("KA"),
	PHI("PHI"),
	HYPER("PVAL"),
	NUM_QUERY("n(" + MarinaGUI.get().parameterMap().getQuery().getBasename()+")"),
	NUM_BASELINE("n(" + MarinaGUI.get().parameterMap().getBaseline().getBasename()+")");
	
	private String row;
	
	private MetricName(String row) {
		this.setRow(row);
	}
	
	/**
	 * Get a list of all the metric abbreviations.
	 * @return list of abbreviations for all of the metrics.
	 * */
	public static String[] getNames() {
		String[] names = new String[MetricName.values().length];
		for (int i=0; i < names.length; i++) {
			names[i] = MetricName.values()[i].get();
		}
		return names;
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
	
}
