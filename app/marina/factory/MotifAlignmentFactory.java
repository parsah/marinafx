package marina.factory;

import java.io.IOException;

import marina.bindingsite.BindingSite;
import marina.bindingsite.LinearDNAMotif;
import marina.group.FASTASequence;
import marina.group.Group;
import marina.gui.MarinaGUI;
import marina.parameter.ParameterMap;

public class MotifAlignmentFactory extends AbstractAlignmentFactory {
	private DNAMotifParser parser;
	
	public MotifAlignmentFactory() {
		this.setParser(MarinaGUI.get().getParameterMap().getMotifParser());
		this.setName("Rabin-Karp");
	}
	
	@Override
	public void align() throws IOException {
		ParameterMap param = MarinaGUI.get().getParameterMap(); // get options
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
				this.updateGUI(group.getBasename() + " - " + this.getName());
				this.updateGUI(j, group.getSize());
			}
			
			for (FASTASequence seq: group.getParser().getSequences()) {
				System.out.println(seq.getHeader());
				for (BindingSite tfbs: seq.getMappings().keySet()) {
					LinearDNAMotif motif = (LinearDNAMotif)tfbs;
					System.out.println("\t" + motif.getGene() +" " + motif.getSequence() +"\t" + seq.getMappings().get(motif).size());
				}
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
