package marina.parser;

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
	
	public Parser() {
		this.setFile(null);
	}
	
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
	public void showNoFilterPrompt() {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File("./demo/"));
		File file = chooser.showOpenDialog(null);
		if (file != null) {
			this.setFile(file);
		}
		else {
			throw new NullPointerException("No file selected.");
		}
	}
	
	/**
	 * Prompt the user to input a FASTA file. Several file-filters exist
	 * which enable only files to be selected which end in a valid filename
	 * extension. Even if a file is in FASTA format but its extensions are 
	 * not, this specific file will therefore not be selected to be displayed.
	 * */
	public void showFASTAFilterPrompt() {
		FileChooser.ExtensionFilter fastaFilter = 
				new FileChooser.ExtensionFilter("FASTA (*.fasta)", "*.fasta");
		FileChooser.ExtensionFilter faFilter = 
				new FileChooser.ExtensionFilter("FASTA (*.fa)", "*.fa");
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File("./demo/"));
		chooser.getExtensionFilters().add(fastaFilter);
		chooser.getExtensionFilters().add(faFilter);
		File file = chooser.showOpenDialog(null);
		if (file != null) {
			this.setFile(file);
		}
		else {
			throw new NullPointerException("No FASTA file selected.");
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
