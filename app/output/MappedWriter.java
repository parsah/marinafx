package output;

import group.FASTASequence;
import group.Group;
import gui.MarinaGUI;

import java.io.BufferedWriter;
import java.io.IOException;

import parameter.ParameterMap;
import bindingsite.BindingSite;

public class MappedWriter extends TabFormattedWriter {
	
	/**
	 * Writes specific information as far as which 
	 * */
	@Override
	public void writeAll() throws IOException {
		ParameterMap paramMap = MarinaGUI.get().parameterMap();
		Group[] groups = new Group[]{paramMap.getQuery(), 
				paramMap.getBaseline()}; // get both groups
		BufferedWriter writer = this.getWriter();
		for (Group group: groups) {
			String groupName = group.getBasename();
			for (FASTASequence seq: group.getParser().getSequences()) {
				System.out.println("\t" + seq.getHeader());
				StringBuilder allTFBSs = new StringBuilder();
				for (BindingSite tfbs: seq.getMappings().keySet()) {
					allTFBSs.append(tfbs+TabFormattedWriter.TAB);
				}
				writer.write(groupName + TabFormattedWriter.TAB + 
						seq.getHeader() + TabFormattedWriter.TAB +
						allTFBSs.toString() + TabFormattedWriter.TAB);
				writer.flush();
				writer.newLine();
			}
		}
	}
}
