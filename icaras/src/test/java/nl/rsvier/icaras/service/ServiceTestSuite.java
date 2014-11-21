package nl.rsvier.icaras.service;

import nl.rsvier.icaras.service.relatiebeheer.RelatieRelatiesTest;
import nl.rsvier.icaras.service.relatiebeheer.RelatieServiceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Deze klasse is bedoeld om alle Core Tests gezamenlijk te runnen binnen
 * dezelfde JUnit test.
 * 
 * @author Gerben
 * @author Gordon
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	RelatieRelatiesTest.class, 
	RelatieServiceTest.class
})
public class ServiceTestSuite {

}