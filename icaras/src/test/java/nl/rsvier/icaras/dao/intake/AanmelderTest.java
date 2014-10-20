package nl.rsvier.icaras.dao.intake;

import static org.junit.Assert.*;
import nl.rsvier.icaras.core.intake.Aanmelder;
import nl.rsvier.icaras.core.intake.CV;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:icarasdb-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback=true)
public class AanmelderTest {
	
	@Autowired
	private AanmelderDAO aanmelderdao;
	
	Aanmelder a1;
	Aanmelder a2;
	
	@Before
	public void setUp(){
		a1 = new Aanmelder();
		a2 = new Aanmelder();
	}
	
	@Test
	@Transactional
	public void testSaveEnGet() {
		
		aanmelderdao.persistAanmelder(a1);
	   	assertNotNull(a1.getId());
	   	a2 = aanmelderdao.findAanmelder(a1.getId());
	   	assertTrue("De attributen uit de database zijn gelijk aan die van de save", a2.equals(a1));
	}
	
	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void testDeleteAanmelder(){
		aanmelderdao.persistAanmelder(a1);
		aanmelderdao.deleteAanmelder(a1);
		a2.setId(3);
		aanmelderdao.deleteAanmelder(a2);
		a2 = aanmelderdao.findAanmelder(a1.getId());
	}
	
	@Test
	@Transactional
	public void UpdateAanmelder(){
		aanmelderdao.persistAanmelder(a1);
		//TODO Vervangen voor het veranderen van een ander atribuut van Aanmelder.
		a1.setId(1);
		//a2.setId(2);
		aanmelderdao.updateAanmelder(a1);
		Aanmelder vergelijken = aanmelderdao.findAanmelder(a1.getId());
		assertTrue("De atributten van de ingeladen en opgeslagen aanmelders zijn gelijk.", a1.equals(vergelijken));
	}
	
	

}
