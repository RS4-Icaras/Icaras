package nl.rsvier.icaras.dao.intake;

import java.io.File;

import nl.rsvier.icaras.core.intake.Aanmelder;
import nl.rsvier.icaras.core.intake.CV;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:icarasdb-context.xml")
public class CVTest {
	
	@Autowired
	private CVDAO testdao;
	@Autowired
	private AanmelderDAO aanmelderdao;
	
	@Test
	public void test() {
		CV a = new CV();
		Aanmelder q = new Aanmelder();
		//q.setcV(a);
		a.setAanmelder(q);
		aanmelderdao.persistAanmelder(q);
		
		//Opslaan van de CV.
	   	testdao.persistCV(a);
	   	long ida = a.getId();
		System.out.println("Het id van CV a is: "+ida);
	   	System.out.println("De CV is gepersisteerd!");
	   	
	   	//De CV weer opzoeken.
	   	CV b = testdao.findCV(ida);
	   	System.out.println("Het id van de opgezochte CV is: " +b.getId());
	   	
	   	//De CV aanpassen door een bestand toe te voegen.
	   	System.out.println("CV een bestand geven");
	   	a.setCvDocument(null);
	   	
	   
	   	testdao.updateCV(a);
	   	
	   	CV c =testdao.findCV(ida);
	   	if(c.getCvDocument()!=null) {
	   		System.out.println("De CV heeft een bestand!");
	   	}else{
	   		System.out.println("De CV heeft geen bestand!");
	   	}
	   	
	   	//De CV deleten en testen of die gedelete is.
	   	System.out.println("De CV wordt gedelete.");
	   	testdao.deleteCV(a);
	   	CV d = testdao.findCV(ida);
	   	if(d != null){
	   		System.out.println("De CV is niet verwijderd!");
	   	}else{
	   		System.out.println("De CV is verwijderd!");
	   	}
	}

}
