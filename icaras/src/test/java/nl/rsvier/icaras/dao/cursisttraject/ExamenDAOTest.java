package nl.rsvier.icaras.dao.cursisttraject;

import java.util.List;

import static org.junit.Assert.*;

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
public class ExamenDAOTest {
	
	@Autowired 
	private ExamenDAO examenDAO;
	
	Examen p1;
	Examen p2;
	
	@Before
	public void setUp() {
		p1 = maakExamen(true, 55, null, "OCA 7", false);
		p2 = maakExamen(true, 55, null, "JAVA 1", true);
	}
	
	public Examen maakExamen (boolean extern, int norm, List<Resultaat> resultaten, String beschrijving, boolean volbracht) {
		Examen b = new Examen ();
		b.setExtern(extern);
		b.setToetsNorm(norm);
		b.setToetsResultaten(null);
		b.setTentamenBeschrijving(beschrijving);
		b.setVolbracht(volbracht);
		return b;
	}

	@Test
	@Transactional
	public void testWrite () {
		examenDAO.saveExamen(p1);
		assertNotNull(p1.getTentamenBeschrijving());
		p2 = examenDAO.findExamenByID(p1.getToetsId());
		assertTrue("attributen vanuit database zijn gelijk aan die van het adres voor save", p2.equals(p1));			
	}
	
	@Test
	@Transactional
	public void testGetAllExamens() {
		examenDAO.saveExamen(p1);
		examenDAO.saveExamen(p2);
		List<Examen> examenlijst = examenDAO.getAllExamens();
		assertTrue("zijn er inderdaad 2 objecten geladen uit de database?", examenlijst.size() == 2);
		assertTrue("eerste opgeslagen object en eerste opgehaalde object uit de opgehaalde lijst zijn gelijk", examenlijst.get(0).equals(p1));
		assertTrue("tweede opgeslagen object en tweede opgehaalde object uit de opgehaalde lijst zijn gelijk", examenlijst.get(1).equals(p2));
	}
	
	@Test
	@Transactional
	public void updateExamen() {
		examenDAO.saveExamen(p1);
		p1.setTentamenBeschrijving("wiskunde");
		examenDAO.updateExamen(p1);
		Examen vergelijkExamen = examenDAO.findExamenByID(p1.getToetsId());
		assertTrue("attributen geupdate gelijk aan attributen ingeladen met dezelfde id", p1.equals(vergelijkExamen));
	}
	
	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void deleteExamen() {
		examenDAO.saveExamen(p1);
		examenDAO.deleteExamen(p1);	
		p2.setToetsNorm(75);
		examenDAO.deleteExamen(p2);
		p2 = examenDAO.findExamenByID(p1.getToetsId());	
	}
}
