package nl.rsvier.icaras.dao.intake;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import nl.rsvier.icaras.core.intake.Bemiddelingsovereenkomst;

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
public class BemiddelingsovereenkomstTest {
	
	@Autowired
	private BemiddelingsovereenkomstDAO testdao;
	
	Bemiddelingsovereenkomst a1;
	Bemiddelingsovereenkomst a2;
			
	@Before
	public void setUp(){
		a1 = new Bemiddelingsovereenkomst();
		a2 = new Bemiddelingsovereenkomst();
		a1.setStartDatum(new Date(2));
		a2.setStartDatum(new Date(2));
		a1.setEindDatum(new Date(2));
		a2.setEindDatum(new Date(2));
	}
	
	@Test
	@Transactional
	public void testSaveEnGet() {
		testdao.persistBemiddelingsovereenkomst(a1);
	   	assertNotNull(a1.getId());
	   	a2 = testdao.findBemiddelingsovereenkomst(a1.getId());
	   	assertTrue("De attributen uit de database zijn gelijk aan die van de save", a2.equals(a1));
	}
	
	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void testDeleteBemiddelingsovereenkomst(){
		testdao.persistBemiddelingsovereenkomst(a1);
		testdao.deleteBemiddelingsovereenkomst(a1);
		a2.setId(3);
		testdao.deleteBemiddelingsovereenkomst(a2);
		a2 = testdao.findBemiddelingsovereenkomst(a1.getId());
	}
	
	@Test
	@Transactional
	public void UpdateBemiddelingsovereenkomst(){
		testdao.persistBemiddelingsovereenkomst(a1);
		a1.setGetekend(true);
		testdao.updateBemiddelingsovereenkomst(a1);
		Bemiddelingsovereenkomst vergelijken = testdao.findBemiddelingsovereenkomst(a1.getId());
		assertTrue("De atributten van de ingeladen en opgeslagen aanmelders zijn gelijk.", a1.equals(vergelijken));
	}
	


}
