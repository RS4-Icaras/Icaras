package nl.rsvier.icaras.dao;

import nl.rsvier.icaras.dao.relatiebeheer.AdresDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.NfaDaoHibernateTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Deze klasse is bedoeld om alle DaoTests gezamenlijk te runnen binnen dezelfde
 * Spring-test. Het opstarten van de ApplicationContext (inclusief het genereren
 * van het databaseschema) hoeft hierdoor maar eenmalig te gebeuren voor alle
 * tests.
 * 
 * @author Gerben
 * @author Gordon
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AdresDaoHibernateTest.class, NfaDaoHibernateTest.class })
// Voeg hier alle DaoTest-klassen aan toe
// @ContextConfiguration(locations={"classpath:icarasdb-context.xml"})//Per
// testklasse toevoegen ivm autowiring
public class DaoTestSuite {

}
