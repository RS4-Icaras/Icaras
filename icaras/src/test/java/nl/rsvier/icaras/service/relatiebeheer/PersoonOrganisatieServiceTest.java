package nl.rsvier.icaras.service.relatiebeheer;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
import nl.rsvier.icaras.core.arbeidsmarkt.Arbeidsovereenkomst;
import nl.rsvier.icaras.core.relatiebeheer.Bedrijf;
import nl.rsvier.icaras.core.relatiebeheer.Contactpersoon;
import nl.rsvier.icaras.core.relatiebeheer.Kandidaat;
import nl.rsvier.icaras.core.relatiebeheer.Leverancier;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.core.relatiebeheer.Werknemer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:icarastestdb-context.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PersoonOrganisatieServiceTest {

	@Autowired
	private IPersoonService persoonService;

	@Autowired
	private IOrganisatieService organisatieService;

	@Autowired
	private IAanbiedingService aanbiedingService;

	@Autowired
	private IArbeidsovereenkomstService arbeidsovereenkomstService;

	private Persoon testPersoon1;
	private Persoon testPersoon2;
	private Persoon testPersoon3;

	private Organisatie testOrganisatie1;
	private Organisatie testOrganisatie2;
	private Organisatie testOrganisatie3;
	private Organisatie testOrganisatie4;

	private Bedrijf testBedrijf1;
	private Bedrijf testBedrijf2;

	private Kandidaat kandidaat1;
	private Kandidaat kandidaat2;

	private Werknemer werknemer1;
	private Werknemer werknemer2;

	private Contactpersoon contactpersoon1;
	private Contactpersoon contactpersoon2;

	private Aanbieding aanbieding1;
	private Aanbieding aanbieding2;
	// private Aanbieding testaanbieding;

	private Arbeidsovereenkomst arbeidsovereenkomst1;
	private Arbeidsovereenkomst arbeidsovereenkomst2;

	// private Arbeidsovereenkomst arbeidsovereenkomst3;

	@Before
	public void setUp() throws InvalidBusinessKeyException {
		testPersoon1 = maakTestPersoonZonderId("Thomas", "", "Slippens",
				new GregorianCalendar(1986, 3, 25));
		testPersoon2 = maakTestPersoonZonderId("Leroy", "van den", "Hoogen",
				new GregorianCalendar(1988, 0, 21));
		testPersoon3 = new Persoon("Voornaam", "van den", "Achternaam",
				new GregorianCalendar(1986, Calendar.SEPTEMBER, 25));
		kandidaat1 = new Kandidaat();
		kandidaat2 = new Kandidaat();
		werknemer1 = new Werknemer();
		werknemer2 = new Werknemer();
		contactpersoon1 = new Contactpersoon();
		testPersoon1.addRol(kandidaat1);
		testPersoon1.addRol(werknemer1);
		testPersoon1.addRol(contactpersoon1);
		testPersoon2.addRol(kandidaat2);
		testOrganisatie1 = new Organisatie("Organisatie1");
		testBedrijf1 = new Bedrijf();
		testOrganisatie1.addRol(testBedrijf1);

		organisatieService.save(testOrganisatie1);

		testOrganisatie1.addContactpersoon(testPersoon1);
		aanbieding1 = new Aanbieding(testPersoon1, testOrganisatie1);
		arbeidsovereenkomst1 = new Arbeidsovereenkomst(aanbieding1);

		persoonService.save(testPersoon1);
		organisatieService.update(testOrganisatie1);
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
	public void testBefore() {
		testPersoon1 = persoonService.getByIdMetRollen(testPersoon1.getId());
		testOrganisatie1 = organisatieService.getByIdMetRollen(testOrganisatie1
				.getId());

		assertNotNull(testOrganisatie1);
		assertNotNull(testPersoon1);
		// aanbieding
		assertSame(1, testPersoon1.getKandidaat().getAanbiedingen().size());
		assertSame(1, testOrganisatie1.getBedrijf().getAanbiedingen().size());
		assertTrue(testPersoon1
				.getKandidaat()
				.getAanbiedingen()
				.iterator()
				.next()
				.equals(testOrganisatie1.getBedrijf().getAanbiedingen()
						.iterator().next()));
		// arbeidsovereenkomst
		assertSame(1, testPersoon1.getWerknemer().getArbeidsovereenkomsten()
				.size());
		assertSame(1, testOrganisatie1.getBedrijf().getArbeidsovereenkomsten()
				.size());
		assertTrue(testPersoon1
				.getWerknemer()
				.getArbeidsovereenkomsten()
				.iterator()
				.next()
				.equals(testOrganisatie1.getBedrijf()
						.getArbeidsovereenkomsten().iterator().next()));
		assertTrue(testOrganisatie1.getBedrijf().heeftMedewerker(testPersoon1));
		// Contactpersoon
		assertSame(1, testPersoon1.getContactpersoon().getOrganisaties().size());
		assertSame(1, testOrganisatie1.getContactpersonen().size());
		assertTrue(testPersoon1.getContactpersoon().heeftOrganisatie(
				testOrganisatie1));
		assertTrue(testOrganisatie1.heeftContactpersoon(testPersoon1));
	}

	@Test
	public void testDeleteZonderOVereenkomsten() {
		persoonService.save(testPersoon2);
		persoonService.delete(testPersoon2);
		assertNull(persoonService.getById(testPersoon2.getId()));
	}

	@Test
	public void testDeletePersoon() {

		//testpersoon1 heeft Kandidaat, Contactpersoon, Aanbieding en
		//Arbeidsovereenkomst naar testOrganisatie1
		persoonService.delete(testPersoon1);
		//onderstaande test controleren of de referenties van testPersoon1
		//naar testOrganisatie1 na verwijderen testPersoon1 ook zijn verwijderd
		testOrganisatie1 = organisatieService.getByIdMetRollen(testOrganisatie1
				.getId());
		assertTrue(testOrganisatie1.getBedrijf().getAanbiedingen().isEmpty());
		assertTrue(testOrganisatie1.getBedrijf().getArbeidsovereenkomsten()
				.isEmpty());
		assertFalse(testOrganisatie1.getBedrijf().heeftMedewerker(testPersoon1));
		assertTrue(testOrganisatie1.getContactpersonen().isEmpty());
		assertFalse(testOrganisatie1.heeftContactpersoon(testPersoon1));
		assertNull(persoonService.getById(testPersoon1.getId()));
		assertNull(aanbiedingService.getById(aanbieding1.getId()));
		assertNull(arbeidsovereenkomstService.getById(arbeidsovereenkomst1
				.getId()));

	}

	@Test
	public void testDeleteOrganisatie() {

		//testOrganisatie1 heeft  Contactpersoon, Aanbieding en
		//Arbeidsovereenkomst naar Organisatie1
		organisatieService.delete(testOrganisatie1);
		//test of na deleten testOrganisatie1 ook bidirectionele relaties
		//zijn verwijderd
		testPersoon1 = persoonService.getByIdMetRollen(testPersoon1
				.getId());
		assertTrue(testPersoon1.getKandidaat().getAanbiedingen().isEmpty());
		assertTrue(testPersoon1.getWerknemer().getArbeidsovereenkomsten().isEmpty());
		assertTrue(testPersoon1.getContactpersoon().getOrganisaties().isEmpty());
		assertTrue(testPersoon1.getContactpersoon().getOrganisaties().isEmpty());
		
		assertNull(organisatieService.getById(testOrganisatie1.getId()));
		assertNull(aanbiedingService.getById(aanbieding1.getId()));
		assertNull(arbeidsovereenkomstService.getById(arbeidsovereenkomst1
				.getId()));
		assertFalse(testPersoon1.getContactpersoon().
				getOrganisaties().contains(testOrganisatie1));

	}

	@Test
	public void testDeleteMetMeerdereOvereenkomsten()
			throws InvalidBusinessKeyException {
		testOrganisatie2 = new Organisatie("organisatie2");
		testOrganisatie2.addRol(new Bedrijf());
		aanbieding2 = new Aanbieding(testPersoon1, testOrganisatie2);
		arbeidsovereenkomst2 = new Arbeidsovereenkomst(aanbieding2);
		testOrganisatie2.addContactpersoon(testPersoon1);
		organisatieService.save(testOrganisatie2);
		persoonService.update(testPersoon1);
		// laadt de instanties uit de db
		testPersoon1 = persoonService.getByIdMetRollen(testPersoon1.getId());
		testOrganisatie2 = organisatieService.getByIdMetRollen(testOrganisatie2
				.getId());
		// Contactpersoon
		assertSame(2, testPersoon1.getContactpersoon().getOrganisaties().size());
		assertSame(1, testOrganisatie2.getContactpersonen().size());
		assertTrue(testPersoon1.getContactpersoon().heeftOrganisatie(
				testOrganisatie2));
		assertTrue(testOrganisatie2.heeftContactpersoon(testPersoon1));
		// arbeidsovereenkomst
		assertSame(2, testPersoon1.getWerknemer().getArbeidsovereenkomsten()
				.size());
		assertSame(1, testOrganisatie2.getBedrijf().getArbeidsovereenkomsten()
				.size());
		// aanbieding
		assertSame(2, testPersoon1.getKandidaat().getAanbiedingen().size());
		assertSame(1, testOrganisatie2.getBedrijf().getAanbiedingen().size());
		persoonService.delete(testPersoon1);
		testOrganisatie1 = organisatieService.getByIdMetRollen(testOrganisatie1
				.getId());
		assertTrue(testOrganisatie1.getBedrijf().getAanbiedingen().isEmpty());
		assertTrue(testOrganisatie1.getBedrijf().getArbeidsovereenkomsten()
				.isEmpty());
		assertFalse(testOrganisatie1.getBedrijf().heeftMedewerker(testPersoon1));
		assertNull(persoonService.getById(testPersoon1.getId()));
		assertNull(aanbiedingService.getById(aanbieding1.getId()));
		assertNull(arbeidsovereenkomstService.getById(arbeidsovereenkomst1
				.getId()));

		testOrganisatie2 = organisatieService.getByIdMetRollen(testOrganisatie2
				.getId());
		assertTrue(testOrganisatie2.getBedrijf().getAanbiedingen().isEmpty());
		assertTrue(testOrganisatie2.getBedrijf().getArbeidsovereenkomsten()
				.isEmpty());
		assertTrue(testOrganisatie2.getContactpersonen().isEmpty());
		assertFalse(testOrganisatie2.heeftContactpersoon(testPersoon1));
		assertTrue(testOrganisatie1.getContactpersonen().isEmpty());
		assertFalse(testOrganisatie1.heeftContactpersoon(testPersoon1));

		assertNull(aanbiedingService.getById(aanbieding2.getId()));
		assertNull(arbeidsovereenkomstService.getById(arbeidsovereenkomst2
				.getId()));
	}

	@Test
	public void testGetAllMetBedrijfsrol() throws InvalidBusinessKeyException {
		testOrganisatie3 = new Organisatie("Organisatie3");
		testOrganisatie3.addRol(new Bedrijf());
		testOrganisatie4 = new Organisatie("Organisatie4");
		organisatieService.save(testOrganisatie3);
		organisatieService.save(testOrganisatie4);

		List<Organisatie> organisaties = organisatieService
				.getAllMetBedrijfsrol();
		assertFalse(organisaties.isEmpty());
		assertSame(2, organisaties.size());
		assertSame(3, organisatieService.getAll().size());
		assertTrue(organisaties.contains(testOrganisatie1));
		assertTrue(organisaties.contains(testOrganisatie3));
		assertFalse(organisaties.contains(testOrganisatie4));

	}

	@Test
	public void testGetAllMetKandidaat() {
		persoonService.save(testPersoon2);// is wel kandidaat
		persoonService.save(testPersoon3);// geen kandidaat
		List<Persoon> kandidaten = persoonService.getAllMetKandidaat();
		assertFalse(kandidaten.isEmpty());
		assertSame(2, kandidaten.size());
		assertSame(3, persoonService.getAll().size());
		assertTrue(kandidaten.contains(testPersoon1));
		assertTrue(kandidaten.contains(testPersoon2));
		assertFalse(kandidaten.contains(testPersoon3));
	}
	
	@Test
	public void testGetAllMetWerknemerEnKandidaat() {
		testPersoon3.addRol(new Werknemer());
		persoonService.save(testPersoon2);// is geen werknemer
		persoonService.save(testPersoon3);
		List<Persoon> werknemers = persoonService.getAllMetWerknemerEnKandidaat();
		assertFalse(werknemers.isEmpty());
		assertSame(2, werknemers.size());
		assertSame(3, persoonService.getAll().size());
		assertTrue(werknemers.contains(testPersoon1));
		assertTrue(werknemers.contains(testPersoon3));
		assertFalse(werknemers.contains(testPersoon2));
	}
	
	@Test
	public void testGetAllMetContactPersoon() {
		contactpersoon2 = new Contactpersoon();
		contactpersoon2.setFunctie("super uniek");
		testPersoon3.addRol(contactpersoon2);
		persoonService.save(testPersoon2);// is geen contactpersoon
		persoonService.save(testPersoon3);
		List<Persoon> werknemers = persoonService.getAllMetContactPersoon();
		assertFalse(werknemers.isEmpty());
		assertSame(2, werknemers.size());
		assertSame(3, persoonService.getAll().size());
		assertTrue(werknemers.contains(testPersoon1));
		assertTrue(werknemers.contains(testPersoon3));
		assertFalse(werknemers.contains(testPersoon2));
	}
	
	@Test
	public void testGetAllAanTeBiedenOrganisaties() throws InvalidBusinessKeyException {
		persoonService.save(testPersoon2);// is wel kandidaat
		persoonService.save(testPersoon3);// geen kandidaat
		organisatieService.getAllAanTeBiedenOrganisaties(testPersoon1);
		testOrganisatie2 = new Organisatie("organisatie2");
		testOrganisatie2.addRol(new Bedrijf());
		aanbieding2 = new Aanbieding(testPersoon1, testOrganisatie2);
		organisatieService.save(testOrganisatie2);
		persoonService.update(testPersoon1);
		testOrganisatie3 = new Organisatie("Organisatie3");
		testOrganisatie3.addRol(new Bedrijf());
		testOrganisatie4 = new Organisatie("Organisatie4");//geen Bedrijf
		organisatieService.save(testOrganisatie3);
		organisatieService.save(testOrganisatie4);
		testPersoon1 = persoonService.getByIdMetRollen(testPersoon1.getId());
		testPersoon2 = persoonService.getByIdMetRollen(testPersoon2.getId());
		testPersoon3 = persoonService.getByIdMetRollen(testPersoon3.getId());
		assertSame(3, organisatieService.getAllAanTeBiedenOrganisaties(testPersoon3).size());
		assertSame(3, organisatieService.getAllAanTeBiedenOrganisaties(testPersoon2).size());
		assertSame(1, organisatieService.getAllAanTeBiedenOrganisaties(testPersoon1).size());
	}
}
