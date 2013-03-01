package marina.group;

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
	
	public Group() {
		this.setSequences(new ArrayList<FASTASequence>());
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

}
