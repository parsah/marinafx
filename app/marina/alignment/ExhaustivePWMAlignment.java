package marina.alignment;

import java.io.IOException;

import marina.bindingsite.PositionWeightMatrix;
import marina.group.FASTASequence;
import marina.group.Group;
import marina.gui.MarinaGUI;
import marina.parameter.ParameterMap;
import marina.parser.PWMParser;

public class ExhaustivePWMAlignment extends AbstractAlignment {
	private PWMParser parser;
	
	public ExhaustivePWMAlignment() {
		this.setParser(MarinaGUI.get().getParameterMap().getPWMParser());
		this.setName("P-MATCH");
	}

	@Override
	public void align() throws IOException {
		ParameterMap param = MarinaGUI.get().getParameterMap(); // get options
		Group[] groups = new Group[]{param.getQuery(), param.getBaseline()};
		for (int i = 0; i < groups.length; i++) { // for each group ...
			Group group = groups[i];
			for (int j = 0; j < group.getSize(); j++) { // for each sequence
				FASTASequence seq = group.getParser().getSequences().get(j);
				// align sequence and motif using P-MATCH algorithm.
				for (PositionWeightMatrix pwm: this.getParser().getMatrices()) {
					
				}
			}
		}
	}

	/**
	 * @return the parser
	 */
	private PWMParser getParser() {
		return parser;
	}

	/**
	 * @param parser the parser to set
	 */
	private void setParser(PWMParser parser) {
		this.parser = parser;
	}

}
