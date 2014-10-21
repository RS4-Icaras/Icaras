package nl.rsvier.icaras.dao.cursisttraject;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.cursisttraject.Proeftoets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:icarasdb-context.xml")  
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)  
public class ProeftoetsDAOTest {
	
	@Autowired
	private ProeftoetsDAO proeftoetsDAO;
	
	Proeftoets p1;
	Proeftoets p2;
	
	@Before
	public void setUp() {
		p1 = maakProeftoets("OCA 7", null, false, 40, false);
		p2 = maakProeftoets("OCA 7", null, true, 80, true);
	}
	
	public Proeftoets maakProeftoets (String beschrijving, Date datum, boolean inBezit, int score, boolean isVoldoende) {
		Proeftoets p = new Proeftoets ();
		p.setBeschrijving(beschrijving);
		p.setDatum(datum);
		p.setInBezit(inBezit);
		p.setScore(score);
		p.setVoldoende(isVoldoende);
		return p;
	}
	
	@Test
	@Transactional
	public void testWrite () {
		proeftoetsDAO.saveProeftoets(p1);
		assertNotNull(p1.getBeschrijving());
		p2 = proeftoetsDAO.findProeftoetsByID(p1.getMateriaal_id());
		assertTrue("attributen vanuit database zijn gelijk aan die van het adres voor save", p2.equals(p1));
	}
	
	@Test
	@Transactional
	public void testGetAllProeftoetsen() {
		proeftoetsDAO.saveProeftoets(p1);
		proeftoetsDAO.saveProeftoets(p2);
		List<Proeftoets> proeftoetsenlijst = proeftoetsDAO.getAllProeftoetsen();
		assertTrue("zijn er inderdaad 2 objecten geladen uit de database?", proeftoetsenlijst.size() == 2);
		assertTrue("eerste opgeslagen object en eerste opgehaalde object uit de opgehaalde lijst zijn gelijk", proeftoetsenlijst.get(0).equals(p1));
		assertTrue("tweede opgeslagen object en tweede opgehaalde object uit de opgehaalde lijst zijn gelijk", proeftoetsenlijst.get(1).equals(p2));
	}
	
	@Test
	@Transactional
	public void updateProeftoets() {
		proeftoetsDAO.saveProeftoets(p1);
		p1.setBeschrijving("Wiskunde");
		proeftoetsDAO.updateProeftoets(p1);
		Proeftoets vergelijkProeftoets = proeftoetsDAO.findProeftoetsByID(p1.getMateriaal_id());
		assertTrue("attributen geupdate gelijk aan attributen ingeladen met dezelfde id", p1.equals(vergelijkProeftoets));
	}
	
	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void deleteProeftoets() {
		proeftoetsDAO.saveProeftoets(p1);
		proeftoetsDAO.deleteProeftoets(p1);	
		p2.setBeschrijving("natuurkunde");
		proeftoetsDAO.deleteProeftoets(p2);
		p2 = proeftoetsDAO.findProeftoetsByID(p1.getMateriaal_id());	
	}
}