package nl.rsvier.icaras.dao.intake;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
public class CVTest {
	
	@Autowired
	private CVDAO testdao;
		
	CV a1;
	CV a2;
	
	@Before
	public void setUp(){
		a1 = new CV();
		a2 = new CV();
		a1.setAanmelder(new Aanmelder());
		a2.setAanmelder(new Aanmelder());

	}
	
	@Test
	@Transactional
	public void testSaveEnGet() {
		testdao.persistCV(a1);
	   	assertNotNull(a1.getId());
	   	a2 = testdao.findCV(a1.getId());
	   	assertTrue("De attributen uit de database zijn gelijk aan die van de save", a2.equals(a1));
	}
	
	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void testDeleteCV(){
		testdao.persistCV(a1);
		testdao.deleteCV(a1);
		a2.setId(3);
		testdao.deleteCV(a2);
		a2 = testdao.findCV(a1.getId());
	}
	
	@Test
	@Transactional
	public void UpdateCV(){
		testdao.persistCV(a1);
		a1.setAanmelder(new Aanmelder());
		testdao.updateCV(a1);
		CV vergelijken = testdao.findCV(a1.getId());
		assertTrue("De atributten van de ingeladen en opgeslagen aanmelders zijn gelijk.", a1.equals(vergelijken));
	}

}
