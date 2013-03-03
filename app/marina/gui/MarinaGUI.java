package marina.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import marina.gui.listener.MenuEventHandler;
import marina.parameter.ParameterMap;

public class MarinaGUI extends Application {
	private static MarinaGUI instance = new MarinaGUI();
	private BorderPane layout;
	private StatusPane statusBar;
	private ParameterMap parameterMap;
	
	/**
	 * Represent the GUI as a singleton so that it can be accessible 
	 * all-across the application.
	 * @return GUI reference.
	 * */
	public static MarinaGUI get() {
		return MarinaGUI.instance;
	}
	
	/**
	 * Helpful method to take a string and generate a corresponding header.
	 * This header is bold (by default) and done-so to enable easy separation
	
	 * between other GUI sub-sections.
	 * @return header Text object
	 * */
	public static Text createHeader(String text) {
		Text header = new Text(text);
		header.setFont(Font.font(null, FontWeight.BOLD, 12));
		return header;
	}
	
	/**
	 * Helper-method to position Menus and MenuBar on the actual stage.
	 * */
	private void placeMenuBar() {
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(this.getFileMenu(), this.getHelpMenu());
		MarinaGUI.get().getLayout().setTop(menuBar);
	}
	
	/**
	 * Helper-method to position the real-time status bar.
	 * */
	private void placeStatusBar() {
		MarinaGUI.get().getLayout().setBottom(this.getStatusBar());
	}
	
	/**
	 * Get the File menu which encapsulates runtime capabilities.
	 * @return Menu item reference.
	 * */
	private Menu getFileMenu() {
		Menu menuFile = new Menu("File");
		MenuItem itemNew = new MenuItem("New");
		MenuItem itemOptions = new MenuItem("Options");
		MenuItem itemExit = new MenuItem("Exit");
		menuFile.getItems().addAll(itemNew, this.getFASTAMenu(),
				this.getTFBSMenu(), new SeparatorMenuItem(), itemOptions,
				new SeparatorMenuItem(), itemExit);
		itemOptions.setId("showOptions");
		itemExit.setId("exit");
		itemOptions.setOnAction(new MenuEventHandler());
		itemExit.setOnAction(new MenuEventHandler());
		return menuFile;
	}
	
	/**
	 * Creates a Menu instance to encapsulate the Help menu. This menu is
	 * useful in-that it provides information as-to TFBS model schemas be-it
	 * DNA motifs or PWMs.
	 * @return Menu item referencing the Help-menu capabilities.
	 * */
	private Menu getHelpMenu() {
		Menu menuHelp = new Menu("Help");
		MenuItem itemSchema = new MenuItem("TFBS Schemas");
		itemSchema.setOnAction(new MenuEventHandler());
		itemSchema.setId("showSchema");
		menuHelp.getItems().addAll(itemSchema);
		return menuHelp;
	}
	
	/**
	 * Get the FASTA menu which is used for inputting user-provided promoter
	 * sequences. These sequences serve as groups: collections of promoter
	 * sequences. A total of 2x groups are provided: a conditional and a
	 * baseline. Their respective TFBS abundances are contrasted against
	 * one another.
	 * @return Menu referencing input of FASTA files.
	 * */
	private Menu getFASTAMenu() {
		Menu fileLoadFASTA = new Menu("Load FASTA");
		MenuItem itemQuery = new MenuItem("Load query");
		MenuItem itemBaseline = new MenuItem("Load baseline");
		itemQuery.setId("loadQuery");
		itemBaseline.setId("loadBaseline");
		itemQuery.setOnAction(new MenuEventHandler());
		itemBaseline.setOnAction(new MenuEventHandler());
		fileLoadFASTA.getItems().addAll(itemQuery, itemBaseline);
		return fileLoadFASTA;
	}
	
	/**
	 * Get the Menu object referencing TFBS objects. This menu simply enables
	 * user-input of desired TFBS models.
	 * @return Menu referencing input of TFBS models.
	 * */
	private Menu getTFBSMenu() {
		Menu fileLoadTFBS = new Menu("Load TFBSs");
		MenuItem itemPWMs= new MenuItem("Load PWMs");
		MenuItem itemMotifs = new MenuItem("Load DNA motifs");
		itemPWMs.setId("loadPWMs");
		itemMotifs.setId("loadMotifs");
		itemPWMs.setOnAction(new MenuEventHandler());
		itemMotifs.setOnAction(new MenuEventHandler());
		fileLoadTFBS.getItems().addAll(itemPWMs, itemMotifs);
		return fileLoadTFBS;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		MarinaGUI.get().setLayout(new BorderPane());
		// create all other GUI components
		MarinaGUI.get().setStatusBar(new StatusPane());
		MarinaGUI.get().setParameterMap(new ParameterMap());
		MarinaGUI.get().placeMenuBar();
		MarinaGUI.get().placeStatusBar();
		Scene scene = new Scene(MarinaGUI.get().getLayout());
		stage.setTitle("Marina v." + Marina.getVersion());
		stage.setScene(scene);
		stage.setHeight(300);
		stage.setWidth(350);
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
	private void setLayout(BorderPane layout) {
		this.layout = layout;
	}

	/**
	 * @return the statusBar
	 */
	public StatusPane getStatusBar() {
		return statusBar;
	}

	/**
	 * @param statusBar the statusBar to set
	 */
	private void setStatusBar(StatusPane statusBar) {
		this.statusBar = statusBar;
	}

	/**
	 * @return the runtimeArgs
	 */
	public ParameterMap getParameterMap() {
		return parameterMap;
	}

	/**
	 * @param runtimeArgs the runtimeArgs to set
	 */
	private void setParameterMap(ParameterMap params) {
		this.parameterMap = params;
	}
}
