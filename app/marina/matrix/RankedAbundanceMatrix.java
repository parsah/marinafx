package marina.matrix;

import marina.bean.RepresentedBeanBuilder;
import marina.quantification.MetricName;
import marina.quantification.Statistic;

public class RankedAbundanceMatrix extends Matrix {

	public RankedAbundanceMatrix(double[][] data) {
		super(data);
	}

	/**
	 * Ranks and sorts a matrix such that only those columns having
	 * statistical metrics are processed. All other columns are not ranked.
	 * */
	public void order() {
		MetricName[] metrics = MetricName.values();
		for (int colNum = 0; colNum < this.getWidth(); colNum++) {
			double[] theColumn = this.getColumn(colNum);
			MetricName col = metrics[colNum];
			if (col.isStat()) {
				int[] ranks = Statistic.rank(theColumn);
				for (int rowNum = 0; rowNum < theColumn.length; rowNum++) {
					this.getData()[rowNum][colNum] = ranks[rowNum];
				}
			}
		}
	}
	
	public RepresentedBeanBuilder generateBeanFactory() {
		return new RepresentedBeanBuilder(this);
	}
}
