package marina.gui;

import java.math.BigDecimal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.labs.internal.scene.control.skin.BigDecimalFieldSkin;
import jfxtras.labs.internal.scene.control.skin.ListSpinnerCaspianSkin;
import jfxtras.labs.scene.control.BigDecimalField;
import jfxtras.labs.scene.control.ListSpinner;
import marina.parameter.BaseWeightParameter;
import marina.parameter.BooleanParameter;
import marina.parameter.DoubleParameter;
import marina.parameter.IntegerParameter;
import marina.parameter.Parameter;
import marina.parameter.ParameterMap;
import marina.parameter.ParameterName;

/**
 * Represents the stage for-which the user can tweak parameters which will
 * be used throughout runtime. These parameters pertain to Laplace 
 * correction, PWM cutoff probability, support and count thresholds, amongst
 * others.
 * @author Parsa Hosseini
 * */
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
		for (ParameterName name: paramSet.keySet()) { // create widgets per parameter
			Parameter p = paramSet.get(name);
			if (p instanceof DoubleParameter) { // create sliders
				DoubleParameter np = (DoubleParameter)p;
				BigDecimalField slider = this.buildDoubleSlider(np);
				this.getLayout().add(slider, 1, rowNum);
				this.getLayout().add(new Label(p.getName().get()), 0, rowNum);
			}
			if (p instanceof IntegerParameter) { // create sliders
				IntegerParameter ip = (IntegerParameter)p;
				ListSpinner<Integer> slider = this.buildIntegerSlider(ip);
				this.getLayout().add(slider, 1, rowNum);
				this.getLayout().add(new Label(p.getName().get()), 0, rowNum);
			}
			if (p instanceof BooleanParameter) { // create checkbox
				CheckBox cb = this.buildCheckBox((BooleanParameter)p);
				this.getLayout().add(cb, 1, rowNum);
			}
			if (p instanceof BaseWeightParameter) { // base weights
				GridPane weights = this.buildWeightsPane((BaseWeightParameter)p);
				this.getLayout().add(weights, 0, rowNum, 2, 1);
			}
			rowNum += 1;
		}
		this.getLayout().add(this.buildButtons(), 0, rowNum, 2, 1);
	}

	/**
	 * Trivial function to create an arbitrary slider given its minimum and
	 * maximum range.
	 * @return slider object preset to a specified range.
	 * */
	private BigDecimalField buildDoubleSlider(final DoubleParameter p) {
		BigDecimalField field = new BigDecimalField();
		field.setSkin(new BigDecimalFieldSkin(field));
		field.setMinValue(new BigDecimal(p.getMinValue()));
		field.setMaxValue(new BigDecimal(p.getMaxValue()));
		field.setNumber(new BigDecimal(p.getArgument()));
		field.setStepwidth(new BigDecimal(0.01));
		field.numberProperty().addListener(new ChangeListener<BigDecimal>() {
			@Override
			public void changed(ObservableValue<? extends BigDecimal> repr,
					BigDecimal past, BigDecimal now) {
				p.setArgument(now.doubleValue()); // set argument
			}
		});
		return field;
	}

	/**
	 * Helper-function to create an arbitrary slider given its minimum and
	 * maximum range.
	 * @return slider object preset to a specified range.
	 * */
	private ListSpinner<Integer> buildIntegerSlider(final IntegerParameter p) {
		ListSpinner<Integer> spinner = new ListSpinner<Integer>(
				p.getMinValue(), p.getMaxValue());
		spinner.setValue(p.getArgument());
		spinner.setSkin(new ListSpinnerCaspianSkin<>(spinner));
		spinner.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> repr,
					Integer past, Integer now) {
				p.setArgument(now); // update argument
			}
		});
		return spinner;
	}

	/**
	 * Create a checkbox so that a true/false value can be selected. Such a
	 * scenario is especially true in the case of IPF standardization.
	 * @return CheckBox object.
	 * */
	private CheckBox buildCheckBox(final BooleanParameter p) {
		CheckBox cb = new CheckBox(p.getName().get());
		cb.setId(p.getName().get());
		cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> repr,
					Boolean past, Boolean now) {
				p.setArgument(now); // update argument to current state
			}
		});
		return cb;
	}

	/**
	 * Contains weights for each of the 4 bases; A, T, G, C, respectively.
	 * The user can adjust weights for these nucleotides individually,
	 * resulting in different PWM computations.
	 * */
	private GridPane buildWeightsPane(BaseWeightParameter p) {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);
		for (int i = 0; i < p.getArguments().size(); i++) {
			DoubleParameter np = p.getArguments().get(i);
			BigDecimalField field = this.buildDoubleSlider(np);
			grid.add(new Label(np.getName().get()), 0, i); // name on row i
			grid.add(field, 1, i); // name on row i+1
		}
		return grid;
	}

	/**
	 * Creates a layout so that useful buttons can be pressed and 
	 * corresponding actions be performed.
	 * @return HBox button layout.
	 * */
	private HBox buildButtons() {
		final Button ok = new Button("OK");
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (MarinaGUI.get().getParameterMap().hasValidWeights()) {
					hide();
				}
				else {
					ok.setTooltip(new Tooltip("All weights must sum to 1.0"));
					ok.setStyle("-fx-border-color: red; -fx-border-width:3");
				}
			}
		});
		HBox layout = new HBox(20);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(ok);
		return layout;
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
