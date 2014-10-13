package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Arbeidsovereenkomst;
import nl.rsvier.icaras.core.intake.TMPBedrijf;
import nl.rsvier.icaras.core.intake.Bemiddelingsovereenkomst;
import nl.rsvier.icaras.core.intake.TMPPersoon;
import nl.rsvier.icaras.core.intake.Scholingsovereenkomst;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:icarasdb-context.xml")  
public class OvereenkomstTest {
	
	@Autowired
	private ScholingsovereenkomstDAO scholingsovereenkomstdao;
	@Autowired
	private ArbeidsovereenkomstDAO arbeidsovereenkomstdao;
	@Autowired
	private BemiddelingsovereenkomstDAO bemiddelingsovereenkomstdao;
	
	@Test
	public void test() {
	

	   	Scholingsovereenkomst s = new Scholingsovereenkomst();
	   	s.setGetekend(true);
	   	s.setStandaardOvereenkomst(true);	
	   	scholingsovereenkomstdao.persistScholingsovereenkomst(s);
	   	
	   	
	 		   
	   	Scholingsovereenkomst a = new Scholingsovereenkomst();
	   	a.setGetekend(true);
	   	a.setStandaardOvereenkomst(true);	
	   	scholingsovereenkomstdao.persistScholingsovereenkomst(a);
	   	
	   	
	   			   
	   	Scholingsovereenkomst b = new Scholingsovereenkomst();
	   	b.setGetekend(true);
	   	b.setStandaardOvereenkomst(false);	
	   	scholingsovereenkomstdao.persistScholingsovereenkomst(b);
	   		
	   			   
	   	Arbeidsovereenkomst d = new Arbeidsovereenkomst();
	   	d.setGetekend(false);
	   	d.setStandaardOvereenkomst(true);	
	   	TMPPersoon pi= new TMPPersoon();
	   	TMPBedrijf be = new TMPBedrijf();
	   	d.setOndergetekende(pi);
	   	d.setWerkgever(be);	   	
	   	arbeidsovereenkomstdao.persistArbeidsovereenkomst(d);		   
	   	
			   
	   	Bemiddelingsovereenkomst e = new Bemiddelingsovereenkomst();
	   	e.setStandaardOvereenkomst(true);	
	   	bemiddelingsovereenkomstdao.persistBemiddelingsovereenkomst(e);	
	   	
	 	}
	}


