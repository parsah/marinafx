package marina.factory;

import java.io.IOException;

import marina.bindingsite.LinearDNAMotif;
import marina.group.FASTASequence;
import marina.group.Group;
import marina.gui.MarinaGUI;

public class MotifAlignmentFactory extends AbstractAlignmentFactory {
	private DNAMotifParser parser;
	
	public MotifAlignmentFactory() {
		this.setParser(MarinaGUI.get().getParameterMap().getMotifParser());
	}
	
	@Override
	public void align() throws IOException {
		Group[] groups = this.getGroups();
		for (int i = 0; i < groups.length; i++) { // for each group ...
			Group group = groups[i];
			for (int j = 0; j < group.getSize(); j++) { // for each sequence
				FASTASequence seq = group.getParser().getSequences().get(j);
				// perform Boyer-Moore-Horspool between sequence and DNA motif
				for (LinearDNAMotif motif: this.getParser().getLinearMotifs()) {
					BoyerMooreHorspool bmh = new BoyerMooreHorspool();
					bmh.align(seq, motif);
				}
				this.updateGUI(group);
				this.updateGUI(j, group.getSize());
			}
		}
		this.updateTaskDone("Boyer-Moore-Horspool alignment complete");
	}
	
	
	/**
	 * @return the parser
	 */
	public DNAMotifParser getParser() {
		return parser;
	}

	/**
	 * @param parser the parser to set
	 */
	private void setParser(DNAMotifParser parser) {
		this.parser = parser;
	}
}
