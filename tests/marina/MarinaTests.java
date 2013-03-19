package marina;

import marina.alignment.AlignmentActionTest;
import marina.alignment.PMatchTest;
import marina.alignment.RabinKarpTest;
import marina.bindingsite.LinearDNAMotifTest;
import marina.bindingsite.PositionWeightMatrixTest;
import marina.bindingsite.TextualPWMWrapperTest;
import marina.group.FASTAFragmentTest;
import marina.group.FASTASequenceTest;
import marina.group.GroupAbundanceWrapperTest;
import marina.group.GroupTest;
import marina.matrix.ContingencyMatrixCellTest;
import marina.matrix.ContingencyMatrixTest;
import marina.matrix.MatrixTest;
import marina.parameter.BooleanParameterTest;
import marina.parameter.DoubleParameterTest;
import marina.parameter.IntegerParameterTest;
import marina.parser.FASTAParserTest;
import marina.parser.PWMParserTest;
import marina.quantification.MetricNameTest;
import marina.quantification.StatisticTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * High-level test-suite to centralize all unit-tests.
 * */
@RunWith(Suite.class)
@SuiteClasses({ RabinKarpTest.class, LinearDNAMotifTest.class,
	FASTASequenceTest.class, StatisticTest.class, 
	ContingencyMatrixTest.class, ContingencyMatrixCellTest.class,
	AlignmentActionTest.class, GroupTest.class, FASTAParserTest.class,
	PositionWeightMatrixTest.class, BooleanParameterTest.class,
	MatrixTest.class, IntegerParameterTest.class, DoubleParameterTest.class,
	PMatchTest.class, FASTAParserTest.class, PWMParserTest.class,
	TextualPWMWrapperTest.class, MetricNameTest.class, 
	FASTAFragmentTest.class, GroupAbundanceWrapperTest.class,
	
})
public class MarinaTests {

}
