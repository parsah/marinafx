package marina.bindingsite;

import static org.junit.Assert.*;

import java.io.IOException;

import marina.bindingsite.PositionWeightMatrix;
import marina.parser.TextualPWMWrapper;

import org.junit.Before;
import org.junit.Test;

public class TextualPWMWrapperTest {
	private TextualPWMWrapper wrapper;

	@Before
	public void setUp() throws Exception {
		// this wrapper sums to 100 but does not have to; sums solely for
		// illustrative purposes and ease of demonstration and testing.
		this.wrapper = new TextualPWMWrapper();
		this.wrapper.setHeader(">PWM_header");
		this.wrapper.getRawRows().add("A	12	52	11	8	1");
		this.wrapper.getRawRows().add("T	70	28	80	90	8");
		this.wrapper.getRawRows().add("G	18	10	5	1	90");
		this.wrapper.getRawRows().add("C	10	10	4	1	1");
	}
	
	/**
	 * The PWM wrapper's header must equal the parsed-PWM header.
	 * */
	@Test
	public void testWrapperNameEqualsPWM() throws IOException {
		PositionWeightMatrix pwm = this.wrapper.toPWM();
		assertTrue(pwm.getName().equals(this.wrapper.getHeader()));
	}
	
	/**
	 * A PWM with an invalid number of rows must throw an exception.
	 * */
	@Test(expected=IOException.class)
	public void testInvalidRowsNumsYieldsException() throws IOException {
		this.wrapper.getRawRows().remove(0);
		this.wrapper.toPWM();
	}
	
	/**
	 * PWM wrapper height must equal the parsed-PWM height.
	 * @throws IOException 
	 * */
	@Test
	public void testWrapperHeightEqualsPWMHeight() throws IOException {
		PositionWeightMatrix pwm = this.wrapper.toPWM();
		assertTrue(pwm.getHeight() == this.wrapper.getRawRows().size());
	}
	
	/**
	 * An invalid character in the matrix must throw an exception.
	 * */
	@Test(expected=IOException.class)
	public void testInvalidRowNameYieldsException() throws IOException {
		// deliberately alter some rows
		this.wrapper.getRawRows().clear();
		this.wrapper.getRawRows().add("A	12	52	11	8");
		this.wrapper.getRawRows().add("T	28	80	90	8");
		this.wrapper.getRawRows().add("G	18	10	5	1");
		this.wrapper.getRawRows().add("X	10	10	4	1");
		this.wrapper.toPWM();
	}
}
