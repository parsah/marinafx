package marina.parameter;

public class IntegerParameter extends Parameter {
	private int argument;
	private int minValue;
	private int maxValue;
	
	public IntegerParameter(ParameterName name, int arg, int min, int max) {
		super(name);
		this.setMaxValue(max);
		this.setArgument(arg);
		this.setMinValue(min);
	}

	/**
	 * @return the argument
	 */
	public int getArgument() {
		return argument;
	}

	/**
	 * @param argument the argument to set
	 */
	public void setArgument(int argument) {
		this.argument = argument;
	}

	/**
	 * @return the minValue
	 */
	public int getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return the maxValue
	 */
	public int getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

}
