package nl.rsvier.icaras.dao.cursisttraject;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.cursisttraject.Examen;
import nl.rsvier.icaras.core.cursisttraject.Resultaat;

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
public class ResultaatDAOTest {
	
	@Autowired
	private ResultaatDAO resultaatDAO;

	Resultaat p1;
	Resultaat p2;
	
	@Before
	public void setUp() {
		p1 = maakResultaat(40, null, null);
		p2 = maakResultaat(80, null, null);
	}
	
	public Resultaat maakResultaat (int cijfer, Examen examen, Date datum) {
		Resultaat r = new Resultaat ();
		r.setCijfer(cijfer);
		r.setExamen(examen);
		r.setToetsDatum(datum);
		return r;
	}
	
	@Test
	@Transactional
	public void testWrite () {
		resultaatDAO.saveResultaat(p1);
		assertNotNull(p1.getCijfer());
		p2 = resultaatDAO.findResultaatByID(p1.getId());
		assertTrue("attributen vanuit database zijn gelijk aan die van het adres voor save", p2.equals(p1));	
	}
	
	@Test
	@Transactional
	public void testGetAllBoeken() {
		resultaatDAO.saveResultaat(p1);
		resultaatDAO.saveResultaat(p2);
		List<Resultaat> resultatenlijst = resultaatDAO.getAllResultaten();
		assertTrue("zijn er inderdaad 2 objecten geladen uit de database?", resultatenlijst.size() == 2);
		assertTrue("eerste opgeslagen object en eerste opgehaalde object uit de opgehaalde lijst zijn gelijk", resultatenlijst.get(0).equals(p1));
		assertTrue("tweede opgeslagen object en tweede opgehaalde object uit de opgehaalde lijst zijn gelijk", resultatenlijst.get(1).equals(p2));
	}
	
	@Test
	@Transactional
	public void updateProeftoets() {
		resultaatDAO.saveResultaat(p1);
		p1.setCijfer(100);
		resultaatDAO.updateResultaat(p1);
		Resultaat vergelijkResultaat = resultaatDAO.findResultaatByID(p1.getId());
		assertTrue("attributen geupdate gelijk aan attributen ingeladen met dezelfde id", p1.equals(vergelijkResultaat));
	}
	
	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void deleteProeftoets() {
		resultaatDAO.saveResultaat(p1);
		resultaatDAO.deleteResultaat(p1);	
		p2.setCijfer(60);
		resultaatDAO.deleteResultaat(p2);
		p2 = resultaatDAO.findResultaatByID(p1.getId());	
	}

}
