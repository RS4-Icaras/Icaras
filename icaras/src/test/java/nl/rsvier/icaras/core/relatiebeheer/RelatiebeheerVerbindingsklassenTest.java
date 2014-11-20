package nl.rsvier.icaras.core.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
import nl.rsvier.icaras.core.arbeidsmarkt.Arbeidsovereenkomst;
import nl.rsvier.icaras.core.arbeidsmarkt.Vacature;

import org.junit.Before;
import org.junit.Test;

public class RelatiebeheerVerbindingsklassenTest {
	// TODO vacature meenemen in test van aanbieding en arbeidsovereenkomst

	private Persoon persoon1;
	private Persoon persoon2;

	private Organisatie organisatie1;
	private Organisatie organisatie2;

	private Bedrijf bedrijf1;
	private Bedrijf bedrijf2;

	private Kandidaat kandidaat1 = new Kandidaat();
	private Kandidaat kandidaat2 = new Kandidaat();

	private Werknemer werknemer1 = new Werknemer();
	private Werknemer werknemer2 = new Werknemer();

	private Aanbieding aanbieding1;
	private Aanbieding aanbieding2;
	private Aanbieding aanbieding3;

	private Vacature vacature1;
	private Vacature vacature2;

	private Arbeidsovereenkomst arbeidsovereenkomst1;
	private Arbeidsovereenkomst arbeidsovereenkomst2;
	private Arbeidsovereenkomst arbeidsovereenkomst3;

	@Before
	public void setUp() throws InvalidBusinessKeyException {
		persoon1 = maakTestPersoonZonderId("Thomas", "", "Slippens",
				new GregorianCalendar(1986, 3, 25));
		persoon2 = maakTestPersoonZonderId("Leroy", "van den", "Hoogen",
				new GregorianCalendar(1988, 0, 21));
		persoon1.addRol(kandidaat1);
		persoon1.addRol(werknemer1);
		persoon2.addRol(kandidaat2);
		organisatie1 = new Organisatie("Organisatie1");
		bedrijf1 = new Bedrijf();
		organisatie1.addRol(bedrijf1);
		organisatie2 = new Organisatie("Organisatie2");
		bedrijf2 = new Bedrijf();
		organisatie2.addRol(bedrijf2);
		aanbieding1 = new Aanbieding(persoon1, organisatie1);// voegt de
																// aanbieding
																// al
																// toe

	}

	private Persoon maakTestPersoonZonderId(String voornaam,
			String tussenvoegsels, String achternaam, Calendar geboortedatum) {
		Persoon p = new Persoon();
		p.setVoornaam(voornaam);
		p.setTussenvoegsels(tussenvoegsels);
		p.setAchternaam(achternaam);
		p.setGeboortedatum(geboortedatum);
		return p;
	}

	@Test
	public void testAddAndRemoveAanbiedingSucces()
			throws InvalidBusinessKeyException {
		assertTrue(kandidaat1.removeAanbieding(aanbieding1));// is automatisch
																// toegevoegd
		// door new Aanbieding()
		// voeg de aanbieding toe aan een kandidaat
		assertTrue("methode addAanbieding geeft true",
				kandidaat1.addAanbieding(aanbieding1));
		assertTrue(aanbieding1.getPersoon().getKandidaat() == kandidaat1);
		assertTrue(aanbieding1.getOrganisatie().getBedrijf() == bedrijf1);
		assertSame(1, kandidaat1.getAanbiedingen().size());
		assertSame(1, bedrijf1.getAanbiedingen().size());
		assertTrue(
				"De kandidaat heeft de toegevoegde aanbieding in zijn lijst",
				kandidaat1.heeftAanbieding(aanbieding1));
		assertTrue(
				"Het betreffende bedrijf heeft ook de toegevoegde aanbieding in zijn lijst",
				bedrijf1.heeftAanbieding(aanbieding1));
		// voeg nog een aanbieding toe aan dezelfde persoon
		aanbieding2 = new Aanbieding(persoon1, organisatie2);
		assertTrue(aanbieding2.getOrganisatie().getBedrijf() == bedrijf2);
		assertSame(2, kandidaat1.getAanbiedingen().size());
		assertSame(1, bedrijf2.getAanbiedingen().size());
		// verwijder via kandidaat en voegtoe via bedrijf
		assertTrue("methode removeAanbieding geeft true",
				kandidaat1.removeAanbieding(aanbieding2));
		assertSame(1, kandidaat1.getAanbiedingen().size());
		assertSame(0, bedrijf2.getAanbiedingen().size());
		assertTrue("methode addAanbieding geeft true",
				bedrijf2.addAanbieding(aanbieding2));
		assertSame(2, kandidaat1.getAanbiedingen().size());
		assertSame(1, bedrijf2.getAanbiedingen().size());
		assertTrue(
				"De kandidaat heeft de toegevoegde aanbieding in zijn lijst",
				kandidaat1.heeftAanbieding(aanbieding2));
		assertTrue(
				"Het betreffende bedrijf heeft ook de toegevoegde aanbieding in zijn lijst",
				bedrijf2.heeftAanbieding(aanbieding2));
		// voeg nog een aanbieding toe aan een andere persoon
		aanbieding3 = new Aanbieding(persoon2, organisatie1);// bedrijf1
		assertSame(1, kandidaat2.getAanbiedingen().size());
		assertSame(2, aanbieding1.getOrganisatie().getBedrijf()
				.getAanbiedingen().size());
		assertTrue(
				"De kandidaat heeft de toegevoegde aanbieding in zijn lijst",
				kandidaat2.heeftAanbieding(aanbieding3));
		assertTrue(
				"Het betreffende bedrijf heeft ook de toegevoegde aanbieding in zijn lijst",
				bedrijf1.heeftAanbieding(aanbieding3));
		// verwijder de aanbieding bij een kandidaat
		assertTrue("methode removeAanbieding geeft true",
				kandidaat1.removeAanbieding(aanbieding1));
		assertFalse(
				"De kandidaat heeft de verwijderde aanbieding niet meer in zijn lijst",
				kandidaat1.heeftAanbieding(aanbieding1));
		assertFalse(
				"Het betreffende bedrijf heeft de verwijderde aanbieding niet meer in zijn lijst",
				bedrijf1.heeftAanbieding(aanbieding1));
		// verwijder ook de 2 later toegevoegde aanbiedingen
		// doe dit via het bedrijf
		assertTrue("methode removeAanbieding geeft true",
				bedrijf2.removeAanbieding(aanbieding2));
		assertTrue("methode removeAanbieding geeft true",
				bedrijf1.removeAanbieding(aanbieding3));
		assertSame(0, kandidaat1.getAanbiedingen().size());
		assertSame(0, bedrijf1.getAanbiedingen().size());
		assertSame(0, kandidaat2.getAanbiedingen().size());
		assertSame(0, bedrijf2.getAanbiedingen().size());
	}

	@Test
	public void testAddAndRemoveAanbiedingFail()
			throws InvalidBusinessKeyException {
		assertFalse(
				"methode kandidaat.addAanbieding met aanbieding die al voorkomt in de lijst geeft false",
				kandidaat1.addAanbieding(aanbieding1));
		assertFalse(
				"methode bedrijf.addAanbieding met aanbieding die al voorkomt in de lijst geeft false",
				bedrijf1.addAanbieding(aanbieding1));
		// Verwijderen van een aanbieding die niet voorkomt in de lijst
		aanbieding2 = new Aanbieding(persoon2, organisatie2);
		assertFalse(
				"methode removeAanbieding met niet voorkomende aanbieding geeft false",
				kandidaat1.removeAanbieding(aanbieding2));
		assertFalse(
				"methode removeAanbieding met niet voorkomende aanbieding geeft false",
				bedrijf1.removeAanbieding(aanbieding2));
		assertTrue(kandidaat1.removeAanbieding(aanbieding1));// verwijder zodat
																// de lijst
		// leeg is
		assertTrue("De lijst van de kandidaat is leeg", kandidaat1
				.getAanbiedingen().isEmpty());
		assertTrue("De lijst van het bedrijf is leeg", bedrijf1
				.getAanbiedingen().isEmpty());
		assertFalse(
				"methode addAanbieding met aanbieding die niet naar de juiste persoon refereert geeft false",
				kandidaat1.addAanbieding(aanbieding2));
		assertFalse(
				"methode addAanbieding met aanbieding die niet naar de juiste organisatie refereert geeft false",
				bedrijf1.addAanbieding(aanbieding2));
		assertFalse("methode addAanbieding met null geeft false",
				kandidaat1.addAanbieding(null));
		assertFalse("methode addAanbieding met null geeft false",
				bedrijf1.addAanbieding(null));
		assertTrue(
				"De lijst van de kandidaat is nog steeds leeg, dus ook niets toegevoegd",
				kandidaat1.getAanbiedingen().isEmpty());
		assertTrue(
				"De lijst van het bedrijf is nog steeds leeg, dus ook niets toegevoegd",
				bedrijf1.getAanbiedingen().isEmpty());
		assertFalse(
				"methode kandidaat.removeAanbieding met aanbieding die niet voorkomt in de lijst geeft false (lege lijst)",
				kandidaat1.removeAanbieding(aanbieding1));
		assertFalse(
				"methode bedrijf.removeAanbieding met aanbieding die niet voorkomt in de lijst geeft false (lege lijst)",
				bedrijf1.removeAanbieding(aanbieding1));
		assertFalse("methode removeAanbieding met null geeft false",
				kandidaat1.removeAanbieding(null));
		assertFalse("methode removeAanbieding met null geeft false",
				bedrijf1.removeAanbieding(null));
		// Toevoegen aanbieding met referentie naar organisatie zonder bedrijf
		// Toevoegen aanbieding met referentie naar persoon zonder kandidaat
		// beide zijn niet mogelijk vanwege de constructor die dit niet toelaat.
		// Testen via MOCKING zou dit oplossen.

		// Toevoegen van een kandidaat met aanbiedingen aan een andere persoon
		// is nog problematisch:
		persoon1 = new Persoon("voornaam1", "achternaam1");
		assertTrue(kandidaat2.getAanbiedingen().size() > 0);
		assertTrue(persoon1.addRol(kandidaat2));
		assertTrue(persoon1.heeftRol(Kandidaat.class));
		assertFalse(kandidaat2.getAanbiedingen().iterator().next().getPersoon() == kandidaat2
				.getCvGenerator().getPersoon());// REFEREERT AAN DE VERKEERDE
												// PERSOON!!!
		// Toevoegen van een kandidaat met aanbiedingen aan een andere persoon
		// is nog problematisch:
		organisatie1 = new Organisatie("organisatienaam1");
		assertTrue(bedrijf2.getAanbiedingen().size() > 0);
		assertTrue(organisatie1.addRol(bedrijf2));
		assertTrue(organisatie1.heeftRol(Bedrijf.class));
		assertFalse(bedrijf2.getAanbiedingen().iterator().next()
				.getOrganisatie() == organisatie1);// REFEREERT AAN DE
													// VERKEERDE ORGANISATIE!!!

	}

	@Test(expected = InvalidBusinessKeyException.class)
	public void testDuplicaatAanbiedingConstrueren()
			throws InvalidBusinessKeyException {
		// TODO toevoegen aanbieding zelfde organisatie en persoon? fail of
		// succes?
		aanbieding2 = new Aanbieding(persoon2, organisatie2);
		aanbieding2 = new Aanbieding(persoon2, organisatie2);
	}

	@Test
	public void testAddAndRemoveArbeidsovereenkomstSucces()
			throws InvalidBusinessKeyException {
		arbeidsovereenkomst1 = new Arbeidsovereenkomst(aanbieding1);
		assertTrue(werknemer1.removeArbeidsovereenkomst(arbeidsovereenkomst1));// was
																				// al
																				// automatisch
																				// toegevoegd
		assertTrue(werknemer1.addArbeidsovereenkomst(arbeidsovereenkomst1));
		assertTrue(
				"De arbeidsovereenkomst is toegewezen aan het bedrijf van de organisatie uit de aanbieding",
				organisatie1.getBedrijf().heeftArbeidsovereenkomst(
						arbeidsovereenkomst1));
		assertTrue(
				"De arbeidsovereenkomst is toegewezen aan de werknemer van de persoon uit de aanbieding",
				organisatie1.getBedrijf().heeftArbeidsovereenkomst(
						arbeidsovereenkomst1));
		assertSame(1, werknemer1.getArbeidsovereenkomsten().size());
		assertSame(1, bedrijf1.getArbeidsovereenkomsten().size());
		// voeg nog een arbeidsovereenkomst toe aan dezelfde persoon
		aanbieding2 = new Aanbieding(persoon1, organisatie2);
		arbeidsovereenkomst2 = new Arbeidsovereenkomst(aanbieding2);
		// verwijder via werknemer en voegtoe via bedrijf
		assertTrue("methode removeArbeidsovereenkomst geeft true",
				werknemer1.removeArbeidsovereenkomst(arbeidsovereenkomst2));
		assertTrue("methode addArbeidsovereenkomst geeft true",
				bedrijf2.addArbeidsovereenkomst(arbeidsovereenkomst2));
		assertSame(2, werknemer1.getArbeidsovereenkomsten().size());
		assertSame(1, bedrijf2.getArbeidsovereenkomsten().size());
		assertTrue(
				"De werknemer heeft de toegevoegde arbeidsovereenkomst in zijn lijst",
				werknemer1.heeftArbeidsovereenkomst(arbeidsovereenkomst2));
		assertTrue(
				"Het betreffende bedrijf heeft ook de toegevoegde aanbieding in zijn lijst",
				bedrijf2.heeftArbeidsovereenkomst(arbeidsovereenkomst2));
		// voeg nog een arbeidsovereenkomst toe aan een andere persoon
		werknemer2 = new Werknemer();
		persoon2.addRol(werknemer2);
		aanbieding3 = new Aanbieding(persoon2, organisatie1);
		arbeidsovereenkomst3 = new Arbeidsovereenkomst(aanbieding3);// bedrijf1
		assertSame(1, werknemer2.getArbeidsovereenkomsten().size());
		assertSame(2, bedrijf1.getArbeidsovereenkomsten().size());
		assertTrue(
				"De werknemer heeft de toegevoegde arbeidsovereenkomst in zijn lijst",
				werknemer2.heeftArbeidsovereenkomst(arbeidsovereenkomst3));
		assertTrue(
				"Het betreffende bedrijf heeft ook de toegevoegde arbeidsovereenkomst in zijn lijst",
				bedrijf1.heeftArbeidsovereenkomst(arbeidsovereenkomst3));
		// verwijder de arbeidsovereenkomst bij een werknemer
		assertTrue("methode removeArbeidsovereenkomst geeft true",
				werknemer1.removeArbeidsovereenkomst(arbeidsovereenkomst1));
		assertFalse(
				"De werknemer heeft de verwijderde arbeidsovereenkomst in zijn lijst",
				werknemer1.heeftArbeidsovereenkomst(arbeidsovereenkomst1));
		assertFalse(
				"Het betreffende bedrijf heeft de verwijderde arbeidsovereenkomst in zijn lijst",
				bedrijf1.heeftArbeidsovereenkomst(arbeidsovereenkomst1));
		// verwijder ook de 2 later toegevoegde aanbiedingen
		// doe dit via het bedrijf
		assertTrue("methode removeArbeidsovereenkomst geeft true",
				bedrijf2.removeArbeidsovereenkomst(arbeidsovereenkomst2));
		assertTrue("methode removeArbeidsovereenkomst geeft true",
				bedrijf1.removeArbeidsovereenkomst(arbeidsovereenkomst3));
		assertSame(0, werknemer1.getArbeidsovereenkomsten().size());
		assertSame(0, arbeidsovereenkomst1.getOrganisatie().getBedrijf()
				.getArbeidsovereenkomsten().size());
		assertSame(0, werknemer2.getArbeidsovereenkomsten().size());
		assertSame(0, bedrijf2.getArbeidsovereenkomsten().size());

	}

	@Test
	public void testAddAndRemoveArbeidsovereenkomstFail()

	throws InvalidBusinessKeyException {
		arbeidsovereenkomst1 = new Arbeidsovereenkomst(aanbieding1);
		assertFalse(
				"methode werknemer.addArbeidsovereenkomst met arbeidsovereenkomst die al voorkomt in de lijst geeft false",
				werknemer1.addArbeidsovereenkomst(arbeidsovereenkomst1));
		assertFalse(
				"methode bedrijf.addArbeidsovereenkomst met arbeidsovereenkomst die al voorkomt in de lijst geeft false",
				bedrijf1.addArbeidsovereenkomst(arbeidsovereenkomst1));
		persoon2.addRol(new Werknemer());
		aanbieding2 = new Aanbieding(persoon2, organisatie2);
		arbeidsovereenkomst2 = new Arbeidsovereenkomst(aanbieding2);
		assertFalse(
				"methode removeArbeidsovereenkomst met niet voorkomende aanbieding geeft false",
				werknemer1.removeArbeidsovereenkomst(arbeidsovereenkomst2));
		assertFalse(
				"methode removeArbeidsovereenkomst met niet voorkomende aanbieding geeft false",
				bedrijf1.removeArbeidsovereenkomst(arbeidsovereenkomst2));
		werknemer1.removeArbeidsovereenkomst(arbeidsovereenkomst1);// verwijder
																	// zodat de
																	// lijst
		// leeg is
		assertTrue("De lijst van de werknemer is leeg", werknemer1
				.getArbeidsovereenkomsten().isEmpty());
		assertTrue("De lijst van het bedrijf is leeg", bedrijf1
				.getArbeidsovereenkomsten().isEmpty());
		assertFalse(
				"methode addArbeidsovereenkomst met arbeidsovereenkomst die niet naar de juiste persoon refereert geeft false",
				werknemer1.addArbeidsovereenkomst(arbeidsovereenkomst2));
		assertFalse(
				"methode addArbeidsovereenkomst met arbeidsovereenkomst die niet naar de juiste organisatie refereert geeft false",
				bedrijf1.addArbeidsovereenkomst(arbeidsovereenkomst2));
		assertFalse("methode addArbeidsovereenkomst met null geeft false",
				werknemer1.addArbeidsovereenkomst(null));
		assertFalse("methode addArbeidsovereenkomst met null geeft false",
				bedrijf1.addArbeidsovereenkomst(null));
		assertTrue(
				"De lijst van de werknemer is nog steeds leeg, dus ook niets toegevoegd",
				werknemer1.getArbeidsovereenkomsten().isEmpty());
		assertTrue(
				"De lijst van het bedrijf is nog steeds leeg, dus ook niets toegevoegd",
				bedrijf1.getArbeidsovereenkomsten().isEmpty());
		assertFalse(
				"methode werknemer.removeArbeidsovereenkomst met arbeidsovereenkomst die niet voorkomt in de lijst geeft false (lege lijst)",
				werknemer1.removeArbeidsovereenkomst(arbeidsovereenkomst2));
		assertFalse(
				"methode bedrijf.removeArbeidsovereenkomst met arbeidsovereenkomst die niet voorkomt in de lijst geeft false (lege lijst)",
				bedrijf1.removeArbeidsovereenkomst(arbeidsovereenkomst2));
		assertFalse("methode removeArbeidsovereenkomst met null geeft false",
				werknemer1.removeArbeidsovereenkomst(null));
		assertFalse("methode removeArbeidsovereenkomst met null geeft false",
				bedrijf1.removeArbeidsovereenkomst(null));
	}

	@Test
	public void testVacatureAanbiedingSucces()
			throws InvalidBusinessKeyException, MalformedURLException {
		// voeg een vacature toe aan een reeds toegekende aanbieding
		vacature1 = new Vacature(organisatie1, "testvacature1");
		assertTrue(
				"Het toevoegen van een vacature aan een toegekende aanbieding "
						+ "met dezelfde organisatie geeft true",
				aanbieding1.setVacatureReferentie(vacature1));
		assertTrue("de vacature van aanbieding1 is de toegekende vacature1",
				aanbieding1.getVacature() == vacature1);
		assertTrue("De aanbieding van kandidaat1 heeft vacature1",
				getGegevenAanbiedingVanKandidaat(kandidaat1, aanbieding1)
						.getVacature() == vacature1);
		assertTrue("De aanbieding van bedrijf1 heeft vacature1",
				getGegevenAanbiedingVanBedrijf(bedrijf1, aanbieding1)
						.getVacature() == vacature1);
		assertTrue("De organisatie van vacature1 heeft vacature1"
				+ "in de vacaturelijst van diens bedrijf ",
				vacature1.getOrganisatie().getBedrijf().getVacatures()
						.contains(vacature1));
		// maak aanbieding met vacature voor organisatie1 persoon1
		vacature2 = new Vacature(organisatie2, "testvacature2",
				"http://www.google.com");
		// voeg deze toe aan via een nieuwe aanbieding
		aanbieding2 = new Aanbieding(persoon1, organisatie2, vacature2);
		assertFalse("Returned of toevoegen lukt, aangezien de constructor van "
				+ "Aanbieding de Vacature al heeft geinitialiseerd "
				+ "returned deze methode false",
				aanbieding2.setVacatureReferentie(vacature2));
		assertTrue(
				" Test of onze vorige aanname (dat de constructor van Aanbieding de "
						+ "Vacature al heeft geinitialiseerd) correct is",
				aanbieding2.getVacature().equals(vacature2));
		assertTrue("de vacature van aanbieding2 is de toegekende vacature2",
				aanbieding2.getVacature() == vacature2);
		assertTrue("vacature2 heeft aanbieding2", vacature2.getAanbiedingen()
				.contains(aanbieding2));
		assertTrue("kandidaat1 heeft in zijn lijst een aanbieding "
				+ "met een referentie naar vacature2",
				getGegevenAanbiedingVanKandidaat(kandidaat1, aanbieding2)
						.getVacature() == vacature2);
		assertTrue("bedrijf2 heeft in zijn lijst een aanbieding "
				+ "met een referentie naar vacature2",
				getGegevenAanbiedingVanBedrijf(bedrijf2, aanbieding2)
						.getVacature() == vacature2);
		assertTrue("De organisatie van vacature2 heeft vacature2"
				+ "in de vacaturelijst van diens bedrijf ",
				vacature2.getOrganisatie().getBedrijf().getVacatures()
						.contains(vacature2));
		// voeg een arbeidsovereenkomst toe met een vacature
		arbeidsovereenkomst1 = new Arbeidsovereenkomst(aanbieding2);
		assertSame("In de lijst van arbeidsovereenkomsten van het bedrijf"
				+ "van de organisatie van aanbieding2 zit de "
				+ "arbeidsovereenkomst met een aanbieding"
				+ "die refereert aan vacature2", vacature2, aanbieding2
				.getOrganisatie().getBedrijf().getArbeidsovereenkomsten()
				.iterator().next().getAanbieding().getVacature());
		assertSame("In de lijst van arbeidsovereenkomsten van de werknemer"
				+ "van de persoon van aanbieding2 zit de"
				+ "arbeidsovereenkomst met een aanbieding"
				+ "die refereert aan vacature2", vacature2, aanbieding2
				.getPersoon().getWerknemer().getArbeidsovereenkomsten()
				.iterator().next().getAanbieding().getVacature());
		// aan een vacature kan nog een tweede aanbieding worden toegevoegd
		aanbieding3 = new Aanbieding(persoon2, organisatie2, vacature2);
		assertTrue("de vacature van aanbieding3 is de toegekende vacature2",
				aanbieding3.getVacature() == vacature2);
		assertTrue("vacature2 heeft aanbieding3", vacature2.getAanbiedingen()
				.contains(aanbieding3));
		assertSame("De vacature heeft nu 2 aanbiedingen in diens lijst", 2,
				vacature2.getAanbiedingen().size());
		assertTrue("kandidaat2 heeft aanbieding3 met vacature2",
				kandidaat2.heeftAanbieding(aanbieding3));
		assertTrue("bedrijf2 heeft aanbieding3 met vacature2",
				bedrijf2.heeftAanbieding(aanbieding3));
		assertTrue("De organisatie van vacature2 heeft vacature2"
				+ "in de vacaturelijst van diens bedrijf ",
				vacature2.getOrganisatie().getBedrijf().getVacatures()
						.contains(vacature2));
	}

	@Test
	public void testVacatureAanbiedingFail()
			throws InvalidBusinessKeyException, MalformedURLException {
		// voeg een vacature toe aan een reeds toegekende aanbieding
		vacature1 = new Vacature(organisatie2, "testvacature1");
		assertFalse(
				"Het toevoegen van een vacature met een andere organisatie "
						+ "dan waaraan de aanbieding is toegekend geeft false",
				aanbieding1.setVacatureReferentie(vacature1));
		assertSame("De aanbiedingenlijst van vacature is dus 0 items groot", 0,
				vacature1.getAanbiedingen().size());
		assertNull("De vacature van aanbieding1 is null",
				aanbieding1.getVacature());
		assertFalse("De organisatie van aanbieding1 heeft vacature1 niet"
				+ "in de vacaturelijst van diens bedrijf ",
				aanbieding1.getOrganisatie().getBedrijf().getVacatures()
						.contains(vacature1));
		// toevoegen van null waarde
		assertFalse("Het toevoegen van een null-vacature geeft false",
				aanbieding1.setVacatureReferentie(null));
		// toevoegen van de niet juiste aanbieding aan de vacature
		assertFalse(
				"Het toevoegen van aanbieding1 die aan een andere organisatie"
						+ "is toegekend dan vacature1 geeft false",
				vacature1.addAanbieding(aanbieding1));
		assertFalse("vacature1 heeft aanbieding1 niet in diens lijst",
				vacature1.getAanbiedingen().contains(aanbieding1));
		assertFalse("Het toevoegen van een null-aanbieding geeft false",
				vacature1.addAanbieding(null));

	}

	@Test(expected = InvalidBusinessKeyException.class)
	public void testVacatureAanbiedingVerschillendeOrganisaties()
			throws MalformedURLException, InvalidBusinessKeyException {
		// vacature met organisatie1
		vacature2 = new Vacature(organisatie1, "testvacature2",
				"http://www.google.com");
		// aanbieding met organisatie2
		aanbieding2 = new Aanbieding(persoon1, organisatie2, vacature2);
	}

	private Aanbieding getGegevenAanbiedingVanKandidaat(Kandidaat kandidaat,
			Aanbieding aanbieding) {
		Aanbieding temp = null;
		for (Aanbieding a : kandidaat.getAanbiedingen()) {
			if (a.equals(aanbieding)) {
				temp = a;
			}
		}
		return temp;
	}

	private Aanbieding getGegevenAanbiedingVanBedrijf(Bedrijf bedrijf,
			Aanbieding aanbieding) {
		Aanbieding temp = null;
		for (Aanbieding a : bedrijf.getAanbiedingen()) {
			if (a.equals(aanbieding)) {
				temp = a;
			}
		}
		return temp;
	}
}
