package marina.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import marina.bindingsite.PositionWeightMatrix;
import marina.parameter.ParameterMap;
import marina.parameter.ParameterName;

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

	public List<PositionWeightMatrix> toPWMs(List<TextualPWMWrapper> wrappers) throws IOException {
		List<PositionWeightMatrix> pwms = new ArrayList<PositionWeightMatrix>();
		for (TextualPWMWrapper pwmWrapper: wrappers) {
			PositionWeightMatrix pwm = pwmWrapper.toPWM();
			if (pwm.getWidth() >= ParameterMap.toInteger(ParameterName.LENGTH)) {
				pwms.add(pwmWrapper.toPWM());				
			}
		}
		if (pwms.size() == 0) {
			throw new IOException("No PWMs passed the filter cutoff.");
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
						if (pwmWrapper != null) {
							pwmWrapper.getRawRows().add(line);
						}
						else {
							throw new IOException("File does not represent PWMs.");
						}
					}
				}
			}
			this.getMatrices().addAll(this.toPWMs(pwms)); // add parsed PWMs
		} catch (MalformedInputException | IndexOutOfBoundsException e) {
			throw new IOException("Malformed PWM input file.");
		}
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
