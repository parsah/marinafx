package quantification;

import gui.MarinaGUI;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import output.OutputTable;
import bean.RepresentedBeanBuilder;
import bean.RepresentedMatrixBean;

/**
 * Since TFBS quantification entails numerous computations, run all TFBS
 * computations in a background worker thread. Everything from derivation of
 * likely TFBSs to identification of statistically--sound TFBSs are performed
 * in this background thread. Resultant TFBSs are presented in a table widget.
 * @author Parsa Hosseini.
 * */
public class QuantificationFactory extends Task<Void> {

	@Override
	protected Void call() throws Exception {
		CandidateMatrixBuilder mb = new CandidateMatrixBuilder();
		AbundanceInference infer = new AbundanceInference(mb);
		infer.bindAbundances();
		RepresentedBeanBuilder builder = new 
				RepresentedBeanBuilder(infer.getOrderedMatrix());
		ObservableList<RepresentedMatrixBean> beans = builder.build();
		OutputTable table = MarinaGUI.get().getTable();
		table.addObservables(beans);
		return null;
	}
}
