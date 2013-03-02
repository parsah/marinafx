package marina.parameter;

public class NumericParameter extends Parameter {
	private double argument;
	private double minValue;
	private double maxValue;

	public NumericParameter(String name, double arg, double min, double max) {
		super(name);
		this.setArgument(arg);
		this.setMin(min);
		this.setMax(max);
	}
	
	/**
	 * @return the argument
	 */
	public double getArgument() {
		return argument;
	}

	/**
	 * @param argument the argument to set
	 */
	private void setArgument(double argument) {
		this.argument = argument;
	}

	/**
	 * @return the minValue
	 */
	public double getMin() {
		return minValue;
	}

	/**
	 * @param minValue the minValue to set
	 */
	private void setMin(double minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return the maxValue
	 */
	public double getMax() {
		return maxValue;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	private void setMax(double maxValue) {
		this.maxValue = maxValue;
	}
	
	
}
