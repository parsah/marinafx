package marina.factory;

import marina.group.FASTASequence;
import marina.group.Group;
import marina.gui.MarinaGUI;

public class MotifAlignmentFactory extends AbstractAlignmentFactory {
	private DNAMotifParser parser;
	
	public MotifAlignmentFactory() {
		this.setBaseline(MarinaGUI.get().getParameterMap().getBaseline());
		this.setQuery(MarinaGUI.get().getParameterMap().getQuery());
		this.setParser(MarinaGUI.get().getParameterMap().getMotifParser());
	}
	
	@Override
	protected Void call() throws Exception {
		for(FASTASequence seq: this.getQuery().getParser().getSequences()) {
			Thread.sleep(500);
			System.out.println(seq);
		}
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

	/**
	 * @return the query
	 */
	public Group getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	private void setQuery(Group query) {
		this.query = query;
	}

	/**
	 * @return the baseline
	 */
	public Group getBaseline() {
		return baseline;
	}

	/**
	 * @param baseline the baseline to set
	 */
	private void setBaseline(Group baseline) {
		this.baseline = baseline;
	}
}
