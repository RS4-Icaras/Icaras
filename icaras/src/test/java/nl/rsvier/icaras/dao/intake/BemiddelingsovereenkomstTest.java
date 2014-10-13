package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Bemiddelingsovereenkomst;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:icarasdb-context.xml")
public class BemiddelingsovereenkomstTest {
	
	@Autowired
	private BemiddelingsovereenkomstDAO testdao;
	
	@Test
	public void test() {
		Bemiddelingsovereenkomst a = new Bemiddelingsovereenkomst();
		
		
		//Opslaan van de Bemiddelingsovereenkomst.
	   	testdao.persistBemiddelingsovereenkomst(a);
	   	long ida = a.getId();
		System.out.println("Het id van Bemiddelingsovereenkomst a is: "+ida);
	   	System.out.println("De Bemiddelingsovereenkomst is gepersisteerd!");
	   	
	   	//De Bemiddelingsovereenkomst weer opzoeken.
	   	Bemiddelingsovereenkomst b = testdao.findBemiddelingsovereenkomst(ida);
	   	System.out.println("Het id van de opgezochte Bemiddelingsovereenkomst is: " +b.getId());
	   	
	   	//De Bemiddelingsovereenkomst aanpassen
	   	System.out.println("Bemiddelingsovereenkomst de waarde true geven voor isGetekend");
	   	
	   	
	   	a.setGetekend(true);
	   	testdao.updateBemiddelingsovereenkomst(a);
	   	
	   	Bemiddelingsovereenkomst c =testdao.findBemiddelingsovereenkomst(ida);
	   	if(c.isGetekend()!=false){
	   		System.out.println("De Bemiddelingsovereenkomst is getekend!");
	   	}else{
	   		System.out.println("De Bemiddelingsovereenkomst is niet getekend!");
	   	}
	   	
	   	//De Bemiddelingsovereenkomst deleten en testen of die gedelete is.
	   	System.out.println("De Bemiddelingsovereenkomst wordt gedelete.");
	   	testdao.deleteBemiddelingsovereenkomst(a);
	   	Bemiddelingsovereenkomst d = testdao.findBemiddelingsovereenkomst(ida);
	   	if(d != null){
	   		System.out.println("De Bemiddelingsovereenkomst is niet verwijderd!");
	   	}else{
	   		System.out.println("De Bemiddelingsovereenkomst is verwijderd!");
	   	}
	}

}
