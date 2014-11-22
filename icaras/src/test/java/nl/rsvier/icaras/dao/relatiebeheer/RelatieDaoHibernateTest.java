package nl.rsvier.icaras.dao.relatiebeheer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.core.relatiebeheer.Relatie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/*
 * Ter info: 
 * De methoden public Relatie getByIdMetAdres(int id) 
 * en public List<Relatie> getAllMetAdressen()
 * worden hier niet getest omdat deze al getest werden in 
 * RelatieServiceTest voordat de implementatie naar de dao werden verplaatst.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:icarastestdb-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class RelatieDaoHibernateTest {

	@Autowired
	// via @ContextConfiguration wordt deze testklasse aangemeld bij de
	// ApplicationContext
	private RelatieDaoHibernate relatieDaoHibernate;

	Persoon testRelatie1;
	Relatie testRelatie2;

	@Before
	public void setUp() throws InvalidBusinessKeyException {
		testRelatie1 = new Persoon();
		testRelatie1.setGearchiveerd(true);
		testRelatie1.setPriveRelatie(false);
		testRelatie1.setVoornaam("Voornaam");
		testRelatie1.setTussenvoegsels("van den");
		testRelatie1.setAchternaam("Achternaam");
		testRelatie1.setOpmerking("Dit is een persoon");
		testRelatie1.setGeboortedatum(new GregorianCalendar(1988,
				GregorianCalendar.FEBRUARY, 13));
		testRelatie2 = new Organisatie("Organisatienaam");// kan exception
															// gooien
		testRelatie2.setGearchiveerd(false);
		testRelatie2.setPriveRelatie(false);
		testRelatie2.setOpmerking("Dit is een organisatie");

	}

	@Test
	@Transactional
	public void testSaveEnGet() {
		relatieDaoHibernate.save(testRelatie1);
		assertNotNull(testRelatie1.getId());
		relatieDaoHibernate.getHibernateTemplate().evict(testRelatie1);
		testRelatie2 = relatieDaoHibernate.getById(testRelatie1.getId());
		assertTrue(
				"attributen vanuit database zijn gelijk aan die van de relatie voor save",
				testRelatie1.equals(testRelatie2));
	}

	@Test
	@Transactional
	public void testGetAll() {
		relatieDaoHibernate.save(testRelatie1);
		relatieDaoHibernate.save(testRelatie2);
		relatieDaoHibernate.getHibernateTemplate().clear();
		List<Relatie> nfaLijst = relatieDaoHibernate.getAll();
		assertTrue("Zijn er inderdaad 2 objecten geladen uit de database?",
				nfaLijst.size() == 2);
		// toekennen van juiste nfa aan juiste vergelijkNfa (om ordening van
		// nfaLijst irrelevant te maken)
		Relatie vergelijkRelatie1 = null, vergelijkRelatie2 = null;
		{
			for (Relatie n : nfaLijst) {
				if (n.getId() == testRelatie1.getId())
					vergelijkRelatie1 = n;
			}
		}
		;
		{
			for (Relatie n : nfaLijst) {
				if (n.getId() == testRelatie2.getId())
					vergelijkRelatie2 = n;
			}
		}
		;
		assertTrue(
				"eerste opgeslagen relatie en relatie uit de opgehaalde lijst met dezelfde id zijn gelijk",
				testRelatie1.equals(vergelijkRelatie1));
		assertTrue(
				"tweede opgeslagen relatie en relatie uit de opgehaalde lijst met dezelfde id zijn gelijk",
				testRelatie2.equals(vergelijkRelatie2));
	}

	@Test
	@Transactional
	public void testDelete() {
		relatieDaoHibernate.save(testRelatie1);
		relatieDaoHibernate.delete(testRelatie1);
		relatieDaoHibernate.getHibernateTemplate().flush();
		relatieDaoHibernate.getHibernateTemplate().evict(testRelatie1);
		testRelatie2 = relatieDaoHibernate.getById(testRelatie1.getId());
		assertNull("verwijderde nfa opvragen uit database geeft null",
				testRelatie2);
	}

	@Test
	@Transactional
	public void testUpdate() {
		relatieDaoHibernate.save(testRelatie1);
		testRelatie1.setOpmerking(testRelatie2.getOpmerking());
		relatieDaoHibernate.update(testRelatie1);
		relatieDaoHibernate.getHibernateTemplate().flush();
		relatieDaoHibernate.getHibernateTemplate().evict(testRelatie1);
		Relatie vergelijkNfa = relatieDaoHibernate
				.getById(testRelatie1.getId());
		assertTrue(
				"attributen geupdate adres is gelijk aan attributen ingeladen adres met dezelfde id",
				testRelatie1.equals(vergelijkNfa));
	}
}
