package marina.factory;

import marina.gui.MarinaGUI;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

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
			String msg = "Alignment successful.";
			MarinaGUI.get().getStatusBar().setText(msg);
		}
		if (WorkerStateEvent.WORKER_STATE_FAILED == null) {
			String msg = "Alignment failure due to background thread.";
			MarinaGUI.get().getStatusBar().setText(msg);
		}
	}
}
