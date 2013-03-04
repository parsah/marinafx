package marina.group;

import java.util.HashMap;
import java.util.List;

import marina.bindingsite.BindingSite;

/**
 * All input promoter sequences are represented as FASTA files. In such a
 * format, each entry is identified by its header, referencing its 
 * respective sequence.
 * @author Parsa Hosseini
 * */
public class FASTASequence extends DNASequence {
	private String header;
	private HashMap<BindingSite, List<Integer>> mappings; // TFBS mappings
	
	public FASTASequence(String header, String sequence) {
		super();
		this.setHeader(header);
		this.setSequence(sequence);
		this.setMappings(new HashMap<BindingSite, List<Integer>>());
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
	private void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the mappings
	 */
	public HashMap<BindingSite, List<Integer>> getMappings() {
		return mappings;
	}

	/**
	 * @param mappings the mappings to set
	 */
	private void setMappings(HashMap<BindingSite, List<Integer>> mappings) {
		this.mappings = mappings;
	}
}
