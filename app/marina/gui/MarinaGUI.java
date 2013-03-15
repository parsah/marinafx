package marina.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import marina.bean.RepresentedMatrixBean;
import marina.gui.listener.MenuEventHandler;
import marina.parameter.ParameterMap;

public class MarinaGUI extends Application {
	private static MarinaGUI instance = new MarinaGUI();
	private BorderPane layout;
	private StatusPane statusBar;
	private ParameterMap parameterMap;
	private TableView<RepresentedMatrixBean> table;
	
	/**
	 * Represent the GUI as a singleton so that it can be accessible 
	 * all-across the application.
	 * @return GUI reference.
	 * */
	public static MarinaGUI get() {
		return MarinaGUI.instance;
	}
	
	/**
	 * Reset the current state of the GUI and make it so that a fresh
	 * analysis can be executed. Parameters set from the prior analysis
	 * are also removed and reverted back to their original value.
	 * */
	public void reset() {
		MarinaGUI.get().getStatusBar().reset();
		MarinaGUI.get().setParameterMap(new ParameterMap());
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
		menuBar.getMenus().addAll(this.fileMenu(), this.helpMenu());
		MarinaGUI.get().getLayout().setTop(menuBar);
	}
	
	/**
	 * Helps place and give structure to the results-table.
	 * */
	private void placeTable() {
		String msg = "No over-represented TFBSs.\n" +
				"Quantify TFBS significance given 2x FASTA files and TFBS models.";
		Text text = new Text(msg);
		text.setTextAlignment(TextAlignment.CENTER);
		this.getTable().setPlaceholder(text);
		MarinaGUI.get().getLayout().setCenter(this.getTable());
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
	private Menu fileMenu() {
		Menu menuFile = new Menu("File");
		MenuItem itemNew = new MenuItem("New");
		MenuItem itemOptions = new MenuItem("Options");
		MenuItem itemExit = new MenuItem("Exit");
		menuFile.getItems().addAll(itemNew, this.fastaMenu(),
				this.getTFBSMenu(), new SeparatorMenuItem(), itemOptions,
				this.runMenu(), new SeparatorMenuItem(), itemExit);
		itemOptions.setId("showOptions");
		itemExit.setId("exit");
		itemNew.setId("new");
		itemNew.setOnAction(new MenuEventHandler());
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
	private Menu helpMenu() {
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
	private Menu fastaMenu() {
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
	 * The Run menu provides the bulk of all analytical capabilities. Using
	 * these functions, the user can execute group-alignment given two Groups
	 * and from these, determine their magnitude of over-representation.
	 * @return Menu referencing the Run menu.
	 * */
	private Menu runMenu() {
		Menu fileRunMenu = new Menu("Run");
		MenuItem itemRun = new MenuItem("Align");
		MenuItem itemQuantify = new MenuItem("Quantify");
		itemRun.setId("run");
		itemQuantify.setId("quantify");
		itemRun.setOnAction(new MenuEventHandler());
		itemQuantify.setOnAction(new MenuEventHandler());
		fileRunMenu.getItems().addAll(itemRun, itemQuantify);
		return fileRunMenu;
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
		MarinaGUI.get().setTable(new TableView<RepresentedMatrixBean>());
		MarinaGUI.get().placeMenuBar();
		MarinaGUI.get().placeStatusBar();
		MarinaGUI.get().placeTable();
		MarinaGUI.get().reset();
		Scene scene = new Scene(MarinaGUI.get().getLayout());
		stage.setTitle("Marina v." + Marina.getVersion());
		stage.setScene(scene);
		stage.setWidth(520);
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
	public ParameterMap parameterMap() {
		if (this.parameterMap == null) {
			this.setParameterMap(new ParameterMap());
		}
		return parameterMap;
	}

	/**
	 * @param runtimeArgs the runtimeArgs to set
	 */
	private void setParameterMap(ParameterMap params) {
		this.parameterMap = params;
	}

	/**
	 * @return the resultsTable
	 */
	public TableView<RepresentedMatrixBean> getTable() {
		return table;
	}

	/**
	 * @param table the resultsTable to set
	 */
	private void setTable(TableView<RepresentedMatrixBean> table) {
		this.table = table;
	}
}
