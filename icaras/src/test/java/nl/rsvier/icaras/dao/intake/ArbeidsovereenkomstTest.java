package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Arbeidsovereenkomst;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:icarasdb-context.xml")
public class ArbeidsovereenkomstTest {
	
	@Autowired
	private ArbeidsovereenkomstDAO testdao;
	
	@Test
	public void test() {
		Arbeidsovereenkomst a = new Arbeidsovereenkomst();
		
		
		//Opslaan van de Arbeidsovereenkomst.
	   	testdao.persistArbeidsovereenkomst(a);
	   	long ida = a.getId();
		System.out.println("Het id van Arbeidsovereenkomst a is: "+ida);
	   	System.out.println("De Arbeidsovereenkomst is gepersisteerd!");
	   	
	   	//De Arbeidsovereenkomst weer opzoeken.
	   	Arbeidsovereenkomst b = testdao.findArbeidsovereenkomst(ida);
	   	System.out.println("Het id van de opgezochte Arbeidsovereenkomst is: " +b.getId());
	   	
	   	//De Arbeidsovereenkomst aanpassen
	   	System.out.println("Arbeidsovereenkomst de waarde true geven voor isGetekend");
	   	
	   	
	   	a.setGetekend(true);
	   	testdao.updateArbeidsovereenkomst(a);
	   	
	   	Arbeidsovereenkomst c =testdao.findArbeidsovereenkomst(ida);
	   	if(c.isGetekend()!=false){
	   		System.out.println("De Arbeidsovereenkomst is getekend!");
	   	}else{
	   		System.out.println("De Arbeidsovereenkomst is niet getekend!");
	   	}
	   	
	   	//De Arbeidsovereenkomst deleten en testen of die gedelete is.
	   	System.out.println("De Arbeidsovereenkomst wordt gedelete.");
	   	testdao.deleteArbeidsovereenkomst(a);
	   	Arbeidsovereenkomst d = testdao.findArbeidsovereenkomst(ida);
	   	if(d != null){
	   		System.out.println("De Arbeidsovereenkomst is niet verwijderd!");
	   	}else{
	   		System.out.println("De Arbeidsovereenkomst is verwijderd!");
	   	}
	}

}
