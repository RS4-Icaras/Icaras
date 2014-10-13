package nl.rsvier.icaras.dao.relatiebeheer;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.relatiebeheer.Adres;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:icarasdb-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback=true)
public class AdresDaoHibernateSpringTest {
	
	@Autowired //via @ContextConfiguration wordt deze testklasse aangemeld bij de ApplicationContext
	private AdresDaoHibernate adresDaoHibernate;
	
	Adres testAdres;
	Adres testAdres2;
	
	@Before
	public void setUp(){
		testAdres = maakTestAdresZonderId(false, "1234AB", "191", "Hilversum", "Larenseweg");
		testAdres2 = maakTestAdresZonderId(false, "9876ZY", "123A", "Utrecht", "Oude Markt");		
	}
	
	private Adres maakTestAdresZonderId(boolean isPostbus, String postcode, String huisOfPostbusNummer, String plaats,
			String straat) {
		Adres nieuwAdres = new Adres();
		nieuwAdres.setIsPostbus(isPostbus);
		nieuwAdres.setPostcode(postcode);
		nieuwAdres.setHuisOfPostbusNummer(huisOfPostbusNummer);
		nieuwAdres.setPlaats(plaats);
		nieuwAdres.setStraat(straat);
		return nieuwAdres;		
	}

	@Test
	@Transactional
	public void testSaveEnGetAdres() {
		adresDaoHibernate.saveAdres(testAdres);
		assertNotNull(testAdres.getAdresId());
		testAdres2 = adresDaoHibernate.getAdresById(testAdres.getAdresId());
		assertTrue("attributen vanuit database zijn gelijk aan die van het adres voor save", testAdres2.equals(testAdres));
	}
	
	@Test
	@Transactional
	public void testGetAllAdressen(){
		adresDaoHibernate.saveAdres(testAdres);
		adresDaoHibernate.saveAdres(testAdres2);
		List<Adres> adressenlijst = adresDaoHibernate.getAllAdressen();//deze orderen voor testen van gelijkheid
		assertTrue("Zijn er inderdaad 2 objecten geladen uit de database?", adressenlijst.size()==2);
		assertTrue("eerste opgeslagen adres en eerste adres uit de opgehaalde lijst zijn gelijk", adressenlijst.get(0).equals(testAdres));
		assertTrue("tweede opgeslagen adres en tweede adres uit de opgehaalde lijst zijn gelijk", adressenlijst.get(1).equals(testAdres2));
	}
	
	@Test (expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void testDeleteAdres(){
		adresDaoHibernate.saveAdres(testAdres);
		adresDaoHibernate.deleteAdres(testAdres);
		testAdres2.setAdresId(2);
		adresDaoHibernate.deleteAdres(testAdres2);//het deleten van een niet bestaand adres geeft geen exception
//		try{//Deze try catch is niet meer nodig, expected exception achter @Test
		testAdres2 = adresDaoHibernate.getAdresById(testAdres.getAdresId());
//		}
//		catch (HibernateObjectRetrievalFailureException e) {//Het opvagen van een niet bestaand (Adres)object wel
//			assertFalse("verwijderde adres opvragen uit database geeft null", testAdres2==null);
//		}
		
	}
	
	@Test
	@Transactional
	public void updateAdres(){
		adresDaoHibernate.saveAdres(testAdres);
		testAdres.setPlaats(testAdres2.getPlaats());
		adresDaoHibernate.updateAdres(testAdres);
		Adres vergelijkAdres = adresDaoHibernate.getAdresById(testAdres.getAdresId());
		assertTrue("attributen geupdate adres is gelijk aan attributen ingeladen adres met dezelfde id", testAdres.equals(vergelijkAdres));
	}
}
