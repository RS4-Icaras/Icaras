package nl.rsvier.icaras.dao.relatiebeheer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author Gerben en Gordon
 * Deze klasse is bedoeld om alle DaoTests gezamenlijk te runnen binnen dezelfde Spring-test.
 * Het opstarten van de ApplicationContext (inclusief het genereren van het databaseschema)
 * hoeft hierdoor maar eenmalig te gebeuren voor alle tests.
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AdresDaoHibernateSpringTest.class, })//Voeg hier alle DaoTest-klassen aan toe
//@ContextConfiguration(locations={"classpath:icarasdb-context.xml"})//Per testklasse toevoegen ivm autowiring
public class DaoTestSuiteSpring {

}
