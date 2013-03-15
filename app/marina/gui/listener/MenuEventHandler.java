package marina.gui.listener;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import marina.alignment.AlignmentAction;
import marina.bean.RepresentedBeanBuilder;
import marina.group.Group;
import marina.gui.MarinaGUI;
import marina.gui.ParameterStage;
import marina.gui.SchemaStage;
import marina.matrix.RankedAbundanceMatrix;
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
				else if (menuItem.getId().equals("loadQuery")) {
					FASTAParser parser = new FASTAParser();
					parser.showFASTAFilterPrompt();
					parser.parse(); // set parser to the parameter-set
					Group queryGroup = new Group(parser);
					params.setQuery(queryGroup);
				}
				else if (menuItem.getId().equals("loadBaseline")) {
					FASTAParser parser = new FASTAParser();
					parser.showFASTAFilterPrompt();
					parser.parse(); // set parser to the parameter-set
					Group controlGroup = new Group(parser);
					params.setBaseline(controlGroup);
				}
				else if (menuItem.getId().equals("loadPWMs")) {
					PWMParser parser = new PWMParser();
					parser.showNoFilterPrompt();
					parser.parse();
					params.setPWMParser(parser);
				}
				else if (menuItem.getId().equals("loadMotifs")) {
					DNAMotifParser parser = new DNAMotifParser();
					parser.showNoFilterPrompt();
					parser.parse();
					params.setMotifParser(parser);
				}
				else if (menuItem.getId().equals("run")) {
					this.loadDemoGroups();
//					this.loadDemoPWMs();
					this.loadDemoMotifs();
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
					if (params.isAlignmentSuccess()) {
						// contrast TFBS abundances between two groups
						CandidateMatrixBuilder mb = new CandidateMatrixBuilder();
						AbundanceInference infer = new AbundanceInference(mb);
						RankedAbundanceMatrix matrix = infer.buildAbundanceMatrix();
						RepresentedBeanBuilder builder = RepresentedBeanBuilder.get();
						builder.build(matrix);
						
					}
					else {
						String msg = "Alignment must be performed first.";
						throw new IOException(msg);
					}
				}
			}
		} catch (IOException | IndexOutOfBoundsException | 
				NullPointerException | NumberFormatException e ) {
			MarinaGUI.get().getStatusBar().setText(e.getMessage());
		}
	}

	/**
	 * Solely for demonstration purposes; loads the demo files and saves time
	 * explicitly loading them at runtime.
	 * */
	protected void loadDemoGroups() throws IOException {
		// baseline dataset
		FASTAParser p = new FASTAParser(new File("./demo/most_suppressed.fasta"));
		p.parse();
		Group controlGroup = new Group(p);
		MarinaGUI.get().parameterMap().setBaseline(controlGroup);
		
		// query dataset
		FASTAParser j = new FASTAParser(new File("./demo/most_induced.fasta"));
		j.parse();
		Group queryGroup = new Group(j);
		MarinaGUI.get().parameterMap().setQuery(queryGroup);
	}
	
	/**
	 * Implemented for purposes of loading sample / demonstration PWMs.
	 * */
	protected void loadDemoPWMs() throws IOException {
		PWMParser parser = new PWMParser(new File("./demo/sample_pwms.txt"));
		parser.parse();
		MarinaGUI.get().parameterMap().setPWMParser(parser);
	}
	/**
	 * Implemented for purposes of loading sample / demonstration DNA motifs.
	 * */	
	protected void loadDemoMotifs() throws IOException {
		DNAMotifParser parser = new DNAMotifParser(new File("./demo/sample_motifs.txt"));
		parser.parse();
		MarinaGUI.get().parameterMap().setMotifParser(parser);
	}
}
