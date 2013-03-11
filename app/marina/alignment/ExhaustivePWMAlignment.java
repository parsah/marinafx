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
		this.setParser(MarinaGUI.get().parameterMap().getPWMParser());
		this.setName("P-MATCH");
	}

	@Override
	public void align() throws IOException {
		ParameterMap param = MarinaGUI.get().parameterMap(); // get options
		Group[] groups = new Group[]{param.getQuery(), param.getBaseline()};
		// TODO implement weights now-that there is no WEIGHTS param
		for (int i = 0; i < groups.length; i++) { // for each group ...
			Group group = groups[i];
			System.out.println(group.getBasename());
			for (int j = 0; j < group.getSize(); j++) { // for each sequence
				FASTASequence seq = group.getParser().getSequences().get(j);
				// align sequence and motif using P-MATCH algorithm.
				System.out.println("\t" + seq.getHeader());
				for (PositionWeightMatrix pwm: this.getParser().getMatrices()) {
					System.out.println("\t\t" + pwm.getName());
					System.out.println("\t\t" + pwm.getRows());
				}
			}
		}
//		System.out.println(((DoubleParameter)MarinaGUI.get().parameterMap().get(ParameterName.A)).getArgument());
//		System.out.println(ParameterName.valueOf("A").get());
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
