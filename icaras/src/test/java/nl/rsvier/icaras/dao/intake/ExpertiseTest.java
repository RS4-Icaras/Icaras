package nl.rsvier.icaras.dao.intake;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nl.rsvier.icaras.core.cursisttraject.Cursist;
import nl.rsvier.icaras.core.intake.Aanmelder;
import nl.rsvier.icaras.core.intake.Expertise;

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
public class ExpertiseTest {
	
	@Autowired
	private ExpertiseDAO testdao;
	
	Expertise a1;
	Expertise a2;
			
	@Before
	public void setUp(){
		a1 = new Expertise();
		a2 = new Expertise();
		//Cursist c1 = new Cursist();
		//Cursist c2 = new Cursist();
		
	}
	
	@Test
	@Transactional
	public void testSaveEnGet() {
		testdao.persistExpertise(a1);
	   	assertNotNull(a1.getId());
	   	a2 = testdao.findExpertise(a1.getId());
	   	assertTrue("De attributen uit de database zijn gelijk aan die van de save", a2.equals(a1));
	}
	
	
	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void testDeleteExpertise(){
		testdao.persistExpertise(a1);
		testdao.deleteExpertise(a1);
		a2.setId(3);
		testdao.deleteExpertise(a2);
		a2 = testdao.findExpertise(a1.getId());
	}
	
	@Test
	@Transactional
	public void UpdateExpertise(){
		testdao.persistExpertise(a1);
		a1.setExpertise("JAVA");
		testdao.updateExpertise(a1);
		Expertise vergelijken = testdao.findExpertise(a1.getId());
		assertTrue("De atributten van de ingeladen en opgeslagen aanmelders zijn gelijk.", a1.equals(vergelijken));
	}
}
