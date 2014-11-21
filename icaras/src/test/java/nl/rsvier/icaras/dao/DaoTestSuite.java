package nl.rsvier.icaras.dao;

import nl.rsvier.icaras.dao.arbeidsmarkt.AanbiedingDaoHibernateTest;
import nl.rsvier.icaras.dao.arbeidsmarkt.VacatureDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.AdresDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.NfaDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.OrganisatieDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.OrganisatieRolDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.PersoonsRolDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.RelatieDaoHibernateTest;
import nl.rsvier.icaras.dao.relatiebeheer.TestDaoAdapterTest;

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
@Suite.SuiteClasses({ 
	AdresDaoHibernateTest.class, 
	NfaDaoHibernateTest.class,

	OrganisatieDaoHibernateTest.class, 
	RelatieDaoHibernateTest.class, 
	
	PersoonsRolDaoHibernateTest.class,
	OrganisatieRolDaoHibernateTest.class,
	
	AanbiedingDaoHibernateTest.class, 
	VacatureDaoHibernateTest.class, 
	
	TestDaoAdapterTest.class
})
public class DaoTestSuite {

}
