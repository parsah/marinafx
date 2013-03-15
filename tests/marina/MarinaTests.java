package marina;

import marina.alignment.AlignmentActionTest;
import marina.alignment.PMatchTest;
import marina.alignment.RabinKarpTest;
import marina.bindingsite.LinearDNAMotifTest;
import marina.bindingsite.PositionWeightMatrixTest;
import marina.group.FASTASequenceTest;
import marina.group.GroupTest;
import marina.matrix.RankedAbundanceMatrixTest;
import marina.matrix.ContingencyMatrixCellTest;
import marina.matrix.ContingencyMatrixTest;
import marina.matrix.MatrixTest;
import marina.parameter.BooleanParameterTest;
import marina.parameter.DoubleParameterTest;
import marina.parameter.IntegerParameterTest;
import marina.parser.FASTAParserTest;
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
	PMatchTest.class, RankedAbundanceMatrixTest.class,
})
public class MarinaTests {

}
