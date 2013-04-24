package marina.gui.listener;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import marina.gui.MarinaGUI;

/**
 * A listener for handling completion-states of various alignment factories.
 * Ideally, a factory must reach completion, however runtime errors must
 * also be caught and handled.
 * @author Parsa Hosseini
 * */
public class AlignmentTaskListener implements EventHandler<WorkerStateEvent> {

	@Override
	public void handle(WorkerStateEvent event) {
		if (WorkerStateEvent.WORKER_STATE_SUCCEEDED != null) {
			String msg = "Alignments OK. Ready for quantification.";
			MarinaGUI.get().getStatusBar().setText(msg);
			MarinaGUI.get().parameterMap().setAlignmentSuccess(true);
		}
		if (WorkerStateEvent.WORKER_STATE_FAILED == null) {
			String msg = "Alignment failure due to background thread.";
			MarinaGUI.get().getStatusBar().setText(msg);
			MarinaGUI.get().parameterMap().setAlignmentSuccess(false);
		}
	}
}
