package nl.rsvier.icaras.core.arbeidsmarkt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Bedrijf;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse voor de core klasse: Vacature
 * 
 * @author Mark van Meerten
 */

public class VacatureTest {

	Organisatie sterlingcooper;
	Organisatie pearsonhardman;
	Organisatie organisatieZonderRol;

	List<Vacature> vacatures_correct;
	Vacature vacature1;
	Vacature vacature1_clone;
	Vacature vacature2;
	Vacature vacature2_clone;
	Vacature vacature3;
	Vacature vacature3_clone;
	Vacature vacature4;
	Vacature vacature4_clone;

	// TODO: Add clone methode to Aanbieding?

	@Before
	public void setUp() throws InvalidBusinessKeyException {

		/*
		 * Vacature: Een Organisatie plaatst een Omschrijving van de functie
		 * waarvoor ze op zoek zijn. Organisatie en Omschrijving vormen de
		 * business key en er is voor gekozen deze immutable te maken
		 */

		sterlingcooper = new Organisatie("Sterling Cooper Advertising Agency");
		sterlingcooper.addRol(new Bedrijf());
		pearsonhardman = new Organisatie("Pearson Hardman");
		pearsonhardman.addRol(new Bedrijf());

		organisatieZonderRol = new Organisatie("Organisatie zonder rol");

		vacature1 = new Vacature(sterlingcooper, "looking for a copy writer");
		// vacature1_clone = new Vacature(sterlingcooper,
		// "looking for a copy writer");

		vacature2 = new Vacature(sterlingcooper,
				"looking for a new head of television");
		// vacature2_clone = new Vacature(sterlingcooper,
		// "looking for a new head of television");

		vacature3 = new Vacature(pearsonhardman, "looking for interns");
		// vacature3_clone = new Vacature(pearsonhardman,
		// "looking for interns");

		vacature4 = new Vacature(pearsonhardman, "looking for cheaters");
		// vacature4_clone = new Vacature(pearsonhardman,
		// "looking for cheaters");

		vacatures_correct = new ArrayList<Vacature>();
		vacatures_correct.addAll(Arrays.asList(vacature1, vacature2, vacature3,
				vacature4));

	}

	/*
	 * Constructor Tests
	 */

	@Test()
	public void testConstructorSuccess() {

		/*
		 * Is van elke Vacature de business key geinitialiseerd?
		 */

		for (Vacature v : vacatures_correct) {
			assertTrue("Organisatie is gezet", v.heeftOrganisatie());
			assertTrue("Omschrijving is gezet", v.heeftOmschrijving());

			assertTrue("Bi-directionele relatie geinitialiseerd", v
					.getOrganisatie().getBedrijf().heeftVacature(v));
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
		new Vacature(null, "Spook Omschrijving");

	}

	@Test(expected = InvalidBusinessKeyException.class)
	public void testConstructorIncorrectBusinessKey_NullOmschrijving()
			throws InvalidBusinessKeyException {

		/*
		 * Omschrijving is immutable en kan alleen worden geinitialiseerd in de
		 * constructor, check of er geen instanties worden gemaakt met een
		 * (deels) incorrecte business key
		 */

		new Vacature(sterlingcooper, null);

	}

	@Test(expected = InvalidBusinessKeyException.class)
	public void testConstructorIncorrectBusinessKey_LegeOmschrijving()
			throws InvalidBusinessKeyException {

		/*
		 * Omschrijving is immutable en kan alleen worden geinitialiseerd in de
		 * constructor, check of er geen instanties worden gemaakt met een
		 * (deels) incorrecte business key
		 */

		new Vacature(sterlingcooper, "");

	}

	/*
	 * Methode Tests
	 */

	@Test
	public void test_organisatieConstraint() {
		assertFalse("Organisatie mag niet null zijn",
				vacature1.organisatieConstraint(null));
		assertFalse("Organisatie moet een bedrijfsrol bevatten",
				vacature1.organisatieConstraint(organisatieZonderRol));
	}

	@Test
	public void test_organisatieMagWordenToegevoegd() {
		assertFalse(
				"Organisatie mag niet worden toegevoegd omdat dit al eerder is gedaan",
				vacature1.organisatieMagWordenToegevoegd(pearsonhardman));
	}

	@Test
	public void test_omschrijvingConstraint() {
		assertFalse("Omschrijving mag niet null zijn",
				vacature1.omschrijvingConstraint(null));
		assertFalse("Omschrijving mag niet een lege string zijn",
				vacature1.omschrijvingConstraint(""));
	}

	@Test
	public void test_omschrijvingMagWordenToegevoegd() {
		assertFalse(
				"Omschrijving mag niet worden gezet omdat dit al eerder is gedaan",
				vacature1.omschrijvingMagWordenToegevoegd("bla bla"));
	}

	@Test
	public void test_helperSetUrl() {
		String invalidUrl = "geen geldige url";
		for (Vacature v : vacatures_correct) {
			try {
				// Url die geen exception gooit
				v.helperSetUrl("http://www.google.com/");
				assertNotNull("Website Url toegevoegd", v.getWebsite());
				// Url die wel een exception gooit
				v.helperSetUrl(invalidUrl);
				assertNull("Website Url toegevoegd", v.getWebsite());
			} catch (MalformedURLException e) {
				assertTrue("Website Url is niet geldig",
						e.getMessage().equals("no protocol: " + invalidUrl));
			}
		}
	}

	@Test
	public void test_removeAllReferences() {

		for (Vacature use_vacature : vacatures_correct) {

			for (Aanbieding a : use_vacature.getAanbiedingen()) {
				assertNotNull("", a.getVacature());
			}

			assertNotNull("", use_vacature.getOrganisatie().getBedrijf()
					.getVacatures().contains(use_vacature));

			assertTrue("", use_vacature.removeAllReferences());

			for (Aanbieding a : use_vacature.getAanbiedingen()) {
				assertNull("", a.getVacature());
			}

			assertFalse("", use_vacature.getOrganisatie().getBedrijf()
					.getVacatures().contains(use_vacature));

		}

	}

	/*
	 * Util Tests
	 */

	@Test
	public void test_hashCode() {

		/*
		 * Test of de hashcodes genereren naar verwachting
		 */
		// assertTrue("Hashcodes zijn gelijk",
		// vacature1.hashCode() == vacature1_clone.hashCode());
		// assertTrue("Hashcodes zijn gelijk",
		// vacature2.hashCode() == vacature2_clone.hashCode());
		// assertTrue("Hashcodes zijn gelijk",
		// vacature3.hashCode() == vacature3_clone.hashCode());

		assertTrue("Hashcodes zijn niet gelijk",
				vacature1.hashCode() != vacature2.hashCode());
		assertTrue("Hashcodes zijn niet gelijk",
				vacature2.hashCode() != vacature3.hashCode());
		assertTrue("Hashcodes zijn niet gelijk",
				vacature1.hashCode() != vacature3.hashCode());

		/*
		 * Attribuut id is geen onderdeel van de businesskey en zou geen invloed
		 * moeten hebben op de generatie
		 */
		// vacature1.setId(9999);
		// assertTrue(
		// "Hashcodes zijn gelijk, id word niet meegenomen in de berekening van de hashCode",
		// vacature1.hashCode() == vacature1_clone.hashCode());

		/*
		 * Attribuut Website Url is geen onderdeel van de businesskey en zou
		 * geen invloed moeten hebben op de generatie
		 */
		// try {
		// vacature1.helperSetUrl("http://www.omewillem.nl");
		// assertTrue(
		// "Hashcodes zijn gelijk, URL word niet meegenomen in de berekening van de hashCode",
		// vacature1.hashCode() == vacature1_clone.hashCode());
		// } catch (MalformedURLException e) {
		// fail("Website Url is niet legaal terwijl deze dat wel zou moeten zijn");
		// }
	}

	@Test
	public void testEquals() {

		/*
		 * Test of de equals methode uitvoer geeft naar verwachting
		 */
		assertTrue("Vergeleken met zichzelf", vacature1.equals(vacature1));
		// assertTrue("Vergeleken met clone van zichzelf",
		// vacature1.equals(vacature1_clone));

		assertTrue("Vergeleken met zichzelf", vacature2.equals(vacature2));
		// assertTrue("Vergeleken met clone van zichzelf",
		// vacature2.equals(vacature2_clone));

		assertTrue("Vergeleken met zichzelf", vacature3.equals(vacature3));
		// assertTrue("Vergeleken met clone van zichzelf",
		// vacature3.equals(vacature3_clone));

		assertFalse("Vergeleken met gelijke objecten",
				vacature1.equals(vacature2));
		assertFalse("Vergeleken met gelijke objecten",
				vacature1.equals(vacature3));
		assertFalse("Vergeleken met gelijke objecten",
				vacature1.equals(vacature4));
		assertFalse("Vergeleken met gelijke objecten",
				vacature2.equals(vacature3));
		assertFalse("Vergeleken met gelijke objecten",
				vacature2.equals(vacature4));
		assertFalse("Vergeleken met gelijke objecten",
				vacature3.equals(vacature4));

		assertFalse("Vergeleken met ander type object (String)",
				vacature1.equals(new String("willekeurige string")));
	}

}
