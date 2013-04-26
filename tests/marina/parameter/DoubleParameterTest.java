package marina.parameter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import parameter.DoubleParameter;
import parameter.ParameterName;

public class DoubleParameterTest {
	private DoubleParameter param;

	@Before
	public void setUp() throws Exception {
		this.param = new DoubleParameter(ParameterName.PWM_CUTOFF, 0.80, 0, 1);
	}
	
	/**
	 * Test that a valid name is provided which is not null
	 * */
	@Test
	public void testParameterNotNull() {
		assertNotNull(this.param.getName());
	}
	
	/**
	 * Each parameter must have its argument between max and minimum.
	 * */
	@Test
	public void testArgumentBetweenMinMax() {
		assertTrue(this.param.getArgument() >= this.param.getMinValue() &&
				this.param.getArgument() <= this.param.getMaxValue());
	}
	
	/**
	 * The maximum argument-value must be larger than its minimum.
	 * */
	@Test
	public void testMaxLargerThanMin() {
		assertTrue(this.param.getMaxValue() > this.param.getMinValue());
	}
	
	/**
	 * Test that the parameter is an object of type ParameterName
	 * */
	@Test
	public void testNameIsParameterNameEnum() {
		assertTrue(this.param.getName() instanceof ParameterName);
	}
	
	/**
	 * Assert that this parameter accepts an argument of type double.
	 * */
	@Test
	public void testArgumentOfTypeDouble() {
		Double b = Double.valueOf(this.param.getArgument());
		assertTrue(b == this.param.getArgument());
	}

}
