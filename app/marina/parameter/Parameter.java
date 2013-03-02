package marina.parameter;

public abstract class Parameter {
	private String name;
	
	public Parameter(String name) {
		this.setName(name);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

}
