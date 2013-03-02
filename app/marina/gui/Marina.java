package marina.gui;

/**
 * The application initializer class. 
 * */
public class Marina {
	
	/**
	 * Return version of Marina.
	 * @return Application version.
	 * */
	public static String getVersion() {
		return "1.0";
	}
	
	/**
	 * Get the number of available workers which will be used for runtime
	 * computation.
	 * @return integer-number of available workers.
	 * */
	public static int getNumWorkers() {
		return Runtime.getRuntime().availableProcessors();
	}
}
