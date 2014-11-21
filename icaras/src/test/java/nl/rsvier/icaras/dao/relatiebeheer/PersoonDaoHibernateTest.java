
package nl.rsvier.icaras.dao.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
import nl.rsvier.icaras.core.arbeidsmarkt.Arbeidsovereenkomst;
import nl.rsvier.icaras.core.relatiebeheer.Kandidaat;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.core.relatiebeheer.Werknemer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:icarastestdb-context.xml"
})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class PersoonDaoHibernateTest {
	
	//TODO test opschonen
	
	@Autowired
	private PersoonDaoHibernate persoonDao;
	
	private Persoon testPersoon1;
	private Persoon testPersoon2;
	
//	private Kandidaat kandidaat1 = new Kandidaat();
//	private Kandidaat kandidaat2 = new Kandidaat();
//	
//	private Werknemer werknemer1 = new Werknemer();
//	private Werknemer werknemer2 = new Werknemer();
//	
//	private Aanbieding aanbieding1;
//	private Aanbieding aanbieding2;
//	
//	private Arbeidsovereenkomst arbeidsovereenkomst1;
//	private Arbeidsovereenkomst arbeidsovereenkomst2;
	
	
	@Before
	public void setUp() {
		testPersoon1 = maakTestPersoonZonderId("Thomas", "", "Slippens",
				new GregorianCalendar(1986, 3, 25));
		testPersoon2 = maakTestPersoonZonderId("Leroy", "van den",
				"Hoogen", new GregorianCalendar(1988, 0, 21));

//		testPersoon1.addRol(kandidaat1);
//		testPersoon1.addRol(werknemer1);		
//		testPersoon2.addRol(kandidaat2);
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
	public void testSaveEnGetPersoon() {
		persoonDao.save(testPersoon1);
		persoonDao.save(testPersoon2);			
		persoonDao.getHibernateTemplate().flush();
		persoonDao.getHibernateTemplate().clear();
		assertNotNull(persoonDao.getById(testPersoon1.getId()));
		assertNotNull(persoonDao.getById(testPersoon2.getId()));
		
		//Persoon testPersoonTest1 = testPersoon1;
		Persoon testPersoonTest1 = persoonDao
			.getById(testPersoon1.getId());
		//Persoon testPersoonTest2 = testPersoon2;
		Persoon testPersoonTest2 = persoonDao
				.getById(testPersoon2.getId());
		
		//equals() van Persoon klasse
		assertTrue(
				"attributen vanuit database zijn gelijk aan die van het persoon voor save",
				testPersoonTest1.equals(testPersoon1));
		assertTrue(
				"attributen vanuit database zijn gelijk aan die van het persoon voor save",
				testPersoonTest2.equals(testPersoon2));
		
	}
	
	@Test
	@Transactional
	public void testUpdatePersoon() {
		persoonDao.save(testPersoon1);
		persoonDao.save(testPersoon2);
		persoonDao.getHibernateTemplate().flush();
		assertFalse("attributen geupdate achternaam is ongelijk aan attributen ingeladen persoon met dezelfde id",
				persoonDao.getById(testPersoon1.getId()).getAchternaam().equals(persoonDao.getById(testPersoon2.getId()).getAchternaam()));
		persoonDao.getById(testPersoon1.getId()).setAchternaam(persoonDao.getById(testPersoon2.getId()).getAchternaam());
		persoonDao.getById(testPersoon1.getId()).setTussenvoegsels(persoonDao.getById(testPersoon2.getId()).getTussenvoegsels());
		persoonDao.getById(testPersoon1.getId()).setVoornaam(persoonDao.getById(testPersoon2.getId()).getVoornaam());
		persoonDao.getById(testPersoon1.getId()).setGeboortedatum(persoonDao.getById(testPersoon2.getId()).getGeboortedatum());
//		assertNull(testPersoon2.getWerknemer());
//		persoonDao.getById(testPersoon2.getId()).addRol(werknemer2);
		
//		persoonDao.getHibernateTemplate().clear();
		
		persoonDao.update(testPersoon1);
		persoonDao.update(testPersoon2);
		persoonDao.getHibernateTemplate().flush();
		persoonDao.getHibernateTemplate().clear();

//		assertNotNull(testPersoon2.getKandidaat());
//		assertNotNull(testPersoon2.getWerknemer());
		
		assertTrue("attributen geupdate achternaam is gelijk aan attributen ingeladen persoon met dezelfde id",
				persoonDao.getById(testPersoon1.getId()).equals(persoonDao.getById(testPersoon2.getId())));
	}
    
	@Test
	@Transactional
	public void testDeletePersoon() {
		persoonDao.save(testPersoon1);
		persoonDao.getHibernateTemplate().flush();		
		persoonDao.getHibernateTemplate().clear();
		assertNotNull(persoonDao.getById(testPersoon1.getId()));
		
		persoonDao.delete(persoonDao.getById(testPersoon1.getId()));
		persoonDao.getHibernateTemplate().flush();
		persoonDao.getHibernateTemplate().clear();
		assertNull(persoonDao.getById(testPersoon1.getId()));
	}
	
	@Test
	@Transactional
	public void testGetAllPersonen() {
		persoonDao.save(testPersoon1);
		persoonDao.save(testPersoon2);
		persoonDao.getHibernateTemplate().flush();
		persoonDao.getHibernateTemplate().clear();
		Persoon p1 = persoonDao.getById(testPersoon1.getId());
		Persoon p2 = persoonDao.getById(testPersoon2.getId());
		List<Persoon> testlijst = new ArrayList<Persoon>();
		testlijst.add(p1);
		testlijst.add(p2);
		
		/*The public boolean equals(Object o) for Lists first checks if the specified object is this list. 
		 * If so, it returns true; if not, it checks if the specified object is a list. 
		 * If not, it returns false; if so, it iterates over both lists, comparing corresponding pairs of elements. 
		 * If any comparison returns false, this method returns false. 
		 * If either iterator runs out of elements before the other it returns false (as the lists are of unequal length);
		 *  otherwise it returns true when the iterations complete.
		 */
		assertTrue(persoonDao.getAll().equals(testlijst));
	}
	
	@Test
	@Transactional
	public void testGetAllPersonenByQuery() {
		persoonDao.save(testPersoon1);
		persoonDao.save(testPersoon2);
		persoonDao.getHibernateTemplate().flush();
		persoonDao.getHibernateTemplate().clear();
		Persoon p1 = persoonDao.getById(testPersoon1.getId());
		Persoon p2 = persoonDao.getById(testPersoon2.getId());
		List<Persoon> testlijst = new ArrayList<Persoon>();
		testlijst.add(p1);
		testlijst.add(p2);
		
		List<Persoon> testlijst2 = new ArrayList<Persoon>();
		testlijst2.add(p2);
	
		assertTrue(persoonDao.getAll().equals(testlijst));
		//assertTrue(persoonDao.getAll("from Persoon where voornaam = 'Leroy'").equals(testlijst2));
	}

}
