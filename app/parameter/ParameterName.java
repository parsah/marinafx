package parameter;

public enum ParameterName {
	SUPPORT("Support"),
	DIFF("Difference"),
	LENGTH("Length"),
	COUNT("Count"),
	PWM_CUTOFF("PWM cutoff"),
	LAPL("Laplace"),
	P_VALUE("p-value"),
	WORKERS("#/workers"),
	IPF("IPF-standardize");
	
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
