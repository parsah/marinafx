package marina.group;

import java.util.HashMap;
import java.util.Map;

import marina.bindingsite.BindingSite;
import marina.factory.FASTAParser;

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
	private FASTAParser parser;

	public Group(FASTAParser parser) {
		this.setParser(parser);
	}

	/**
	 * Return a map of how many times each TFBS is found within the group.
	 * @return map referencing a BindingSite object and its group-abundance.
	 * */
	public Map<BindingSite, Integer> mappings() {
		Map<BindingSite, Integer> maps = new HashMap<BindingSite, Integer>();
		for (FASTASequence seq: this.getParser().getSequences()) {
			for (BindingSite tfbs: seq.getMappings().keySet()) {
				if (!maps.containsKey(tfbs)) {
					maps.put(tfbs, 0);
				}
				int newValue = seq.getMappings().get(tfbs).size();
				maps.put(tfbs,  maps.get(tfbs) + newValue);
			}
		}
		return maps;
	}

	/**
	 * Returns the base-name of the specific parser filename.
	 * @return string representing base-name of file.
	 * */
	public String getBasename() {
		return "\""+this.getParser().getFile().getName() + "\"";
	}

	/**
	 * Return the number of sequences in the group; its size
	 * @return group size.
	 * */
	public int getSize() {
		return this.getParser().getSequences().size();
	}

	/**
	 * @return the parser
	 */
	public FASTAParser getParser() {
		return parser;
	}

	/**
	 * @param parser the parser to set
	 */
	private void setParser(FASTAParser parser) {
		this.parser = parser;
	}
}
