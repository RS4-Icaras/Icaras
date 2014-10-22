package nl.rsvier.icaras.core.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class NfaTest {

	Nfa standaardNfa;
	Nfa gelijkAanStandaardNfa;
	Nfa metNullAttribuut;
	Nfa metAfwijkendAttribuut;
	Nfa vanAnderType;
	
	@Before
	public void setUp(){
		standaardNfa = new Email();
		standaardNfa.setNfaAdres("blabla@blabla.bla");
		standaardNfa.setExtraInfo("Dan weet je dat ook");
		gelijkAanStandaardNfa = new Email();
		gelijkAanStandaardNfa.setNfaAdres("blabla@blabla.bla");
		gelijkAanStandaardNfa.setExtraInfo("Dan weet je dat ook");
		metNullAttribuut = new Email();
		metNullAttribuut.setNfaAdres("blabla@blabla.bla");
		metNullAttribuut.setExtraInfo(null);
		metAfwijkendAttribuut = new Email();
		metAfwijkendAttribuut.setNfaAdres("email@adres.extensie");
		metAfwijkendAttribuut.setExtraInfo("Dan weet je dat ook");
		vanAnderType = new Twitter();
		vanAnderType.setNfaAdres("blabla@blabla.bla");
		vanAnderType.setExtraInfo("Dan weet je dat ook");		
	}
	
	@Test
	public void testEquals(){
		assertTrue("Vergeleken met zichzelf", standaardNfa.equals(standaardNfa));
		assertTrue("Vergeleken met adres met dezelfde attributen", standaardNfa.equals(gelijkAanStandaardNfa));
		assertFalse("Vergeleken met ander soort object", standaardNfa.equals("ander Object"));
		assertFalse("extraInfo is null", standaardNfa.equals(metNullAttribuut));
		assertFalse("Vergeleken met andere tekst bij adres", standaardNfa.equals(metAfwijkendAttribuut));
		assertFalse("Vergeleken met Twitter instantie met dezelfde waarden", standaardNfa.equals(vanAnderType));
	}
}
