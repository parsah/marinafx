package marina.quantification;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MetricNameTest {
	private MetricName[] names = MetricName.values();
	
	/**
	 * Assert that there are multiple statistical metrics.
	 * */
	@Test
	public void testMultipleMetricsExist() {
		int numIsStat = 0;
		for (int i = 0; i < this.names.length; i++) {
			MetricName metric = this.names[i];
			if (metric.isStat()) {
				numIsStat += 1;
			}
		}
		assertTrue(numIsStat > 0);
	}

}
