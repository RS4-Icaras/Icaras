package nl.rsvier.icaras.dao.intake;

import java.io.File;
import java.util.ArrayList;

import nl.rsvier.icaras.core.intake.Aanmelder;
import nl.rsvier.icaras.core.intake.CV;
import nl.rsvier.icaras.core.intake.Opleiding;
import nl.rsvier.icaras.core.intake.Werkervaringseenheid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:icarasdb-context.xml")  
public class AanmelderCVTest {
	
	@Autowired
	private CVDAO cvdao;
	@Autowired
	private AanmelderDAO aanmelderdao;
	@Autowired
	private OpleidingDAO opleidingdao;
	@Autowired
	private WerkervaringseenheidDAO werkervaringseenheiddao;
	
	@Test
	public void test() {
		Aanmelder a = new Aanmelder();
		//aanmelderdao.persistAanmelder(a);
		CV cv = new CV();
		Opleiding o1 = new Opleiding();
		Opleiding o2 = new Opleiding();
		File cvdoc = new File("///%USERPROFILE%/Documents/");
		Werkervaringseenheid w1 = new Werkervaringseenheid();
		Werkervaringseenheid w2 = new Werkervaringseenheid();
		ArrayList<Werkervaringseenheid> we = new ArrayList<Werkervaringseenheid>();
		ArrayList<Opleiding> ol = new ArrayList<Opleiding>();
		ol.add(o1);
		ol.add(o2);
		we.add(w1);
		we.add(w2);
		cv.setWerkervaringsEenheden(we);
		cv.setOpleidingen(ol);
		a.setcV(cv);
		cv.setAanmelder(a);
		o1.setCv(cv);
		o2.setCv(cv);
		w1.setCv(cv);
		w2.setCv(cv);
		cv.setCvDocument(cvdoc);
		
		aanmelderdao.persistAanmelder(a);
		cvdao.persistCV(cv);
		opleidingdao.persistOpleiding(o1);
		opleidingdao.persistOpleiding(o1);
		werkervaringseenheiddao.persistWerkervaringseenheid(w1);
		werkervaringseenheiddao.persistWerkervaringseenheid(w2);
	}

}
