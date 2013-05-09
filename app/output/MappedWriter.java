package output;

import group.FASTASequence;
import group.Group;
import gui.Dialog;
import gui.MarinaGUI;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parameter.ParameterMap;

import bean.RepresentedMatrixBean;
import bindingsite.BindingSite;

public class MappedWriter extends TabFormattedWriter {

	/**
	 * A useful function which mines only those TFBSs which are rendered
	 * over-represented.
	 * @return Set a collection of BindingSite objects set as over-represented.
	 * */
	private Set<BindingSite> getAbundantSites() {
		Set<BindingSite> sites = new HashSet<BindingSite>();
		List<RepresentedMatrixBean> items = MarinaGUI.get().getTable().getItems();
		for (RepresentedMatrixBean bean: items) {
			sites.add(bean.getSite());
		}
		return sites;
	}

	/**
	 * Writes specific information as far as which 
	 * */
	@Override
	public void writeAll() throws IOException {
		// first, get a list of only the TFBSs which wbere rendered abundant.
		Set<BindingSite> abundantSites = this.getAbundantSites();
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
						if (abundantSites.contains(tfbs)) {
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
			Dialog.showCustom("TFBS mappings successfully saved", false);
		} catch (IOException e) {
			String msg = "Error writing output. Please try another file.";
			throw new IOException(msg);
		}
	}
}
