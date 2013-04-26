package marina.parameter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import parameter.BooleanParameter;
import parameter.ParameterName;

public class BooleanParameterTest {
	private BooleanParameter param;
	
	@Before
	public void setUp() throws Exception {
		this.param = new BooleanParameter(ParameterName.IPF, true);
	}
	
	/**
	 * Test that the pre-defined argument is true.
	 * */
	@Test
	public void testArgumentIsTrue() {
		assertTrue(this.param.getArgument());
	}
	
	/**
	 * Test that a valid name is provided which is not null
	 * */
	@Test
	public void testParameterNotNull() {
		assertNotNull(this.param.getName());
	}
	
	/**
	 * Test that the parameter is an object of type ParameterName
	 * */
	@Test
	public void testNameIsParameterNameEnum() {
		assertTrue(this.param.getName() instanceof ParameterName);
	}
	
	/**
	 * Assert that this parameter accepts an argument of type boolean.
	 * */
	@Test
	public void testArgumentOfTypeBoolean() {
		Boolean b = Boolean.valueOf(this.param.getArgument());
		assertTrue(b == this.param.getArgument());
	}

}
