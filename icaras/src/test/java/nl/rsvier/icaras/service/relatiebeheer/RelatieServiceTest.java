package nl.rsvier.icaras.service.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Adres;
import nl.rsvier.icaras.core.relatiebeheer.Leverancier;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.core.relatiebeheer.Relatie;
import nl.rsvier.icaras.core.relatiebeheer.Werknemer;
import nl.rsvier.icaras.dao.relatiebeheer.IAdresDao;
import nl.rsvier.icaras.dao.relatiebeheer.IRelatieDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:icarastestdb-context.xml" })
//Zorgt voor herladen van de applicatieContext na elke test. 
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RelatieServiceTest {

	@Autowired
	private IRelatieService relatieService;
	
	@Autowired
	private IPersoonService persoonService;
	
	@Autowired
	private IOrganisatieService organisatieService;

	@Autowired
	private IAdresDao adresDao;
;

	private Persoon relatie1;
	private Persoon relatie1_clone;

	private Organisatie relatie2;
	private Organisatie relatie2_clone;

	private Adres adresRelatie1;
	private Adres adresRelatie1_clone;
	private Adres adresRelatie2;
	private Adres adresRelatie2_clone;
	private Adres adresRelatie3;
	private Adres adresRelatie3_clone;

	@Before
	public void setUp() throws InvalidBusinessKeyException {

		adresRelatie1 = new Adres();
		adresRelatie1.maakStraat();
		adresRelatie1.setHuisOfPostbusNummer("456");
		adresRelatie1.setPlaats("Volendam");
		adresRelatie1.setPostcode("4565AK");
		adresRelatie1.setStraat("palingstraat");

		adresRelatie2 = new Adres();
		adresRelatie2.maakPostbus();
		adresRelatie2.setHuisOfPostbusNummer("1000");
		adresRelatie2.setPlaats("Wageningen");
		adresRelatie2.setPostcode("6700AA");
		adresRelatie2.setStraat("Bugs Bunnystraat");

		adresRelatie3 = new Adres();
		adresRelatie3.maakPostbus();
		adresRelatie3.setHuisOfPostbusNummer("3423");
		adresRelatie3.setPlaats("Hilversum");
		adresRelatie3.setPostcode("1000AB");
		adresRelatie3.setStraat("blablastraat");

		relatie1 = new Persoon();
		relatie1.setAchternaam("Smit");
		relatie1.setVoornaam("Jantje");
		relatie1.setTussenvoegsels("de");
		relatie1.setGeboortedatum(new GregorianCalendar(1989, 9, 23));
		relatie1.setOpmerking("Beroerde zanger");
		relatie1.addRol(new Werknemer());
		relatie1.addAdres(adresRelatie1);

		relatie2 = new Organisatie("ACME");
		relatie2.addRol(new Leverancier());
		relatie2.setOpmerking("A company that manufactures EVERYTHING!");
		relatie2.addAdres(adresRelatie2);
		relatie2.addAdres(adresRelatie3);

		/*
		 * Maak een kopie van elke relatie zodat we deze kunnen re-inserten
		 * (zonder id dus) na een delete
		 */

		adresRelatie1_clone = new Adres();
		adresRelatie1_clone.maakStraat();
		adresRelatie1_clone.setHuisOfPostbusNummer("456");
		adresRelatie1_clone.setPlaats("Volendam");
		adresRelatie1_clone.setPostcode("4565AK");
		adresRelatie1_clone.setStraat("palingstraat");

		adresRelatie2_clone = new Adres();
		adresRelatie2_clone.maakPostbus();
		adresRelatie2_clone.setHuisOfPostbusNummer("1000");
		adresRelatie2_clone.setPlaats("Wageningen");
		adresRelatie2_clone.setPostcode("6700AA");
		adresRelatie2_clone.setStraat("Bugs Bunnystraat");

		adresRelatie3_clone = new Adres();
		adresRelatie3_clone.maakPostbus();
		adresRelatie3_clone.setHuisOfPostbusNummer("3423");
		adresRelatie3_clone.setPlaats("Hilversum");
		adresRelatie3_clone.setPostcode("1000AB");
		adresRelatie3_clone.setStraat("blablastraat");

		relatie1_clone = new Persoon();
		relatie1_clone.setAchternaam("Smit");
		relatie1_clone.setVoornaam("Jantje");
		relatie1_clone.setTussenvoegsels("de");
		relatie1_clone.setGeboortedatum(new GregorianCalendar(1989, 9, 23));
		relatie1_clone.setOpmerking("Beroerde zanger");
		relatie1_clone.addRol(new Werknemer());
		relatie1_clone.addAdres(adresRelatie1_clone);

		relatie2_clone = new Organisatie("ACME");
		relatie2_clone.addRol(new Leverancier());
		relatie2_clone.setOpmerking("A company that manufactures EVERYTHING!");
		relatie2_clone.addAdres(adresRelatie2_clone);
		relatie2_clone.addAdres(adresRelatie3_clone);

		/*
		 * Save de relatie
		 */

		relatieService.save(relatie1);
		relatieService.save(relatie2);
	}

//	@After
//	public void tearDown() {
//		/**
//		 * Schoon de tabel op na elke test zodat deze bruikbaar is in een
//		 * volgende test
//		 */
//		service.delete(relatie1);
//		service.delete(relatie2);
//	}

	@Test
	public void testSaveEnGet() {

		relatie1 = (Persoon) relatieService.getById(relatie1.getId());
		relatie2 = (Organisatie) relatieService.getById(relatie2.getId());

		assertTrue("gesavede relatie1 is gelijke aan opgehaalde relatie",
				relatie1.equals(relatie1));
		assertTrue("gesavede relatie2 is gelijke aan opgehaalde relatie",
				relatie2.equals(relatie2));

	}

	@Test
	public void testUpdateVelden() {

		relatie1.setVoornaam("Kees");
		relatie2.setOpmerking("Bijna alles");

		relatieService.update(relatie1);
		relatieService.update(relatie2);

		relatie1 = (Persoon) relatieService.getById(relatie1.getId());
		relatie2 = (Organisatie) relatieService.getById(relatie2.getId());

		assertTrue("opgehaalde relatie1 gelijk aan relatie1Updated",
				relatie1.equals(relatie1));
		assertTrue("opgehaalde relatie2 gelijk aan relatie2Updated Object",
				relatie2.equals(relatie2));

	}

	@Test
	public void testDeleteAdresVanRelatie() {

		relatie1 = (Persoon) relatieService.getByIdMetAdres(relatie1.getId());
		relatie2 = (Organisatie) relatieService.getByIdMetAdres(relatie2.getId());

		relatie1.verwijderLaatsteAdres();

		Adres tmp = null;
		for (Adres adres : relatie2.getAdressen()) {
			if (!adres.isCorrespondentieAdres()) {
				tmp = adres;
			}
		}
		assertTrue("Adres kan niet worden verwijderd",
				relatie2.removeAdres(tmp));

		relatieService.update(relatie1);
		relatieService.update(relatie2);

		assertNotNull("Relatie kan niet worden gevonden",
				relatie1 = (Persoon) relatieService.getByIdMetAdres(relatie1.getId()));
		assertNotNull(
				"Relatie kan niet worden gevonden",
				relatie2 = (Organisatie) relatieService.getByIdMetAdres(relatie2
						.getId()));

		assertTrue("Relatie bevat nog een adres", relatie1.getAdressen().isEmpty());
		assertSame("Relatie ", 1, relatie2.getAdressen().size());

	}

	@Test
	public void testDelete() {

		relatie1 = (Persoon) relatieService.getByIdMetAdres(relatie1.getId());
		relatie2 = (Organisatie) relatieService.getByIdMetAdres(relatie2.getId());

		persoonService.delete(relatie1);
		organisatieService.delete(relatie2);

		assertNull("Gedelete relatie1 is null",
				relatieService.getById(relatie1.getId()));
		assertNull("Gedelete relatie2 is null",
				relatieService.getById(relatie2.getId()));

		assertNull(
				"Controleer of de adressen die bij de relatie horen ook zijn verwijderd",
				adresDao.getById(relatie1.getCorrespondentieAdres().getId()));
		assertSame(
				"Controleer of de adressen die bij de relatie horen ook zijn verwijderd",
				0, adresDao.getAll().size());

//		service.save(relatie1_clone);
//		service.save(relatie2_clone);

//		relatie1 = relatie1_clone;
//		relatie2 = relatie2_clone;
	}

	@Test
	public void testGetAll() {

		List<Relatie> relatieLijst = relatieService.getAll();

		assertTrue("relatieLijst bevat " + relatieLijst.size()
				+ " relaties, dit zou 2 moeten zijn", relatieLijst.size() == 2);

		Relatie[] relatieArray = new Relatie[relatieLijst.size()];
		Persoon returnRelatie1 = null;
		Organisatie returnRelatie2 = null;

		for (Relatie r : relatieLijst.toArray(relatieArray)) {
			if (r instanceof Persoon) {
				returnRelatie1 = (Persoon) r;
			} else if (r instanceof Organisatie) {
				returnRelatie2 = (Organisatie) r;
			}
		}

		assertTrue("Relatie1 uit lijst is niet gelijk aan relatie1",
				returnRelatie1.equals(relatie1));
		assertTrue("Relatie2 uit lijst is niet gelijk aan relatie2",
				returnRelatie2.equals(relatie2));

	}

	@Test
	public void testGetAllMetAdres() {

		List<Relatie> relatieLijst = relatieService.getAllMetAdres();

		assertTrue("relatieLijst bevat " + relatieLijst.size()
				+ " relaties, dit zou 2 moeten zijn", relatieLijst.size() == 2);
		Relatie[] relatieArray = new Relatie[relatieLijst.size()];
		Persoon returnRelatie1 = null;
		Organisatie returnRelatie2 = null;

		for (Relatie r : relatieLijst.toArray(relatieArray)) {
			if (r instanceof Persoon) {

				returnRelatie1 = (Persoon) r;
			} else if (r instanceof Organisatie) {
				returnRelatie2 = (Organisatie) r;
			}
		}

		assertTrue("Relatie1 uit lijst is niet gelijk aan relatie1",
				returnRelatie1.equals(relatie1));
		assertTrue("Relatie2 uit lijst is niet gelijk aan relatie2",
				returnRelatie2.equals(relatie2));

		Set<Adres> relatie1Adressen = returnRelatie1.getAdressen();
		Set<Adres> relatie2Adressen = returnRelatie2.getAdressen();

		assertFalse("adressen Relatie1 zijn leeg ", relatie1Adressen.isEmpty());
		assertFalse("adressen Relatie2 zijn leeg ", relatie2Adressen.isEmpty());
		assertTrue("relatie1 adressen niet gelijk aan returnRelatie1Adressen",
				(relatie1.getAdressen()).equals(relatie1Adressen));
		assertTrue("relatie2 adressen niet gelijk aan returnRelatie2 Adressen",
				(relatie2.getAdressen()).equals(relatie2Adressen));

	}


	@Test
	public void testGetByIdMetAdressen() {

		int verwachtAantal = -1;

		Persoon returnRelatie1MetAdres = (Persoon) relatieService
				.getByIdMetAdres(relatie1.getId());
		Organisatie returnRelatie2MetAdres = (Organisatie) relatieService
				.getByIdMetAdres(relatie2.getId());

		assertFalse(
				"ReturnRelatie1MetAdressen heeft geen adressen, moet wel zo zijn",
				returnRelatie1MetAdres.getAdressen().isEmpty());
		verwachtAantal = relatie1.getAdressen().size();
		assertSame(
				String.format(
						"We verwachten %s element, we hebben er meer of minder gevonden",
						verwachtAantal), verwachtAantal, returnRelatie1MetAdres
						.getAdressen().size());

		assertFalse("returnRelatie2MetAdressen heeft geen adressen",
				returnRelatie2MetAdres.getAdressen().isEmpty());
		verwachtAantal = relatie2.getAdressen().size();
		assertSame(
				String.format(
						"We verwachten %s element, we hebben er meer of minder gevonden",
						verwachtAantal), verwachtAantal, returnRelatie2MetAdres
						.getAdressen().size());

		Set<Adres> returnAdressen1 = returnRelatie1MetAdres.getAdressen();
		Set<Adres> returnAdressen2 = returnRelatie2MetAdres.getAdressen();
		Set<Adres> relatie1Adressen = relatie1.getAdressen();
		Set<Adres> relatie2Adressen = relatie2.getAdressen();

		assertTrue(
				"adressen returnRelatie1 zijn niet gelijk aan die van relatie1",
				returnAdressen1.equals(relatie1Adressen));
		assertTrue(
				"adressen returnRelatie2 zijn niet gelijk aan die van relatie2",
				returnAdressen2.equals(relatie2Adressen));

	}

}
