package marina.alignment;

import java.io.IOException;
import java.util.List;

import marina.bindingsite.FASTAFragment;
import marina.bindingsite.PositionWeightMatrix;
import marina.group.FASTASequence;
import marina.group.Group;
import marina.gui.MarinaGUI;
import marina.parameter.ParameterMap;
import marina.parser.PWMParser;

public class ExhaustivePWMAlignment extends AbstractAlignment {
	private PWMParser parser;
	
	public ExhaustivePWMAlignment() {
		this.setParser(MarinaGUI.get().parameterMap().getPWMParser());
		this.setName("P-MATCH");
	}

	@Override
	public void align() throws IOException {
		ParameterMap param = MarinaGUI.get().parameterMap(); // get options
		Group[] groups = new Group[]{param.getQuery(), param.getBaseline()};
		for (Group group: groups) {
			for (int j=0; j < group.getSize(); j++) {
				FASTASequence seq = group.getParser().getSequences().get(j);
				for (PositionWeightMatrix pwm: this.getParser().getMatrices()) {
					List<FASTAFragment> fragments = seq.toFragments(pwm.getWidth());
					for (FASTAFragment fragment: fragments) {
//						System.out.println(seq.getHeader()+"\t" + fragment.getSequence());
						// align fragment given a PWM using P-MATCH algorithm.
						PMatch pMatch = new PMatch(fragment, pwm);
						pMatch.extrapolate();
					}
				}
				this.updateGUI(j, group.getSize()); // update progress-bar
				this.updateGUI(this.getName() + " - " + seq.getHeader());
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
