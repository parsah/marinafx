package group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bindingsite.BindingSite;


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

	/**
	 * Splits a FASTA object into chunks of a preset length.
	 * @param size length of each new substring.
	 * */
	public List<FASTAFragment> toFragments(int size) {
		List<FASTAFragment> frags = new ArrayList<FASTAFragment>();
		for (int i=0; i < this.getLength(); i++) {
			String sub = this.getSequence().substring(i, Math.min(this.getLength(), (i + size)));
			FASTAFragment f = new FASTAFragment(this, this.getHeader(), sub, i);
			if (f.getLength() == size) {
				frags.add(f);
			}
		}
		return frags;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if(obj instanceof FASTASequence) { // equality based on header
			FASTASequence other = (FASTASequence)obj;
			return (this.getHeader().equals(other.getHeader()) &&
					this.getSequence().equals(other.getSequence()));
		}
		return false;
	}

	@Override
	public int hashCode() { // FASTA header is set to be unique
		return this.getHeader().hashCode();
	}
}
