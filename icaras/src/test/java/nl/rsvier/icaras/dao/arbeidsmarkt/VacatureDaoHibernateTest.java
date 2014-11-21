package nl.rsvier.icaras.dao.arbeidsmarkt;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.arbeidsmarkt.Vacature;
import nl.rsvier.icaras.core.relatiebeheer.Bedrijf;
import nl.rsvier.icaras.core.relatiebeheer.Kandidaat;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.dao.relatiebeheer.OrganisatieDaoHibernate;
import nl.rsvier.icaras.dao.relatiebeheer.OrganisatieRolDaoHibernate;
import nl.rsvier.icaras.dao.relatiebeheer.PersoonDaoHibernate;
import nl.rsvier.icaras.dao.relatiebeheer.PersoonsRolDaoHibernate;

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
public class VacatureDaoHibernateTest {

	@Autowired
	private OrganisatieDaoHibernate organisatieDao;
	@Autowired
	private PersoonDaoHibernate persoonDao;
	@Autowired
	private AanbiedingDaoHibernate aanbiedingDao;
	@Autowired
	private VacatureDaoHibernate vacatureDao;
	@Autowired
	private OrganisatieRolDaoHibernate orgrolDao;
	@Autowired
	private PersoonsRolDaoHibernate perrolDao;

	ArrayList<Organisatie> organisatielijst = new ArrayList<Organisatie>();
	Organisatie sterlingcooper;
	Organisatie pearsonhardman;

	ArrayList<Persoon> personenlijst = new ArrayList<Persoon>();
	private Persoon don;
	private Persoon peggy;
	private Persoon bertram;
	private Persoon harvey;
	private Persoon louis;

	ArrayList<Vacature> vacaturelijst = new ArrayList<Vacature>();
	private Vacature vacature1;
	private Vacature vacature2;
	private Vacature vacature3;

	@Before
	public void setUp() throws InvalidBusinessKeyException,
			MalformedURLException {

		sterlingcooper = new Organisatie("Sterling Cooper Advertising Agency");
		pearsonhardman = new Organisatie("Pearson Hardman");

		organisatielijst.addAll(Arrays.asList(sterlingcooper, pearsonhardman));
		for (Organisatie org : organisatielijst) {
			Bedrijf bedrijfsrol = new Bedrijf();
			orgrolDao.save(bedrijfsrol);
			org.addRol(bedrijfsrol);
			organisatieDao.save(org);
			organisatieDao.getHibernateTemplate().flush();
		}

		organisatieDao.getHibernateTemplate().flush();

		don = new Persoon("Don", "Draper");
		peggy = new Persoon("Peggy", "Olson");
		bertram = new Persoon("Bertram", "Cooper");
		harvey = new Persoon("Harvey", "Specter");
		louis = new Persoon("Louis", "Litt");

		personenlijst.addAll(Arrays.asList(don, peggy, bertram, harvey, louis));
		for (Persoon per : personenlijst) {
			Kandidaat kandidatenrol = new Kandidaat();
			perrolDao.save(kandidatenrol);
			per.addRol(kandidatenrol);
			persoonDao.save(per);
			persoonDao.getHibernateTemplate().flush();
		}

		persoonDao.getHibernateTemplate().flush();

		vacature1 = new Vacature(sterlingcooper, "looking for a new copywriter");
		vacature2 = new Vacature(sterlingcooper, "looking for a new assistant");
		vacature3 = new Vacature(pearsonhardman, "looking for interns",
				"http://www.harvard.edu.com/");

		vacaturelijst.addAll(Arrays.asList(vacature1, vacature2, vacature3));
		for (Vacature v : vacaturelijst) {
			vacatureDao.save(v);
		}

		vacatureDao.getHibernateTemplate().flush();

	}

	@Test
	@Transactional
	public void testSave() throws InvalidBusinessKeyException {

		// Gebruik de volgende Aanbieding binnen deze test:
		Organisatie use_organisatie = pearsonhardman;
		String use_omschrijving = "looking for named partners";

		// Creer een nieuwe Vacature n.a.v. de te gebruiken variabelen
		Vacature save_vacature = new Vacature(use_organisatie, use_omschrijving);

		vacatureDao.save(save_vacature);

		vacatureDao.getHibernateTemplate().flush();
		vacatureDao.getHibernateTemplate().clear();

		assertTrue("De Vacature is toegevoegd aan de database en heeft een id",
				save_vacature.getId() > 0);

		assertTrue(
				"Vacature in het geheugen heeft gelijke waarden aan de Vacature in de database",
				save_vacature.equals(vacatureDao.getById(save_vacature.getId())));

	}

	@Test
	@Transactional
	public void testGet() {

		// Gebruik de volgende Vacature binnen deze test:
		Vacature use_vacature = vacature3;

		vacatureDao.getHibernateTemplate().flush();
		vacatureDao.getHibernateTemplate().clear();

		Vacature get_vacature = vacatureDao.getById(use_vacature.getId());

		assertTrue("De Vacature is toegevoegd aan de database en heeft een id",
				get_vacature.getId() > 0);

		assertTrue(
				"Vacature in de database heeft gelijke waarden aan de Vacature in het geheugen",
				get_vacature.equals(use_vacature));

	}

	@Test
	@Transactional
	public void testGetAll() {

		assertNotNull(
				"De collectie van Vacatures uit de database is niet leeg",
				vacatureDao.getAll());
		assertTrue(
				"Vacature collectie bevat (na de setup) tenminste 3 elementen",
				vacatureDao.getAll().size() >= 3);

	}

	@Test
	@Transactional()
	public void test_delete() {

		// // Test met:
		// Vacature use_vacature = vacature1;
		//
		// assertNotNull("Vacature staat in de tabel",
		// vacatureDao.getById(use_vacature.getId()));
		//
		// vacatureDao.getHibernateTemplate().flush();
		// vacatureDao.getHibernateTemplate().clear();
		//
		// System.out.println("--------------------------");
		// System.out.println(use_vacature);
		// System.out.println("--------------------------");
		// for(Vacature a :
		// use_vacature.getOrganisatie().getBedrijf().getVacatures()) {
		// System.out.println(a);
		// }
		// System.out.println("--------------------------");
		//
		// assertTrue(use_vacature.removeAllReferences());
		//
		//
		// System.out.println("-------------------------- 222");
		// System.out.println(use_vacature);
		// System.out.println("--------------------------");
		// for(Vacature a :
		// use_vacature.getOrganisatie().getBedrijf().getVacatures()) {
		// System.out.println(a);
		// }
		// System.out.println("--------------------------");
		//
		//
		// vacatureDao.delete(use_vacature);
		//
		// vacatureDao.getHibernateTemplate().flush();
		// vacatureDao.getHibernateTemplate().clear();
		//
		// assertNull(
		// "Vacature kan niet langer worden teruggevonden in de tabel",
		// vacatureDao.getById(use_vacature.getId()));
		//
	}

}
