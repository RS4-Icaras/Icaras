package nl.rsvier.icaras.core;

import nl.rsvier.icaras.core.relatiebeheer.AdresTest;
import nl.rsvier.icaras.core.relatiebeheer.ContactpersoonTest;
import nl.rsvier.icaras.core.relatiebeheer.CorrespondentieAdresTest;
import nl.rsvier.icaras.core.relatiebeheer.NfaTest;
import nl.rsvier.icaras.core.relatiebeheer.OrganisatieTest;
import nl.rsvier.icaras.core.relatiebeheer.PersoonKandidaatCVGeneratorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Deze klasse is bedoeld om alle Core Tests gezamenlijk te runnen binnen dezelfde
 * JUnit test.
 * 
 * @author Gerben
 * @author Gordon
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AdresTest.class, 
	NfaTest.class,
	CorrespondentieAdresTest.class,
	
	PersoonKandidaatCVGeneratorTest.class,
	
	OrganisatieTest.class,
	//BedrijfTest.class,
	ContactpersoonTest.class,
	//AanbiedingTest.class,
	//VacatureTest.class,
})
public class CoreTestSuite {

}