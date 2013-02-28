package marina.gui;

import java.util.Arrays;

import marina.matrix.Matrix;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The application initializer class. 
 * */
public class Marina extends Application {
	
	/**
	 * Return version of Marina.
	 * @return Application version.
	 * */
	public String getVersion() {
		return "1.0";
	}
	
	public static void main(String args[]) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		double[][] d = new double[][]{{1,2,3}, {4,5,6}};
		Matrix m = new Matrix(d);
		System.out.println(m.getWidth());
		m.debug();
		
		System.out.println(Arrays.toString(m.getColumn(2)));
	}

}
