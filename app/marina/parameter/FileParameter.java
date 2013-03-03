package marina.parameter;

import java.io.File;

public class FileParameter extends Parameter {
	private File argument;
	
	public FileParameter(String name) {
		super(name);
	}

	/**
	 * @return the argument
	 */
	public File getArgument() {
		return argument;
	}

	/**
	 * @param argument the argument to set
	 */
	public void setArgument(File argument) {
		this.argument = argument;
	}

}
