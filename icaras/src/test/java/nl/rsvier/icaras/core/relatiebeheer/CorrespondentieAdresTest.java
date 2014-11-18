package nl.rsvier.icaras.core.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import nl.rsvier.icaras.core.InvalidBusinessKeyException;

import org.junit.Before;
import org.junit.Test;

public class CorrespondentieAdresTest {

	private Relatie persoon1;
	private Relatie persoon2;
	private Relatie organisatie1;
	private Relatie organisatie2;

	private Adres adres1;
	private Adres adres2;
	private Adres adres3;
	private Adres adres4;

	@Before
	public void setUp() throws InvalidBusinessKeyException {
		persoon1 = new Persoon("Voornaam1", "Achternaam1");
		persoon2 = new Persoon("Voornaam2", "van den", "Achternaam2");
		organisatie1 = new Organisatie("Organisatie1");
		organisatie2 = new Organisatie("Organisatie2");
		adres1 = new Adres(false, true, "9876ZY", "123A", "Utrecht",
				"Oude Markt");// postbus
		adres2 = new Adres(false, false, "1234AB", "191", "Hilversum",
				"Larenseweg");
		adres3 = new Adres(false, false, "5555CC", "632 bis",
				"IngevoerdePlaats1", "Dorpsstraat");
		adres4 = new Adres(true, true, "4325LL", "5677", "IngevoerdePlaats2",
				"");// postbus en correspondentieadres
	}

	@Test
	// hierin wordt indirect maakCorrespondentieAdres() getest
	public void addAndRemoveAdresTest() {
		assertTrue(persoon1.addAdres(adres1));// maakt hier automatisch
												// correspondentieadres van
		assertSame("De lijst van persoon1 is nu 1 adres groot", 1, persoon1
				.getAdressen().size());
		assertTrue("adres1 is correspondentieadres",
				adres1.isCorrespondentieAdres());
		assertFalse(
				"toevoegen adres1 terwijl die al is toegevoegd geeft false",
				persoon1.addAdres(adres1));
		assertSame("lijst is nog steeds 1 groot", 1, persoon1.getAdressen()
				.size());
		assertFalse(
				"Van adres2 het correspondentieadres maken vóór toevoegen mislukt",
				adres2.maakCorrespondentieAdres(persoon1));
		assertTrue("Toevoegen adres2 slaagt", persoon1.addAdres(adres2));
		assertSame("De lijst van persoon1 is nu 2 adressen groot", 2, persoon1
				.getAdressen().size());
		assertFalse(
				"adres2 is nog geen correspondentieadres (inmiddels wel toegevoegd)",
				adres2.isCorrespondentieAdres());
		assertTrue(
				"Van adres2 het correspondentieadres maken na toevoegen lukt",
				adres2.maakCorrespondentieAdres(persoon1));
		assertTrue(
				"Toevoegen van een niet-correspondentieadres verandert wel de grootte van de lijst,"
						+ " maar niet het correspondentieadres in de lijst",
				persoon1.addAdres(adres3));
		assertSame("De lijst van persoon1 is nu 3 adressen groot", 3, persoon1
				.getAdressen().size());
		assertTrue(
				"adres2 is correspondentieadres "
						+ "(inderdaad geslaagd en niet gewijzigd door toevoegen adres3)",
				adres2.isCorrespondentieAdres());
		assertTrue(
				"Het opvragen van het correspondentieadres uit de lijst levert ook adres2",
				adres2 == persoon1.getCorrespondentieAdres());
		assertFalse(
				"adres1 is hierdoor automatisch geen correspondentieadres meer",
				adres1.isCorrespondentieAdres());
		assertTrue("Toevoegen van een correspondentieadres lukt",
				persoon1.addAdres(adres4));
		assertSame("De lijst van persoon1 is nu 4 adressen groot", 4, persoon1
				.getAdressen().size());
		assertTrue("adres4 is correspondentieadres",
				adres4.isCorrespondentieAdres());
		assertTrue(
				"Het opvragen van het correspondentieadres uit de lijst levert ook adres4",
				adres4 == persoon1.getCorrespondentieAdres());
		assertFalse(
				"adres2 is hierdoor automatisch geen correspondentieadres meer",
				adres2.isCorrespondentieAdres());

		assertFalse(
				"Adres2 correspondentieadres maken met de verkeerde persoon als parameter geeft false",
				adres2.maakCorrespondentieAdres(persoon2));
		assertFalse("Na mislukte poging tot correspondentieadres maken, "
				+ "is adres2 dan ook (nog steeds) geen correspondentieadres",
				adres2.isCorrespondentieAdres());
		// Het kan fout gaan door dezelfde adresinstanties aan 2 relaties toe te wijzen
		// Het wijzigen van een adres in de ene persoon verandert deze ook bij
		// de andere
		assertTrue("Toevoegen adres4 aan organisatie1 slaagt",
				organisatie1.addAdres(adres4));
		assertTrue(
				"Het opvragen van het correspondentieadres uit de lijst levert ook adres4",
				adres4 == persoon1.getCorrespondentieAdres());
		organisatie1.addAdres(adres2);// adres2 en adres4 komen nu voor in beide
										// lijsten
		assertTrue(
				"verander het correspondentieadres in de lijst van organisatie1",
				adres2.maakCorrespondentieAdres(organisatie1));
		// adres2 en 4 worden nu ook ongevraagd gewijzigd bij persoon1
		assertTrue(
				"Het opvragen van het correspondentieadres uit de lijst van persoon1 levert hierdoor ook adres2",
				adres2 == persoon1.getCorrespondentieAdres());
		// wat als je een wijziging aanbrengt in 1 vd lijsten op een adres die
		// de ander niet heeft, terwijl ze wel het correspondentieadres delen?
		assertFalse("Verwijder adres3 uit lijst organisatie",
				organisatie1.removeAdres(adres3));// zit niet in de lijst
		assertFalse(
				"kan niet gewijzigd worden voor organisatie1, want komt niet voor in de lijst",
				adres3.maakCorrespondentieAdres(organisatie1));
		assertTrue(
				"Maak nieuw correspondentieadres van adres die enkel in de lijst voorkomt van degene die bij de wijziging wordt meegegeven",
				adres3.maakCorrespondentieAdres(persoon1));
		assertNull(
				"Dan is het correspondentieadres bij degene die geen nieuwe kreeg verdwenen!",
				organisatie1.getCorrespondentieAdres());
		// Ook al is er een ander correspondentie adres, dan nog gaat het fout

		assertTrue(
				"zorg voor verschillende correspondentieadressen en maak een gemeenschappelijk adres tot correspondentieadres"
						+ "Degene waar de wijziging niet op is aangeroepen heeft dan 2 correspondentieadressen!!!",
				true);
	}

	@Test
	public void testRemoveAdres() {
		assertTrue("Toevoegen adres1 aan organisatie1 lukt",
				organisatie1.addAdres(adres1));
		assertFalse("Verwijderen via removeAdres lukt niet "
				+ "als er slechts 1 adres in de lijst zit",
				organisatie1.removeAdres(adres1));
		assertTrue("Toevoegen adres2 aan organisatie1 lukt",
				organisatie1.addAdres(adres2));
		assertFalse("Verwijderen via removeAdres lukt nog steeds niet",
				organisatie1.removeAdres(adres1));// is nog steeds
													// correspondentieadres
		assertTrue("Toevoegen adres3 aan organisatie1 lukt",
				organisatie1.addAdres(adres3));
		assertTrue("Verwijderen adres2 van organisatie1 lukt",
				organisatie1.removeAdres(adres2));// geen correspondentieadres
		// organisatie2
		assertTrue("Toevoegen adres2 aan organisatie2 lukt",
				organisatie2.addAdres(adres2));
		// verwijderen als het adres niet voorkomt mislukt:
		assertFalse(
				"Verwijderen bij andere organisatie die wel een adres in de lijst heeft"
						+ " maar niet het gegeven adres geeft false",
				organisatie2.removeAdres(adres3));
		assertFalse("Verwijderen bij relatie die geen adres in de lijst heeft"
				+ "geeft false", organisatie2.removeAdres(adres3));
		// verwijderen laatste adres in de lijst
		assertSame("Adressenlijst organisatie1 = 1", 1, organisatie2
				.getAdressen().size());
		assertTrue("Verwijderen laatste adres in de lijst via ",
				organisatie2.verwijderLaatsteAdres());
		assertSame(
				"Adressenlijst organisatie1 na verwijderen laatste adres = 0",
				0, organisatie2.getAdressen().size());
		// verwijderLaatsteAdres in geval er nog meer adressen in de lijst staan
		assertSame("Adressenlijst organisatie1 = 2", 2, organisatie1
				.getAdressen().size());
		assertFalse(
				"VerwijderLaatsteAdres in geval er nog meer adressen in de lijst staan "
						+ "geeft false en doet niets",
				organisatie1.verwijderLaatsteAdres());
		assertSame("Adressenlijst organisatie1 = 2", 2, organisatie1
				.getAdressen().size());
		// Verwijderen correspondentieadres na ander adres in lijst
		// correspondentieadres maken
		assertTrue(organisatie1.getCorrespondentieAdres() == adres1);
		assertTrue(organisatie1.heeftAdres(adres3));
		assertTrue("Maak adres3 correspondentieadres, daarmee wordt "
				+ "adres1 een niet-correspondentieadres",
				adres3.maakCorrespondentieAdres(organisatie1));
		assertTrue("adres1 kan derhalve gewoon verwijderd worden",
				organisatie1.removeAdres(adres1));
		assertSame("Adressenlijst organisatie1 na verwijdering adres1 = 1", 1,
				organisatie1.getAdressen().size());

	}
}
