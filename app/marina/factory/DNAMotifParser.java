package marina.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import marina.bindingsite.LinearDNAMotif;
import marina.gui.MarinaGUI;

/**
 * DNA motifs are represented as tab-delimited files whereby the first column
 * represents the Transcription Factor (TF) family, the second column
 * represents the TF gene name, and the third column represents the actual
 * DNA motif. This DNA motif is a linear DNA string from the DNA alphabet.
 * Parsing the above file-format is therefore the goal of this class.

 * @author Parsa Hosseini
 * @see LinearDNAMotif
 * */
public class DNAMotifParser extends Parser {

	public DNAMotifParser(File file) {
		super(file);
	}

	public DNAMotifParser() {
		super(null);
	}

	@Override
	public void parse() throws IOException {
		Charset cs = Charset.defaultCharset();
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get(this.getFile().toString()), cs)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] columns = line.trim().split("\t");
				if (columns.length != 3) {
					String msg = "Motifs must have 3 tab-delimited columns.";
					throw new IndexOutOfBoundsException(msg);
				}
				else {
					for (int i = 0; i < columns.length; i++) {
						// TODO process each column
					}
				}
			}
		}
		MarinaGUI.get().getStatusBar().setText("TFBSs parsed successfully.");
	}

	@Override
	public void filter(int minLen) {
		// TODO Auto-generated method stub

	}
}
