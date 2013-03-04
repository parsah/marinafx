package marina.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import marina.group.FASTASequence;
import marina.gui.MarinaGUI;

/**
 * Provides the ability to parse user-provided FASTA files. The purpose of
 * having this class responsible for such functionality is to prevent the
 * beneficiary class, Group, from implementing such functionality and making
 * that class bloated. Each instance of this very class references the
 * parsed sequences which can be easily set to its respective group object.
 * @author Parsa Hosseini
 * */
public class FASTAParser extends Parser {
	private List<FASTASequence> sequences;

	public FASTAParser(File file) {
		this.setFile(file);
		this.setSequences(new ArrayList<FASTASequence>());
	}

	public FASTAParser() {
		this.setFile(null);
		this.setSequences(new ArrayList<FASTASequence>());
	}

	@Override
	public void parse() throws IOException {
		Charset cs = Charset.defaultCharset();
		// store entries as strings first and save as FASTA objects later.
		HashMap<String, StringBuilder> seqs = new HashMap<String, StringBuilder>();
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get(this.getFile().toString()), cs)) {
			String eachLine = "";
			String header = "";
			while ((eachLine = reader.readLine()) != null) {
				if (eachLine.startsWith(">")) {
					header = eachLine.substring(1, eachLine.length());
					seqs.put(header, new StringBuilder());
				}
				else {
					seqs.get(header).append(eachLine); // append sequence
				}
			}
		} // lastly, per hash-entry, create a FASTASequence object from it
		for (String head: seqs.keySet()) {
			FASTASequence i = new FASTASequence(head, seqs.get(head).toString());
			this.getSequences().add(i);
		}
		String msg = seqs.size() + " FASTA entries parsed successfully.";
		MarinaGUI.get().getStatusBar().setText(msg);
	}

	@Override
	public void filter(int minLen) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the sequences
	 */
	public List<FASTASequence> getSequences() {
		return sequences;
	}

	/**
	 * @param sequences the sequences to set
	 */
	private void setSequences(List<FASTASequence> sequences) {
		this.sequences = sequences;
	}

}
