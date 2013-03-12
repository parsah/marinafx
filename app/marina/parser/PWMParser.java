package marina.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import marina.bindingsite.PositionWeightMatrix;
import marina.gui.MarinaGUI;

/**
 * Position Weight Matrices (PWMs) are matrices which represent frequencies
 * as-to likelihood of specific TFBSs being a regulatory element. Parsing
 * such a matrix is necessary as it must pass both length requirements as well
 * as various checks apart of the PWM.
 * 
 * @author Parsa Hosseini
 * @see PositionWeightMatrix
 * */
public class PWMParser extends Parser {
	private List<PositionWeightMatrix> matrices;

	public PWMParser(File file) {
		super(file);
		this.setMatrices(new ArrayList<PositionWeightMatrix>());
	}

	public PWMParser() {
		super(null);
		this.setMatrices(new ArrayList<PositionWeightMatrix>());
	}
	
	public List<PositionWeightMatrix> toPWM(List<TextualPWMWrapper> wrappers) throws IOException {
		List<PositionWeightMatrix> pwms = new ArrayList<PositionWeightMatrix>();
		for (TextualPWMWrapper pwmWrapper: wrappers) {
			pwms.add(pwmWrapper.toPWM());
		}
		return pwms;
	}

	@Override
	public void parse() throws IOException {
		Charset cs = Charset.defaultCharset();
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get(this.getFile().toString()), cs)) {
			String line = null;
			TextualPWMWrapper pwmWrapper = null;
			List<TextualPWMWrapper> pwms = new ArrayList<TextualPWMWrapper>();
			while ((line = reader.readLine()) != null) {
				if (line.length() != 0) { // exit upon a blank line
					if (line.startsWith(">")) {
						pwmWrapper = new TextualPWMWrapper();
						pwmWrapper.setHeader(line.substring(1, line.length()));
						pwms.add(pwmWrapper);
					}
					else {
						pwmWrapper.getRawRows().add(line);
					}
				}
			}
			this.getMatrices().addAll(this.toPWM(pwms)); // add parsed PWMs
		}
		String msg = this.getMatrices().size() + " PWMs parsed.";
		MarinaGUI.get().getStatusBar().setText(msg);
	}

	@Override
	public void filter(int minLen) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the matrices
	 */
	public List<PositionWeightMatrix> getMatrices() {
		return matrices;
	}

	/**
	 * @param matrices the matrices to set
	 */
	public void setMatrices(List<PositionWeightMatrix> matrices) {
		this.matrices = matrices;
	}
}
