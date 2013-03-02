package marina.parameter;

import java.io.File;

public class FileParameter extends Parameter {
	private File argument; // parameter argument
	
	public FileParameter(String param, File arg) {
		super(param);
		this.setArgument(arg);
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
