package marina.gui.listener;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import marina.alignment.AlignmentAction;
import marina.bean.RepresentedBeanBuilder;
import marina.bean.RepresentedMatrixBean;
import marina.group.Group;
import marina.gui.Dialog;
import marina.gui.MarinaGUI;
import marina.gui.ParameterStage;
import marina.gui.SchemaStage;
import marina.output.OutputTable;
import marina.output.ResultWriter;
import marina.parameter.ParameterMap;
import marina.parser.DNAMotifParser;
import marina.parser.FASTAParser;
import marina.parser.PWMParser;
import marina.quantification.AbundanceInference;
import marina.quantification.CandidateMatrixBuilder;

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
				else if (menuItem.getId().equals("save")) {
					if (MarinaGUI.get().getTable().getItems().size() > 0) {
						ResultWriter writer = new ResultWriter();
						writer.showSaveDialog();
						writer.write();
					}
					else {
						String msg = "Over-represented TFBSs must first " +
								"be quantified.";
						Dialog.show(msg, true);
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
					if (params.canRun() == true) {
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
				else if (menuItem.getId().equals("quantify")) {
					if (MarinaGUI.get().getTable().getItems().size() > 0) {
						String msg = "TFBSs already quantified. Start new " +
								"analysis by selecting File -> New";
						throw new IOException(msg);
					}
					if (params.isAlignmentSuccess()) {
						// contrast TFBS abundances between two groups
						CandidateMatrixBuilder mb = new CandidateMatrixBuilder();
						AbundanceInference infer = new AbundanceInference(mb);
						infer.bindAbundances();
						RepresentedBeanBuilder builder = new 
								RepresentedBeanBuilder(infer.getOrderedMatrix());
						ObservableList<RepresentedMatrixBean> beans = builder.build();
						OutputTable table = MarinaGUI.get().getTable();
						table.addObservables(beans);
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
