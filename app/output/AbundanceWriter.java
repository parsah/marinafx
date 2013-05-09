package output;

import gui.Dialog;
import gui.MarinaGUI;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import quantification.Metric;
import bean.RepresentedMatrixBean;

/**
 * A AbundanceWriter serves the purpose of writing over-represented binding
 * sites in a flat-text file format. As a result, the user can easily parse
 * such files in their spreadsheet software tool of choice.
 * @author Parsa Hosseini
 * */
public class AbundanceWriter extends TabFormattedWriter {

	/**
	 * Saves a single bean-entry to the user-provided output file.
	 * @return String tab-delimited line for a specific bean object.
	 * */
	public String writeBean(RepresentedMatrixBean bean) {
		StringBuilder line = new StringBuilder();
		line.append(bean.getSite());
		for (Metric.Name metric: Metric.Name.values()) {
			switch (metric) {
			case CONFIDENCE:
				line.append(TabFormattedWriter.TAB + bean.getConfidence());
				break;
			case COSINE:
				line.append(TabFormattedWriter.TAB + bean.getCosine());
				break;
			case JACCARD:
				line.append(TabFormattedWriter.TAB + bean.getJaccard());
				break;
			case KAPPA:
				line.append(TabFormattedWriter.TAB + bean.getKappa());
				break;
			case LAPLACE:
				line.append(TabFormattedWriter.TAB + bean.getLaplace());
				break;
			case LIFT:
				line.append(TabFormattedWriter.TAB + bean.getLift());
				break;
			case NUM_BASELINE:
				line.append(TabFormattedWriter.TAB + bean.getNumBaseline());
				break;
			case NUM_QUERY:
				line.append(TabFormattedWriter.TAB + bean.getNumQuery());
				break;
			case PHI:
				line.append(TabFormattedWriter.TAB + bean.getPhi());
				break;
			case PVALUE:
				line.append(TabFormattedWriter.TAB + bean.getPvalue());
				break;
			}
		}
		return line.toString();
	}

	/**
	 * Writes over-represented TFBSs to the user-selected output file.
	 * @throws IOException 
	 * */
	@Override
	public void writeAll() throws IOException {
		List<RepresentedMatrixBean> items = MarinaGUI.get().getTable().getItems();
		try {
			BufferedWriter writer = this.getWriter();
			writer.write(this.columns());
			writer.newLine();
			writer.flush();
			for (RepresentedMatrixBean bean: items) {
				String line = this.writeBean(bean);
				writer.write(line.trim());
				writer.newLine();
				writer.flush();
			}
			Dialog.showCustom("TFBSs successfully saved", false);
		} catch (IOException e) {
			String msg = "Error writing output. Please try another file.";
			throw new IOException(msg);
		}
	}
	
	/**
	 * Concatenates all the column-names into one long string. This string is
	 * simply the header which solely represents the column names of the
	 * output file.
	 * @return String concatenated string of all the column names.
	 * */
	private String columns() {
		StringBuilder columns = new StringBuilder();
		columns.append("TFBS");
		for (Metric.Name metric: Metric.Name.values()) {
			columns.append(TabFormattedWriter.TAB + metric.get());
		}
		return columns.toString();
	}
}
