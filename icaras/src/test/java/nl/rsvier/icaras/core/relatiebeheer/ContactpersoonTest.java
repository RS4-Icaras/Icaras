package nl.rsvier.icaras.core.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse voor de core klasse: Contactpersoon
 * 
 * @author Mark van Meerten
 */

public class ContactpersoonTest {

	List<Persoon> personen_correct;
	Organisatie sterlingcooper;
	Organisatie pearsonhardman;

	Bedrijf bedrijfsrol;
	Leverancier leveranciersrol;

	Persoon bertram;
	Persoon bertram_clone;
	Persoon peggy;
	Persoon peggy_clone;

	@Before
	public void setUp() throws InvalidBusinessKeyException {

		sterlingcooper = new Organisatie("Sterling Cooper Advertising Agency");
		pearsonhardman = new Organisatie("Pearson Hardman");

		bertram = new Persoon("Bertram", "Cooper");
		bertram.addRol(new Contactpersoon());
		bertram.getContactpersoon().setFunctie("named partner");
		sterlingcooper.addContactpersoon(bertram);

		bertram_clone = new Persoon("Bertram", "Cooper");
		bertram_clone.addRol(new Contactpersoon());
		bertram_clone.getContactpersoon().setFunctie("named partner");
		sterlingcooper.addContactpersoon(bertram_clone);

		peggy = new Persoon("Peggy", "Olsen");
		peggy.addRol(new Contactpersoon());
		peggy.getContactpersoon().setFunctie("copywriter");
		sterlingcooper.addContactpersoon(peggy);

		peggy_clone = new Persoon("Peggy", "Olsen");
		peggy_clone.addRol(new Contactpersoon());
		peggy_clone.getContactpersoon().setFunctie("copywriter");
		sterlingcooper.addContactpersoon(peggy_clone);

		personen_correct = new ArrayList<Persoon>();
		personen_correct.addAll(Arrays.asList(bertram, bertram_clone, peggy,
				peggy_clone));
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

	public void test_heeftOrganisatie(Persoon use_persoon)
			throws InvalidBusinessKeyException {
		for (Organisatie organisatie : use_persoon.getContactpersoon()
				.getOrganisaties()) {
			// Follows rules
			assertTrue(
					String.format(
							"%s is (zoals verwacht) contactpersoon voor organisatie: %s",
							use_persoon, organisatie), use_persoon
							.getContactpersoon().heeftOrganisatie(organisatie));
			// Try and break some rules
			assertFalse(
					String.format(
							"%s bevat (onverwacht) een random organisatie",
							use_persoon), use_persoon.getContactpersoon()
							.heeftOrganisatie(newRandomTestOrganisatie()));
		}
	}

	public void test_organisatieConstraint(Persoon use_persoon) {
		// Follows rules
		for (Organisatie organisatie : use_persoon.getContactpersoon()
				.getOrganisaties()) {
			assertTrue(
					String.format(
							"%s voldoet aan de voorwaarden die worden gesteld "
									+ "aan een Organisatie om te worden toegevoegd "
									+ "aan een Contactpersoon", organisatie),
					use_persoon.getContactpersoon().organisatieConstraint(
							organisatie));
		}
		// Try and break some rules
		assertFalse(
				"Om een Organisatie aan een Contactpersoon toe te voegen mag deze geen null zijn",
				use_persoon.getContactpersoon().organisatieConstraint(null));
	}

	@Test
	public void test_organisatieMagWordenToegevoegd()
			throws InvalidBusinessKeyException {
		for (Persoon p : personen_correct) {
			// Follow rules
			this.test_heeftOrganisatie(p);
			this.test_organisatieConstraint(p);
			// Try and break some rules
			Organisatie testorganisatie = newRandomTestOrganisatie();
			assertTrue(
					"Organisatie mag worden toegevoegd omdat dit nog niet eerder is gedaan",
					p.getContactpersoon().organisatieMagWordenToegevoegd(
							testorganisatie));
			assertTrue(p.getContactpersoon().addOrganisatie(testorganisatie, p));
			assertFalse(
					"Organisatie mag niet worden toegevoegd omdat dit al eerder is gedaan",
					p.getContactpersoon().organisatieMagWordenToegevoegd(
							testorganisatie));
		}
	}

	@Test
	public void testBidirectioneleRelatie_Contactpersoon_Organisatie()
			throws InvalidBusinessKeyException {

		/*
		 * Contactpersoon bevat een lijst met organisaties. Test of de
		 * bidirectionele relatie in stand word gehouden door deze
		 * Contactpersoon ook toe te voegen aan de collectie contactpersonen van
		 * Organisatie
		 */

		// Follow rules

		Persoon testpersoon1 = this.newRandomTestPersoon();
		Organisatie testorganisatie1 = this.newRandomTestOrganisatie();

		// Controleer of de Organisatie nog geen Contactpersoon bevat
		assertFalse(testorganisatie1.heeftContactpersoon(testpersoon1));

		// Controleer of de Contactpersoon nog geen Organisatie bevat
		assertFalse(testpersoon1.getContactpersoon().heeftOrganisatie(
				testorganisatie1));

		// Start de bidirectionele toevoeging via een aanroep op Contactpersoon
		assertTrue(
				String.format(
						"Voeg Organisatie: %s toe aan contactpersoon: %s",
						testorganisatie1, testpersoon1),
				testpersoon1.getContactpersoon().addOrganisatie(
						testorganisatie1, testpersoon1));

		// Nu heeft de Organisatie wel een referentie naar een Persoon
		assertTrue(testorganisatie1.heeftContactpersoon(testpersoon1));

		// Nu heeft de Contactpersoon wel een referentie naar een Organisatie
		assertTrue(testpersoon1.getContactpersoon().heeftOrganisatie(
				testorganisatie1));

		// Verbreek de bidirectionele relatie via een aanroep op Contactpersoon
		testpersoon1.getContactpersoon().removeOrganisatie(testorganisatie1,
				testpersoon1);

		// Nu heeft de Organisatie niet langer een referentie naar een Persoon
		assertFalse(testorganisatie1.heeftContactpersoon(testpersoon1));

		// Nu heeft de Contactpersoon niet langer een referentie naar een
		// Organisatie
		assertFalse(testpersoon1.getContactpersoon().heeftOrganisatie(
				testorganisatie1));

	}

	@Test
	public void test_addOrganisatieFail() throws InvalidBusinessKeyException {

		// Try and break some rules

		Persoon testpersoon2 = this.newRandomTestPersoon();
		Persoon testpersoon2_imposter = this.newRandomTestPersoon();
		Organisatie testorganisatie2 = this.newRandomTestOrganisatie();

		/*
		 * Wat als je een contactpersoon probeert toe te voegen aan een
		 * organisatie via een andere persoon?
		 */

		assertFalse(
				"Organisatie kan niet worden toegevoegd wanneer de "
						+ "persoon niet overeenkomt met de houder van de contactpersoonsrol",
				testpersoon2.getContactpersoon().addOrganisatie(
						testorganisatie2, this.newRandomTestPersoon()));

		// Check of de bi-directionele relatie ook echt niet is toegevoegd
		assertFalse(testorganisatie2.heeftContactpersoon(testpersoon2));
		assertFalse(testorganisatie2.heeftContactpersoon(testpersoon2_imposter));
		assertFalse(testpersoon2.getContactpersoon().heeftOrganisatie(
				testorganisatie2));
		// Zelfde test, maar met contactpersoon zonder persoon die deze rol
		// bezit
		Contactpersoon contactpersoon = new Contactpersoon();
		assertFalse(
				"Organisatie kan niet worden toegevoegd wanneer de "
						+ "houder van de contactpersoonsrol niet bestaat",
				contactpersoon.addOrganisatie(testorganisatie2, testpersoon2));

		// Check of de bi-directionele relatie ook echt niet is toegevoegd
		assertFalse(testorganisatie2.heeftContactpersoon(testpersoon2));
		assertFalse(testpersoon2.getContactpersoon().heeftOrganisatie(
				testorganisatie2));

		/*
		 * Wat als je een contactpersoon probeert toe te voegen aan een
		 * organisatie zonder een persoon?
		 */

		Persoon testpersoon3 = this.newRandomTestPersoon();
		Organisatie testorganisatie3 = this.newRandomTestOrganisatie();

		assertFalse(
				"Organisatie kan niet worden toegevoegd wanneer de "
						+ "persoon niet overeenkomt met de houder van de contactpersoonsrol",
				testpersoon3.getContactpersoon().addOrganisatie(
						testorganisatie3, null));

		// Check of de bi-directionele relatie ook echt niet is toegevoegd
		assertFalse(testorganisatie3.heeftContactpersoon(testpersoon3));
		assertFalse(testpersoon3.getContactpersoon().heeftOrganisatie(
				testorganisatie3));

	}

	@Test
	public void test_RemoveOrganisatieFail() throws InvalidBusinessKeyException {

		// Try and break some rules

		Persoon testpersoon4 = this.newRandomTestPersoon();
		Persoon testpersoon4_zonderrol = this.newRandomTestPersoonZonderRol();
		Persoon testpersoon4_imposter = this.newRandomTestPersoon();
		Organisatie testorganisatie4 = this.newRandomTestOrganisatie();

		/*
		 * Wat als je een contactpersoon probeert te verwijderen van een
		 * organisatie via een nullwaarde?
		 */

		assertFalse("Null check", testpersoon4.getContactpersoon()
				.removeOrganisatie(null, null));

		/*
		 * Wat als je een contactpersoon probeert te verwijderen van een
		 * organisatie via een een persoon zonder contactpersoonsrol?
		 */

		assertFalse(
				"Organisatie kan niet worden verwijderd wanneer de "
						+ "meegegeven persoon geen contactpersoonsrol bevat.",
				testpersoon4.getContactpersoon().removeOrganisatie(
						testorganisatie4, testpersoon4_zonderrol));

		/*
		 * Wat als je een contactpersoon probeert te verwijderen van een
		 * organisatie via een persoon die nog niet is toegevoegd?
		 */

		assertSame(0, testpersoon4.getContactpersoon().getOrganisaties().size());
		assertTrue(
				"Contactpersoon kan niets verwijderen wanneer deze nog geen "
						+ "organisaties heeft. Returnwaarde is true omdat "
						+ "er alleen gekeken word of de collectie leeg is",
				testpersoon4.getContactpersoon().removeOrganisatie(
						testorganisatie4, testpersoon4));

		/*
		 * Wat als je een contactpersoon probeert te verwijderen van een
		 * organisatie via een persoon die niet de houder van de
		 * contactpersoonsrol is?
		 */

		assertFalse(
				"Organisatie kan niet worden verwijderd wanneer de persoon niet "
						+ "overeenkomt met de houder van de contactpersoonsrol.",
				testpersoon4.getContactpersoon().removeOrganisatie(
						testorganisatie4, testpersoon4_imposter));

		// Check of de bi-directionele relatie ook echt niet is toegevoegd
		assertFalse(testorganisatie4.heeftContactpersoon(testpersoon4));
		assertFalse(testorganisatie4
				.heeftContactpersoon(testpersoon4_zonderrol));
		assertFalse(testorganisatie4.heeftContactpersoon(testpersoon4_imposter));
		assertFalse(testpersoon4.getContactpersoon().heeftOrganisatie(
				testorganisatie4));

	}

	@Test
	public void test_equals() {

		/*
		 * Test of de equals methode werkt naar verwachting
		 */

		assertTrue("Vergeleken met zichzelf", bertram.getContactpersoon()
				.equals(bertram.getContactpersoon()));
		assertTrue(
				"Vergeleken met clone van zichzelf (gelijke business key)",
				bertram.getContactpersoon().equals(
						bertram_clone.getContactpersoon()));

		assertTrue("Vergeleken met zichzelf",
				peggy.getContactpersoon().equals(peggy.getContactpersoon()));
		assertTrue(
				"Vergeleken met clone van zichzelf (gelijke business key)",
				peggy.getContactpersoon().equals(
						peggy_clone.getContactpersoon()));

		assertFalse("Vergeleken met gelijke objecten (ongelijke business key)",
				peggy.getContactpersoon().equals(bertram.getContactpersoon()));

		assertFalse("Vergeleken met ander type object (String)", bertram
				.getContactpersoon().equals(new String("willekeurige string")));

		assertTrue("Peggy vertegenwoordigt nu twee organisaties",
				pearsonhardman.addContactpersoon(peggy));

		assertFalse(
				"Vergeleken met clone van zichzelf (gelijke business key)",
				peggy.getContactpersoon().equals(
						peggy_clone.getContactpersoon()));

		assertTrue("Peggy's clone vertegenwoordigt nu ook twee organisaties",
				pearsonhardman.addContactpersoon(peggy_clone));

		assertTrue(
				"Vergeleken met clone van zichzelf (gelijke business key)",
				peggy.getContactpersoon().equals(
						peggy_clone.getContactpersoon()));

	}

}
