package nl.rsvier.icaras.core.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

import nl.rsvier.icaras.core.arbeidsmarkt.CvGenerator;

import org.junit.Before;
import org.junit.Test;

public class PersoonKandidaatCVGeneratorTest {
	
	Persoon persoon;
	Persoon persoon2;
	Persoon persoon3;
	Kandidaat kandidaat;
	Kandidaat kandidaat2;
	Kandidaat kandidaat3;
	CvGenerator cvGen;
	
	@Before
	public void setUp(){
		persoon = new Persoon();
		persoon.setGearchiveerd(true);
		persoon.setPriveRelatie(false);
		persoon.setVoornaam("Voornaam");
		persoon.setTussenvoegsels("van den");
		persoon.setAchternaam("Achternaam");
		persoon.setOpmerking("Dit is een persoon");
		persoon.setGeboortedatum(new GregorianCalendar(1988, GregorianCalendar.FEBRUARY, 13));
		kandidaat = new Kandidaat();
		kandidaat.setOpmerking("Dit is de testkandidaat");
		kandidaat.setGearchiveerd(false);
		persoon.addRol(kandidaat);//persoon met rol kandidaat
		persoon2 = new Persoon();//persoon zonder kandidaatrol
		kandidaat.getCvGenerator().setPersoonReference(persoon2);//toekennen (nieuwe) persoon2 aan kandidaat(rol) van persoon
		kandidaat2 = new Kandidaat();
		kandidaat2.getCvGenerator().setPersoonReference(persoon2);//toekennen van persoon2 zonder kandidaatrol aan kandidaat (rol zonder persoon)
	}

	@Test
	public void setPersoonTest(){
		assertNotNull("persoon van kandidaat is niet null", kandidaat.getCvGenerator().getPersoon());
		assertTrue("persoon van CVGenerator is dezelfde als persoon die hier eigenaar van is", persoon==kandidaat.getCvGenerator().getPersoon());
		assertTrue("persoon van CVGenerator is dezelfde als persoon die hier eigenaar van is", persoon.equals(kandidaat.getCvGenerator().getPersoon()));
		assertFalse("persoon van CVGenerator is NIET dezelfde als persoon2 die getracht is toe te kennen", persoon2==kandidaat.getCvGenerator().getPersoon());
		assertNull("persoon van CvGenerator van kandidaat zonder persoon is ook na toekenning null", kandidaat2.getCvGenerator().getPersoon());
	}
	
	//Niet meer nodig/mogelijk, CvGenerator is nu private
//	@Test
//	public void illustreerNoodzaakPrivateCvGenerator(){
//		persoon2.addRol(kandidaat2);
//		cvGen = kandidaat2.getGegenereerdCV();
//		kandidaat.setGegenereerdCV(cvGen);
//		assertFalse("persoon van CVGenerator is NIET dezelfde als persoon die hier eigenaar van is", persoon==kandidaat.getGegenereerdCV().getPersoon());
//		kandidaat.setGegenereerdCV(null);
//		assertNull("De CvGenerator kan overschreven worden met null", kandidaat.getGegenereerdCV());
//	}
	
	@Test
	public void nullPersoonTest(){
		assertNull("kandidaat die niet toegekend is, heeft als persoon null", kandidaat2.getCvGenerator().getPersoon());
		kandidaat2.getCvGenerator().setPersoonReference(null);//doet niets, laat null als het dit was, laat betreffende persoon staan als deze was toegekend
		assertNull("na persoon set met waarde null is de persoon nog steeds null", kandidaat2.getCvGenerator().getPersoon());
		kandidaat.getCvGenerator().setPersoonReference(null);//doet niets, laat null als het dit was, laat betreffende persoon staan als deze was toegekend
		assertNotNull("toegekende kandidaatrol, heeft als persoon na set null nog steeds de persoon waaraan deze was toegekend", kandidaat.getCvGenerator().getPersoon());
		
	}
	
	@Test
	public void testGetRolByType(){
		PersoonsRol opgevraagdeRol = persoon.getKandidaat();
		assertNotNull("opgevraagde PersoonsRol is niet null", opgevraagdeRol);
	}
	
	@Test
	public void testSetPersoonVanAndereKandidaat(){ //vanwege weg moeten halen van ==this in setPersoon()
		persoon3 = new Persoon();
		persoon3.setGearchiveerd(true);
		persoon3.setPriveRelatie(false);
		persoon3.setVoornaam("Voornaamkopie");
		persoon3.setTussenvoegsels("van den");
		persoon3.setAchternaam("Achternaamkopie");
		persoon3.setOpmerking("Dit is een persoon");
		persoon3.setGeboortedatum(new GregorianCalendar(1988, GregorianCalendar.FEBRUARY, 13));
		kandidaat3 = new Kandidaat();
		persoon3.addRol(kandidaat3);
		
		persoon3.getKandidaat().getCvGenerator().setPersoonReference(persoon);
		assertFalse("De persoon die geen houder van de betreffende CvGenerator was, is geweigerd",
				persoon.equals((persoon3).getKandidaat().getCvGenerator().getPersoon()));
		assertTrue("De persoon van CvGenerator is dus nog steeds persoon3", persoon3.equals(kandidaat3.getCvGenerator().getPersoon()));
	}
}
