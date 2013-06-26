package alignment;

import gui.OperationUpdater;

import java.io.IOException;

public abstract class AbstractAlignment extends OperationUpdater {
	// perform PWM mapping on both forward and reverse strands
	public abstract void forwardStrandAlign() throws IOException;
	public abstract void reverseStrandAlign() throws IOException;
	private String name; // long-name to help ID alignment mode.
	
	/**
	 * @return the alignment name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the alignment name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
