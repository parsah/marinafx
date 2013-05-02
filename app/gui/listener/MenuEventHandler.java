package gui.listener;

import group.FASTASequence;
import group.Group;
import gui.Dialog;
import gui.MarinaGUI;
import gui.ParameterStage;
import gui.SchemaStage;

import java.io.IOException;

import bindingsite.BindingSite;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import output.ResultWriter;
import parameter.ParameterMap;
import parser.DNAMotifParser;
import parser.FASTAParser;
import parser.PWMParser;
import quantification.QuantificationFactory;
import alignment.AlignmentAction;

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
				ParameterMap params = MarinaGUI.get().parameterMap();
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
				else if (menuItem.getId().equals("saveResults")) {
					if (MarinaGUI.get().getTable().getItems().size() > 0) {
						ResultWriter writer = new ResultWriter();
						writer.showSaveDialog();
						writer.write();
					}
					else {
						String msg = "TFBSs must first be quantified.";
						Dialog.show(msg, true);
					}
				}
				else if (menuItem.getId().equals("saveMappings")) {
					ParameterMap paramMap = MarinaGUI.get().parameterMap();
					Group[] groups = new Group[]{paramMap.getQuery(), 
							paramMap.getBaseline()}; // get both groups
					for (Group group: groups) {
						System.out.println(group.getBasename());
						for (FASTASequence seq: group.getParser().getSequences()) {
							System.out.println("\t" + seq.getHeader());
							for (BindingSite tfbs: seq.getMappings().keySet()) {
								System.out.println("\t\t" + tfbs);
							}
						}
					}
				}
				else if (menuItem.getId().equals("loadQuery")) {
					FASTAParser parser = new FASTAParser();
					parser.showFASTAFilterPrompt();
					parser.parse(); // set parser to the parameter-set
					Group queryGroup = new Group(parser);
					params.setQuery(queryGroup);
					Dialog.show(queryGroup.getSize() + 
							" sequences parsed.", false);
				}
				else if (menuItem.getId().equals("loadBaseline")) {
					FASTAParser parser = new FASTAParser();
					parser.showFASTAFilterPrompt();
					parser.parse(); // set parser to the parameter-set
					Group controlGroup = new Group(parser);
					params.setBaseline(controlGroup);
					Dialog.show(controlGroup.getSize() + 
							" sequences parsed.", false);
				}
				else if (menuItem.getId().equals("loadPWMs")) {
					PWMParser parser = new PWMParser();
					parser.showNoFilterPrompt();
					parser.parse();
					params.setPWMParser(parser);
					Dialog.show(parser.getMatrices().size() + 
							" PWMs parsed.", false);
				}
				else if (menuItem.getId().equals("loadMotifs")) {
					DNAMotifParser parser = new DNAMotifParser();
					parser.showNoFilterPrompt();
					parser.parse();
					params.setMotifParser(parser);
					Dialog.show(parser.getLinearMotifs().size()+
							" motifs parsed.", false);
				}
				else if (menuItem.getId().equals("run")) {
					if (MarinaGUI.get().getTable().getItems().size() > 0 ||
							params.isAlignmentSuccess()) {
						String msg = "TFBSs already aligned. Start new " +
								"analysis by selecting File -> New";
						throw new IOException(msg);
					}
					else if (params.isAlignmentInvoked() == true) {
						Dialog.show("Alignment already in-progress", false);
					}
					else {
						if (params.canRun() == true) {
							params.setAlignmentInvoked(true);
							AlignmentAction factory = new AlignmentAction();
							factory.setOnSucceeded(new AlignmentTaskListener());
							factory.setOnFailed(new AlignmentTaskListener());
							Thread t = new Thread(factory);
							t.setDaemon(true);
							t.start();
						}
						else {
							String msg = "2x FASTA files & DNA motifs " +
									"and/or PWMs needed";
							throw new IOException(msg);
						}
					}
				}
				else if (menuItem.getId().equals("quantify")) {
					if (MarinaGUI.get().getTable().getItems().size() > 0) {
						String msg = "TFBSs already quantified. Start new " +
								"analysis by selecting File -> New";
						throw new IOException(msg);
					}
					if (params.isAlignmentSuccess()) {
						// contrast TFBS abundances between two groups
						QuantificationFactory factory = new QuantificationFactory();
						factory.setOnFailed(new QuantificationTaskListener());
						factory.setOnSucceeded(new QuantificationTaskListener());
						Thread t = new Thread(factory);
						t.setDaemon(true);
						t.start();
					}
					else {
						String msg = "Alignment must be performed first.";
						throw new IOException(msg);
					}
				}
			}
		} catch (IOException | IndexOutOfBoundsException |
				NumberFormatException e ) {
			Dialog.show(e.getMessage(), true);
		} catch (NullPointerException e) {
			MarinaGUI.get().getStatusBar().setText(e.getMessage());
		}
	}
}
