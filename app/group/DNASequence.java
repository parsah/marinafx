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
	
	/**
	 * Reverses the actual sequence component of a DNASequence object.
	 * */
	public void reverse() {
		StringBuilder reverseString = new StringBuilder();
		for (int i=this.getLength()-1; i >= 0; i--) {
			reverseString.append(this.getSequence().charAt(i));
		}
		this.setSequence(reverseString.toString());
	}
	
	/**
	 * Derives the compliment of the sequence component of a DNASequence object.
	 * */
	public void complement() {
		StringBuilder complimentString = new StringBuilder();
		for (int i=0; i < this.getLength(); i++) {
			char c = this.getSequence().charAt(i);
			if (c == 'A') {
				complimentString.append('T');
			}
			else if (c == 'T') {
				complimentString.append('A');
			}
			else if (c == 'G') {
				complimentString.append('C');
			}
			else if (c == 'C') {
				complimentString.append('G');
			}
			else {
				complimentString.append(c);
			}
		}
		this.setSequence(complimentString.toString());
	}
	
	/**
	 * Derives the reverse compliment a DNASequence object.
	 * */
	public void reverseComplement() {
		this.reverse();
		this.complement();
	}
}
