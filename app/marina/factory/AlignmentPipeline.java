package marina.factory;

import javafx.concurrent.Task;
import marina.gui.MarinaGUI;
import marina.parameter.ParameterMap;

/**
 * An AlignmentPipeline does not perform alignment options per-se but rather
 * oversees its operation. This object feeds-off Groups and TFBS models to 
 * the respective alignment algorithm. These algorithms, due to their
 * computational requirements, run in a background thread and update the
 * main GUI progress widget with each incremental operation.
 * @author Parsa Hosseini
 * */
public final class AlignmentPipeline {
	private ParameterMap parameters;

	public AlignmentPipeline() {
		this.setParameters(MarinaGUI.get().getParameterMap());
	}

	/**
	 * Determine whether only DNA motifs will be aligned and not PWMs.
	 * @return boolean whether only DNA motifs will be run.
	 * */
	private boolean useMotifsOnly() {
		return (this.getParameters().getMotifParser() != null &&
				this.getParameters().getPWMParser() == null);
	}

	/**
	 * Determine whether only PWMs will be aligned and not DNA motifs.
	 * @return boolean whether only PWMs will be run.
	 * */
	private boolean usePWMOnly() {
		return (this.getParameters().getMotifParser() == null &&
				this.getParameters().getPWMParser() != null);
	}

	/**
	 * @return the parameters
	 */
	public ParameterMap getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(ParameterMap parameters) {
		this.parameters = parameters;
	}
	
	public static void runTask(Task<Void> task) {
		Thread t = new Thread(task);
		t.setDaemon(true);
		t.start();
	}

	public void perform() {
		if (this.useMotifsOnly()) {
			MotifAlignmentFactory factory = new MotifAlignmentFactory();
			AlignmentPipeline.runTask(factory);
		}
		else if (this.usePWMOnly()) {
			// TODO implement background thread to map only PWMs
		}
		else {
			// TODO implement capability to run both alignment methods
		}
	}

}
