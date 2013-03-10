package marina.parameter;

public abstract class Parameter {
	private ParameterName name;
	
	public Parameter(ParameterName name) {
		this.setName(name);
	}
	
	/**
	 * @return the name
	 */
	public ParameterName getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	private void setName(ParameterName name) {
		this.name = name;
	}
}
