package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * For displaying a modal dialog; useful for instances where an error has
 * occured or the user is to be alerted.
 * @author Parsa Hosseini
 * */
public abstract class Dialog {

	public static void show(String message, boolean isError) {
		// creates a simple layout for displaying a user-alert dialog.
		Button ok = new Button("OK");
		VBox layout = new VBox(5);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(5, 5, 5, 5));
		layout.getChildren().addAll(new Text(message), ok);
		final Stage stage = new Stage(); // create the main user-interface.
		if (isError) {
			stage.setTitle("Error");
		}
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(layout));
		stage.centerOnScreen();
		stage.show();
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();
			}
		});
	}
}
