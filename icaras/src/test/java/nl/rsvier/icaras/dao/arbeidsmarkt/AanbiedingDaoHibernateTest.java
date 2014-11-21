package nl.rsvier.icaras.dao.arbeidsmarkt;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
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
public class AanbiedingDaoHibernateTest {

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

	ArrayList<Aanbieding> aanbiedingenlijst = new ArrayList<Aanbieding>();
	private Aanbieding aanbieding1;
	private Aanbieding aanbieding1b;
	private Aanbieding aanbieding1c;
	private Aanbieding aanbieding2;
	private Aanbieding aanbieding3;
	private Aanbieding aanbieding4;
	private Aanbieding aanbieding4b;
	private Aanbieding aanbieding4c;
	private Aanbieding aanbieding4d;
	private Aanbieding aanbieding5;

	@Before
	public void setUp() throws MalformedURLException,
			InvalidBusinessKeyException {

		sterlingcooper = new Organisatie("Sterling Cooper Advertising Agency");
		pearsonhardman = new Organisatie("Pearson Hardman");

		organisatielijst.addAll(Arrays.asList(sterlingcooper, pearsonhardman));
		for (Organisatie org : organisatielijst) {
			Bedrijf bedrijfsrol = new Bedrijf();
			orgrolDao.save(bedrijfsrol);
			org.addRol(bedrijfsrol);
			organisatieDao.save(org);
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
		}

		vacature1 = new Vacature(sterlingcooper, "looking for a new copywriter");
		vacature2 = new Vacature(sterlingcooper, "looking for a new assistant");
		vacature3 = new Vacature(pearsonhardman, "looking for interns",
				"http://www.harvard.edu.com/");

		vacaturelijst.addAll(Arrays.asList(vacature1, vacature2, vacature3));
		for (Vacature v : vacaturelijst) {
			vacatureDao.save(v);
		}

		/*
		 * Aanbiedingen zijn wel erg streng nu. Duplicaten mogen niet meer, en
		 * vacature is nog geen deel van de businesskey. Comment problematische
		 * gevallen nog even weg
		 */
		aanbieding1 = new Aanbieding(don, sterlingcooper);
		// aanbieding1b = new Aanbieding(don, sterlingcooper, vacature2);
		aanbieding1c = new Aanbieding(don, pearsonhardman, vacature3);
		aanbieding2 = new Aanbieding(peggy, sterlingcooper, vacature1);
		aanbieding3 = new Aanbieding(bertram, sterlingcooper, vacature2);
		aanbieding4 = new Aanbieding(harvey, pearsonhardman);
		// aanbieding4d = new Aanbieding(harvey, pearsonhardman, vacature1);
		aanbieding4c = new Aanbieding(harvey, sterlingcooper, vacature1);
		// aanbieding4b = new Aanbieding(harvey, sterlingcooper);
		aanbieding5 = new Aanbieding(louis, pearsonhardman, vacature3);

		aanbiedingenlijst.addAll(Arrays.asList(aanbieding1, aanbieding1b,
				aanbieding1c, aanbieding2, aanbieding3, aanbieding4,
				aanbieding4b, aanbieding4c, aanbieding4d, aanbieding5));
		for (Aanbieding a : aanbiedingenlijst) {
			if (a != null) {
				aanbiedingDao.save(a);
			}
		}

		aanbiedingDao.getHibernateTemplate().flush();

	}

	@Test
	@Transactional
	public void testDao_save() throws InvalidBusinessKeyException {

		// Gebruik de volgende Aanbieding binnen deze test:
		Organisatie use_organisatie = sterlingcooper;
		Persoon use_persoon = louis;
		Vacature use_vacature = vacature1;

		// Creer een nieuwe Vacature n.a.v. de te gebruiken variabelen
		Aanbieding new_aanbieding = new Aanbieding(use_persoon,
				use_organisatie, use_vacature);

		if (aanbiedingenlijst.contains(new_aanbieding)) {
			fail("Als je een kopie maakt van een bestaande (en dus eerder \""
					+ "weggeschreven) Aanbieding valt er weinig te testen");
		}

		aanbiedingDao.save(new_aanbieding);

		vacatureDao.getHibernateTemplate().flush();
		vacatureDao.getHibernateTemplate().clear();

		assertTrue(
				"De Aanbieding is toegevoegd aan de database en heeft een id",
				new_aanbieding.getId() > 0);

		assertTrue(
				"Aanbieding in de database heeft gelijke waarden aan de Aanbieding in het geheugen",
				new_aanbieding.equals(aanbiedingDao.getById(new_aanbieding
						.getId())));

	}

	@Test
	@Transactional
	public void testDao_update() {

		// Gebruik de volgende Aanbieding binnen deze test:
		Aanbieding use_aanbieding = aanbieding1;
		Vacature use_vacature = vacature2;

		if (use_aanbieding.getVacature() != null) {
			fail("Kan niet testen met een Aanbieding die al een Vacature bevat");
		}

		if (!use_vacature.getOrganisatie().equals(
				use_aanbieding.getOrganisatie())) {
			fail("Kan niet testen met een Vacature die is uitgeschreven door een \""
					+ "andere Organisatie dan waar de Aanbieding aan is gericht");
		}

		assertTrue(
				"De Aanbieding is toegevoegd aan de database en heeft een id",
				use_aanbieding.getId() > 0);

		assertTrue(
				"Aanbieding in de database heeft gelijke waarden aan de Aanbieding in het geheugen",
				use_aanbieding.equals(aanbiedingDao.getById(use_aanbieding
						.getId())));

		assertNull("Aanbieding bevat nog geen Vacature",
				aanbiedingDao.getById(use_aanbieding.getId()).getVacature());

		use_aanbieding.setVacatureReferentie(use_vacature);
		aanbiedingDao.update(use_aanbieding);

		vacatureDao.getHibernateTemplate().flush();
		vacatureDao.getHibernateTemplate().clear();

		assertNotNull("De Vacature is toegevoegd aan de Aanbieding",
				aanbiedingDao.getById(use_aanbieding.getId()).getVacature());

		assertTrue(
				"De Aanbieding is toegevoegd aan de collectie Aanbiedingen van Vacature",
				vacatureDao.getById(use_vacature.getId()).getAanbiedingen()
						.contains(use_aanbieding));

	}

	@Test
	@Transactional
	public void testDao_get() {

		// Gebruik de volgende Aanbieding binnen deze test:
		Aanbieding use_aanbieding = aanbieding3;

		// Geleid de benodigde variabelen af van de Aanbieding die we testen
		Persoon use_persoon = use_aanbieding.getPersoon();

		vacatureDao.getHibernateTemplate().flush();
		vacatureDao.getHibernateTemplate().clear();

		assertTrue(
				"De Aanbieding is toegevoegd aan de database en heeft een id",
				aanbiedingDao.getById(use_aanbieding.getId()).getId() > 0);

		assertTrue(
				"Aanbieding in de database heeft gelijke waarden aan de Aanbieding in het geheugen",
				aanbiedingDao.getById(use_aanbieding.getId()).equals(
						use_aanbieding));

		for (Aanbieding a : this.aanbiedingenlijst) {
			if (a != null && use_persoon.equals(a.getPersoon())) {
				assertNotNull("Persoon: " + use_persoon.getVolledigeNaam()
						+ " aangeboden aan meerdere Organisaties",
						aanbiedingDao.getById(a.getId()).getOrganisatie());
			}
		}

	}

	@Test
	@Transactional
	public void testDao_getAll() {

		vacatureDao.getHibernateTemplate().flush();
		vacatureDao.getHibernateTemplate().clear();

		assertTrue(
				"aanbiedingenDao.getAll() levert een lijst van gedane aanbiedingen op",
				aanbiedingDao.getAll().size() > 0);

	}
	
	@Test
	@Transactional()
	public void test_delete() {
		
//		// Test met:
//		Aanbieding use_aanbieding = aanbieding3;
//
//		assertNotNull("Aanbieding staat al in de tabel",
//				aanbiedingDao.getById(use_aanbieding.getId()));
//
//		aanbiedingDao.getHibernateTemplate().flush();
//		aanbiedingDao.getHibernateTemplate().clear();
//
//		System.out.println("--------------------------");
//		System.out.println(use_aanbieding);
//		System.out.println(use_aanbieding.getVacature());
//		System.out.println(use_aanbieding.getVacature().getAanbiedingen());
//		System.out.println("--------------------------");
//		for(Aanbieding a : use_aanbieding.getOrganisatie().getBedrijf().getAanbiedingen()) {
//			System.out.println(a);
//		}
//		System.out.println("--------------------------");
//		System.out.println();
//		System.out.println();
//		assertTrue(use_aanbieding.removeAllReferences());
//		System.out.println();
//		System.out.println();
//		System.out.println("-------------------------- 222");
//		System.out.println(use_aanbieding);
//		System.out.println(use_aanbieding.getVacature());
//		System.out.println(use_aanbieding.getVacature().getAanbiedingen());
//		System.out.println("--------------------------");
//		for(Aanbieding a : use_aanbieding.getOrganisatie().getBedrijf().getAanbiedingen()) {
//			System.out.println(a);
//		}
//		System.out.println("--------------------------");
//		
//		aanbiedingDao.getHibernateTemplate().flush();
//		aanbiedingDao.getHibernateTemplate().clear();
//		
//		aanbiedingDao.delete(use_aanbieding);
//
//		aanbiedingDao.getHibernateTemplate().flush();
//		aanbiedingDao.getHibernateTemplate().clear();
//
//		assertNull(
//				"Aanbieding kan niet langer worden teruggevonden in de tabel",
//				aanbiedingDao.getById(use_aanbieding.getId()));

	}

}
