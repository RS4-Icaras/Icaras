package nl.rsvier.icaras.dao.relatiebeheer;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.relatiebeheer.Email;
import nl.rsvier.icaras.core.relatiebeheer.Facebook;
import nl.rsvier.icaras.core.relatiebeheer.Nfa;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:icarastestdb-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback=true)
public class NfaDaoHibernateTest {

	@Autowired //via @ContextConfiguration wordt deze testklasse aangemeld bij de ApplicationContext
	private NfaDaoHibernate nfaDaoHibernate;
	
	Email testNfa1;
	Nfa testNfa2;
	
	
	@Before
	public void setUp(){
		testNfa1 = new Email();
		testNfa1.setNfaAdres("blabla@blabla.bla");
		testNfa1.setExtraInfo("Deze infomatie is van grote toegevoegde waarde");
		testNfa2 = new Facebook();
		testNfa2.setNfaAdres("Mientje99_onzin");
		testNfa2.setExtraInfo("Mientje is de beste");
	}
	@Test
	@Transactional
	public void testSaveEnGet() {
		nfaDaoHibernate.save(testNfa1);
		assertNotNull(testNfa1.getId());
		nfaDaoHibernate.getHibernateTemplate().evict(testNfa1);
		testNfa2 = nfaDaoHibernate.getById(testNfa1.getId());
		assertTrue("attributen vanuit database zijn gelijk aan die van het adres voor save", testNfa1.equals(testNfa2));
	}
	@Test
	@Transactional
	public void testGetAll(){
		nfaDaoHibernate.save(testNfa1);
		nfaDaoHibernate.save(testNfa2);
		nfaDaoHibernate.getHibernateTemplate().clear();
		List<Nfa> nfaLijst = nfaDaoHibernate.getAll();
		assertTrue("Zijn er inderdaad 2 objecten geladen uit de database?", nfaLijst.size()==2);
		//toekennen van juiste nfa aan juiste vergelijkNfa (om ordening van nfaLijst irrelevant te maken)
		Nfa vergelijkNfa1 = null, vergelijkNfa2 = null;
		{for (Nfa n : nfaLijst){ if (n.getId()==testNfa1.getId()) vergelijkNfa1 = n;}};
		{for (Nfa n : nfaLijst){ if (n.getId()==testNfa2.getId()) vergelijkNfa2 = n;}};
		assertTrue("eerste opgeslagen nfa en nfa uit de opgehaalde lijst met dezelfde id zijn gelijk", testNfa1.equals(vergelijkNfa1));
		assertTrue("tweede opgeslagen nfa en nfa uit de opgehaalde lijst met dezelfde id zijn gelijk", testNfa2.equals(vergelijkNfa2));
	}
	
	@Test
	@Transactional
	public void testDelete(){
		nfaDaoHibernate.save(testNfa1);
		nfaDaoHibernate.delete(testNfa1);
		nfaDaoHibernate.getHibernateTemplate().flush();
		nfaDaoHibernate.getHibernateTemplate().evict(testNfa1);
		testNfa2 = nfaDaoHibernate.getById(testNfa1.getId());
		assertNull("verwijderde nfa opvragen uit database geeft null", testNfa2);
	}	
	
	@Test
	@Transactional
	public void testUpdate(){
		nfaDaoHibernate.save(testNfa1);
		testNfa1.setExtraInfo(testNfa2.getExtraInfo());
		nfaDaoHibernate.update(testNfa1);
		nfaDaoHibernate.getHibernateTemplate().flush();
		nfaDaoHibernate.getHibernateTemplate().evict(testNfa1);
		Nfa vergelijkNfa = nfaDaoHibernate.getById(testNfa1.getId());
		assertTrue("attributen geupdate adres is gelijk aan attributen ingeladen adres met dezelfde id", testNfa1.equals(vergelijkNfa));
	}
}
