package nl.rsvier.icaras.dao;

import nl.rsvier.icaras.dao.relatiebeheer.AdresDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.ArbeidsovereenkomstDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.KandidaatDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.NfaDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.PersoonDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.RelatieDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.WerknemerDaoHibernateTest;
import nl.rsvier.icaras.service.relatiebeheer.PersoonOrganisatiemoduleServiceTest;
import nl.rsvier.icaras.service.relatiebeheer.RelatieServiceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author Gerben en Gordon Deze klasse is bedoeld om alle DaoTests gezamenlijk
 *         te runnen binnen dezelfde Spring-test. Het opstarten van de
 *         ApplicationContext (inclusief het genereren van het databaseschema)
 *         hoeft hierdoor maar eenmalig te gebeuren voor alle tests.
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AdresDaoHibernateTest.class, NfaDaoHibernateTest.class,
		RelatieDaoHibernateTest.class, RelatieServiceTest.class,
		ArbeidsovereenkomstDaoHibernateTest.class,
		KandidaatDaoHibernateTest.class, PersoonDaoHibernateTest.class,
		WerknemerDaoHibernateTest.class, PersoonOrganisatiemoduleServiceTest.class })
// Voeg hier alle DaoTest-klassen aan toe
// @ContextConfiguration(locations={"classpath:icarasdb-context.xml"})//Per
// testklasse toevoegen ivm autowiring
public class DaoTestSuite {

}
