package marina.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Provides a simple user interface so the user can see what are valid
 * TFBS and DNA motif schemas.
 * */
public class SchemaDialog extends Stage {

	public SchemaDialog() {
		super();
		this.setTitle("TFBS Schemas");
		this.initModality(Modality.APPLICATION_MODAL);
		this.setWidth(550);
		this.setHeight(450);
		this.positionTabs();
		this.show();
	}

	/**
	 * Helper-method to place nodes associated with how various TFBS models
	 * are modeled and ultimately visualized.
	 * */
	private void positionTabs() {
		Accordion pane = new Accordion();
		Scene scene = new Scene(pane);

		this.setScene(scene);
		pane.getPanes().add(this.createMotifSchema());
		pane.getPanes().add(this.createPWMSchema());
		pane.setExpandedPane(pane.getPanes().get(0));
	}

	/**
	 * Creates a pane to encapsulate DNA motif schema; how they must be 
	 * formatted in a user-provided text file.
	 * @return pane containing DNA motif schema.
	 * */
	private TitledPane createMotifSchema() {
		String schema =         
				"DNA motifs follow a tab-delimited 3-column schema:\n" +
						"Column 1: TF family\n" +
						"Column 2: TF gene name\n" +
						"Column 3: Binding site (DNA; no reg. expressions.)\n" +
						"For example:\n\n" +
						"HD      KN1        TGACAGGT\n" +
						"HD      KN1        TGACAGCT\n" +
						"bHLH  OsIRO2   CACGTGG\n";
		TextArea textSchema = new TextArea();
		textSchema.setEditable(false);
		textSchema.setText(schema);
		textSchema.setWrapText(true);
		textSchema.setPadding(new Insets(10, 10, 10, 10));

		TitledPane pane = new TitledPane();
		pane.setText("DNA motifs");
		pane.setContent(textSchema);
		return pane;
	}

	/**
	 * Creates a pane to encapsulate PWM schema; how they must be formatted
	 * and presented in a user-provided text file.
	 * @return pane containing DNA motif schema.
	 * */
	private TitledPane createPWMSchema() {
		String schema =         
				"PWMs follow a 4-row schema. " +
						"Each row must have the same number of columns. " +
						"There is no limit on the number of PWM columns.\n" +
						"For example:\n\n" +
						">2QHB_3DTF\n" +
						"A    215     40     313    0     0      0      0\n" +
						"C    407     100   4        0     0      0      0\n" +
						"G    353     0       2       999  999  999  541\n" +
						"T     24      859   679    0     0       0     458\n" +
						"\nPWMs must also be tab-delimited and only " +
						"positive matrix values are permitted.";
		TextArea textSchema = new TextArea();
		textSchema.setEditable(false);
		textSchema.setText(schema);
		textSchema.setWrapText(true);
		textSchema.setPadding(new Insets(10, 10, 10, 10));

		TitledPane pane = new TitledPane();
		pane.setText("Position Weight Matrices (PWMs)");
		pane.setContent(textSchema);
		return pane;
	}

}
