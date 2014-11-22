package nl.rsvier.icaras.dao.relatiebeheer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.relatiebeheer.Adres;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;

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
public class TestDaoAdapterTest {

	@Autowired
	//type vd daoInjector is irrelevant, is een normale Dao, 
	//dient als leverancier van een autowired HibernateTemplate
	OrganisatieRolDaoHibernate daoInjector;
	TestDaoAdapter<Adres> adresDao;
	TestDaoAdapter<Persoon> persoonDao;
	
	Persoon testPersoon1;
	Adres adres1, adres2;
	@Before
	public void setUp() {
		adresDao = new TestDaoAdapter<Adres>(Adres.class, daoInjector);
		adres1 = new Adres();
		adres1.maakPostbus();
		adres1.setHuisOfPostbusNummer("123A");
		adres1.setPlaats("Mooie plek");
		adres1.setPostcode("9876AB");
		adres2 = new Adres();
		adres2.maakPostbus();
		adres2.setHuisOfPostbusNummer("123A");
		adres2.setPlaats("Mooie plek");
		adres2.setPostcode("9876AB");
		testPersoon1 = maakTestPersoonZonderId("Thomas", "", "Slippens",
				new GregorianCalendar(1986, 3, 25));
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
	@Transactional
	public void testDaoHelper() {
		adresDao.save(adres1);
		adresDao.flush();
		adresDao.clear();
		Adres vergelijkAdres = adresDao.getAnEntityById(adres1);//speciaal voor Mark
		assertNotNull(vergelijkAdres);
		assertTrue(adres1.equals(vergelijkAdres));
	}
	
	@Test
	@Transactional
	public void testDaoHelperGetAllGetById() {
		adresDao.save(adres1);
		adresDao.save(adres2);
		List<Adres> adressen = new ArrayList<Adres>();
		adressen.add(adres1);
		adressen.add(adres2);
		adresDao.flush();
		adresDao.clear();
		//test of de nu onzinnige getById() methode ook werkt...
		Adres vergelijkAdres = adresDao.getById(adres1.getId());
		assertNotNull(vergelijkAdres);
		assertTrue(adres1.equals(vergelijkAdres));
		List<Adres> adressenNaSave = adresDao.getAll();
		assertTrue("lijst gelijk aan die uit database", adressen.equals(adressenNaSave));
		
	}
	@Test
	@Transactional
	public void test2DaoHelpers1Injector() {
		persoonDao = new TestDaoAdapter<Persoon>(Persoon.class, daoInjector);
		persoonDao.save(testPersoon1);
		persoonDao.flush();
		persoonDao.clear();
		Persoon vergelijkPersoon = persoonDao.getAnEntityById(testPersoon1);
		assertNotNull("testPersoon1 is ingelezen uit databas", vergelijkPersoon);
		assertTrue("opgeslagen persoon equals persoon uit database", testPersoon1.equals(vergelijkPersoon));
	}
}
