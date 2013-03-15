package marina.matrix;

import java.util.Arrays;

import marina.quantification.MetricName;

public class RankedAbundanceMatrix extends Matrix {

	public RankedAbundanceMatrix(double[][] data) {
		super(data);
	}

	/**
	 * Ranks and sorts a matrix such that only those columns having
	 * statistical metrics are processed. All other columns are not ranked.
	 * */
	public void rankMatrix() {
		MetricName[] colName = (MetricName[])this.getColumns();
//		System.out.println(this.getRows());
//		this.debug();
//		System.out.println();
		for (int colNum = 0; colNum < this.getWidth(); colNum++) {
			if (colName[colNum].isStat()) {
				double[] theColumn = this.getColumn(colNum);
				int[] ranks = this.rank(theColumn);
				for (int rowNum = 0; rowNum < theColumn.length; rowNum++) {
					this.getData()[rowNum][colNum] = ranks[rowNum];
				}
			}
		}
//		System.out.println(this.getRows());
//		System.out.println();
//		this.debug();
	}

	/**
	 * Ranks the pre-defined column array such that larger values are
	 * closed to 1 whereas smaller values are closed to N; N references
	 * the upper-bound array size.
	 * @return array referencing integer-ranks for all column values.
	 * */
	public int[] rank(double[] column) {
		// sort the array and get its index
		double[] sorted = this.sort(column);
		int[] ranks = new int[column.length];
		for (int i = 0; i < column.length; i++) {
			int rank = Arrays.binarySearch(sorted, column[i]);
			// larger measures have higher ranks versus smaller measures.
			ranks[i] = Math.abs(rank-column.length);
		}
		return ranks;
	}

	/**
	 * Sorts a pre-defined column and returns the result as a new list.
	 * @return array referencing sorted values given a prior array.
	 * */
	public double[] sort(double[] column) {
		double[] sorted = new double[column.length];
		System.arraycopy(column, 0, sorted, 0, column.length );
		Arrays.sort(sorted);
		return sorted;
	}
}
