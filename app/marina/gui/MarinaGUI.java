package marina.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MarinaGUI extends Application {
	private static MarinaGUI instance = new MarinaGUI();
	private BorderPane layout;
	private OptionsWindow options;
	private StatusBar statusBar;
	
	/**
	 * Represent the GUI as a singleton so that it can be accessible 
	 * all-across the application.
	 * @return GUI reference.
	 * */
	public static MarinaGUI get() {
		return MarinaGUI.instance;
	}
	
	/**
	 * Helper-method to position Menus and MenuBar on the actual stage.
	 * */
	private void createMenu() {
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuHelp = new Menu("Help");
		MenuItem itemNew = new MenuItem("New");
		MenuItem itemOptions = new MenuItem("Options");
		MenuItem itemExit = new MenuItem("Exit");
		menuFile.getItems().addAll(itemNew, this.createFASTAMenu(), 
				this.createTFBSMenu(), new SeparatorMenuItem(), itemOptions,
				new SeparatorMenuItem(), itemExit);
		menuBar.getMenus().addAll(menuFile, menuHelp);
		
		MarinaGUI.get().getLayout().setTop(menuBar);
	}
	
	private Menu createFASTAMenu() {
		Menu fileLoadFASTA = new Menu("Load FASTA");
		MenuItem itemQuery = new MenuItem("Load query");
		MenuItem itemBaseline = new MenuItem("Load baseline");
		fileLoadFASTA.getItems().addAll(itemQuery, itemBaseline);
		return fileLoadFASTA;
	}
	
	private Menu createTFBSMenu() {
		Menu fileLoadTFBS = new Menu("Load TFBSs");
		MenuItem itemPWMs= new MenuItem("Load PWMs");
		MenuItem itemMotifs = new MenuItem("Load DNA motifs");
		fileLoadTFBS.getItems().addAll(itemPWMs, itemMotifs);
		return fileLoadTFBS;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		MarinaGUI.get().setLayout(new BorderPane());
		// create all other GUI components
		MarinaGUI.get().setOptions(new OptionsWindow());
		MarinaGUI.get().setStatusBar(new StatusBar());
		MarinaGUI.get().createMenu();
		Scene scene = new Scene(MarinaGUI.get().getLayout());
		stage.setTitle("Marina v." + Marina.getVersion());
		stage.setScene(scene);
		stage.setHeight(300);
		stage.setWidth(300);
		stage.show();
	}
	
	public static void main(String args[]) {
		Application.launch(args);
	}


	/**
	 * @return the layout
	 */
	public BorderPane getLayout() {
		return layout;
	}


	/**
	 * @param layout the layout to set
	 */
	public void setLayout(BorderPane layout) {
		this.layout = layout;
	}

	/**
	 * @return the options
	 */
	public OptionsWindow getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	private void setOptions(OptionsWindow options) {
		this.options = options;
	}

	/**
	 * @return the statusBar
	 */
	public StatusBar getStatusBar() {
		return statusBar;
	}

	/**
	 * @param statusBar the statusBar to set
	 */
	private void setStatusBar(StatusBar statusBar) {
		this.statusBar = statusBar;
	}
}
