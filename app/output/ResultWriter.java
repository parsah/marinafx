package output;

import gui.Dialog;
import gui.MarinaGUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import quantification.Metric;

import bean.RepresentedMatrixBean;

import javafx.stage.FileChooser;

/**
 * A ResultWriter serves the purpose of writing over-represented binding
 * sites in a flat-text file format. As a result, the user can easily parse
 * such files in their spreadsheet software tool of choice.
 * @author Parsa Hosseini
 * */
public class ResultWriter {
	private static final String TAB = "\t";
	private static final String SUFFIX = ".tab"; // file suffix
	private File file;

	/**
	 * Presents a user-prompt so that over-represented TFBSs can be saved
	 * to this specified file.
	 * */
	public void showSaveDialog() {
		FileChooser.ExtensionFilter csvFilter = 
				new FileChooser.ExtensionFilter("Tab-delimited (*.tab)", "*.tab");
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(csvFilter);
		File file = chooser.showSaveDialog(null);
		if (file != null) {
			if (!file.getAbsolutePath().endsWith(ResultWriter.SUFFIX)) {
				file = new File(file.getAbsolutePath() + ResultWriter.SUFFIX);
			}
			System.out.println(file);
			this.setFile(file);
		}
		else {
			throw new NullPointerException("No file selected.");
		}
	}
	
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
				line.append(ResultWriter.TAB + bean.getConfidence());
				break;
			case COSINE:
				line.append(ResultWriter.TAB + bean.getCosine());
				break;
			case JACCARD:
				line.append(ResultWriter.TAB + bean.getJaccard());
				break;
			case KAPPA:
				line.append(ResultWriter.TAB + bean.getKappa());
				break;
			case LAPLACE:
				line.append(ResultWriter.TAB + bean.getLaplace());
				break;
			case LIFT:
				line.append(ResultWriter.TAB + bean.getLift());
				break;
			case NUM_BASELINE:
				line.append(ResultWriter.TAB + bean.getNumBaseline());
				break;
			case NUM_QUERY:
				line.append(ResultWriter.TAB + bean.getNumQuery());
				break;
			case PHI:
				line.append(ResultWriter.TAB + bean.getPhi());
				break;
			case PVALUE:
				line.append(ResultWriter.TAB + bean.getPvalue());
				break;
			}
		}
		return line.toString();
	}

	/**
	 * Writes over-represented TFBSs to the user-selected output file.
	 * @throws IOException 
	 * */
	public void write() throws IOException {
		List<RepresentedMatrixBean> items = MarinaGUI.get().getTable().getItems();
		this.columns();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.getFile()));
			writer.write(this.columns());
			writer.newLine();
			writer.flush();
			for (RepresentedMatrixBean bean: items) {
				String line = this.writeBean(bean);
				writer.write(line.trim());
				writer.newLine();
				writer.flush();
			}
			writer.close();
			Dialog.show("TFBSs successfully saved to " + 
					this.getFile().getAbsolutePath(), false);
		} catch (IOException e) {
			String msg = "Error writing output. Please try another file.";
			throw new IOException(msg);
		}
	}

	private String columns() {
		StringBuilder columns = new StringBuilder();
		columns.append("TFBS");
		for (Metric.Name metric: Metric.Name.values()) {
			columns.append(ResultWriter.TAB + metric.get());
		}
		return columns.toString();
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	private void setFile(File file) {
		this.file = file;
	}
}
