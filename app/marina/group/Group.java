package marina.group;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A Group is a collection of FASTA sequences, i.e. promoter sequences.
 * Ultimately, TFBS abundances across each group are to be contrasted
 * against one-another; driven by a series of statistical metrics. For this
 * analysis to occur, two contrasting-groups are to valid sequences whereby
 * TFBSs map to these sequences.
 * 
 * @author Parsa Hosseini
 * */
public class Group {
	private List<FASTASequence> sequences;
	private File file; // input filename to represent the group.
	
	
	public Group(File file) {
		this.setSequences(new ArrayList<FASTASequence>());
		this.setFile(file);
	}
	
	/**
	 * Parse a user-provided FASTA file since each group is essentially a
	 * collection of these data-types.
	 * */
	public void parseFASTA() {
		
	}
	
	/**
	 * Returns the base-name of the specific parser filename.
	 * @return string representing base-name of file.
	 * */
	public String getBasename() {
		return this.getFile().getName();
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
	
	/**
	 * Return the number of sequences in the group; its size
	 * @return group size.
	 * */
	public int getSize() {
		return this.getSequences().size();
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
	private void setFile(File file) {
		this.file = file;
	}
}
