package marina.matrix;

import java.util.Comparator;

public class ColumnComparator implements Comparator<Integer> {
	private double[] values;
	
	public ColumnComparator(double[] column) {
		this.values = column;
	}
	
	public Integer positions() {
		Integer[] positions = new Integer[this.values.length];
		return null;
	}
	
	@Override
	public int compare(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
