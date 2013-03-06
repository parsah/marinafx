package marina.factory;

import java.io.IOException;

import javafx.application.Platform;
import marina.gui.MarinaGUI;

public abstract class AbstractAlignmentFactory {
	public abstract void align() throws IOException;
	private String name; // long-name to help ID alignment mode.
	
	/**
	 * @return the alignment name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the alignment name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	protected void updateGUI(final String text) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				MarinaGUI.get().getStatusBar().setText(text);
			}
		});
	}
	
	protected void updateGUI(final double i, final double max) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				MarinaGUI.get().getStatusBar().setProgressValue(i/max);
			}
		});
		
	}
	
	protected void updateTaskDone(final String msg) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				MarinaGUI.get().getStatusBar().setText(msg);
				MarinaGUI.get().getStatusBar().setProgressValue(0.01);
			}
		});
	}
}
