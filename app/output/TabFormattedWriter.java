package output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import javafx.stage.FileChooser;

/**
 * A high-level class which writes output in tab-delimited format.
 * @author Parsa Hosseini 
 * */
public abstract class TabFormattedWriter {
	private static final String SUFFIX = ".tab"; // file suffix
	public static final String TAB = "\t";

	private BufferedWriter writer;

	public TabFormattedWriter() {
		this.setWriter(null);
	}
	
	/**
	 * Writes the entire contents of the writer. This prevents implementation
	 * of a loop that iterates over all set entries.
	 * @throws IOException
	 * */
	public abstract void writeAll() throws IOException;

	/**
	 * Presents a user-prompt so that over-represented TFBSs can be saved
	 * to this specified file.
	 * @throws IOException
	 * */
	public void showSaveDialog() throws IOException {
		FileChooser.ExtensionFilter csvFilter = 
				new FileChooser.ExtensionFilter("Tab-delimited (*.tab)", "*.tab");
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(csvFilter);
		File file = chooser.showSaveDialog(null);
		if (file != null) {
			if (!file.getAbsolutePath().endsWith(TabFormattedWriter.SUFFIX)) {
				file = new File(file.getAbsolutePath() + TabFormattedWriter.SUFFIX);
			}
			BufferedWriter buf = Files.newBufferedWriter(
					file.toPath(), Charset.defaultCharset());
			this.setWriter(buf);
		}
		else {
			throw new NullPointerException("No file selected.");
		}
	}
	
	/**
	 * Closes the writer output stream.
	 * */
	public void close() throws IOException {
		this.getWriter().close();
	}

	/**
	 * @return the writer
	 */
	public BufferedWriter getWriter() {
		return writer;
	}

	/**
	 * @param writer the writer to set
	 */
	private void setWriter(BufferedWriter writer) {
		this.writer = writer;
	}
}
