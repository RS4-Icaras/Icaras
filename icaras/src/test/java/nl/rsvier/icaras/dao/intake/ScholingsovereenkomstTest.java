package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Scholingsovereenkomst;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:icarasdb-context.xml")
public class ScholingsovereenkomstTest {
	
	@Autowired
	private ScholingsovereenkomstDAO testdao;
	
	@Test
	public void test() {
		Scholingsovereenkomst a = new Scholingsovereenkomst();
		
		
		//Opslaan van de Scholingsovereenkomst.
	   	testdao.persistScholingsovereenkomst(a);
	   	long ida = a.getId();
		System.out.println("Het id van Scholingsovereenkomst a is: "+ida);
	   	System.out.println("De Scholingsovereenkomst is gepersisteerd!");
	   	
	   	//De Scholingsovereenkomst weer opzoeken.
	   	Scholingsovereenkomst b = testdao.findScholingsovereenkomst(ida);
	   	System.out.println("Het id van de opgezochte Scholingsovereenkomst is: " +b.getId());
	   	
	   	//De Scholingsovereenkomst aanpassen
	   	System.out.println("Scholingsovereenkomst de waarde true geven voor isGetekend");
	   	
	   	
	   	a.setGetekend(true);
	   	testdao.updateScholingsovereenkomst(a);
	   	
	   	Scholingsovereenkomst c =testdao.findScholingsovereenkomst(ida);
	   	if(c.isGetekend()!=false){
	   		System.out.println("De Scholingsovereenkomst is getekend!");
	   	}else{
	   		System.out.println("De Scholingsovereenkomst is niet getekend!");
	   	}
	   	
	   	//De Scholingsovereenkomst deleten en testen of die gedelete is.
	   	System.out.println("De Scholingsovereenkomst wordt gedelete.");
	   	testdao.deleteScholingsovereenkomst(a);
	   	Scholingsovereenkomst d = testdao.findScholingsovereenkomst(ida);
	   	if(d != null){
	   		System.out.println("De Scholingsovereenkomst is niet verwijderd!");
	   	}else{
	   		System.out.println("De Scholingsovereenkomst is verwijderd!");
	   	}
	}

}
