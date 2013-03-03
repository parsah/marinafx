package marina.factory;

import java.io.File;
import java.io.IOException;

import javafx.stage.FileChooser;

/**
 * User-provided input must ultimately be parsed, enabling the ability to
 * quantify abundance-of. All Parser sub-classes must therefore be able
 * to not only parse compatible files but also filter it, respectively.
 * @author Parsa Hosseini
 * */
public abstract class Parser {
	private File file;
	
	public Parser(File file) {
		this.setFile(file);
	}
	
	/**
	 * Be-it DNA motifs or TFBSs, these models must be uniquely parsed
	 * since each is represented slightly different from one another.
	 * @throws IOException 
	 * 
	 * */
	public abstract void parse() throws IOException;
	
	/**
	 * Some TFBSs do not pass minimum-length cutoffs. Those that do are kept
	 * however those which do not must be filtered-out.
	 * @param minLen Minimum length threshold.
	 * */
	public abstract void filter(int minLen);
	
	/**
	 * Prompt the user by displaying a dialog prompt. This dialog prompt
	 * enables selection of input files which will ultimately be specified
	 * as input for the various TFBS models.
	 * */
	public void showTFBSPrompt(String title) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle(title);
		chooser.setInitialDirectory(new File("./demo/"));
		File file = chooser.showOpenDialog(null);
		if (file != null) {
			this.setFile(file);
		}
		else {
			throw new NullPointerException("No TFBS file selected.");
		}
	}
	
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

}
