package gui.listener;

import parameter.ParameterMap;
import parameter.ParameterName;
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
			int numTFBSs = MarinaGUI.get().getTable().getItems().size();
			String finished = "# / TFBSs: " + numTFBSs + 
					"; IPF: " + ParameterMap.toBoolean(ParameterName.IPF);
			MarinaGUI.get().getStatusBar().setText(finished);		
			MarinaGUI.get().parameterMap().setAlignmentSuccess(true);
		}
		if (WorkerStateEvent.WORKER_STATE_FAILED == null) {
			String msg = "Quantification failure due to background thread.";
			MarinaGUI.get().getStatusBar().setText(msg);
		}
	}
}
