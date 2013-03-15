package marina.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import marina.matrix.RankedAbundanceMatrix;
import marina.matrix.ContingencyMatrix;
import marina.quantification.MetricName;

/**
 * Provides the ability to build a collection of RepresentedMatrixBean
 * objects; data-models which encapsulate all the necessary information for
 * when an over-represented binding-site will be displayed.
 * @author Parsa Hosseini
 * */
public class RepresentedBeanBuilder {
	private static RepresentedBeanBuilder instance = new RepresentedBeanBuilder();

	public static RepresentedBeanBuilder get() {
		return RepresentedBeanBuilder.instance;
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
	public List<RepresentedMatrixBean> build(RankedAbundanceMatrix matrix) 
			throws IOException {
		List<RepresentedMatrixBean> beans = new ArrayList<RepresentedMatrixBean>();
		// rows are represented as a linked-hashmap hence they are sorted.
		ContingencyMatrix[] rowNames = Arrays.copyOf(
				matrix.getRows().keySet().toArray(), 
				matrix.getRows().keySet().toArray().length, 
				ContingencyMatrix[].class);
		MetricName[] columnNames = Arrays.copyOf(matrix.getColumns(), 
				matrix.getColumns().length, MetricName[].class);
		// iterate over each row and column and set the specific bean measure.
		for (int rowNum = 0; rowNum < matrix.getHeight(); rowNum++) {
			RepresentedMatrixBean matBean = new RepresentedMatrixBean();
			matBean.setBindingSite(rowNames[rowNum].getBindingSite());
			for (int colNum = 0; colNum < matrix.getWidth(); colNum++) {
				MetricName metric = columnNames[colNum];
				double measure = matrix.getData()[rowNum][colNum];
				if (metric == MetricName.LAPLACE) {
					matBean.setLaplace(measure);
				}
				else if (metric == MetricName.CONFIDENCE) {
					matBean.setConfidence(measure);
				}
				else if (metric == MetricName.LIFT) {
					matBean.setLift(measure);
				}
				else if (metric == MetricName.COSINE) {
					matBean.setCosine(measure);
				}
				else if (metric == MetricName.JACCARD) {
					matBean.setJaccard(measure);
				}
				else if (metric == MetricName.KAPPA) {
					matBean.setKappa(measure);
				}
				else if (metric == MetricName.PHI) {
					matBean.setPhi(measure);
				}
				else if (metric == MetricName.PVALUE) {
					matBean.setPvalue(measure);
				}
				else if (metric == MetricName.NUM_BASELINE) {
					matBean.setNumBaseline(measure);
				}
				else if (metric == MetricName.NUM_QUERY) {
					matBean.setNumQuery(measure);
				}
				else {
					throw new IOException("Invalid metric.");
				}
			}
			beans.add(matBean);
		}
		return beans;
	}
}
