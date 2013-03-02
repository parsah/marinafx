package marina.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ParameterStage extends Stage {
	private GridPane layout;
	
	public ParameterStage() {
		super();
		this.setTitle("Options");
		this.initModality(Modality.APPLICATION_MODAL);
		this.setLayout(new GridPane());
		this.getLayout().setPadding(new Insets(10));
		this.getLayout().setHgap(10);
		this.getLayout().setVgap(10);
		this.position(); // create scene and position widgets
	}
	
	/**
	 * Positions widgets in the preset layout and creates the scene for the
	 * user-interface.
	 * */
	private void position() {
		Scene scene = new Scene(this.getLayout());
		this.setScene(scene);
		this.positionAdjustments();
	}

	
	/**
	 * All widgets associated with general adjustments are positions here.
	 * These nodes are associated with functions such as support,
	 * length, PWM, and Laplace-cutoff selection, amongst others.
	 * */
	private void positionAdjustments() {
		this.getLayout().add(MarinaGUI.createHeader("General Options"),  
				0, 0, 2, 1);
		// Length, count and difference cutoffs
		Slider sliderLength = this.createSlider(6, 0, 100, 50);
		Slider sliderCount = this.createSlider(3, 0, 100, 50);
		Slider sliderDifference = this.createSlider(4, 0, 100, 50);
		// Support, PWM cutoff and PWM cutoffs
		Slider sliderSupport = this.createSlider(0, 0, 100, 10);
		Slider sliderPWMCutoff = this.createSlider(0.80, 0, 1.0, 0.10);
		Slider sliderLaplace = this.createSlider(0.30, 0, 1.0, 0.10);
		
		this.getLayout().add(sliderLength, 1, 1);
		this.getLayout().add(sliderCount, 1, 2);
		this.getLayout().add(sliderDifference, 1, 3);
		this.getLayout().add(sliderSupport, 1, 4);
		this.getLayout().add(sliderPWMCutoff, 1, 5);
		this.getLayout().add(sliderLaplace, 1, 6);
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
