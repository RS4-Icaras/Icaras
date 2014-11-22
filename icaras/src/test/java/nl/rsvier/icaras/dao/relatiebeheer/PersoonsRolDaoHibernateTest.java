package nl.rsvier.icaras.dao.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.relatiebeheer.Contactpersoon;
import nl.rsvier.icaras.core.relatiebeheer.Kandidaat;
import nl.rsvier.icaras.core.relatiebeheer.PersoonsRol;
import nl.rsvier.icaras.core.relatiebeheer.Werknemer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:icarastestdb-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class PersoonsRolDaoHibernateTest {

	@Autowired
	private PersoonsRolDaoHibernate persoonsrolDao;

	ArrayList<PersoonsRol> persoonsrollen_correct;
	Kandidaat kandidatenrol;
	Werknemer werknemersrol;
	Contactpersoon contactpersoon;

	@Before
	public void setUp() {
		kandidatenrol = new Kandidaat();
		werknemersrol = new Werknemer();
		contactpersoon = new Contactpersoon();
		contactpersoon.setFunctie("pr tante");

		persoonsrollen_correct = new ArrayList<PersoonsRol>();
		persoonsrollen_correct.addAll(Arrays.asList(kandidatenrol,
				werknemersrol, contactpersoon));
	}

	@Test
	@Transactional
	public void test_getById() {
		for (PersoonsRol rol : persoonsrollen_correct) {

			assertNull("Rol staat nog niet in de tabel",
					persoonsrolDao.getById(rol.getId()));

			rol.setOpmerking("");
			rol.setGearchiveerd(false);

			persoonsrolDao.save(rol);

			persoonsrolDao.getHibernateTemplate().flush();
			persoonsrolDao.getHibernateTemplate().clear();

			assertNotNull(
					String.format("Rol: %s is teruggevonden in de tabel", rol),
					persoonsrolDao.getById(rol.getId()).equals(rol));
		}
	}

	@Test
	@Transactional
	public void test_getAll() {

		assertTrue("Rollen staan nog niet in de database", persoonsrolDao
				.getAll().size() == 0);

		for (PersoonsRol rol : persoonsrollen_correct) {
			persoonsrolDao.save(rol);
			persoonsrolDao.getHibernateTemplate().flush();
			persoonsrolDao.getHibernateTemplate().clear();
		}

		List<PersoonsRol> testlijst = persoonsrolDao.getAll();

		assertNotNull("Lijst met Rollen bestaat in geheugen", testlijst);
		assertTrue("Alle rollen zijn uit de database gelezen",
				testlijst.size() == persoonsrollen_correct.size());

	}

	@Test
	@Transactional
	public void test_saveOrUpdate() {

		Random r = new Random();

		for (PersoonsRol rol : persoonsrollen_correct) {

			assertNull(
					String.format("Rol: %s staat nog niet in de tabel", rol),
					persoonsrolDao.getById(rol.getId()));

			persoonsrolDao.getHibernateTemplate().flush();
			persoonsrolDao.getHibernateTemplate().clear();

			rol.setOpmerking("");
			rol.setGearchiveerd(false);

			persoonsrolDao.save(rol);

			persoonsrolDao.getHibernateTemplate().flush();
			persoonsrolDao.getHibernateTemplate().clear();

			assertNotNull(
					String.format("Rol: %s is teruggevonden in de tabel", rol),
					persoonsrolDao.getById(rol.getId()).equals(rol));

			assertFalse(String.format("Rol: %s is nog niet gearchiveerd", rol),
					persoonsrolDao.getById(rol.getId()).isGearchiveerd());

			persoonsrolDao.getHibernateTemplate().flush();
			persoonsrolDao.getHibernateTemplate().clear();

			rol.setGearchiveerd(true);
			persoonsrolDao.update(rol);

			persoonsrolDao.getHibernateTemplate().flush();
			persoonsrolDao.getHibernateTemplate().clear();

			assertTrue(String.format("Rol: %s is gearchiveerd", rol),
					persoonsrolDao.getById(rol.getId()).isGearchiveerd());

			persoonsrolDao.getHibernateTemplate().flush();
			persoonsrolDao.getHibernateTemplate().clear();

			String teststring = String.valueOf(r.nextInt(1000000));
			rol.setOpmerking(teststring);
			persoonsrolDao.update(rol);

			persoonsrolDao.getHibernateTemplate().flush();
			persoonsrolDao.getHibernateTemplate().clear();

			assertTrue("Rol heeft een nieuwe Opmerking", persoonsrolDao
					.getById(rol.getId()).getOpmerking().equals(teststring));

		}

	}

	@Test
	@Transactional
	public void test_delete() {
		for (PersoonsRol rol : persoonsrollen_correct) {

			assertFalse("Rol staat nog niet in de tabel",
					persoonsrolDao.getById(rol.getId()) != null);

			persoonsrolDao.getHibernateTemplate().flush();
			persoonsrolDao.getHibernateTemplate().clear();

			rol.setOpmerking("");
			rol.setGearchiveerd(false);

			persoonsrolDao.save(rol);

			persoonsrolDao.getHibernateTemplate().flush();
			persoonsrolDao.getHibernateTemplate().clear();

			assertNotNull(
					String.format("Rol: %s is teruggevonden in de tabel", rol),
					persoonsrolDao.getById(rol.getId()).equals(rol));

			persoonsrolDao.getHibernateTemplate().flush();
			persoonsrolDao.getHibernateTemplate().clear();

			persoonsrolDao.delete(rol);

			persoonsrolDao.getHibernateTemplate().flush();
			persoonsrolDao.getHibernateTemplate().clear();

			assertNull("Rol kan niet langer worden teruggevonden in de tabel",
					persoonsrolDao.getById(rol.getId()));

		}
	}

}
