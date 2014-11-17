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

	private Persoon randomTestPersoon() {
		Random r = new Random();
		Persoon p = new Persoon(String.valueOf(r.nextInt(1000000)),
				String.valueOf(r.nextInt(1000000)));
		p.addRol(new Contactpersoon());
		return p;
	}

	/*
	 * Methode Tests
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
		for (Persoon persoon : use_organisatie.getContactpersonen()) {
			assertTrue(String.format(
					"%s is contactpersoon voor organisatie: %s", persoon,
					use_organisatie),
					use_organisatie.heeftContactpersoon(persoon));
		}
		assertFalse(String.format(
				"%s bevat (onverwacht) een random persoon als contactpersoon",
				use_organisatie),
				use_organisatie.heeftContactpersoon(randomTestPersoon()));
	}

	public void test_contactpersoonConstraint(Organisatie use_organisatie) {
		for (Persoon persoon : use_organisatie.getContactpersonen()) {
			assertTrue(
					String.format(
							"%s voldoet aan de voorwaarden die worden gesteld \""
									+ "aan een contactpersoon om te worden toegevoegd \""
									+ "aan een organisatie", persoon),
					use_organisatie.contactpersoonConstraint(persoon));
			assertTrue(
					String.format("%s heeft een Contactpersoonsrol", persoon),
					persoon.hasRol(Contactpersoon.class));
		}
		assertFalse(
				"Om een Contactpersoon aan een Organisatie toe te voegen mag deze geen null zijn",
				use_organisatie.contactpersoonConstraint(null));
		assertFalse(
				"Om een Contactpersoon aan een Organisatie toe te voegen moet de Persoon \""
						+ "wel een Contactpersoonsrol hebben",
				use_organisatie.contactpersoonConstraint(new Persoon("Test", "Testerson")));
	}

	@Test
	public void test_contactpersoonMagWordenToegevoegd() {
		for (Organisatie o : organisaties_correct) {
			// Follow rules
			this.test_heeftContactpersoon(o);
			this.test_contactpersoonConstraint(o);
			// Break rules
			Persoon testpersoon = randomTestPersoon();
			assertTrue(
					"Contactpersoon mag worden toegevoegd omdat dit nog niet eerder is gedaan",
					o.contactpersoonMagWordenToegevoegd(testpersoon));
			o.addContactpersoon(testpersoon);
			assertFalse(
					"Contactpersoon mag niet worden toegevoegd omdat dit al eerder is gedaan",
					o.contactpersoonMagWordenToegevoegd(testpersoon));
		}
	}

	@Test
	public void testBidirectionalAddingOfContactpersoonFromOrganisatie() {

		/*
		 * Organisatie bevat een collectie contactpersonen. Test of de
		 * bidirectionele relatie in stand word gehouden door deze Organisatie
		 * ook toe te voegen aan de collectie organisaties van Contactpersoon
		 */

		Persoon don = new Persoon("Don", "Draper");
		don.addRol(new Contactpersoon());

		// Controleer of de Organisatie nog geen Contactpersoon bevat
		assertFalse(sterlingcooper.getContactpersonen().contains(don));

		// Controleer of de Contactpersoon nog geen Organisatie bevat
		assertFalse(don.getContactpersoon().getOrganisaties()
				.contains(sterlingcooper));

		// Start de bidirectionele toevoeging via een aanroep op Organisatie
		sterlingcooper.addContactpersoon(don);

		// Nu heeft de Organisatie wel een referentie naar een Persoon
		assertTrue(sterlingcooper.getContactpersonen().contains(don));

		// Nu heeft de Contactpersoon wel een referentie naar een Organisatie
		assertTrue(don.getContactpersoon().getOrganisaties()
				.contains(sterlingcooper));

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
				sterlingcooper.hasRol(Bedrijf.class));

		sterlingcooper.addRol(leveranciersrol);
		sterlingcooper.addRol(leveranciersrol);
		sterlingcooper.addRol(new Leverancier());
		sterlingcooper.addRol(new Leverancier());

		assertTrue("De collectie bevat slechts 2 rollen, geen duplicaten",
				sterlingcooper.getRollen().size() == 2);

		assertTrue("De collectie bevat een rol van het type Leverancier",
				sterlingcooper.hasRol(Bedrijf.class));
	}

	@Test
	public void test_hasRol() {

		/*
		 * Test of er een rol van een gespecificeerd type bestaat in de
		 * collectie
		 */

		sterlingcooper.getRollen().clear();

		assertTrue("Er zijn nog geen OrganisatieRollen toegevoegd",
				sterlingcooper.getRollen().size() == 0);

		sterlingcooper.addRol(leveranciersrol);

		assertTrue("De collectie bevat een rol van het type Leverancier",
				sterlingcooper.hasRol(Leverancier.class));

		assertFalse(
				"De collectie bevat geen rol van een ander type dan Leverancier",
				sterlingcooper.hasRol(Bedrijf.class));

		sterlingcooper.addRol(bedrijfsrol);

		assertTrue(
				"De collectie bevat nu wel een rol van een ander type dan Leverancier",
				sterlingcooper.hasRol(Bedrijf.class));

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
				sterlingcooper.getRol(Bedrijf.class));
		assertNotNull("Gevonden rol: Leverancier in de collectie",
				sterlingcooper.getRol(Leverancier.class));

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
