package nl.rsvier.icaras.dao.cursisttraject;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.cursisttraject.Boek;

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
public class BoekDAOTest {
	
	@Autowired
	private BoekDAO boekDAO;
	
	Boek b;
	Boek b2;
	
	@Before
	public void setUp() {
		b = maakBoek("Henkie", 123467891011l, 3, "Henkies Boek");
		b2 = maakBoek("Paultje", 2354546345l, 1, "Java 1");		
	}
	
	public Boek maakBoek (String auteur, long isbn, int druk, String titel) {
		Boek b = new Boek ();
		b.setAuteur(auteur);
		b.setIsbn(isbn);
		b.setDruk(druk);
		b.setTitel(titel);	
		return b;
	}

	@Test 
	@Transactional
	public void testWrite () {
		boekDAO.saveBoek(b);
		assertNotNull(b.getMateriaal_id());
		b2 = boekDAO.findBoekByID(b.getMateriaal_id());
		assertTrue("attributen vanuit database zijn gelijk aan die van het adres voor save", b2.equals(b));		
	}
	
	@Test
	@Transactional
	public void testGetAllBoeken() {
		boekDAO.saveBoek(b);
		boekDAO.saveBoek(b2);
		List<Boek> boekenlijst = boekDAO.getAllBoeken();
		assertTrue("zijn er inderdaad 2 objecten geladen uit de database?", boekenlijst.size() == 2);
		assertTrue("eerste opgeslagen boek en eerste opgehaalde boek uit de opgehaalde lijst zijn gelijk", boekenlijst.get(0).equals(b));
		assertTrue("tweede opgeslagen boek en tweede opgehaalde boek uit de opgehaalde lijst zijn gelijk", boekenlijst.get(1).equals(b2));
	}
	
	
	@Test
	@Transactional
	public void updateBoek() {
		boekDAO.saveBoek(b);
		b.setAuteur(b2.getAuteur());
		boekDAO.updateBoek(b);
		Boek vergelijkBoek = boekDAO.findBoekByID(b.getMateriaal_id());
		assertTrue("attributen geupdate adres is gelijk aan attributen ingeladen adres met dezelfde id", b.equals(vergelijkBoek));	
	}

	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void deleteBoek() {
		boekDAO.saveBoek(b);
		boekDAO.deleteBoek(b);	
		b2.setAuteur("Jantje");
		boekDAO.deleteBoek(b2);
		b2 = boekDAO.findBoekByID(b.getMateriaal_id());	
	}
}
