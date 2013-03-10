package marina.parameter;

import java.io.File;

public class FileParameter extends Parameter {
	private File argument;
	
	public FileParameter(ParameterName name, File argument) {
		super(name);
		this.setArgument(argument);
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
