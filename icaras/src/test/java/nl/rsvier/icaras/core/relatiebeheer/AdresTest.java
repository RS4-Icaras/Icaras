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
		standaardAdres.setHuisOfPostbusNummer("123A");
		standaardAdres.maakStraat();
		standaardAdres.setPlaats("Hilversum");
		standaardAdres.setPostcode("1234AB");
		standaardAdres.setStraat("Larenseweg");
		gelijkAanStandaardAdres = new Adres();
		gelijkAanStandaardAdres.setHuisOfPostbusNummer("123A");
		gelijkAanStandaardAdres.maakStraat();
		gelijkAanStandaardAdres.setPlaats("Hilversum");
		gelijkAanStandaardAdres.setPostcode("1234AB");
		gelijkAanStandaardAdres.setStraat("Larenseweg");
		metNullAttribuut = new Adres();
		metNullAttribuut.setHuisOfPostbusNummer("123A");
		metNullAttribuut.maakStraat();
		metNullAttribuut.setPlaats(null);
		metNullAttribuut.setPostcode("1234AB");
		metNullAttribuut.setStraat("Larenseweg");
		metEenAfwijkendAttribuut = new Adres();
		metEenAfwijkendAttribuut.setHuisOfPostbusNummer("123A");
		metEenAfwijkendAttribuut.maakPostbus();
		metEenAfwijkendAttribuut.setPlaats("Hilversum");
		metEenAfwijkendAttribuut.setPostcode("1234AB");
	}
	
	@Test
	public void testEquals(){
		assertTrue("Vergeleken met zichzelf", standaardAdres.equals(standaardAdres));
		assertTrue("Vergeleken met adres met dezelfde attributen", standaardAdres.equals(gelijkAanStandaardAdres));
		assertFalse("Vergeleken met ander soort object", standaardAdres.equals("ander Object"));
		assertFalse("Plaats is null", standaardAdres.equals(metNullAttribuut));
		assertFalse("Vergeleken met zelfde adres als postbus", standaardAdres.equals(metEenAfwijkendAttribuut));
	}
	
	@Test
	public void testMaakPostbus(){
		standaardAdres.maakPostbus();
		assertTrue("De boolean isPostbus is nu true", standaardAdres.getIsPostbus());
		assertTrue("straatnaam is veranderd in \"nvt\"", standaardAdres.getStraat().equals("nvt"));
	}
	
	@Test
	public void testMaakStraat(){
		metEenAfwijkendAttribuut.maakStraat();
		assertFalse("boolean isPostbus is false", metEenAfwijkendAttribuut.getIsPostbus());
		assertTrue("Straatnaam is gewijzigd van \"nvt\" naar lege String", metEenAfwijkendAttribuut.getStraat().equals(""));
		Adres adresVoorWijziging = standaardAdres;
		standaardAdres.maakPostbus();
		standaardAdres.maakStraat();
		assertTrue("Na maakPostbus() en terug via maakStraat() alles ongewijzigd", adresVoorWijziging.equals(standaardAdres));
		
	}

}
