package output;

import group.FASTASequence;
import group.Group;
import gui.Dialog;
import gui.MarinaGUI;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

import parameter.ParameterMap;
import bindingsite.BindingSite;

/**
 * A MappedWriter is responsible for writing which TFBS is found in which
 * input sequence. This class therefore helps the user understand what TFBSs
 * are present and what are absent in a sequence of interest.
 * @author Parsa Hosseini
 * */
public class MappedWriter extends TabFormattedWriter {

	@Override
	public void writeAll() throws IOException {
		// first, get a list of only the TFBSs which where rendered abundant.
		Set<BindingSite> sites = MarinaGUI.get().getTable().getAbundantSites();
		ParameterMap paramMap = MarinaGUI.get().parameterMap();
		Group[] groups = new Group[]{paramMap.getQuery(), 
				paramMap.getBaseline()}; // get both groups
		try {
			BufferedWriter writer = this.getWriter();
			for (Group group: groups) {
				String groupName = group.getBasename();
				for (FASTASequence seq: group.getParser().getSequences()) {
					StringBuilder allTFBSs = new StringBuilder();
					for (BindingSite tfbs: seq.getMappings().keySet()) {
						// only save a TFBS to the output mapping file if that 
						// TFBS is over-represented. Otherwise, spurious TFBS
						// mappings will be saved and these mappings won't be very
						// meaningful.
						if (sites.contains(tfbs)) {
							allTFBSs.append(tfbs+TabFormattedWriter.TAB);
						}
					}
					writer.write(groupName + TabFormattedWriter.TAB + 
							seq.getHeader() + TabFormattedWriter.TAB +
							allTFBSs.toString().trim() + TabFormattedWriter.TAB);
					writer.flush();
					writer.newLine();
				}
			}
			writer.close();
			Dialog.showCustom("TFBS mappings successfully saved", false);
		} catch (IOException e) {
			String msg = "Error writing output. Please try another file.";
			throw new IOException(msg);
		}
	}
}
