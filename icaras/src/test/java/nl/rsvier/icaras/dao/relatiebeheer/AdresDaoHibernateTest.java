package nl.rsvier.icaras.dao.relatiebeheer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.relatiebeheer.Adres;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:icarastestdb-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback=true)
public class AdresDaoHibernateTest {
	
	@Autowired //via @ContextConfiguration wordt deze testklasse aangemeld bij de ApplicationContext
	private AdresDaoHibernate adresDaoHibernate;
	
	Adres testAdres;
	Adres testAdres2;
	
	@Before
	public void setUp(){
		testAdres = maakTestAdresZonderId(false, "1234AB", "191", "Hilversum", "Larenseweg");
		testAdres2 = maakTestAdresZonderId(true, "9876ZY", "123A", "Utrecht", "Oude Markt");		
	}
	
	private Adres maakTestAdresZonderId(boolean isPostbus, String postcode, String huisOfPostbusNummer, String plaats,
			String straat) {
		Adres nieuwAdres = new Adres();
		if (isPostbus){
		nieuwAdres.maakPostbus();
		}
		else {
			nieuwAdres.maakStraat();
			nieuwAdres.setStraat(straat);
		}
		nieuwAdres.setPostcode(postcode);
		nieuwAdres.setHuisOfPostbusNummer(huisOfPostbusNummer);
		nieuwAdres.setPlaats(plaats);
		return nieuwAdres;		
	}

	@Test
	@Transactional
	public void testSaveEnGetAdres() {
//		adresDaoHibernate.save(null);//Het saven van null waarde geeft java.lang.IllegalArgumentException
		System.out.println("Zojuist geprobeerd null te saven");
		adresDaoHibernate.save(testAdres);
		assertNotNull(testAdres.getId());
		//adresDaoHibernate.getHibernateTemplate().flush(); HIER GEEN FLUSH NODIG
		adresDaoHibernate.getHibernateTemplate().evict(testAdres);//Verwijdert gegeven object van 1st level cache
		testAdres2 = adresDaoHibernate.getById(testAdres.getId());
		assertTrue("attributen vanuit database zijn gelijk aan die van het adres voor save", testAdres2.equals(testAdres));
	}
	
	@Test
	@Transactional
	public void testGetAllAdressen(){
		adresDaoHibernate.save(testAdres);
		adresDaoHibernate.save(testAdres2);
		adresDaoHibernate.getHibernateTemplate().clear();//leegt de gehele 1st level cache (deze is sessie gebonden)
		List<Adres> adressenlijst = adresDaoHibernate.getAll();
		assertTrue("Zijn er inderdaad 2 objecten geladen uit de database?", adressenlijst.size()==2);
		//toekennen van juiste adres aan juiste vergelijkAdres (om ordening van adressenlijst irrelevant te maken)
		Adres vergelijkAdres = null, vergelijkAdres2 = null;
		{for (Adres a : adressenlijst){ if (a.getId()==testAdres.getId()) vergelijkAdres = a;}};
		{for (Adres a : adressenlijst){ if (a.getId()==testAdres2.getId()) vergelijkAdres2 = a;}};
		assertTrue("eerste opgeslagen adres en adres uit de opgehaalde lijst met dezelfde id zijn gelijk", testAdres.equals(vergelijkAdres));
		assertTrue("tweede opgeslagen adres en adres uit de opgehaalde lijst met dezelfde id zijn gelijk", testAdres2.equals(vergelijkAdres2));
	}
	
	@Test
	@Transactional
	public void testDeleteAdres(){
		adresDaoHibernate.save(testAdres);
		adresDaoHibernate.delete(testAdres);
		adresDaoHibernate.getHibernateTemplate().flush();
		adresDaoHibernate.getHibernateTemplate().evict(testAdres);
		testAdres2 = adresDaoHibernate.getById(testAdres.getId());
			assertNull("verwijderde adres opvragen uit database geeft null terug", testAdres2);
		
	}
	
	@Test
	@Transactional
	public void updateAdres(){
		adresDaoHibernate.save(testAdres);
		testAdres.setPlaats(testAdres2.getPlaats());
		adresDaoHibernate.update(testAdres);
		adresDaoHibernate.getHibernateTemplate().flush();
		adresDaoHibernate.getHibernateTemplate().evict(testAdres);
		Adres vergelijkAdres = adresDaoHibernate.getById(testAdres.getId());
		assertTrue("attributen geupdate adres is gelijk aan attributen ingeladen adres met dezelfde id", testAdres.equals(vergelijkAdres));
	}
}
