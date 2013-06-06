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
 * A CoordinateWriter is different to a MappedWriter in that it outputs 
 * specific indices as-to where in the promoter sequence a particular TFBS
 * maps to.
 * @author Parsa Hosseini
 * */
public class CoordinateWriter extends TabFormattedWriter {

	@Override
	public void writeAll() throws IOException {
		// first, get a list of only the TFBSs which wbere rendered abundant.
		Set<BindingSite> sites = MarinaGUI.get().getTable().getAbundantSites();
		ParameterMap paramMap = MarinaGUI.get().parameterMap();
		Group[] groups = new Group[]{paramMap.getQuery(), 
				paramMap.getBaseline()}; // get both groups
		try {
			BufferedWriter writer = this.getWriter();
			for (Group group: groups) { // iterate over each group
				String groupName = group.getBasename(); // loop over sequences
				for (FASTASequence seq: group.getParser().getSequences()) {
					// loop over all the TFBSs mapping to that sequence
					for (BindingSite tfbs: seq.getMappings().keySet()) {
						StringBuilder indices = new StringBuilder();
						if (sites.contains(tfbs)) { // save if over-represented
							// loop over all the indices that the TFBS maps at
							for (Integer index: seq.getMappings().get(tfbs)) {
								indices.append(index + TabFormattedWriter.TAB);
							}
						}
						writer.write(groupName + TabFormattedWriter.TAB + 
								seq.getHeader() + TabFormattedWriter.TAB +
								tfbs + TabFormattedWriter.TAB +
								indices.toString().trim() + TabFormattedWriter.TAB);
						writer.flush();
						writer.newLine();
					}
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
