package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Aanmelder;
import nl.rsvier.icaras.core.intake.CV;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:icarasdb-context.xml")
public class AanmelderTest {
	
	@Autowired
	private AanmelderDAO aanmelderdao;
	
	@Test
	public void test() {
		Aanmelder a = new Aanmelder();
		
		
		//Opslaan van de Aanmelder.
	   	aanmelderdao.persistAanmelder(a);
	   	long ida = a.getId();
		System.out.println("Het id van Aanmelder a is: "+ida);
	   	System.out.println("De Aanmelder is gepersisteerd!");
	   	
	   	//De Aanmelder weer opzoeken.
	   	Aanmelder b = aanmelderdao.findAanmelder(ida);
	   	System.out.println("Het id van de opgezochte Aanmelder is: " +b.getId());
	   	
	   	//De aanmelder aanpassen
	   	System.out.println("Een cv toevoegen aan Aanmelder");
	   	CV cv = new CV();
	   	cv.setAanmelder(a);
	   	a.setcV(cv);
	   	aanmelderdao.updateAanmelder(a);
	   	
	   	Aanmelder c =aanmelderdao.findAanmelder(ida);
	   	if(c.getcV()!=null){
	   		System.out.println("De aanmelder heeft een cv!");
	   	}else{
	   		System.out.println("De aanmelder heeft geen cv!");
	   	}
	   	
	   	//De aanmelder deleten en testen of die gedelete is.
	   	System.out.println("De Aanmelder wordt gedelete.");
	   	aanmelderdao.deleteAanmelder(a);
	   	Aanmelder d = aanmelderdao.findAanmelder(ida);
	   	if(d != null){
	   		System.out.println("De Aanmelder is niet verwijderd!");
	   	}else{
	   		System.out.println("De Aanmelder is verwijderd!");
	   	}
	   	
	   	
	}

}
