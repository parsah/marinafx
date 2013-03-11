package marina;

import marina.alignment.RabinKarpTest;
import marina.bindingsite.LinearDNAMotifTest;
import marina.group.FASTASequenceTest;
import marina.matrix.ContingencyMatrixCellTest;
import marina.matrix.ContingencyMatrixTest;
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
	ContingencyMatrixTest.class, ContingencyMatrixCellTest.class})
public class MarinaTests {

}
