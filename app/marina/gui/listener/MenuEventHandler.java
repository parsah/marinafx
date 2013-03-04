package marina.gui.listener;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import marina.factory.DNAMotifParser;
import marina.factory.FASTAParser;
import marina.factory.PWMParser;
import marina.group.Group;
import marina.gui.MarinaGUI;
import marina.gui.ParameterStage;
import marina.gui.SchemaStage;

/**
 * Performs the core-logic when a clickable item is pressed by the user.
 * @author Parsa Hosseini
 * */
public class MenuEventHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		try {
			Object source = event.getSource();
			if (source instanceof MenuItem ) { // actions per menu item.
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
				else if (menuItem.getId().equals("loadQuery")) {
					FASTAParser parser = new FASTAParser();
					parser.showFASTAFilterPrompt();
					parser.parse(); // set parser to the parameter-set
					Group queryGroup = new Group(parser);
					MarinaGUI.get().getParameterMap().setQuery(queryGroup);
				}
				else if (menuItem.getId().equals("loadBaseline")) {
					FASTAParser parser = new FASTAParser();
					parser.showFASTAFilterPrompt();
					parser.parse(); // set parser to the parameter-set
					Group controlGroup = new Group(parser);
					MarinaGUI.get().getParameterMap().setBaseline(controlGroup);
				}
				else if (menuItem.getId().equals("loadPWMs")) {
					PWMParser parser = new PWMParser();
					parser.showNoFilterPrompt();
					parser.parse(); // TODO implement PWM parsing
				}
				else if (menuItem.getId().equals("loadMotifs")) {
					DNAMotifParser parser = new DNAMotifParser();
					parser.showNoFilterPrompt();
					parser.parse();
				}
			}
		} catch (IOException | IndexOutOfBoundsException | 
				NullPointerException e) {
			MarinaGUI.get().getStatusBar().setText(e.getMessage());
		}
	}
}
