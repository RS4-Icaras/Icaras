package nl.rsvier.icaras.core.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse voor de core klasse: Organisatie
 * 
 * @author Mark van Meerten
 */

public class OrganisatieTest {

	List<Organisatie> organisaties_correct;
	Organisatie sterlingcooper;
	Organisatie sterlingcooper_clone;
	Organisatie pearsonhardman;
	Organisatie pearsonhardman_clone;

	Bedrijf bedrijfsrol;
	Leverancier leveranciersrol;

	Persoon bertram;
	Persoon peggy;

	@Before
	public void setUp() throws InvalidBusinessKeyException {

		sterlingcooper = new Organisatie("Sterling Cooper Advertising Agency");
		sterlingcooper_clone = new Organisatie(
				"Sterling Cooper Advertising Agency");

		pearsonhardman = new Organisatie("Pearson Hardman");
		pearsonhardman_clone = new Organisatie("Pearson Hardman");

		bedrijfsrol = new Bedrijf();
		leveranciersrol = new Leverancier();

		bertram = new Persoon("Bertram", "Cooper");
		bertram.addRol(new Contactpersoon());
		sterlingcooper.addContactpersoon(bertram);
		sterlingcooper_clone.addContactpersoon(bertram);

		peggy = new Persoon("Peggy", "Olsen");
		peggy.addRol(new Contactpersoon());
		sterlingcooper.addContactpersoon(peggy);
		sterlingcooper_clone.addContactpersoon(peggy);

		organisaties_correct = new ArrayList<Organisatie>();
		organisaties_correct.addAll(Arrays.asList(sterlingcooper,
				sterlingcooper_clone, pearsonhardman, pearsonhardman_clone));
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
		p.addRol(new Contactpersoon());
		p.getContactpersoon().setFunctie(String.valueOf(r.nextInt(1000000)));
		return p;
	}

	private Persoon newRandomTestPersoonZonderRol() {
		Random r = new Random();
		Persoon p = new Persoon(String.valueOf(r.nextInt(1000000)),
				String.valueOf(r.nextInt(1000000)));
		return p;
	}

	/*
	 * Test methodes
	 */

	public void test_heeftNaam(Organisatie use_organisatie) {
		assertTrue(
				"Naam is zonder problemen geinitialiseerd in de constructor",
				use_organisatie.heeftNaam());
	}

	public void test_naamConstraint(Organisatie use_organisatie) {
		assertFalse("Naam mag niet null zijn",
				use_organisatie.naamConstraint(null));
		assertFalse("Naam mag geen lege string zijn",
				use_organisatie.naamConstraint(""));
	}

	@Test
	public void test_naamMagWordenToegevoegd() {
		for (Organisatie o : organisaties_correct) {
			// Follow rules
			this.test_heeftNaam(o);
			this.test_naamConstraint(o);
			// Break rules
			assertFalse(
					"Naam mag niet worden veranderd als deze eenmaal geinitialiseerd is",
					o.naamMagWordenToegevoegd("bla bla"));
		}
	}

	public void test_heeftContactpersoon(Organisatie use_organisatie) {
		// Follow rules
		for (Persoon persoon : use_organisatie.getContactpersonen()) {
			assertTrue(String.format(
					"%s is contactpersoon voor organisatie: %s", persoon,
					use_organisatie),
					use_organisatie.heeftContactpersoon(persoon));
		}
		// Break rules
		assertFalse(String.format(
				"%s bevat (onverwacht) een random persoon als contactpersoon",
				use_organisatie),
				use_organisatie.heeftContactpersoon(newRandomTestPersoon()));
	}

	public void test_contactpersoonConstraint(Organisatie use_organisatie) {
		// Follow rules
		for (Persoon persoon : use_organisatie.getContactpersonen()) {
			assertTrue(String.format(
					"%s voldoet aan de voorwaarden die worden gesteld "
							+ "aan een contactpersoon om te worden toegevoegd "
							+ "aan een organisatie", persoon),
					use_organisatie.contactpersoonConstraint(persoon));
			assertTrue(
					String.format("%s heeft een Contactpersoonsrol", persoon),
					persoon.heeftRol(Contactpersoon.class));
		}
		// Break rules
		assertFalse(
				"Om een Contactpersoon aan een Organisatie toe te voegen mag deze geen null zijn",
				use_organisatie.contactpersoonConstraint(null));
		assertFalse(
				"Om een Contactpersoon aan een Organisatie toe te voegen moet de Persoon "
						+ "wel een Contactpersoonsrol hebben",
				use_organisatie.contactpersoonConstraint(new Persoon("Test",
						"Testerson")));
	}

	@Test
	public void test_contactpersoonMagWordenToegevoegd() {
		for (Organisatie o : organisaties_correct) {
			// Follow rules
			this.test_heeftContactpersoon(o);
			this.test_contactpersoonConstraint(o);
			// Break rules
			Persoon testpersoon = newRandomTestPersoon();
			assertTrue(
					"Contactpersoon mag worden toegevoegd omdat dit nog niet eerder is gedaan",
					o.contactpersoonMagWordenToegevoegd(testpersoon));
			assertTrue(o.addContactpersoon(testpersoon));
			assertFalse(
					"Contactpersoon mag niet worden toegevoegd omdat dit al eerder is gedaan",
					o.contactpersoonMagWordenToegevoegd(testpersoon));
		}
	}

	@Test
	public void testBidirectioneleRelatie_Organisatie_Contactpersoon()
			throws InvalidBusinessKeyException {

		/*
		 * Organisatie bevat een collectie contactpersonen. Test of de
		 * bidirectionele relatie in stand word gehouden door deze Organisatie
		 * ook toe te voegen aan de collectie organisaties van Contactpersoon
		 */

		// Follow rules

		Persoon testpersoon = this.newRandomTestPersoon();
		Organisatie testorganisatie = this.newRandomTestOrganisatie();

		// Controleer of de Organisatie nog niet deze Contactpersoon bevat
		assertFalse(testorganisatie.heeftContactpersoon(testpersoon));

		// Controleer of de Contactpersoon nog niet deze Organisatie bevat
		assertFalse(testpersoon.getContactpersoon().heeftOrganisatie(
				testorganisatie));

		// Start de bidirectionele toevoeging via een aanroep op Organisatie
		assertTrue(testorganisatie.addContactpersoon(testpersoon));
		// TODO wordt hierna nog een keer aangeroepen, moet false teruggeven

		assertTrue(String.format(
				"Voeg Contactpersoon: %s toe aan Organisatie: %s", testpersoon,
				testorganisatie),
				testorganisatie.addContactpersoon(testpersoon));

		// Nu heeft de Organisatie wel een referentie naar een Persoon
		assertTrue(testorganisatie.heeftContactpersoon(testpersoon));

		// Nu heeft de Contactpersoon wel een referentie naar een Organisatie
		assertTrue(testpersoon.getContactpersoon().heeftOrganisatie(
				testorganisatie));

		// Verbreek de bidirectionele relatie via een aanroep op Organisatie
		testorganisatie.removeContactpersoon(testpersoon);

		// Nu heeft de Organisatie niet langer een referentie naar een Persoon
		assertFalse(testorganisatie.heeftContactpersoon(testpersoon));

		// Nu heeft de Contactpersoon niet langer een referentie naar een
		// Organisatie
		assertFalse(testpersoon.getContactpersoon().heeftOrganisatie(
				testorganisatie));

		/*
		 * Doe dit geheel nog een keer vanuit met toevoegen vanuit
		 * contactpersoon
		 */

		assertTrue(String.format(
				"Voeg Organisatie: %s toe aan Contactpersoon: %s",
				testorganisatie, testpersoon), testpersoon.getContactpersoon()
				.addOrganisatie(testorganisatie, testpersoon));

		// Nu heeft de Contactpersoon wel een referentie naar een Organisatie
		assertTrue(testpersoon.getContactpersoon().heeftOrganisatie(
				testorganisatie));
		
		// Nu heeft de Organisatie wel een referentie naar een Persoon
		assertTrue(testorganisatie.heeftContactpersoon(testpersoon));

		// Verbreek de bidirectionele relatie via een aanroep op Organisatie
		testorganisatie.removeContactpersoon(testpersoon);

		// Nu heeft de Contactpersoon niet langer een referentie naar een
		// Organisatie
		assertFalse(testpersoon.getContactpersoon().heeftOrganisatie(
				testorganisatie));

		// Nu heeft de Organisatie niet langer een referentie naar een Persoon
		assertFalse(testorganisatie.heeftContactpersoon(testpersoon));

		// Try and break some rules

		/*
		 * Wat als je een contactpersoon probeert toe te voegen aan een
		 * organisatie via een persoon zonder contactpersoonsrol?
		 */

		Persoon testpersoon2 = this.newRandomTestPersoon();
		Persoon testpersoon2_imposter = this.newRandomTestPersoonZonderRol();
		Organisatie testorganisatie2 = this.newRandomTestOrganisatie();

		assertFalse("Organisatie kan niet worden toegevoegd wanneer de "
				+ "persoon geen contactpersoonsrol heeft",
				testorganisatie2.addContactpersoon(testpersoon2_imposter));

		// Check of de bi-directionele relatie ook echt niet is toegevoegd
		assertFalse(testorganisatie2.heeftContactpersoon(testpersoon2));
		assertFalse(testorganisatie2.heeftContactpersoon(testpersoon2_imposter));
		assertFalse(testpersoon2.getContactpersoon().heeftOrganisatie(
				testorganisatie2));

		/*
		 * Wat als je een contactpersoon probeert te verwijderen van een
		 * organisatie via een nullwaarde, een persoon zonder
		 * contactpersoonsrol, of een persoon die nog niet is toegevoegd?
		 */

		Persoon testpersoon3 = this.newRandomTestPersoon();
		Persoon testpersoon3_imposter = this.newRandomTestPersoonZonderRol();
		Organisatie testorganisatie3 = this.newRandomTestOrganisatie();

		assertFalse("Organisatie heeft een hekel aan nullwaardes",
				testorganisatie3.removeContactpersoon(null));

		// TODO de persoon komt niet voor in de lijst, dus geen zuivere test
		assertFalse("Contactpersoon kan niet worden verwijderd wanneer de "
				+ "persoon geen contactpersoonsrol bevat.",
				testorganisatie3.removeContactpersoon(testpersoon3_imposter));

		// TODO return value van de methode aanpassen?
		assertTrue("Organisatie heeft nog geen contactpersonen. Returnwaarde "
				+ "is true omdat er alleen gekeken word naar de uitkomst",
				testorganisatie3.removeContactpersoon(testpersoon3));

		// Check of de bi-directionele relatie ook echt niet is toegevoegd
		assertFalse(testorganisatie3.heeftContactpersoon(testpersoon3));
		assertFalse(testorganisatie3.heeftContactpersoon(testpersoon3_imposter));
		assertFalse(testpersoon3.getContactpersoon().heeftOrganisatie(
				testorganisatie3));
	}

	@Test
	public void test_addRol() {

		/*
		 * Test of er daadwerkelijk maar 1 van elk type rol kan worden
		 * toegevoegd aan de collectie
		 */

		sterlingcooper.getRollen().clear();

		assertTrue("Er zijn nog geen OrganisatieRollen toegevoegd",
				sterlingcooper.getRollen().size() == 0);

		sterlingcooper.addRol(null);

		assertTrue("Rollen die als waarde null hebben worden niet toegevoegd",
				sterlingcooper.getRollen().size() == 0);

		sterlingcooper.addRol(bedrijfsrol);
		sterlingcooper.addRol(bedrijfsrol);
		sterlingcooper.addRol(new Bedrijf());
		sterlingcooper.addRol(new Bedrijf());

		assertTrue("De collectie bevat slechts 1 rol, geen duplicaten",
				sterlingcooper.getRollen().size() == 1);
		assertTrue("De collectie bevat een rol van het type Bedrijf",
				sterlingcooper.heeftRol(Bedrijf.class));

		sterlingcooper.addRol(leveranciersrol);
		sterlingcooper.addRol(leveranciersrol);
		sterlingcooper.addRol(new Leverancier());
		sterlingcooper.addRol(new Leverancier());

		assertTrue("De collectie bevat slechts 2 rollen, geen duplicaten",
				sterlingcooper.getRollen().size() == 2);

		assertTrue("De collectie bevat een rol van het type Leverancier",
				sterlingcooper.heeftRol(Leverancier.class));
	}

	@Test
	public void test_heeftRol() {

		/*
		 * Test of er een rol van een gespecificeerd type bestaat in de
		 * collectie
		 */

		sterlingcooper.getRollen().clear();

		assertTrue("Er zijn nog geen OrganisatieRollen toegevoegd",
				sterlingcooper.getRollen().size() == 0);

		sterlingcooper.addRol(leveranciersrol);

		assertTrue("De collectie bevat een rol van het type Leverancier",
				sterlingcooper.heeftRol(Leverancier.class));

		assertFalse(
				"De collectie bevat geen rol van een ander type dan Leverancier",
				sterlingcooper.heeftRol(Bedrijf.class));

		sterlingcooper.addRol(bedrijfsrol);

		assertTrue(
				"De collectie bevat nu wel een rol van een ander type dan Leverancier",
				sterlingcooper.heeftRol(Bedrijf.class));

	}

	@Test
	public void test_getRol() {

		/*
		 * Test of er een rol van een gespecificeerd type kan worden opgevraagd
		 * uit de collectie
		 */

		sterlingcooper.getRollen().clear();

		assertTrue("Er zijn nog geen OrganisatieRollen toegevoegd",
				sterlingcooper.getRollen().size() == 0);

		sterlingcooper.addRol(bedrijfsrol);
		sterlingcooper.addRol(leveranciersrol);

		assertNotNull("Gevonden rol: Bedrijf in de collectie",
				sterlingcooper.getRolByType(Bedrijf.class));
		assertNotNull("Gevonden rol: Leverancier in de collectie",
				sterlingcooper.getRolByType(Leverancier.class));

		sterlingcooper.getRollen().clear();

		assertTrue("Collectie Rollen is daadwerkelijk leeg", sterlingcooper
				.getRollen().size() == 0);

		assertNull("Rol: Bedrijf kan niet worden gevonden in de collectie",
				sterlingcooper.getBedrijf());
		assertNull("Rol: Leverancier kan niet worden gevonden in de collectie",
				sterlingcooper.getLeverancier());

	}

	/*
	 * Utils
	 */

	@Test
	public void test_hashCode() {

		/*
		 * Test of de hashcodes genereren naar verwachting
		 */

		assertTrue("Hashcodes zijn gelijk",
				sterlingcooper.hashCode() == sterlingcooper_clone.hashCode());
		assertTrue("Hashcodes zijn gelijk",
				pearsonhardman.hashCode() == pearsonhardman_clone.hashCode());
		assertFalse("Hashcodes zijn niet gelijk",
				sterlingcooper.hashCode() == pearsonhardman.hashCode());

		sterlingcooper.setId(9999);
		assertTrue(
				"Hashcodes zijn gelijk, id word niet meegenomen in de berekening van de hashCode",
				sterlingcooper.hashCode() == sterlingcooper_clone.hashCode());
		sterlingcooper.setGearchiveerd(true);
		assertTrue(
				"Hashcodes zijn gelijk, gearchiveerd word niet meegenomen in de berekening van de hashCode",
				sterlingcooper.hashCode() == sterlingcooper_clone.hashCode());

		/*
		 * Bewijs dat het aanpassen van de business key invloed heeft op de
		 * generatie van de hashcodes
		 */
		try {
			/*
			 * Omzeil private methode m.b.v. reflectie
			 */
			Method method = sterlingcooper.getClass().getDeclaredMethod(
					"setNaam", String.class);
			method.setAccessible(true);
			method.invoke(sterlingcooper, "een nieuwe naam");

			assertFalse(
					"Hashcodes zijn niet langer gelijk, naam word meegenomen in de berekening van de hashCode",
					sterlingcooper.hashCode() == sterlingcooper_clone
							.hashCode());

		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			fail("Reflection failed while trying to access the private method Organisatie.setNaam(String)");
		}

	}

	@Test
	public void test_equals() {

		/*
		 * Test of de equals methode werkt naar verwachting
		 */

		assertTrue("Vergeleken met zichzelf",
				sterlingcooper.equals(sterlingcooper));
		assertTrue("Vergeleken met clone van zichzelf (gelijke business key)",
				sterlingcooper.equals(sterlingcooper_clone));

		assertTrue("Vergeleken met zichzelf",
				pearsonhardman.equals(pearsonhardman));
		assertTrue("Vergeleken met clone van zichzelf (gelijke business key)",
				pearsonhardman.equals(pearsonhardman_clone));

		assertFalse("Vergeleken met gelijke objecten (ongelijke business key)",
				sterlingcooper.equals(pearsonhardman));

		assertFalse("Vergeleken met ander type object (String)",
				sterlingcooper.equals(new String("willekeurige string")));

	}
}
