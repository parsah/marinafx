package marina.factory;

import javafx.application.Platform;
import javafx.concurrent.Task;
import marina.group.Group;
import marina.gui.MarinaGUI;

public abstract class AbstractAlignmentFactory extends Task<Void> {
	
	public Group[] getGroups() {
		Group query = MarinaGUI.get().getParameterMap().getQuery();
		Group baseline = MarinaGUI.get().getParameterMap().getBaseline();
		return new Group[]{query, baseline};
	}
	
	protected void updateGUI(final Group group) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				String msg = "Aligning group " + group.getBasename();
				MarinaGUI.get().getStatusBar().setText(msg);
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
