package marina.bean;

import java.io.IOException;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import marina.matrix.ContingencyMatrix;
import marina.matrix.Matrix;
import marina.quantification.Metric;
import marina.quantification.Metric.Name;

/**
 * Provides the ability to build a collection of RepresentedMatrixBean
 * objects; data-models which encapsulate all the necessary information for
 * when an over-represented binding-site will be displayed.
 * @author Parsa Hosseini
 * */
public class RepresentedBeanBuilder {
	private Matrix matrix;
	private Metric.Name[] metrics;
	private ContingencyMatrix[] cms;

	public RepresentedBeanBuilder(Matrix m) {
		this.setMatrix(m);
		this.setContingencyMatrices(Arrays.copyOf(
				this.getMatrix().getRows().keySet().toArray(), 
				this.getMatrix().getRows().keySet().toArray().length, 
				ContingencyMatrix[].class));
		this.setMetrics(Arrays.copyOf(this.getMatrix().getColumns(), 
				this.getMatrix().getColumns().length, Metric.Name[].class));
	}

	/**
	 * Each row in an AbundanceMatrix object is essentially its own bean.
	 * Such a bean encapsulates outputs from all the various metrics and 
	 * provides the ability to efficiently visualize its contents without
	 * overlapping any functionality with ContingencyMatrix or
	 * AbundanceMatrix objects.
	 * @return list of RepresentedMatrixBean objects.
	 * @throws IOException 
	 * */
	public ObservableList<RepresentedMatrixBean> build() throws IOException {
		ObservableList<RepresentedMatrixBean> beans = FXCollections.observableArrayList();
		if (this.getMetrics().length != this.getMatrix().getWidth()) {
			String msg = "Ranked matrix must be same length as #/metrics.";
			throw new IOException(msg);
		}
		// iterate over each row and column and set the specific bean measure.
		for (int rowNum = 0; rowNum < this.getMatrix().getHeight(); rowNum++) {
			RepresentedMatrixBean bean = new RepresentedMatrixBean();
			bean.setSite(this.getContingencyMatrices()[rowNum].getBindingSite());
			for (int colNum = 0; colNum < this.getMatrix().getWidth(); colNum++) {
				Name metric = this.getMetrics()[colNum];
				double value = this.getMatrix().getData()[rowNum][colNum];
				switch(metric) {
				case CONFIDENCE: 
					bean.setConfidence((int)value);
					break;
				case COSINE:
					bean.setCosine((int)value);
					break;
				case JACCARD:
					bean.setJaccard((int)value);
					break;
				case KAPPA:
					bean.setKappa((int)value);
					break;
				case LAPLACE:
					bean.setLaplace((int)value);
					break;
				case LIFT:
					bean.setLift((int)value);
					break;
				case NUM_BASELINE:
					bean.setNumBaseline(value);
					break;
				case NUM_QUERY:
					bean.setNumQuery(value);
					break;
				case PHI:
					bean.setPhi((int)value);
					break;
				case PVALUE:
					bean.setPvalue(value);
					break;
				}
			}
			beans.add(bean);
		}
		return beans;
	}

	/**
	 * @return the matrix
	 */
	private Matrix getMatrix() {
		return matrix;
	}

	/**
	 * @param matrix the matrix to set
	 */
	private void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

	/**
	 * @return the rows
	 */
	private ContingencyMatrix[] getContingencyMatrices() {
		return cms;
	}

	/**
	 * @param args the rows to set
	 */
	private void setContingencyMatrices(ContingencyMatrix[] args) {
		this.cms = args;
	}

	/**
	 * @return the names
	 */
	private Name[] getMetrics() {
		return metrics;
	}

	/**
	 * @param arg the names to set
	 */
	private void setMetrics(Name[] arg) {
		this.metrics = arg;
	}
}
