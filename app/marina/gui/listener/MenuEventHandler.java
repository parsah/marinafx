package marina.gui.listener;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import marina.gui.ParameterStage;
import marina.gui.SchemaStage;

/**
 * Performs the core-logic when a clickable item is pressed by the user.
 * @author Parsa Hosseini
 * */
public class MenuEventHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Object source = event.getSource();
		if (source instanceof MenuItem ) { // invoke actions per menu item.
			MenuItem menuItem = (MenuItem)source;
			if (menuItem.getId().equals("showSchema")) {
				SchemaStage dialog = new SchemaStage();
				dialog.show();
			}
			else if (menuItem.getId().equals("showOptions")) {
				ParameterStage paramStage = new ParameterStage();
				paramStage.show();
			}
			else if (menuItem.getId().equals("exit")) {
				Platform.exit();
			}
		}
	}
}
