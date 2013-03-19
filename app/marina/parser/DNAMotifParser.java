package marina.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import marina.bindingsite.LinearDNAMotif;
import marina.parameter.ParameterMap;
import marina.parameter.ParameterName;

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
	private List<LinearDNAMotif> linearMotifs;

	public DNAMotifParser(File file) {
		super(file);
		this.setLinearMotifs(new ArrayList<LinearDNAMotif>());
	}

	public DNAMotifParser() {
		super(null);
		this.setLinearMotifs(new ArrayList<LinearDNAMotif>());
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
					LinearDNAMotif motif = new LinearDNAMotif(columns);
					if (motif.getLength() >= ParameterMap.toInteger(ParameterName.LENGTH)) {
						this.getLinearMotifs().add(motif);
					}
				}
			}
			if (this.getLinearMotifs().size() == 0) {
				throw new IOException("No motifs passed the filter cutoff.");
			}
		} catch (MalformedInputException | IndexOutOfBoundsException e) {
			throw new IOException("Malformed motifs input file.");
		}
	}

	/**
	 * @return the motifs
	 */
	public List<LinearDNAMotif> getLinearMotifs() {
		return linearMotifs;
	}

	/**
	 * @param motifs the motifs to set
	 */
	public void setLinearMotifs(List<LinearDNAMotif> motifs) {
		this.linearMotifs = motifs;
	}
}
