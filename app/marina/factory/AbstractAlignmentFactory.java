package marina.factory;

import java.io.IOException;

import javafx.application.Platform;
import marina.group.Group;
import marina.gui.MarinaGUI;

public abstract class AbstractAlignmentFactory {
	
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

	public void align() throws IOException {
		// TODO Auto-generated method stub
	}
	
}
