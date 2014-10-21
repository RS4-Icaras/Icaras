package nl.rsvier.icaras.dao.cursisttraject;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.cursisttraject.Cursist;
import nl.rsvier.icaras.core.cursisttraject.Traject;
import nl.rsvier.icaras.core.cursisttraject.TrajectEenheid;

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
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager")  
public class TrajectDAOTest {
	
	@Autowired
	private TrajectDAO trajectDAO;
	
	Traject p1;
	Traject p2;

	@Before
	public void setUp() {
		p1 = maakTraject("OCA 7", null, null);
		p2 = maakTraject("Java 1", null, null);
	}
		
	public Traject maakTraject (String trajectNaam, List<TrajectEenheid> eenheden, List<Cursist> cursisten) {
		Traject t = new Traject ();
		t.setCursisten(cursisten);
		t.setTrajectEenheden(eenheden);
		t.setTrajectNaam(trajectNaam);
		return t;
	}
	
	@Test
	@Transactional
	public void testWrite () {
		trajectDAO.saveTraject(p1);
		assertNotNull(p1.getTrajectNaam());
		p2 = trajectDAO.findTrajectByID(p1.getTrajectId());
		assertTrue("attributen vanuit database zijn gelijk aan die van het adres voor save", p2.equals(p1));
	}
	
	@Test
	@Transactional
	public void testGetAllTrajecten() {
		trajectDAO.saveTraject(p1);
		trajectDAO.saveTraject(p2);
		List<Traject> trajectenlijst = trajectDAO.getAllTrajecten();
		assertTrue("zijn er inderdaad 2 objecten geladen uit de database?", trajectenlijst.size() == 2);
		assertTrue("eerste opgeslagen object en eerste opgehaalde object uit de opgehaalde lijst zijn gelijk", trajectenlijst.get(0).equals(p1));
		assertTrue("tweede opgeslagen object en tweede opgehaalde object uit de opgehaalde lijst zijn gelijk", trajectenlijst.get(1).equals(p2));	
	}
	
	@Test
	@Transactional
	public void updateTraject() {
		trajectDAO.saveTraject(p1);
		p1.setTrajectNaam(".NET");
		trajectDAO.updateTraject(p1);
		Traject vergelijkTraject = trajectDAO.findTrajectByID(p1.getTrajectId());
		assertTrue("attributen geupdate gelijk aan attributen ingeladen met dezelfde id", p1.equals(vergelijkTraject));
	}
	
	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void deleteTraject() {
		trajectDAO.saveTraject(p1);
		trajectDAO.deleteTraject(p1);	
		p2.setTrajectNaam("test");
		trajectDAO.deleteTraject(p2);
		p2 = trajectDAO.findTrajectByID(p1.getTrajectId());	
	}

}
