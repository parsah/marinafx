package marina.gui;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import marina.parameter.BaseWeightParameter;
import marina.parameter.BooleanParameter;
import marina.parameter.NumericParameter;
import marina.parameter.Parameter;
import marina.parameter.ParameterMap;

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
		ParameterMap paramSet = MarinaGUI.get().getParameterMap();
		int rowNum = 0; // for each parameter, add it to the layout
		for (String name: paramSet.keySet()) {
			Parameter param = paramSet.get(name);
			if (param instanceof NumericParameter) { // create sliders
				NumericParameter p = (NumericParameter)param;
				Slider slider = this.createSlider(p.getArgument(), 
						p.getMin(), p.getMax());
				this.getLayout().add(new Label(p.getName()), 0, rowNum);
				this.getLayout().add(slider, 1, rowNum);
			}
			if (param instanceof BooleanParameter) { // create checkbox
				BooleanParameter p = (BooleanParameter)param;
				CheckBox cb = this.createCheckBox(p.getName(), p.getArgument());
				this.getLayout().add(cb, 1, rowNum);
			}
			if (param instanceof BaseWeightParameter) { // base weights
				BaseWeightParameter p = (BaseWeightParameter)param;
				GridPane weights = this.createWeightsPane(p.getArguments());
				this.getLayout().add(weights, 0, rowNum, 2, 1);
			}
			rowNum += 1;
		}
	}
		
	/**
	 * Trivial function to create an arbitrary slider given its minimum and
	 * maximum range.
	 * @return slider object preset to a specified range.
	 * */
	private Slider createSlider(double val, double min, double max) {
		Slider slider = new Slider();
		slider.setMin(min);
		slider.setMax(max);
		slider.setValue(val);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		if (max == 1.0) { // make the tick-unit less if maximum is 1.0
			slider.setMajorTickUnit(0.1);
		}
		if (max <= Marina.getNumWorkers() && max != 1.0) { // worker-slider
			slider.setMajorTickUnit(1);
			slider.setMinorTickCount(0);
			slider.setSnapToTicks(true);
		}
		return slider;
	}
	
	/**
	 * Create a checkbox so that a true/false value can be selected. Such a
	 * scenario is especially true in the case of IPF standardization.
	 * @return CheckBox object.
	 * */
	private CheckBox createCheckBox(String name, boolean state) {
		CheckBox cb = new CheckBox(name);
		return cb;
	}
	
	/**
	 * Contains weights for each of the 4 bases; A, T, G, C, respectively.
	 * The user can adjust weights for these nucleotides individually,
	 * resulting in different PWM computations.
	 * */
	private GridPane createWeightsPane(List<NumericParameter> params) {
		GridPane grid = new GridPane(); // name and weight ontop of each other
		grid.setAlignment(Pos.CENTER);
		for (int i = 0; i < params.size(); i++) {
			NumericParameter p = params.get(i);
			grid.add(new Label(p.getName()), i, 0); // name on row i
			TextField field = new TextField(String.valueOf(p.getArgument()));
			field.setPrefColumnCount(3);
			grid.add(field, i, 1); // name on row i+1
		}
		return grid;
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
