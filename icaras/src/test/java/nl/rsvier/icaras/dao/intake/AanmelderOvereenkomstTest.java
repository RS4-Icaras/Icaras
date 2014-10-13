package nl.rsvier.icaras.dao.intake;

import java.util.ArrayList;
import nl.rsvier.icaras.core.intake.Aanmelder;
import nl.rsvier.icaras.core.intake.Bemiddelingsovereenkomst;
import nl.rsvier.icaras.core.intake.Interneovereenkomst;
import nl.rsvier.icaras.core.intake.Scholingsovereenkomst;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:icarasdb-context.xml")  
public class AanmelderOvereenkomstTest {
	
	@Autowired
	private AanmelderDAO aanmelderdao;
	
	@Test
	public void test() {
		Aanmelder a = new Aanmelder();
		
		ArrayList<Interneovereenkomst> interneovereenkomst = new ArrayList<Interneovereenkomst>();
		Scholingsovereenkomst i1 = new Scholingsovereenkomst();
		i1.setGetekend(true);
		i1.setAanmelder(a);
		interneovereenkomst.add(i1);
		Bemiddelingsovereenkomst i2 = new Bemiddelingsovereenkomst();
		i2.setGetekend(false);
		i2.setAanmelder(a);
		interneovereenkomst.add(i2);
		a.setInterneovereenkomsten(interneovereenkomst);
		
	   	aanmelderdao.persistAanmelder(a);
	   	
	}

}
