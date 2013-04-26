package alignment;

import group.FASTASequence;
import group.Group;
import gui.MarinaGUI;

import java.io.IOException;

import parameter.ParameterMap;
import parser.DNAMotifParser;

import bindingsite.LinearDNAMotif;


public class ExhaustiveMotifAlignment extends AbstractAlignment {
	private DNAMotifParser parser;
	
	public ExhaustiveMotifAlignment() {
		this.setParser(MarinaGUI.get().parameterMap().getMotifParser());
		this.setName("Rabin-Karp");
	}
	
	@Override
	public void align() throws IOException {
		ParameterMap param = MarinaGUI.get().parameterMap(); // get options
		Group[] groups = new Group[]{param.getQuery(), param.getBaseline()};
		for (int i = 0; i < groups.length; i++) { // for each group ...
			Group group = groups[i];
			for (int j = 0; j < group.getSize(); j++) { // for each sequence
				FASTASequence seq = group.getParser().getSequences().get(j);
				// align sequence and motif using Rabin-Karp.
				for (LinearDNAMotif motif: this.getParser().getLinearMotifs()) {
					RabinKarp rk = new RabinKarp();
					rk.align(seq, motif);
				}
				this.updateGUI(this.getName() + " - " + seq.getHeader());
				this.updateGUI(j, group.getSize()); // update progress-bar
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
