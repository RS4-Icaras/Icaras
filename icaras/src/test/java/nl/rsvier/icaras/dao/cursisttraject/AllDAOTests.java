package nl.rsvier.icaras.dao.cursisttraject;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BoekDAOTest.class, CursistDAOTest.class, ExamenDAOTest.class,
		OpdrachtDAOTest.class, ProeftoetsDAOTest.class, ResultaatDAOTest.class,
		TrajectDAOTest.class, TrajectEenheidDAOTest.class })
public class AllDAOTests {

}
