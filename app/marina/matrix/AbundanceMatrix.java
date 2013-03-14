package marina.matrix;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import marina.quantification.MetricName;

public class AbundanceMatrix extends Matrix {

	public AbundanceMatrix(double[][] data) {
		super(data);
	}

	/**
	 * Ranks and sorts a matrix such that only those columns having
	 * statistical metrics are processed. All other columns are not ranked.
	 * */
	public void rank() {
		MetricName[] names = (MetricName[])this.getColumns();
		this.debug();
		System.out.println();
		for (int colNum = 0; colNum < this.getWidth(); colNum++) {
			if (names[colNum].isStat()) {
				double[] theColumn = this.getColumn(colNum);
				System.out.println("col: " + Arrays.toString(theColumn));
				ColumnComparator comparator = new ColumnComparator(theColumn);
			}
		}
		//		this.debug();
	}

}
