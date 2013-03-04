package marina.parameter;

public abstract class Parameter {
	private String name;
	// These IDs are to prevent hard-coding of IDs for the various input files
	public final static String ID_BASELINE = "baseline";
	public final static String ID_QUERY = "query";
	public final static String ID_MOTIFS = "motifs";
	public final static String ID_PWMS = "pwms";
	
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
