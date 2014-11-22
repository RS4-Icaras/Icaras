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

import nl.rsvier.icaras.core.relatiebeheer.Bedrijf;
import nl.rsvier.icaras.core.relatiebeheer.Leverancier;
import nl.rsvier.icaras.core.relatiebeheer.OrganisatieRol;

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
public class OrganisatieRolDaoHibernateTest {

	@Autowired
	private OrganisatieRolDaoHibernate organisatierolDao;

	ArrayList<OrganisatieRol> organisatierollen_correct;
	Bedrijf bedrijfsrol;
	Leverancier leveranciersrol;

	@Before
	public void setUp() {
		bedrijfsrol = new Bedrijf();
		leveranciersrol = new Leverancier();
		leveranciersrol.setFunctie("wc papier supplier");

		organisatierollen_correct = new ArrayList<OrganisatieRol>();
		organisatierollen_correct.addAll(Arrays.asList(bedrijfsrol,
				leveranciersrol));
	}

	@Test
	@Transactional
	public void test_getById() {
		for (OrganisatieRol rol : organisatierollen_correct) {

			assertNull("Rol staat nog niet in de tabel",
					organisatierolDao.getById(rol.getId()));

			rol.setOpmerking("");
			rol.setGearchiveerd(false);

			organisatierolDao.save(rol);

			organisatierolDao.getHibernateTemplate().flush();
			organisatierolDao.getHibernateTemplate().clear();

			assertTrue(
					String.format("Rol: %s is teruggevonden in de tabel", rol),
					organisatierolDao.getById(rol.getId()).equals(rol));
		}
	}

	@Test
	@Transactional
	public void test_getAll() {

		assertTrue("Rollen staan nog niet in de database", organisatierolDao
				.getAll().size() == 0);

		for (OrganisatieRol rol : organisatierollen_correct) {
			organisatierolDao.save(rol);
			organisatierolDao.getHibernateTemplate().flush();
			organisatierolDao.getHibernateTemplate().clear();
		}

		List<OrganisatieRol> testlijst = organisatierolDao.getAll();

		assertNotNull("Lijst met Rollen bestaat in geheugen", testlijst);
		assertTrue("Alle rollen zijn uit de database gelezen",
				testlijst.size() == organisatierollen_correct.size());

	}

	@Test
	@Transactional
	public void test_saveOrUpdate() {

		Random r = new Random();

		for (OrganisatieRol rol : organisatierollen_correct) {

			assertNull(
					String.format("Rol: %s staat nog niet in de tabel", rol),
					organisatierolDao.getById(rol.getId()));

			organisatierolDao.getHibernateTemplate().flush();
			organisatierolDao.getHibernateTemplate().clear();

			rol.setOpmerking("");
			rol.setGearchiveerd(false);

			organisatierolDao.save(rol);

			organisatierolDao.getHibernateTemplate().flush();
			organisatierolDao.getHibernateTemplate().clear();

			assertNotNull(
					String.format("Rol: %s is teruggevonden in de tabel", rol),
					organisatierolDao.getById(rol.getId()).equals(rol));

			assertFalse(String.format("Rol: %s is nog niet gearchiveerd", rol),
					organisatierolDao.getById(rol.getId()).isGearchiveerd());

			organisatierolDao.getHibernateTemplate().flush();
			organisatierolDao.getHibernateTemplate().clear();

			rol.setGearchiveerd(true);
			organisatierolDao.update(rol);

			organisatierolDao.getHibernateTemplate().flush();
			organisatierolDao.getHibernateTemplate().clear();

			assertTrue(String.format("Rol: %s is gearchiveerd", rol),
					organisatierolDao.getById(rol.getId()).isGearchiveerd());

			organisatierolDao.getHibernateTemplate().flush();
			organisatierolDao.getHibernateTemplate().clear();

			String teststring = String.valueOf(r.nextInt(1000000));
			rol.setOpmerking(teststring);
			organisatierolDao.update(rol);

			organisatierolDao.getHibernateTemplate().flush();
			organisatierolDao.getHibernateTemplate().clear();

			assertTrue("Rol heeft een nieuwe Opmerking", organisatierolDao
					.getById(rol.getId()).getOpmerking().equals(teststring));

		}

	}

	@Test
	@Transactional
	public void test_delete() {
		for (OrganisatieRol rol : organisatierollen_correct) {

			assertNull("Rol staat nog niet in de tabel",
					organisatierolDao.getById(rol.getId()));

			organisatierolDao.getHibernateTemplate().flush();
			organisatierolDao.getHibernateTemplate().clear();

			rol.setOpmerking("");
			rol.setGearchiveerd(false);

			organisatierolDao.save(rol);

			organisatierolDao.getHibernateTemplate().flush();
			organisatierolDao.getHibernateTemplate().clear();

			assertNotNull(
					String.format("Rol: %s is teruggevonden in de tabel", rol),
					organisatierolDao.getById(rol.getId()).equals(rol));

			organisatierolDao.getHibernateTemplate().flush();
			organisatierolDao.getHibernateTemplate().clear();

			organisatierolDao.delete(rol);

			organisatierolDao.getHibernateTemplate().flush();
			organisatierolDao.getHibernateTemplate().clear();

			assertNull("Rol kan niet langer worden teruggevonden in de tabel",
					organisatierolDao.getById(rol.getId()));

		}
	}

}
