package marina.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OptionsDialog extends Stage {
	
	private GridPane layout;
	
	public OptionsDialog() {
		this.setLayout(new GridPane());
		this.getLayout().setPadding(new Insets(10));
		this.getLayout().setHgap(10);
		this.getLayout().setVgap(10);
		this.positionFASTAButtons();
		this.positionTFBSButtons();
		this.positionAdjustments();
	}
	
	/**
	 * Helper-method for positioning buttons and associated texts having
	 * roles associated with FASTA input files.
	 * */
	private void positionFASTAButtons() {
		this.getLayout().add(MarinaGUI.createHeader("Input FASTA (both required)"), 
				0, 0, 2, 1);
		Button buttonQuery = new Button("Query FASTA");
		Button buttonBaseline = new Button("Baseline FASTA");
		this.getLayout().add(buttonQuery, 0, 1);
		this.getLayout().add(buttonBaseline, 1, 1);
	}
	
	/**
	 * Helps position nodes associated with selecting of both DNA motifs and
	 * Position Weight Matrices (PWMs).
	 * */
	private void positionTFBSButtons() {
		this.getLayout().add(MarinaGUI.createHeader(
				"Input TFBSs (>= 1 required)"), 0, 2, 2, 1);
		Button buttonPWMs = new Button("Load PWMs");
		Button buttonMotifs = new Button("Load DNA motifs");
		this.getLayout().add(buttonPWMs, 0, 3);
		this.getLayout().add(buttonMotifs, 1, 3);
	}
	
	/**
	 * All widgets associated with general adjustments are positions here.
	 * These nodes are associated with functions such as support,
	 * length, PWM, and Laplace-cutoff selection, amongst others.
	 * */
	private void positionAdjustments() {
		this.getLayout().add(MarinaGUI.createHeader("General Options"),  
				0, 4, 2, 1);
		// Length, count and difference cutoffs
		Slider sliderLength = this.createSlider(6, 0, 100, 50);
		Slider sliderCount = this.createSlider(3, 0, 100, 50);
		Slider sliderDifference = this.createSlider(4, 0, 100, 50);
		// Support, PWM cutoff and PWM cutoffs
		Slider sliderSupport = this.createSlider(0, 0, 100, 10);
		Slider sliderPWMCutoff = this.createSlider(0.80, 0, 1.0, 0.10);
		Slider sliderLaplace = this.createSlider(0.30, 0, 1.0, 0.10);
		
		this.getLayout().add(sliderLength, 1, 5);
		this.getLayout().add(sliderCount, 1, 6);
		this.getLayout().add(sliderDifference, 1, 7);
		this.getLayout().add(sliderSupport, 1, 8);
		this.getLayout().add(sliderPWMCutoff, 1, 9);
		this.getLayout().add(sliderLaplace, 1, 10);
	}
		
	/**
	 * Trivial function to create an arbitrary slider given its minimum and
	 * maximum range.
	 * @return slider object preset to a specified range.
	 * */
	private Slider createSlider(double val, double min, double max, 
			double tickUnit) {
		Slider slider = new Slider();
		slider.setMin(min);
		slider.setMax(max);
		slider.setValue(val);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(tickUnit);
		return slider;
	}

	/**
	 * @return the layout
	 */
	public GridPane getLayout() {
		return layout;
	}

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(GridPane layout) {
		this.layout = layout;
	}
}
