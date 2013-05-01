package gui.listener;

import gui.MarinaGUI;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 * A listener for handling jobs associated with TFBS quantification.
 * @author Parsa Hosseini.
 * */
public class QuantificationTaskListener implements EventHandler<WorkerStateEvent> {
	
	@Override
	public void handle(WorkerStateEvent event) {
		if (WorkerStateEvent.WORKER_STATE_SUCCEEDED != null) {
			MarinaGUI.get().getStatusBar().setText("Quantification complete.");
			MarinaGUI.get().parameterMap().setAlignmentSuccess(true);
		}
		if (WorkerStateEvent.WORKER_STATE_FAILED == null) {
			String msg = "Quantification failure due to background thread.";
			MarinaGUI.get().getStatusBar().setText(msg);
		}
	}
}
