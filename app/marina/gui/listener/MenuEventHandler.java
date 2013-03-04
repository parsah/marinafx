package marina.gui.listener;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import marina.factory.AlignmentPipeline;
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
				else if (menuItem.getId().equals("new")) {
					MarinaGUI.get().reset();
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
					MarinaGUI.get().getParameterMap().setMotifParser(parser);
				}
				else if (menuItem.getId().equals("run")) {
					if (MarinaGUI.get().getParameterMap().canRun() == true) {
						AlignmentPipeline factory = new AlignmentPipeline();
						factory.performAlignment(); // hand-off to algorithm.
					}
					else {
						String msg = "2x FASTA files & DNA motifs " +
								"and/or PWMs needed";
						throw new IOException(msg);
					}
				}
			}
		} catch (IOException | IndexOutOfBoundsException | 
				NullPointerException e) {
			MarinaGUI.get().getStatusBar().setText(e.getMessage());
		}
	}
}
