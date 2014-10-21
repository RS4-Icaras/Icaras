package nl.rsvier.icaras.dao.cursisttraject;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.cursisttraject.Materiaal;
import nl.rsvier.icaras.core.cursisttraject.Toets;
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
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true) 
public class TrajectEenheidDAOTest {
	
	@Autowired
	private TrajectEenheidDAO trajectEenheidDAO;

	TrajectEenheid p1;
	TrajectEenheid p2;
	
	@Before
	public void setUp() {
		p1 = maakTrajectEenheid("Java", "leren programmeren in java", null, null, null);
		p2 = maakTrajectEenheid(".NET", "leren programmeren in .NET", null, null, null);
	}
	
	public TrajectEenheid maakTrajectEenheid (String naam, String omschrijving, Toets toets, List<Materiaal> materiaalLijst,
			List<Traject> trajecten) {
		TrajectEenheid t = new TrajectEenheid ();
		t.setTrajectEenheidNaam(naam);
		t.setTrajectEenheidOmschrijving(omschrijving);
		t.setTrajectEenheidToets(toets);
		t.setTrajectMateriaalLijst(materiaalLijst);
		t.setTrajecten(trajecten);
		return t;
	}
	
	@Test
	@Transactional
	public void testWrite () {
		trajectEenheidDAO.saveTrajectEenheid(p1);
		assertNotNull(p1.getTrajectEenheidNaam());
		p2 = trajectEenheidDAO.findTrajectEenheidByID(p1.getTrajectId());
		assertTrue("attributen vanuit database zijn gelijk aan die van het adres voor save", p2.equals(p1));	
	}
	
	@Test
	@Transactional
	public void testGetAllTrajectEenheden() {
		trajectEenheidDAO.saveTrajectEenheid(p1);
		trajectEenheidDAO.saveTrajectEenheid(p2);
		List<TrajectEenheid> trajectEenhedenlijst = trajectEenheidDAO.getAllTrajectEenheden();
		assertTrue("zijn er inderdaad 2 objecten geladen uit de database?", trajectEenhedenlijst.size() == 2);
		assertTrue("eerste opgeslagen object en eerste opgehaalde object uit de opgehaalde lijst zijn gelijk", trajectEenhedenlijst.get(0).equals(p1));
		assertTrue("tweede opgeslagen object en tweede opgehaalde object uit de opgehaalde lijst zijn gelijk", trajectEenhedenlijst.get(1).equals(p2));
	}
	
	@Test
	@Transactional
	public void updateTrajectEenheid() {
		trajectEenheidDAO.saveTrajectEenheid(p1);
		p1.setTrajectEenheidNaam("OCA");
		trajectEenheidDAO.updateTrajectEenheid(p1);
		TrajectEenheid vergelijkTrajectEenheid = trajectEenheidDAO.findTrajectEenheidByID(p1.getTrajectId());
		assertTrue("attributen geupdate gelijk aan attributen ingeladen met dezelfde id", p1.equals(vergelijkTrajectEenheid));
	}
	
	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void deleteTrajectEenheid() {
		trajectEenheidDAO.saveTrajectEenheid(p1);
		trajectEenheidDAO.deleteTrajectEenheid(p1);	
		p2.setTrajectEenheidNaam("test");
		trajectEenheidDAO.deleteTrajectEenheid(p2);
		p2 = trajectEenheidDAO.findTrajectEenheidByID(p1.getTrajectId());	
	}

}
