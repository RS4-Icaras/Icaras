package nl.rsvier.icaras.dao.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:icarasdb-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class OrganisatieDaoHibernateTest {

	@Autowired
	private OrganisatieDaoHibernate organisatieDao;

	List<Organisatie> organisaties_correct;
	Organisatie sterlingcooper;
	Organisatie pearsonhardman;
	Organisatie sebben;

	@Before
	public void setUp() throws InvalidBusinessKeyException {
		sterlingcooper = new Organisatie("Sterling Cooper Advertising Agency");
		pearsonhardman = new Organisatie("Pearson Hardman");
		sebben = new Organisatie("Sebben & Sebben");

		organisaties_correct = new ArrayList<Organisatie>();
		organisaties_correct.addAll(Arrays.asList(sterlingcooper,
				pearsonhardman, sebben));
	}

	@Test
	@Transactional
	public void testSaveUpdate() {

		// Test met:
		Organisatie use_organisatie = sterlingcooper;

		assertFalse(
				"Organisatie is transient en staat nog niet in de tabel",
				use_organisatie.getId() > 0
						&& organisatieDao.getById(use_organisatie.getId()) != null);

		organisatieDao.getHibernateTemplate().flush();
		organisatieDao.getHibernateTemplate().clear();

		use_organisatie
				.setOpmerking("Founded by Roger Sterling sr. and Bertram Cooper");
		use_organisatie.setGearchiveerd(false);

		organisatieDao.save(use_organisatie);

		organisatieDao.getHibernateTemplate().flush();
		organisatieDao.getHibernateTemplate().clear();

		assertTrue(
				"Organisatie is niet langer transient en kan worden teruggevonden in de tabel",
				use_organisatie.getId() > 0
						&& organisatieDao.getById(use_organisatie.getId()) != null);

		assertFalse("Organisatie is nog niet gearchiveerd", organisatieDao
				.getById(use_organisatie.getId()).isGearchiveerd());

		organisatieDao.getHibernateTemplate().flush();
		organisatieDao.getHibernateTemplate().clear();

		use_organisatie.setGearchiveerd(true);
		organisatieDao.update(use_organisatie);

		organisatieDao.getHibernateTemplate().flush();
		organisatieDao.getHibernateTemplate().clear();

		assertTrue("Organisatie is gearchiveerd",
				organisatieDao.getById(use_organisatie.getId())
						.isGearchiveerd());

		organisatieDao.getHibernateTemplate().flush();
		organisatieDao.getHibernateTemplate().clear();

		String newString = "Mutiny, see: Sterling Cooper Draper Pryce";
		use_organisatie.setOpmerking(newString);
		organisatieDao.update(use_organisatie);

		organisatieDao.getHibernateTemplate().flush();
		organisatieDao.getHibernateTemplate().clear();

		assertTrue("Organisatie heeft een nieuwe Opmerking",
				organisatieDao.getById(use_organisatie.getId()).getOpmerking()
						.equals(newString));

	}

	@Test
	@Transactional()
	public void testDelete() {

		// Test met:
		Organisatie use_organisatie = sebben;

		assertFalse(
				"Organisatie is transient en staat nog niet in de tabel",
				use_organisatie.getId() > 0
						&& organisatieDao.getById(use_organisatie.getId()) != null);

		organisatieDao.save(use_organisatie);

		assertTrue(
				"Organisatie is niet langer transient en kan worden teruggevonden in de tabel",
				use_organisatie.getId() > 0
						&& organisatieDao.getById(use_organisatie.getId()) != null);

		organisatieDao.getHibernateTemplate().flush();
		organisatieDao.getHibernateTemplate().clear();

		organisatieDao.delete(use_organisatie);

		organisatieDao.getHibernateTemplate().flush();
		organisatieDao.getHibernateTemplate().clear();

		assertNull(
				"Organisatie kan niet langer worden teruggevonden in de tabel",
				organisatieDao.getById(use_organisatie.getId()));

	}

	@Test
	@Transactional
	public void testGetOrganisatie() {

		// Test met:
		Organisatie use_organisatie = pearsonhardman;

		assertFalse(
				"Organisatie is transient en staat nog niet in de tabel",
				use_organisatie.getId() > 0
						&& organisatieDao.getById(use_organisatie.getId()) != null);

		organisatieDao.save(use_organisatie);

		organisatieDao.getHibernateTemplate().flush();
		organisatieDao.getHibernateTemplate().clear();

		Organisatie retrieved_organisatie = organisatieDao
				.getById(use_organisatie.getId());

		organisatieDao.getHibernateTemplate().flush();
		organisatieDao.getHibernateTemplate().clear();

		assertTrue(
				"Organisatie is niet langer transient en kan worden teruggevonden in de tabel",
				use_organisatie.getId() > 0 && retrieved_organisatie != null);

		assertTrue(
				"Check of we niet twee referenties in het geheugen met elkaar vergelijken",
				retrieved_organisatie != use_organisatie);

		assertTrue(
				"Organisatie die we hebben opgehaald komt overeen met de Organisatie die we hebben weggeschreven",
				retrieved_organisatie.equals(use_organisatie));
	}

	@Test
	@Transactional()
	public void testGetAll() {
		assertTrue("Begin test met een lege collectie", organisatieDao.getAll()
				.size() == 0);

		for (Organisatie organisatie : organisaties_correct) {
			organisatieDao.save(organisatie);
		}

		organisatieDao.getHibernateTemplate().flush();
		organisatieDao.getHibernateTemplate().clear();

		int counter = 0;
		List<Organisatie> organisaties = organisatieDao.getAll();
		for (Organisatie organisatie : organisaties) {
			System.out.println(">>>>> " + organisatie + " >>>>> "
					+ organisaties_correct.contains(organisatie));
			if (organisaties_correct.contains(organisatie)) {
				counter++;
			}
		}

		assertTrue("Er zijn meerdere organisaties gevonden",
				organisaties.size() > 1);

		assertTrue(
				"Het aantal gevonden organisaties komt overeen met de gecreeerde organisaties",
				counter == organisaties_correct.size());

		assertTrue("De collecties komen overeen",
				organisaties.equals(organisaties_correct));

	}

}
