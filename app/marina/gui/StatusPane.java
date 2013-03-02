package marina.gui;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

/**
 * Enables real-time analysis as to how far along analysis has actually
 * come along. Both a progress-bar and status label are provided to
 * facilitate such an update.
 * @author Parsa Hosseini
 * */
public class StatusPane extends AnchorPane {
	
	private ProgressBar progressBar;
	private Label status;
	
	/**
	 * Create a wrapper to contain all the features necessary to visualize
	 * current progress of a specific task.
	 * */
	public StatusPane() {
		this.resetStatus();
		this.getChildren().addAll(this.getStatus(), this.getProgressBar());
		AnchorPane.setLeftAnchor(this.getStatus(), 10.0);
		AnchorPane.setRightAnchor(this.getProgressBar(), 10.0);
	}
	
	/**
	 * Reset both the progress bar back to zero and set the status-message to
	 * a default value.
	 * */
	public void resetStatus() {
		this.setProgressBar(new ProgressBar(0)); // set to 0%
		this.getProgressBar().setPrefWidth(150);
		this.setStatus(new Label("Ready"));		
	}
	
	/**
	 * @return the progressBar
	 */
	public ProgressBar getProgressBar() {
		return progressBar;
	}
	/**
	 * @param progressBar the progressBar to set
	 */
	private void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	/**
	 * @return the status
	 */
	public Label getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	private void setStatus(Label status) {
		this.status = status;
	}

}
