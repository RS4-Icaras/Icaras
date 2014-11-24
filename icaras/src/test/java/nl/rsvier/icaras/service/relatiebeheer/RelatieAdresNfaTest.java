package nl.rsvier.icaras.service.relatiebeheer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Adres;
import nl.rsvier.icaras.core.relatiebeheer.Email;
import nl.rsvier.icaras.core.relatiebeheer.Facebook;
import nl.rsvier.icaras.core.relatiebeheer.Nfa;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.core.relatiebeheer.Relatie;
import nl.rsvier.icaras.core.relatiebeheer.TelefoonNummer;
import nl.rsvier.icaras.core.relatiebeheer.Website;
import nl.rsvier.icaras.dao.relatiebeheer.AdresDaoHibernate;
import nl.rsvier.icaras.dao.relatiebeheer.NfaDaoHibernate;
import nl.rsvier.icaras.dao.relatiebeheer.RelatieDaoHibernate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:icarastestdb-context.xml" })
@TransactionConfiguration(defaultRollback = true)
public class RelatieAdresNfaTest {

	@Autowired
	private AdresDaoHibernate adresDao;
	@Autowired
	private NfaDaoHibernate nfaDao;
	@Autowired
	private RelatieDaoHibernate relatieDao;

	Adres testAdres1;
	Adres testAdres2;
	Adres testAdres3;
	Adres testAdres4;
	Adres nullAdres = null;
	Nfa testNfa1;
	Nfa testNfa2;
	Nfa testNfa3;
	Nfa testNfa4;
	Nfa nullNfa = null;
	Persoon testRelatie1;
	Organisatie testRelatie2;
	Relatie nullRelatie = null;

	@Before
	public void setUp() throws InvalidBusinessKeyException {
		testAdres1 = maakTestAdresZonderId(true, "9876ZY", "123A", "Utrecht",
				"Oude Markt");
		testAdres2 = maakTestAdresZonderId(false, "1234AB", "191", "Hilversum",
				"Larenseweg");
		testAdres3 = maakTestAdresZonderId(false, "5555CC", "632 bis",
				"IngevoerdePlaats1", "Dorpsstraat");
		testAdres4 = maakTestAdresZonderId(true, "4325LL", "5677",
				"IngevoerdePlaats2", "");
		testNfa1 = new Email();
		testNfa1.setNfaAdres("blabla@blabla.bla");
		testNfa1.setExtraInfo("Deze infomatie is van grote toegevoegde waarde");
		testNfa2 = new Facebook();
		testNfa2.setNfaAdres("Mientje99_onzin");
		testNfa2.setExtraInfo("Mientje is de beste");
		testNfa3 = new TelefoonNummer();
		testNfa3.setNfaAdres("+316-12345678");
		testNfa3.setExtraInfo("Probeer dit nummer als eerste");
		testNfa4 = new Website();
		testNfa4.setNfaAdres("www.rsvier.nl");
		testNfa4.setExtraInfo("In need of a serious make over");
		testRelatie1 = new Persoon();
		testRelatie1.setGearchiveerd(true);
		testRelatie1.setPriveRelatie(false);
		testRelatie1.setVoornaam("Voornaam");
		testRelatie1.setTussenvoegsels("van den");
		testRelatie1.setAchternaam("Achternaam");
		testRelatie1.setOpmerking("Dit is een persoon");
		testRelatie1.setGeboortedatum(new GregorianCalendar(1988,
				GregorianCalendar.FEBRUARY, 13));
		testRelatie1.addAdres(testAdres1);
		testRelatie1.addAdres(testAdres2);
		testRelatie1.addNfa(testNfa1);
		testRelatie1.addNfa(testNfa2);
		testRelatie2 = new Organisatie("OrganisatieNaam");
		testRelatie2.addAdres(testAdres3);
		testRelatie2.addAdres(testAdres4);
		testRelatie2.addNfa(testNfa3);
		testRelatie2.addNfa(testNfa4);
		Iterator<Adres> adresIterator = testRelatie2.getAdressen().iterator();
		adresIterator.next().setPlaats("VeranderdePlaats1");
		adresIterator.next().setPlaats("VeranderdePlaats2");
		testRelatie2.setGearchiveerd(false);
		testRelatie2.setPriveRelatie(true);
		testRelatie2.setOpmerking("Fantastische organisatie");
		// onderstaande niet meer nodig ivm CascadingType.ALL
		// for (Adres a : testRelatie1.getAdressen()) {
		// if (a != null)//saving a null value levert een
		// IllegalArgumentException
		// adresDao.save(a);
		// }
		// for (Nfa n : testRelatie1.getNfaLijst()) {
		// if (n != null)
		// nfaDao.save(n);
		// }
	}

	private Adres maakTestAdresZonderId(boolean isPostbus, String postcode,
			String huisOfPostbusNummer, String plaats, String straat) {
		Adres nieuwAdres = new Adres();
		if (isPostbus) {
			nieuwAdres.maakPostbus();
		} else {
			nieuwAdres.maakStraat();
			nieuwAdres.setStraat(straat);
		}
		nieuwAdres.setPostcode(postcode);
		nieuwAdres.setHuisOfPostbusNummer(huisOfPostbusNummer);
		nieuwAdres.setPlaats(plaats);
		return nieuwAdres;
	}

	@Test
	@Transactional
	// (propagation=Propagation.REQUIRES_NEW)//Hiervoor is Spring versie van
	// @Transactional nodig
	public void testSaveEnGet() {
		relatieDao.save(testRelatie1);
		assertNotNull(testRelatie1.getId());
		relatieDao.getHibernateTemplate().flush();
		relatieDao.getHibernateTemplate().clear();
		Relatie vergelijkRelatie = relatieDao.getById(testRelatie1.getId());
		assertTrue(
				"attributen vanuit database zijn gelijk aan die van het adres voor save",
				testRelatie1.equals(vergelijkRelatie));
		Adres vergelijkAdres = null, vergelijkAdres2 = null;
		{
			for (Adres a : vergelijkRelatie.getAdressen()) {
				if (a.getId() == testAdres1.getId())
					vergelijkAdres = a;
			}
		}
		{
			for (Adres a : vergelijkRelatie.getAdressen()) {
				if (a.getId() == testAdres2.getId())
					vergelijkAdres2 = a;
			}
		}
		assertTrue(
				"eerste adres van opgeslagen persoon en adres uit de lijst van ingeladen persoon met dezelfde Adresid zijn gelijk",
				testAdres1.equals(vergelijkAdres));
		assertTrue(
				"tweede adres van opgeslagen persoon en adres uit de lijst van ingeladen persoon met dezelfde id zijn gelijk",
				testAdres2.equals(vergelijkAdres2));
		Nfa vergelijkNfa1 = null, vergelijkNfa2 = null;
		{
			for (Nfa n : vergelijkRelatie.getNfaLijst()) {
				if (n.getId() == testNfa1.getId())
					vergelijkNfa1 = n;
			}
		}
		{
			for (Nfa n : vergelijkRelatie.getNfaLijst()) {
				if (n.getId() == testNfa2.getId())
					vergelijkNfa2 = n;
			}
		}
		assertTrue(
				"eerste nfa van opgeslagen persoon en nfa uit de lijst van ingeladen persoon met dezelfde id zijn gelijk",
				testNfa1.equals(vergelijkNfa1));
		assertTrue(
				"tweede nfa van opgeslagen persoon en nfa uit de lijst van ingeladen persoon met dezelfde id zijn gelijk",
				testNfa2.equals(vergelijkNfa2));
	}

	@Test(expected = IllegalArgumentException.class)
	@Transactional
	public void testNullSave() {
		relatieDao.save(nullRelatie);
	}

	@Test
	@Transactional
	public void testSaveRelatieMetNullAdressen() {
		testRelatie1.getAdressen().add(nullAdres);
		testRelatie1.getNfaLijst().add(nullNfa);
		relatieDao.save(testRelatie1);// adressen en nfaLijst via cascading,
										// geen probleem in geval van null
										// waarden
		// nfaDao.save(testRelatie1.getNfaLijst().iterator().next());//handmatig
		// saven van null waarde geef wel exception
	}

	@Test
	@Transactional
	public void testGetAll() {
		relatieDao.save(testRelatie1);
		relatieDao.save(testRelatie2);
		relatieDao.getHibernateTemplate().flush();
		relatieDao.getHibernateTemplate().clear();
		List<Relatie> relaties = relatieDao.getAll();
		assertTrue("Lijst van opgehaalde relaties bevat 2 relaties",
				relaties.size() == 2);
		Relatie vergelijkRelatie = null, vergelijkRelatie2 = null;
		{
			for (Relatie r : relaties) {
				if (r.getId() == testRelatie1.getId())
					vergelijkRelatie = r;
			}
		}
		{
			for (Relatie r : relaties) {
				if (r.getId() == testRelatie2.getId())
					vergelijkRelatie2 = r;
			}
		}
		assertTrue(
				"eerste opgeslagen relatie is gelijk aan ingelezen relatie met gelijk id",
				testRelatie1.equals(vergelijkRelatie));
		assertTrue(
				"tweede opgeslagen relatie is gelijk aan ingelezen relatie met gelijk id",
				testRelatie2.equals(vergelijkRelatie2));
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Adres vergelijkAdres = null, vergelijkAdres2 = null;
		{
			for (Adres a : vergelijkRelatie.getAdressen()) {
				if (a.getId() == testAdres1.getId())
					vergelijkAdres = a;
			}
		}
		{
			for (Adres a : vergelijkRelatie.getAdressen()) {
				if (a.getId() == testAdres2.getId())
					vergelijkAdres2 = a;
			}
		}
		assertTrue(
				"eerste adres van opgeslagen persoon en adres uit de lijst van ingeladen persoon met dezelfde Adresid zijn gelijk",
				testAdres1.equals(vergelijkAdres));
		assertTrue(
				"tweede adres van opgeslagen persoon en adres uit de lijst van ingeladen persoon met dezelfde id zijn gelijk",
				testAdres2.equals(vergelijkAdres2));
		Nfa vergelijkNfa1 = null, vergelijkNfa2 = null;
		{
			for (Nfa n : vergelijkRelatie.getNfaLijst()) {
				if (n.getId() == testNfa1.getId())
					vergelijkNfa1 = n;
			}
		}
		{
			for (Nfa n : vergelijkRelatie.getNfaLijst()) {
				if (n.getId() == testNfa2.getId())
					vergelijkNfa2 = n;
			}
		}
		assertTrue(
				"eerste nfa van opgeslagen persoon en nfa uit de lijst van ingeladen persoon met dezelfde id zijn gelijk",
				testNfa1.equals(vergelijkNfa1));
		assertTrue(
				"tweede nfa van opgeslagen persoon en nfa uit de lijst van ingeladen persoon met dezelfde id zijn gelijk",
				testNfa2.equals(vergelijkNfa2));
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Adres vergelijkAdres3 = null, vergelijkAdres4 = null;

		{
			for (Adres a : vergelijkRelatie2.getAdressen()) {
				if (a.getId() == testAdres3.getId())
					vergelijkAdres3 = a;
			}
		}
		{
			for (Adres a : vergelijkRelatie2.getAdressen()) {
				if (a.getId() == testAdres4.getId())
					vergelijkAdres4 = a;
			}
		}
		assertTrue(
				"eerste adres van tweede opgeslagen persoon en adres uit de lijst van ingeladen persoon met dezelfde Adresid zijn gelijk",
				testAdres3.equals(vergelijkAdres3));
		assertTrue(
				"tweede adres van tweede opgeslagen persoon en adres uit de lijst van ingeladen persoon met dezelfde id zijn gelijk",
				testAdres4.equals(vergelijkAdres4));
		Nfa vergelijkNfa3 = null, vergelijkNfa4 = null;
		{
			for (Nfa n : vergelijkRelatie2.getNfaLijst()) {
				if (n.getId() == testNfa3.getId())
					vergelijkNfa3 = n;
			}
		}
		{
			for (Nfa n : vergelijkRelatie2.getNfaLijst()) {
				if (n.getId() == testNfa4.getId())
					vergelijkNfa4 = n;
			}
		}
		assertTrue(
				"eerste nfa van tweede opgeslagen persoon en nfa uit de lijst van ingeladen persoon met dezelfde id zijn gelijk",
				testNfa3.equals(vergelijkNfa3));
		assertTrue(
				"tweede nfa van tweede opgeslagen persoon en nfa uit de lijst van ingeladen persoon met dezelfde id zijn gelijk",
				testNfa4.equals(vergelijkNfa4));
	}

	@Test
	@Transactional
	public void testCascadeUpdate() {
		relatieDao.save(testRelatie1);
		// testRelatie1.getAdressen().iterator().next().setPlaats("Gewijzigde Plaatsnaam");//niet
		// gegarandeerd omdat ik test op testadres1.getAdresId()
		testAdres1.setPlaats("Gewijzigde Plaatsnaam");
		relatieDao.update(testRelatie1);
		// nog een test waarin de persoon zelf wel gewijzigd wordt
		testRelatie2.setOpmerking("New and improved name");
		testAdres3.maakPostbus();
		testNfa4.setExtraInfo(null);// kan vooralsnog omdat nullability nog by
									// default op true staat
									// lvert verder geen probleem op om een null
									// waarde in een veld mee te geven
		relatieDao.update(testRelatie2);
		relatieDao.getHibernateTemplate().flush();
		relatieDao.getHibernateTemplate().clear();
		Persoon vergelijkPersoon = (Persoon) relatieDao.getById(testRelatie1
				.getId());
		assertTrue("Persoon uit db is gelijk aan geupdate persoon",
				testRelatie1.equals(vergelijkPersoon));
		System.out.println("plaatnaam van vergelijkpersoon: "
				+ vergelijkPersoon.getAdressen().iterator().next().getPlaats());
		Adres vergelijkAdres = null;
		for (Object a : vergelijkPersoon.getAdressen().toArray()) {
			if (((Adres) a).getId() == testAdres1.getId()) {
				vergelijkAdres = (Adres) a;
			}
		}
		assertTrue("adres in database heeft de gewijzigde plaatsnaam",
				vergelijkAdres.getPlaats().equals("Gewijzigde Plaatsnaam"));
		// Tweede test asserts
		Organisatie vergelijkOrganisatie = (Organisatie) relatieDao
				.getById(testRelatie2.getId());
		assertTrue("Organisatie uit db is gelijk aan geupdate organisatie",
				testRelatie2.equals(vergelijkOrganisatie));
		Adres vergelijkAdres2 = null;
		for (Object a : vergelijkOrganisatie.getAdressen().toArray()) {
			if (((Adres) a).getId() == testAdres3.getId()) {
				vergelijkAdres2 = (Adres) a;
			}
		}
		assertTrue("adres in database heeft de gewijzigde plaatsnaam",
				vergelijkAdres2.equals(testAdres3));
		Nfa vergelijkNfa = null;
		for (Object n : vergelijkOrganisatie.getNfaLijst().toArray()) {
			if (((Nfa) n).getId() == testNfa4.getId()) {
				vergelijkNfa = (Nfa) n;
			}
		}
		// vergelijken van nfa met methode equals gaf nullpointerException ivm
		// null als string --> null.equals(waarde)
		assertNull(
				"nfa heeft als extra info de toegekende en geupdate waarde null",
				vergelijkNfa.getExtraInfo());

	}

	@Test
	@Transactional
	public void testDelete() {
		relatieDao.save(testRelatie1);
		relatieDao.getHibernateTemplate().flush();// zorgt voor saven van
													// refenties naar adressen
													// en nfaLijst
		int idTestAdres1 = testAdres1.getId();
		int idTestAdres2 = testAdres2.getId();
		int idTestNfa1 = testNfa1.getId();
		int idTestNfa2 = testNfa2.getId();
		relatieDao.delete(testRelatie1);// Bijbehorende adressen en nfa's worden
										// nu via orphanRemoval gedelete//
										// inmiddels cascading all
		relatieDao.getHibernateTemplate().flush();
		relatieDao.getHibernateTemplate().clear();
		Relatie vergelijkRelatie = relatieDao.getById(testRelatie1.getId());
		assertNull("verwijderde relatie opvragen uit database geeft null",
				vergelijkRelatie);
		assertNull(
				"opvragen van eerste adres op id van gedelete relatie geeft null",
				adresDao.getById(idTestAdres1));
		assertNull(
				"opvragen van tweede adres op id van gedelete relatie geeft null",
				adresDao.getById(idTestAdres2));
		assertNull(
				"opvragen van eerste nfa op id van gedelete relatie geeft null",
				nfaDao.getById(idTestNfa1));
		assertNull(
				"opvragen van tweede nfa op id van gedelete relatie geeft null",
				nfaDao.getById(idTestNfa2));
	}

}
