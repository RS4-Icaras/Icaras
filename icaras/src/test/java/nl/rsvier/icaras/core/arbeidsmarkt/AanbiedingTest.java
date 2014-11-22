package nl.rsvier.icaras.core.arbeidsmarkt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Bedrijf;
import nl.rsvier.icaras.core.relatiebeheer.Kandidaat;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse voor de core klasse: Aanbieding
 * 
 * @author Mark van Meerten
 */

public class AanbiedingTest {

	Persoon don;
	Persoon louis;

	Organisatie sterlingcooper;
	Organisatie pearsonhardman;

	Vacature vacature_sterlingcooper;
	Vacature vacature_pearsonhardman;

	List<Aanbieding> aanbiedingen_correct;
	Aanbieding aanbieding1;
	Aanbieding aanbieding2;
	Aanbieding aanbieding3;

	// TODO: Add clone methode to Aanbieding?

	@Before
	public void setUp() throws InvalidBusinessKeyException {

		don = new Persoon("Don", "Draper");
		louis = new Persoon("Louis", "Litt");

		don.addRol(new Kandidaat());
		louis.addRol(new Kandidaat());

		sterlingcooper = new Organisatie("sterling cooper draper pryce");
		sterlingcooper.addRol(new Bedrijf());

		pearsonhardman = new Organisatie("pearson hardman");
		pearsonhardman.addRol(new Bedrijf());

		vacature_sterlingcooper = new Vacature(sterlingcooper,
				"looking for a computer operator");
		vacature_pearsonhardman = new Vacature(pearsonhardman,
				"looking for interns");

		aanbieding1 = new Aanbieding(don, sterlingcooper);
		// aanbieding1_clone = new Aanbieding(don, sterlingcooper);

		aanbieding2 = new Aanbieding(louis, sterlingcooper,
				vacature_sterlingcooper);
		// aanbieding2_clone = new Aanbieding(louis, sterlingcooper, vacature1);

		aanbieding3 = new Aanbieding(louis, pearsonhardman,
				vacature_pearsonhardman);
		// aanbieding3_clone = new Aanbieding(louis, pearsonhardman, vacature2);

		aanbiedingen_correct = new ArrayList<Aanbieding>();
		aanbiedingen_correct.addAll(Arrays.asList(aanbieding1, aanbieding2,
				aanbieding3));

	}

	private Organisatie newRandomTestOrganisatie()
			throws InvalidBusinessKeyException {
		Random r = new Random();
		Organisatie o = new Organisatie(String.valueOf(r.nextInt(1000000)));
		o.addRol(new Bedrijf());
		return o;
	}

	private Persoon newRandomTestPersoon() {
		Random r = new Random();
		Persoon p = new Persoon(String.valueOf(r.nextInt(1000000)),
				String.valueOf(r.nextInt(1000000)));
		p.addRol(new Kandidaat());
		return p;
	}

	@Test()
	public void testConstructorSuccess() {

		/*
		 * Is van elke Aanbieding de business key geinitialiseerd?
		 */

		for (Aanbieding a : aanbiedingen_correct) {
			assertTrue("Persoon is gezet", a.heeftPersoon());
			assertTrue("Organisatie is gezet", a.heeftOrganisatie());

			assertTrue("Bi-directionele relatie geinitialiseerd", a
					.getOrganisatie().getBedrijf().heeftAanbieding(a));
			assertTrue("Bi-directionele relatie geinitialiseerd", a
					.getPersoon().getKandidaat().heeftAanbieding(a));
			if (a.getVacature() != null) {
				assertTrue("Bi-directionele relatie geinitialiseerd", a
						.getVacature().heeftAanbieding(a));
			}

		}

	}

	@Test(expected = InvalidBusinessKeyException.class)
	public void testConstructorIncorrectBusinessKey_NullPersoon()
			throws InvalidBusinessKeyException {

		/*
		 * Persoon is immutable en kan alleen worden geinitialiseerd in de
		 * constructor, check of er geen instanties worden gemaakt met een
		 * (deels) incorrecte business key
		 */

		new Aanbieding(don, null);

	}

	@Test(expected = InvalidBusinessKeyException.class)
	public void testConstructorIncorrectBusinessKey_NullOrganisatie()
			throws InvalidBusinessKeyException {

		/*
		 * Organisatie is immutable en kan alleen worden geinitialiseerd in de
		 * constructor, check of er geen instanties worden gemaakt met een
		 * (deels) incorrecte business key
		 */

		new Aanbieding(null, sterlingcooper);

	}

	@Test
	public void test_organisatieConstraint() throws InvalidBusinessKeyException {
		assertFalse("Organisatie mag niet null zijn",
				aanbieding1.organisatieConstraint(null));
		assertFalse("Organisatie moet een bedrijfsrol bevatten",
				aanbieding1.organisatieConstraint(new Organisatie(
						"Sebben & Sebben")));
	}

	@Test
	public void test_organisatieMagWordenToegevoegd() {
		assertFalse(
				"Organisatie mag niet worden toegevoegd omdat dit al eerder is gedaan",
				aanbieding1.organisatieMagWordenToegevoegd(aanbieding1
						.getOrganisatie()));
	}

	@Test
	public void test_removeAllReferences() {

		for (Aanbieding use_aanbieding : aanbiedingen_correct) {

			if (use_aanbieding.getVacature() != null) {
				assertTrue(
						"De collectie Aanbiedingen van Vacature bevat een referentie naar de Aanbieding",
						use_aanbieding.getVacature().heeftAanbieding(
								use_aanbieding));
			}

			assertTrue(
					"De collectie Aanbiedingen van Bedrijf bevat een referentie naar de Aanbieding",
					use_aanbieding.getOrganisatie().getBedrijf()
							.heeftAanbieding(use_aanbieding));

			assertTrue(
					"De collectie Aanbiedingen van Kandidaat bevat een referentie naar de Aanbieding",
					use_aanbieding.getPersoon().getKandidaat()
							.heeftAanbieding(use_aanbieding));

			assertTrue("Alle referenties naar deze Aanbieding zijn verwijderd",
					use_aanbieding.removeAllReferences());

			if (use_aanbieding.getVacature() != null) {
				assertFalse(
						"Aanbieding is verwijderd uit de collectie Aanbiedingen van Vacature",
						use_aanbieding.getVacature().heeftAanbieding(
								use_aanbieding));
			}

			assertFalse(
					"Aanbieding is verwijderd uit de collectie Aanbiedingen van Bedrijf",
					use_aanbieding.getOrganisatie().getBedrijf()
							.heeftAanbieding(use_aanbieding));

			assertFalse(
					"Aanbieding is verwijderd uit de collectie Aanbiedingen van Kandidaat",
					use_aanbieding.getPersoon().getKandidaat()
							.heeftAanbieding(use_aanbieding));

		}

	}

	@Test
	public void test_setVacatureReferentie() throws InvalidBusinessKeyException {

		Random r = new Random();

		// Use object to test
		Vacature use_vacature1 = new Vacature(sterlingcooper, String.valueOf(r
				.nextInt(1000000)));
		Vacature use_vacature2 = new Vacature(pearsonhardman, String.valueOf(r
				.nextInt(1000000)));
		Aanbieding use_aanbieding = aanbieding1;

		// Follow the rules
		assertTrue("Voeg nieuwe Vacature toe",
				use_aanbieding.setVacatureReferentie(use_vacature1));
		assertNotNull("Vacature is toegevoegd", use_aanbieding.getVacature());
		assertTrue(
				"Aanbieding is toegevoegd aan de collectie Aanbiedingen van Vacature",
				use_aanbieding.getVacature().heeftAanbieding(use_aanbieding));

		// Try and break some rules
		assertFalse(
				"Vacature is van een andere Organisatie en kan dus niet worden toegevoegd",
				use_aanbieding.setVacatureReferentie(use_vacature2));
		assertFalse("De Aanbieding Vacature is niet overschreven",
				use_aanbieding.getVacature().equals(use_vacature2));
		assertFalse(
				"Aanbieding is niet toegevoegd aan de collectie Aanbiedingen van Vacature",
				use_vacature2.heeftAanbieding(use_aanbieding));

		assertFalse(
				"Vacature.setVacatureReferentie() accepteert geen null waarde",
				use_aanbieding.setVacatureReferentie(null));

	}

	@Test
	public void test_removeVacature() {

		// Use object to test
		Aanbieding use_aanbieding = aanbieding2;
		Vacature use_vacature = use_aanbieding.getVacature();

		if (use_vacature == null) {
			fail("De Aanbieding die je wilt testen heeft geen vacature");
		}

		// Try and break some rules
		assertFalse("Kan geen null verwijderen",
				use_aanbieding.removeVacature(null));
		assertFalse(
				"Kan Vacature.setVacatureReferentie() niet overschrijven met een null waarde",
				use_aanbieding.setVacatureReferentie(null));

		assertFalse("Aanbieding bevat geen vacature van Pearson Hardman",
				use_aanbieding.getVacature().equals(vacature_pearsonhardman));
		assertFalse("en kan dus niet worden verwijderd",
				use_aanbieding.removeVacature(vacature_pearsonhardman));

		// Follow the rules
		assertTrue("Vacature bevat een referentie naar een Aanbieding",
				use_vacature.heeftAanbieding(use_aanbieding));
		assertTrue("Aanbieding bevat een referentie naar een Vacature",
				use_aanbieding.getVacature().equals(use_vacature));
		assertTrue("Vacature referentie verwijderd",
				use_aanbieding.removeVacature(use_vacature));
		assertFalse("Check of Vacature is verwijderd",
				use_aanbieding.heeftVacature(use_vacature));
		assertFalse(
				"Check of de referentie van Vacature naar Aanbieding is verwijderd",
				use_vacature.heeftAanbieding(use_aanbieding));

	}

	@Test(expected = InvalidBusinessKeyException.class)
	public void testAanbiedingConflicterendeOrganisatie()
			throws MalformedURLException, InvalidBusinessKeyException {

		Organisatie organisatie = this.newRandomTestOrganisatie();
		Persoon persoon1 = this.newRandomTestPersoon();

		Vacature vacature = new Vacature(organisatie,
				"een willekeurige vacature van organisatie1",
				"http://www.url.com");
		Aanbieding aanbieding_correct = new Aanbieding(persoon1,
				vacature.getOrganisatie(), vacature);
		Aanbieding aanbieding_invalid = new Aanbieding(persoon1,
				this.newRandomTestOrganisatie(), vacature);

		assertTrue(
				"Vacature en Aanbieding moeten dezelfde Organisatie refereren",
				vacature.heeftAanbieding(aanbieding_correct));

		assertFalse(
				"Vacature en Aanbieding moeten dezelfde Organisatie refereren",
				vacature.heeftAanbieding(aanbieding_invalid));

	}

	/*
	 * Util
	 */

	@Test
	public void test_hashCode() {

		/*
		 * Test of de hashcodes genereren naar verwachting
		 */

		// assertTrue("Hashcodes zijn gelijk",
		// aanbieding1.hashCode() == aanbieding1_clone.hashCode());
		// assertTrue("Hashcodes zijn gelijk",
		// aanbieding2.hashCode() == aanbieding2_clone.hashCode());
		// assertTrue("Hashcodes zijn gelijk",
		// aanbieding3.hashCode() == aanbieding3_clone.hashCode());

		assertTrue("Hashcodes zijn niet gelijk",
				aanbieding1.hashCode() != aanbieding2.hashCode());
		assertTrue("Hashcodes zijn niet gelijk",
				aanbieding2.hashCode() != aanbieding3.hashCode());
		assertTrue("Hashcodes zijn niet gelijk",
				aanbieding1.hashCode() != aanbieding3.hashCode());

		// Test of het aanpassen van het id invloed heeft op de hashcode
		// generator
		// aanbieding1.setId(9999);
		// assertTrue(
		// "Hashcodes zijn gelijk, id word niet meegenomen in de berekening van de hashCode",
		// aanbieding1.hashCode() == aanbieding1_clone.hashCode());

	}

	@Test
	public void testEquals() {

		assertTrue("Vergeleken met zichzelf", aanbieding1.equals(aanbieding1));
		// assertTrue("Vergeleken met clone van zichzelf",
		// aanbieding1.equals(aanbieding1_clone));

		assertTrue("Vergeleken met zichzelf", aanbieding2.equals(aanbieding2));
		// assertTrue("Vergeleken met clone van zichzelf",
		// aanbieding2.equals(aanbieding2_clone));

		assertTrue("Vergeleken met zichzelf", aanbieding3.equals(aanbieding3));
		// assertTrue("Vergeleken met clone van zichzelf",
		// aanbieding3.equals(aanbieding3_clone));

		assertFalse("Vergeleken met een ander object van hetzelfde type",
				aanbieding1.equals(aanbieding2));

		assertFalse("Vergeleken met een ander object van hetzelfde type",
				aanbieding2.equals(aanbieding3));

		assertFalse("Vergeleken met een ander object van hetzelfde type",
				aanbieding1.equals(aanbieding3));

		assertFalse("Vergeleken met een ander object van een ander type (String)",
				aanbieding1.equals(new String("willekeurige string")));

	}
}
