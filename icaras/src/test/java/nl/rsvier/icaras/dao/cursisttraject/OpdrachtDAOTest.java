package nl.rsvier.icaras.dao.cursisttraject;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.cursisttraject.Cursist;
import nl.rsvier.icaras.core.cursisttraject.Opdracht;
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
public class OpdrachtDAOTest {

	
	@Autowired
	private OpdrachtDAO  opdrachtDAO;
	
	Opdracht p1;
	Opdracht p2;
	
	@Before
	public void setUp() {
		p1 = maakOpdracht(null, null, null, "opdracht 1", null, true);
		p2 = maakOpdracht(null, null, null, "Icaras", null, false);
	}
	
	public Opdracht maakOpdracht (Cursist cursist, Date eindDatum, Date startDatum, String beschrijving, 
			TrajectEenheid trajectEenheid, boolean volbracht) {
		Opdracht b = new Opdracht ();
		b.setCursist(cursist);
		b.setEindDatum(eindDatum);
		b.setStartDatum(startDatum);
		b.setTentamenBeschrijving(beschrijving);
		b.setTrajectEenheid(trajectEenheid);
		b.setVolbracht(volbracht);
		return b;
	}
	@Test
	@Transactional
	public void testWrite () {
		opdrachtDAO.saveOpdracht(p1);
		assertNotNull(p1.getTentamenBeschrijving());
		p2 = opdrachtDAO.findOpdrachtByID(p1.getToetsId());
		assertNotNull(p2.getTentamenBeschrijving());
		assertTrue("attributen vanuit database zijn gelijk aan die van het adres voor save", p2.equals(p1));	
	}
	
	@Test
	@Transactional
	public void testGetAllOpdrachten() {
		opdrachtDAO.saveOpdracht(p1);
		opdrachtDAO.saveOpdracht(p2);
		List<Opdracht> opdrachtenlijst = opdrachtDAO.getAllOpdrachten();
		assertTrue("zijn er inderdaad 2 objecten geladen uit de database?", opdrachtenlijst.size() == 2);
		assertTrue("eerste opgeslagen object en eerste opgehaalde object uit de opgehaalde lijst zijn gelijk", opdrachtenlijst.get(0).equals(p1));
		assertTrue("tweede opgeslagen object en tweede opgehaalde object uit de opgehaalde lijst zijn gelijk", opdrachtenlijst.get(1).equals(p2));
	}
	
	
	@Test
	@Transactional
	public void updateOpdracht() {
		opdrachtDAO.saveOpdracht(p1);
		p1.setTentamenBeschrijving("Opdracht OCA");
		opdrachtDAO.updateOpdracht(p1);
		Opdracht vergelijkOpdracht = opdrachtDAO.findOpdrachtByID(p1.getToetsId());
		assertTrue("attributen geupdate gelijk aan attributen ingeladen met dezelfde id", p1.equals(vergelijkOpdracht));
	}

	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void deleteOpdracht() {
		opdrachtDAO.saveOpdracht(p1);
		opdrachtDAO.deleteOpdracht(p1);	
		p2.setTentamenBeschrijving("2e opdracht");
		opdrachtDAO.deleteOpdracht(p2);
		p2 = opdrachtDAO.findOpdrachtByID(p1.getToetsId());	
	}
 
}