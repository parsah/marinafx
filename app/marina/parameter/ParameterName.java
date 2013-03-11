package marina.parameter;

public enum ParameterName {
	SUPPORT("Support"),
	DIFF("Difference"),
	LENGTH("Length"),
	COUNT("Count"),
	PWM_CUTOFF("PWM cutoff"),
	LAPL("Laplace"),
	P_VALUE("p-value"),
	WORKERS("#/workers"),
	IPF("IPF-standardize"),
	A("A"), // nucleotide-specific weights
	T("T"),
	G("G"),
	C("C");
	
	private String name;
	
	private ParameterName(String name) {
		this.setName(name);
	}

	/**
	 * @return the name
	 */
	public String get() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}
	
}
