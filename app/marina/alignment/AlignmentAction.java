package marina.alignment;

import javafx.concurrent.Task;
import marina.gui.MarinaGUI;
import marina.parameter.ParameterMap;

/**
 * An AlignmentAction object is therefore involved in decision-making
 * so-as-to determine which alignment mode is invoked. Once the specific
 * alignment mode has been determined, this object will invoke the
 * desired mode as a background thread so that the main GUI experiences
 * no hangs.
 * @author Parsa Hosseini
 * */
public final class AlignmentAction extends Task<Void> {
	
	/**
	 * Determine whether only DNA motifs will be aligned and not PWMs.
	 * @return boolean whether only DNA motifs will be run.
	 * */
	public boolean isUsingMotifs() {
		if (this.getParameters() != null) {
			return this.getParameters().getMotifParser() != null;
		}
		return false;
	}

	/**
	 * Determine whether only PWMs will be aligned and not DNA motifs.
	 * @return boolean whether only PWMs will be run.
	 * */
	public boolean isUsingPWMs() {
		if (this.getParameters() != null) {
			return this.getParameters().getPWMParser() != null;
		}
		return false;
	}

	/**
	 * @return the parameters
	 */
	private ParameterMap getParameters() {
		return MarinaGUI.get().getParameterMap();
	}

	@Override
	protected Void call() throws Exception {
		if (isUsingMotifs()) {
			ExhaustiveMotifAlignment motifAlign = new ExhaustiveMotifAlignment();
			motifAlign.align();
		}
		if (isUsingPWMs()) {
			ExhaustivePWMAlignment pwmAlign = new ExhaustivePWMAlignment();
			pwmAlign.align(); // TODO implement PWM alignment factory
		} // return nothing since Group objects are global.
		return null;
	}
}
