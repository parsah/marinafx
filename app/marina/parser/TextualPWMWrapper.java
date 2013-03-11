package marina.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import marina.bindingsite.PositionWeightMatrix;

/**
 * Position Weight Matrices (PWMs) are represented in flat-text files, however
 * parsing them is a challenge due to their multi-datatype composition.
 * To aide in this parsing objective, an intermediary class, TextualPWMWrapper
 * is used. This class takes the contents of a PWM file and molds it so that
 * a sound PWM can be produced.
 * @author Parsa Hosseini
 * */
public class TextualPWMWrapper {
	private static String DELIMITER = "\t"; // what separates PWM columns.
	private String header; // what the PWM will be eventually named as.
	private List<String> rawRows; // initial String-rows from flat-file.
	
	public TextualPWMWrapper() {
		this.setHeader(null);
		this.setRawRows(new ArrayList<String>());
	}
	
	/**
	 * Produces a PWM object given a PWM wrapper.
	 * @return PWM object given the data and row information in the wrapper.
	 * */
	public PositionWeightMatrix toPWM() {
		PositionWeightMatrix m = new PositionWeightMatrix(this.generateData());
		m.setRows(this.generateRows());
		m.setColumns(this.generateColumns());
		m.setName(this.getHeader());
		return m;
	}
	
	/**
	 * Given the wrapper, this behavior produces mappings between the
	 * base (from the DNA alphabet) and its respective row number.
	 * @return Map representing base and row-number mappings.
	 * */
	private Map<String, Integer> generateRows() {
		// row names must be in the same order as they are in the PWM.
		Map<String, Integer> rowNames = new LinkedHashMap<String, Integer>();
		for (int rowNum=0; rowNum < this.getRawRows().size(); rowNum++) {
			String[] row = this.getRawRows().get(rowNum).split(
					TextualPWMWrapper.DELIMITER);
			rowNames.put(row[0], rowNum);
		}
		return rowNames;
	}
	
	/**
	 * PWMs also must have column names. By default, these column names are
	 * simply the column number represented as a String object.
	 * @return array of Strings which represent PWM column names.
	 * */
	private String[] generateColumns() {
		String aRow = this.getRawRows().get(0);
		// subtract 1 from the columns since the first column is a string
		int len = aRow.split(TextualPWMWrapper.DELIMITER).length-1;
		String[] columns = new String[len];
		for (int i = 0; i < len; i++) {
			columns[i] = String.valueOf(i);
		}
		return columns;
	}
	
	/**
	 * PWMs are modeled as multi-dimensional arrays. This data however is
	 * initially present as String objects and must be processes so that
	 * the multi-dimensional array of row values is produced.
	 * @return array of data-points to comprise the PWM.
	 * */
	private double[][] generateData() {
		double[][] data = new double[4][];
		for (int rowNum=0; rowNum < this.getRawRows().size(); rowNum++) {
			String[] strRow = this.getRawRows().get(rowNum).split(
					TextualPWMWrapper.DELIMITER);
			double[] row = new double[strRow.length-1];
			// start at index 1 since index 0 is the string row-name
			for (int colNum = 1; colNum < strRow.length; colNum++) {
				row[colNum-1] = Double.valueOf(strRow[colNum]);
			}
			data[rowNum] = row;
		}
		return data;
	}

	/**
	 * @return the header
	 */
	private String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the rawRows
	 */
	public List<String> getRawRows() {
		return rawRows;
	}

	/**
	 * @param rawRows the rawRows to set
	 */
	private void setRawRows(List<String> rawRows) {
		this.rawRows = rawRows;
	}
}
