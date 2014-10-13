package nl.rsvier.icaras.core.relatiebeheer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class AdresTest {
	Adres standaardAdres;
	Adres gelijkAanStandaardAdres;
	Adres metNullAttribuut;
	Adres metEenAfwijkendAttribuut;
	
	@Before
	public void initialiseerAdressen(){
		standaardAdres = new Adres();
		standaardAdres.setAdresId(1);
		standaardAdres.setHuisOfPostbusNummer("123A");
		standaardAdres.setIsPostbus(false);
		standaardAdres.setPlaats("Hilversum");
		standaardAdres.setPostcode("1234AB");
		standaardAdres.setStraat("Larenseweg");
		gelijkAanStandaardAdres = new Adres();
		gelijkAanStandaardAdres.setAdresId(1);
		gelijkAanStandaardAdres.setHuisOfPostbusNummer("123A");
		gelijkAanStandaardAdres.setIsPostbus(false);
		gelijkAanStandaardAdres.setPlaats("Hilversum");
		gelijkAanStandaardAdres.setPostcode("1234AB");
		gelijkAanStandaardAdres.setStraat("Larenseweg");
		metNullAttribuut = new Adres();
		metNullAttribuut.setAdresId(1);
		metNullAttribuut.setHuisOfPostbusNummer("123A");
		metNullAttribuut.setIsPostbus(null);
		metNullAttribuut.setPlaats("Hilversum");
		metNullAttribuut.setPostcode("1234AB");
		metNullAttribuut.setStraat("Larenseweg");
		metEenAfwijkendAttribuut = new Adres();
		metEenAfwijkendAttribuut.setAdresId(1);
		metEenAfwijkendAttribuut.setHuisOfPostbusNummer("123");
		metEenAfwijkendAttribuut.setIsPostbus(false);
		metEenAfwijkendAttribuut.setPlaats("Hilversum");
		metEenAfwijkendAttribuut.setPostcode("1234AB");
		metEenAfwijkendAttribuut.setStraat("Larenseweg");
	}
	
	@Test
	public void testEquals(){
		assertTrue("Vergeleken met zichzelf", standaardAdres.equals(standaardAdres));
		assertTrue("Vergeleken met adres met dezelfde attributen", standaardAdres.equals(gelijkAanStandaardAdres));
		assertFalse("Vergeleken met ander soort object", standaardAdres.equals("ander Object"));
		assertFalse("isPostbus is null", standaardAdres.equals(metNullAttribuut));
		assertFalse("Vergeleken met huisnummer zonder toevoeging A", standaardAdres.equals(metEenAfwijkendAttribuut));
	}

}
