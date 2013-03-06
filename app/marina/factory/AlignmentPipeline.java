package marina.factory;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import marina.bindingsite.BindingSite;
import marina.bindingsite.LinearDNAMotif;
import marina.group.FASTASequence;
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
	private boolean isUsingMotifs() {
		return this.getParameters().getMotifParser() != null;
	}

	/**
	 * Determine whether only PWMs will be aligned and not DNA motifs.
	 * @return boolean whether only PWMs will be run.
	 * */
	private boolean isUsingPWMs() {
		return this.getParameters().getPWMParser() != null;
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

	public void perform() {
		final Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				if (isUsingMotifs()) {
					MotifAlignmentFactory factory = new MotifAlignmentFactory();
					factory.align();
				}
				if (isUsingPWMs()) {
					// TODO implement PWM alignment factory
				} // return nothing since Group objects are global.
				return null;
			}
		};
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				for (FASTASequence seq: getParameters().getBaseline().getParser().getSequences()) {
					System.out.println(seq);
					 for(BindingSite site: seq.getMappings().keySet()) {
						 LinearDNAMotif motif = (LinearDNAMotif)site;
						 System.out.println("\t" + seq + "\t"+ motif.getGene() +" " + motif.getFamily() + " " + seq.getMappings().get(motif));
					 }
				}
			}
		});
		new Thread(task).start();
	}
}
