package marina.parameter;

public class BooleanParameter extends Parameter {
	private boolean argument;
	
	public BooleanParameter(ParameterName name, boolean arg) {
		super(name);
		this.setArgument(arg);
	}

	/**
	 * @return the argument
	 */
	public boolean getArgument() {
		return argument;
	}

	/**
	 * @param argument the argument to set
	 */
	public void setArgument(boolean argument) {
		this.argument = argument;
	}

}
