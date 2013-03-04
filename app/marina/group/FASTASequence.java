package marina.group;

/**
 * All input promoter sequences are represented as FASTA files. In such a
 * format, each entry is identified by its header, referencing its 
 * respective sequence.
 * @author Parsa Hosseini
 * */
public class FASTASequence extends DNASequence {
	private String header;
	
	public FASTASequence(String header, String sequence) {
		super();
		this.setHeader(header);
		this.setSequence(sequence);
	}
	
	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}
}
