package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Opleiding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:icarasdb-context.xml")
public class OpleidingTest {
	
	@Autowired
	private OpleidingDAO testdao;
	
	@Test
	public void test() {
		Opleiding a = new Opleiding();
		
		
		//Opslaan van de Opleiding.
	   	testdao.persistOpleiding(a);
	   	long ida = a.getId();
		System.out.println("Het id van Opleiding a is: "+ida);
	   	System.out.println("De Opleiding is gepersisteerd!");
	   	
	   	//De Opleiding weer opzoeken.
	   	Opleiding b = testdao.findOpleiding(ida);
	   	System.out.println("Het id van de opgezochte Opleiding is: " +b.getId());
	   	
	   	//De Opleiding aanpassen
	   	//System.out.println("Opleiding de waarde true geven voor isGetekend");
	   	
	   	
	   	//a.setGetekend(true);
	   //	testdao.updateOpleiding(a);
	   	
	   	//Opleiding c =testdao.findOpleiding(ida);
	   	//if(c.isGetekend()!=false){
	   	//	System.out.println("De Opleiding is getekend!");
	   	//}else{
	   	//	System.out.println("De Opleiding is niet getekend!");
	   	//}
	   	
	   	//De Opleiding deleten en testen of die gedelete is.
	   	System.out.println("De Opleiding wordt gedelete.");
	   	testdao.deleteOpleiding(a);
	   	Opleiding d = testdao.findOpleiding(ida);
	   	if(d != null){
	   		System.out.println("De Opleiding is niet verwijderd!");
	   	}else{
	   		System.out.println("De Opleiding is verwijderd!");
	   	}
	}

}
