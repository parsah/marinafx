package output;

import gui.MarinaGUI;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import quantification.Metric;
import bean.RepresentedMatrixBean;
import bindingsite.BindingSite;

/**
 * Provides a centralized control for the visualization of ranked and
 * over-represented binding-site objects.
 * */
public class OutputTable extends TableView<RepresentedMatrixBean> {

	public OutputTable() {
		this.placeHeaders();
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	/**
	 * Columns for this table are simply the Metrics used for analysis.
	 * */
	private void placeHeaders() {
		Metric.Name[] metrics = Metric.Name.values();
		TableColumn<RepresentedMatrixBean, BindingSite> colTFBS = 
				this.asBindingSiteColumn("TFBS", "site");

		// no need to set column-factory since these are high-level columns.
		TableColumn<RepresentedMatrixBean, Integer> colMets = 
				this.asIntegerColumn("Metrics", null);
		TableColumn<RepresentedMatrixBean, Double> colAbund = 
				this.asDoubleColumn("Abundances", null);
		// add each metric to its respective nested column-set.
		for (Metric.Name m: metrics) {
			TableColumn<RepresentedMatrixBean, Double> col = 
					this.asDoubleColumn(m.get(), m.getValue());
			if (m.isStat()) {
				colMets.getColumns().add(col);
			}
			else {
				colAbund.getColumns().add(col);
			}
		}	
		this.getColumns().add(colTFBS);
		this.getColumns().add(colMets);
		this.getColumns().add(colAbund);
	}

	/**
	 * Helper-function to create a new table column.
	 * @param String represents column name.
	 * @return TableColumn object.
	 * */
	private TableColumn<RepresentedMatrixBean, Double> asDoubleColumn(
			String arg, String arg1) {
		TableColumn<RepresentedMatrixBean, Double> column = 
				new TableColumn<RepresentedMatrixBean, Double>(arg);
		column.setCellValueFactory(
				new PropertyValueFactory<RepresentedMatrixBean, Double>(arg1));
		return column;
	}
	
	/**
	 * Helper-function to create a new table column.
	 * @param String represents column name.
	 * @return TableColumn object.
	 * */
	private TableColumn<RepresentedMatrixBean, Integer> asIntegerColumn(
			String arg, String arg1) {
		TableColumn<RepresentedMatrixBean, Integer> column = 
				new TableColumn<RepresentedMatrixBean, Integer>(arg);
		column.setCellValueFactory(
				new PropertyValueFactory<RepresentedMatrixBean, Integer>(arg1));
		return column;
	}

	/**
	 * Creates a new table column for objects of type BindingSite.
	 * @param String represents column name.
	 * @return TableColumn object.
	 * */
	private TableColumn<RepresentedMatrixBean, BindingSite> asBindingSiteColumn(
			String arg, String arg1) {
		TableColumn<RepresentedMatrixBean, BindingSite> column = 
				new TableColumn<RepresentedMatrixBean, BindingSite>(arg);
		column.setCellValueFactory(
				new PropertyValueFactory<RepresentedMatrixBean, BindingSite>(arg1));
		return column;
	}

	/**
	 * Adds a set of observable-beans to the table and renders them viewable
	 * by the user. These beans are simply over-represented binding sites
	 * modeled as RepresentedMatrixBean objects. This bean does
	 * not perform any computation like that in the ContingencyMatrix class.
	 * Rather, a bean centralizes matrix results and makes it user-viewable 
	 * @param beans Over-represented binding sites represented as beans.
	 * */
	public void addObservables(ObservableList<RepresentedMatrixBean> beans) {
		this.setItems(beans);
	}
	
	/**
	 * A useful function which mines only those TFBSs which are rendered
	 * over-represented and therefore presented in the output table.
	 * @return Set a collection of BindingSite objects set as over-represented.
	 * */
	public Set<BindingSite> getAbundantSites() {
		Set<BindingSite> sites = new HashSet<BindingSite>();
		List<RepresentedMatrixBean> items = MarinaGUI.get().getTable().getItems();
		for (RepresentedMatrixBean bean: items) {
			sites.add(bean.getSite());
		}
		return sites;
	}
}
