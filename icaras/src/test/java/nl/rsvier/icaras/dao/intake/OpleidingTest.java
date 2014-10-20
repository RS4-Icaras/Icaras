package nl.rsvier.icaras.dao.intake;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nl.rsvier.icaras.core.intake.Opleiding;
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
public class OpleidingTest {
	
	@Autowired
	private OpleidingDAO testdao;
	
	Opleiding a1;
	Opleiding a2;
			
	@Before
	public void setUp(){
		a1 = new Opleiding();
		a2 = new Opleiding();
	}
	
	@Test
	@Transactional
	public void testSaveEnGet() {
		testdao.persistOpleiding(a1);
	   	assertNotNull(a1.getId());
	   	a2 = testdao.findOpleiding(a1.getId());
	   	assertTrue("De attributen uit de database zijn gelijk aan die van de save", a2.equals(a1));
	}
	
	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void testDeleteOpleiding(){
		testdao.persistOpleiding(a1);
		testdao.deleteOpleiding(a1);
		a2.setId(3);
		testdao.deleteOpleiding(a2);
		a2 = testdao.findOpleiding(a1.getId());
	}
	
	@Test
	@Transactional
	public void UpdateOpleiding(){
		testdao.persistOpleiding(a1);
		a1.setId(3);
		testdao.updateOpleiding(a1);
		Opleiding vergelijken = testdao.findOpleiding(a1.getId());
		assertTrue("De atributten van de ingeladen en opgeslagen aanmelders zijn gelijk.", a1.equals(vergelijken));
	}
}
