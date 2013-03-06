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
		this.setName("Boyer-Moore-Horspool");
	}
	
	@Override
	public void align() throws IOException {
		Group[] groups = MarinaGUI.get().getParameterMap().getGroups();
		for (int i = 0; i < groups.length; i++) { // for each group ...
			Group group = groups[i];
			for (int j = 0; j < group.getSize(); j++) { // for each sequence
				FASTASequence seq = group.getParser().getSequences().get(j);
				// perform Boyer-Moore-Horspool between sequence and DNA motif
				for (LinearDNAMotif motif: this.getParser().getLinearMotifs()) {
					BoyerMooreHorspool bmh = new BoyerMooreHorspool();
					bmh.align(seq, motif);
				}
				this.updateGUI(group.getBasename() + " - " + this.getName());
				this.updateGUI(j, group.getSize());
			}
		}
	}
	
	/**
	 * @return the parser
	 */
	private DNAMotifParser getParser() {
		return parser;
	}

	/**
	 * @param parser the parser to set
	 */
	private void setParser(DNAMotifParser parser) {
		this.parser = parser;
	}
}
