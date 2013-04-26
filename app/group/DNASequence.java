package group;

/**
 * Top-level class used for modeling biological sequences from the
 * DNA alphabet. Each DNASequence object references a corresponding sequence
 * such that all resultant characters must be from the DNA alphabet.
 * 
 * @author Parsa Hosseini
 * */
public abstract class DNASequence {
	
	public static final char[] CHARSET = new char[]{'A', 'T', 'G', 'C'};
	
	private String sequence;
	
	public DNASequence() {
		this.setSequence(null);
	}

	/**
	 * @return the sequence
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	/**
	 * Trivial function to get the sequence length.
	 * @return integer representing sequence length.
	 * */
	public int getLength() {
		return this.getSequence().length();
	}
	
	/**
	 * Returns a specific base-pair given its respective index.
	 * @param integer representing the desired base.
	 * @return String representing an individual base-pair.
	 * */
	public String getBase(int i) {
		return this.getSequence().substring(i, i+1);
	}

}
