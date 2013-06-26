package alignment;

import group.FASTAFragment;
import group.FASTASequence;
import group.Group;
import gui.MarinaGUI;

import java.io.IOException;
import java.util.List;

import parameter.ParameterMap;
import parser.PWMParser;

import bindingsite.PositionWeightMatrix;


public class ExhaustivePWMAlignment extends AbstractAlignment {
	private PWMParser parser;
	
	public ExhaustivePWMAlignment() {
		this.setParser(MarinaGUI.get().parameterMap().getPWMParser());
		this.setName("P-MATCH");
	}

	@Override
	public void forwardStrandAlign() throws IOException {
		ParameterMap param = MarinaGUI.get().parameterMap(); // get options
		Group[] groups = new Group[]{param.getQuery(), param.getBaseline()};
		for (Group group: groups) {
			for (int j=0; j < group.getSize(); j++) {
				FASTASequence seq = group.getParser().getSequences().get(j);
				for (PositionWeightMatrix pwm: this.getParser().getMatrices()) {
					List<FASTAFragment> fragments = seq.toFragments(pwm.getWidth());
					for (FASTAFragment fragment: fragments) {
						// align fragment given a PWM using P-MATCH algorithm.
						PMatch pMatch = new PMatch(fragment, pwm);
						pMatch.extrapolate();
					}
				}
				this.updateGUI(j, group.getSize()); // update progress-bar
				this.updateGUI("Forward strand - " + this.getName() + " - " + 
						seq.getHeader());
			}
		}
	}
	
	@Override
	public void reverseStrandAlign() throws IOException {
		ParameterMap param = MarinaGUI.get().parameterMap(); // get options
		Group[] groups = new Group[]{param.getQuery(), param.getBaseline()};
		for (Group group: groups) {
			for (int j=0; j < group.getSize(); j++) {
				FASTASequence seq = group.getParser().getSequences().get(j);
				seq.reverseComplement();
				for (PositionWeightMatrix pwm: this.getParser().getMatrices()) {
					List<FASTAFragment> fragments = seq.toFragments(pwm.getWidth());
					for (FASTAFragment fragment: fragments) {
						// align fragment given a PWM using P-MATCH algorithm.
						PMatch pMatch = new PMatch(fragment, pwm);
						pMatch.extrapolate();
					}
				}
				this.updateGUI(j, group.getSize()); // update progress-bar
				this.updateGUI("Reverse strand - " + this.getName() + " - " + 
						seq.getHeader());
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
