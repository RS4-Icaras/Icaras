package nl.rsvier.icaras.dao.cursisttraject;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.cursisttraject.Cursist;
import nl.rsvier.icaras.core.cursisttraject.Materiaal;
import nl.rsvier.icaras.core.cursisttraject.Toets;
import nl.rsvier.icaras.core.cursisttraject.Traject;

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
public class CursistDAOTest {
	
	@Autowired
	private CursistDAO cursistDAO;

	Cursist p1;
	Cursist p2;
	
	@Before
	public void setUp() {
		p1 = maakCursist(false, null, null, null);
		p2 = maakCursist(true, null, null, null);
	}
	
	public Cursist maakCursist (boolean isKlaar, Traject traject, 
			List<Materiaal> materiaalLijst, List<Toets> toetsenLijst) {
		Cursist b = new Cursist ();
		b.setKlaar(isKlaar);
		b.setTraject(traject);
		b.setMateriaalLijst(materiaalLijst);
		b.setToetsenLijst(toetsenLijst);
		return b;
	}
	@Test
	@Transactional
	public void testWrite () {
		cursistDAO.saveCursist(p1);
		assertNotNull(p1.getCursistId());
		p2 = cursistDAO.findCursistByID(p1.getCursistId());
		assertTrue("attributen vanuit database zijn gelijk aan die van het adres voor save", p2.equals(p1));	
	}
	
	@Test
	@Transactional
	public void testGetAllCursisten() {
		cursistDAO.saveCursist(p1);
		cursistDAO.saveCursist(p2);
		List<Cursist> cursistenlijst = cursistDAO.getAllCursisten();
		assertTrue("zijn er inderdaad 2 objecten geladen uit de database?", cursistenlijst.size() == 2);
		assertTrue("eerste opgeslagen object en eerste opgehaalde object uit de opgehaalde lijst zijn gelijk", cursistenlijst.get(0).equals(p1));
		assertTrue("tweede opgeslagen object en tweede opgehaalde object uit de opgehaalde lijst zijn gelijk", cursistenlijst.get(1).equals(p2));
	}
	
	@Test
	@Transactional
	public void updateCursist() {
		cursistDAO.saveCursist(p1);
		p1.setKlaar(true);
		cursistDAO.updateCursist(p1);
		Cursist vergelijkCursist = cursistDAO.findCursistByID(p1.getCursistId());
		assertTrue("attributen geupdate gelijk aan attributen ingeladen met dezelfde id", p1.equals(vergelijkCursist));
	}	

	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void deleteCursist() {
		cursistDAO.saveCursist(p1);
		cursistDAO.deleteCursist(p1);	
		p2.setKlaar(false);
		cursistDAO.deleteCursist(p2);
		p2 = cursistDAO.findCursistByID(p1.getCursistId());	
	}
}
