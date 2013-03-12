package marina.alignment;

import java.io.IOException;

import marina.bindingsite.FASTAFragment;
import marina.bindingsite.PositionWeightMatrix;
import marina.parameter.ParameterMap;
import marina.parameter.ParameterName;

public class PMatch {
	private FASTAFragment fragment;
	private PositionWeightMatrix pwm;

	public PMatch(FASTAFragment fragment, PositionWeightMatrix pwm) {
		this.setFragment(fragment);
		this.setPWM(pwm);
	}
	
	/**
	 * The alignment score must be converted to an alignment probability
	 * considering that there are weights per base at each column. 
	 * */
	public void toProbability(double score) {
		double prob = (score - this.getPWM().getColumnMins()) /
				(this.getPWM().getSum() - this.getPWM().getColumnMins());
		if (prob >= ParameterMap.toDouble(ParameterName.PWM_CUTOFF)) {
//			this.pwm.debug();
			// TODO add index to parent sequence
//			System.out.println("\t"+this.getFragment().getSequence()+"\t" + prob);
		}
	}

	/**
	 * Maps the respective PositionWeightMatrix object with the 
	 * FASTAFragment object, using the P-Match algorithm.
	 * @throws IOException 
	 * */
	public void extrapolate() throws IOException {
		if (this.getFragment().getLength() == this.getPWM().getWidth()) {
			double score = 0; // alignment score.
			for (int colNum=0; colNum < this.getFragment().getLength(); colNum++) {
				String base = this.getFragment().getBase(colNum);
				int rowNum = 0;
				if (this.getPWM().getRows().containsKey(base)) {
					rowNum = this.getPWM().getRows().get(base);
					score += this.getPWM().getData()[rowNum][colNum];
				}
				else {
					// TODO handle ambiguous characters.
				}
			}
			this.toProbability(score);
		}
		else {
			throw new IOException("Fragments and PWMs must be same size.");
		}
	}

	/**
	 * @return the fragment
	 */
	private FASTAFragment getFragment() {
		return fragment;
	}

	/**
	 * @param fragment the fragment to set
	 */
	private void setFragment(FASTAFragment fragment) {
		this.fragment = fragment;
	}

	/**
	 * @return the pwm
	 */
	private PositionWeightMatrix getPWM() {
		return pwm;
	}

	/**
	 * @param pwm the pwm to set
	 */
	private void setPWM(PositionWeightMatrix pwm) {
		this.pwm = pwm;
	}
}
