package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Werkervaringseenheid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:icarasdb-context.xml")
public class WerkervaringseenheidTest {
	
	@Autowired
	private WerkervaringseenheidDAO testdao;
	
	@Test
	public void test() {
		Werkervaringseenheid a = new Werkervaringseenheid();
		
		
		//Opslaan van de Werkervaringseenheid.
	   	testdao.persistWerkervaringseenheid(a);
	   	long ida = a.getId();
		System.out.println("Het id van Werkervaringseenheid a is: "+ida);
	   	System.out.println("De Werkervaringseenheid is gepersisteerd!");
	   	
	   	//De Werkervaringseenheid weer opzoeken.
	   	Werkervaringseenheid b = testdao.findWerkervaringseenheid(ida);
	   	System.out.println("Het id van de opgezochte Werkervaringseenheid is: " +b.getId());
	   	
	   	//De Werkervaringseenheid aanpassen
	   	//System.out.println("Werkervaringseenheid de waarde true geven voor isGetekend");
	   	
	   	
	   	//a.setGetekend(true);
	   //	testdao.updateWerkervaringseenheid(a);
	   	
	   	//Werkervaringseenheid c =testdao.findWerkervaringseenheid(ida);
	   	//if(c.isGetekend()!=false){
	   	//	System.out.println("De Werkervaringseenheid is getekend!");
	   	//}else{
	   	//	System.out.println("De Werkervaringseenheid is niet getekend!");
	   	//}
	   	
	   	//De Werkervaringseenheid deleten en testen of die gedelete is.
	   	System.out.println("De Werkervaringseenheid wordt gedelete.");
	   	testdao.deleteWerkervaringseenheid(a);
	   	Werkervaringseenheid d = testdao.findWerkervaringseenheid(ida);
	   	if(d != null){
	   		System.out.println("De Werkervaringseenheid is niet verwijderd!");
	   	}else{
	   		System.out.println("De Werkervaringseenheid is verwijderd!");
	   	}
	}

}
