package alignment;

import gui.OperationUpdater;

import java.io.IOException;

public abstract class AbstractAlignment extends OperationUpdater {
	public abstract void align() throws IOException;
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
