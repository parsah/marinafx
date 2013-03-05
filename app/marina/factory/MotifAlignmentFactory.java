package marina.factory;

import marina.group.FASTASequence;
import marina.group.Group;
import marina.gui.MarinaGUI;

public class MotifAlignmentFactory extends AbstractAlignmentFactory {
	private DNAMotifParser parser;
	
	public MotifAlignmentFactory() {
		this.setParser(MarinaGUI.get().getParameterMap().getMotifParser());
	}
	
	@Override
	protected Void call() throws Exception {
		Group[] groups = this.getGroups();
		for (int i = 0; i < groups.length; i++) {
			Group group = groups[i];
			for (int j = 0; j < group.getSize(); j++) {
				FASTASequence seq = group.getParser().getSequences().get(j);
				Thread.sleep(10);
				this.updateGUI(group);
				this.updateGUI(j, group.getSize());
			}
		}
		this.updateTaskDone("Boyer-Moore-Horspool alignment successful.");
		return null;
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
