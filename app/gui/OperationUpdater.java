package gui;

import javafx.application.Platform;

/**
 * A useful class containing functions associated with updated GUI components,
 * namely the progress-bar and status textbox.
 * @author Parsa Hosseini
 * */
public class OperationUpdater {
	public void updateGUI(final String text) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				MarinaGUI.get().getStatusBar().setText(text);
			}
		});
	}

	public void updateGUI(final double i, final double max) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				MarinaGUI.get().getStatusBar().setProgressValue(i/max);
			}
		});

	}
}

