package nl.rsvier.icaras.service.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Adres;
import nl.rsvier.icaras.core.relatiebeheer.Leverancier;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.core.relatiebeheer.Relatie;
import nl.rsvier.icaras.core.relatiebeheer.Werknemer;
import nl.rsvier.icaras.dao.relatiebeheer.RelatieDaoHibernate;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//TODO embedded database voor deze test
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:icarasdb-context.xml" })
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class RelatieServiceTest {

	@Autowired
	public IRelatieService service;

	private Persoon relatie1;
	private Organisatie relatie2;
	private Adres adresRelatie1 = new Adres();
	private Adres adresRelatie2 = new Adres();
//	static Runtime rt = Runtime.getRuntime();
//	static String executeBackupCmd = "/C:\\MySQL\\MySQL Server 5.6\\bin\\mysqldump -u" + "root" + " -p" + "masterkey" + " icaras -r " +  "C:\\icaras\\icarasbackup.sql";
//	String execRestoreCmd = "/C:\\MySQL\\MySQL Server 5.6\\bin\\mysql -u" + "root" + " -p" + "masterkey" + " icaras < " +  "C:\\icaras\\icarasbackup.sql";
	private static ArrayList<String> klasseNamen = new ArrayList<String>();

	
//	@BeforeClass
//	public static void init(){
//		try{
//		 rt.exec(executeBackupCmd);
//		}
//		catch(IOException e){
//			System.out.println(e.getMessage());
//		}
//	}
	
	@Before
	public void setUp() throws InvalidBusinessKeyException {
//		//roep mysqldump aan om gemaakte mysql dump backup te loaden
//		String comando = "C:\\MySQL\\MySQL server 5.6\\bin\\mysql.exe  --host=localhost --port=3306 --user=root --password=masterkey < C:\\icaras\\icarasbackup.sql";
//		File f = new File("restore.bat");
//		try{
//		FileOutputStream fos = new FileOutputStream(f);
//		fos.write(comando.getBytes());
//		fos.close();
//		Process run = Runtime.getRuntime().exec("cmd /C start restore.bat ");  
//		}
//		catch(Exception e){
//			System.out.println("errorerror");
//		}
		relatie1 = new Persoon();
		relatie1.setAchternaam("Smit");
		relatie1.setVoornaam("Jantje");
		relatie1.setTussenvoegsels("de");
		relatie1.setGeboortedatum(new GregorianCalendar(1989, 9, 23));
		relatie1.setOpmerking("Beroerde zanger");
		relatie1.addRol(new Werknemer());

		adresRelatie1.maakStraat();
		adresRelatie1.setHuisOfPostbusNummer("456");
		adresRelatie1.setPlaats("Volendam");
		adresRelatie1.setPostcode("4565AK");
		adresRelatie1.setStraat("palingstraat");
		relatie1.addAdres(adresRelatie1);

		relatie2 = new Organisatie("ACME");
		relatie2.addRol(new Leverancier());
		relatie2.setOpmerking("A company that manufactures EVERYTHING!");

		adresRelatie2.maakPostbus();
		adresRelatie2.setHuisOfPostbusNummer("1000");
		adresRelatie2.setPlaats("Wageningen");
		adresRelatie2.setPostcode("6700AA");
		adresRelatie2.setStraat("Bugs Bunnystraat");

		relatie2.addAdres(adresRelatie2);

	}


	private void flushAndClear(){
//		service.getDao().getHibernateTemplate().flush();
//		service.getDao().getHibernateTemplate().clear();
	}

//	@Test
//	public void testInitialisatie() {
//		assertNotNull("RelatieService is null", service);
//
//		RelatieDaoHibernate dao = service.getDao();
//
//		assertNotNull("service.dao is null", dao);
//
//	}

	@Test
	public void testSaveEnGet() {
		service.save(relatie1);
		service.save(relatie2);
//		service.getDao().getHibernateTemplate().flush();
//		service.getDao().getHibernateTemplate().clear();
		

		Persoon returnRelatie1 = (Persoon) service.getById(relatie1
				.getId());
		Organisatie returnRelatie2 = (Organisatie) service.getById(relatie2
				.getId());
		assertTrue("gesavede relatie1 is gelijke aan opgehaalde relatie",
				relatie1.equals(returnRelatie1));
		assertTrue("gesavede relatie2 is gelijke aan opgehaalde relatie",
				relatie2.equals(returnRelatie2));
		service.delete(relatie1);
		service.delete(relatie2);

	}

	@Test
	public void testUpdateVelden() {

		service.save(relatie1);
		service.save(relatie2);
//		service.getDao().getHibernateTemplate().flush();

		relatie1.setVoornaam("Kees");
		relatie2.setOpmerking("Bijna alles");

		service.update(relatie1);
		service.update(relatie2);
//		service.getDao().getHibernateTemplate().flush();
//		service.getDao().getHibernateTemplate().clear();

		Persoon relatie1Updated = (Persoon) service.getById(relatie1
				.getId());
		Organisatie relatie2Updated = (Organisatie) service.getById(relatie2
				.getId());

		assertTrue("opgehaalde relatie1 gelijk aan relatie1Updated",
				relatie1.equals(relatie1Updated));
		assertTrue("opgehaalde relatie2 gelijk aan relatie2Updated Object",
				relatie2.equals(relatie2Updated));
		service.delete(relatie1);
		service.delete(relatie2);
	}

	@Test
	public void testUpdateList() {
		// TODO testUpdateList, in afwachting van equals functies van persoon
		// die ook lijsten meenemen

	}
	
	@Test
	public void testDeleteAdresVanRelatie() {
		service.save(relatie1);
		Persoon dbRelatie = (Persoon) service.getByIdMetAdres(relatie1.getId());
		dbRelatie.verwijderLaatsteAdres();
		service.update(dbRelatie);
		Persoon dbPersNaUpdate = (Persoon) service.getByIdMetAdres(relatie1.getId());
		assertTrue(dbPersNaUpdate.getAdressen().isEmpty());
		service.delete(relatie1);		
	}

	@Test
	public void testDelete() {
		service.save(relatie1);
		service.save(relatie2);
//		flushAndClear();

		service.delete(relatie1);
		service.delete(relatie2);
//		flushAndClear();

		assertNull("Gedelete relatie1 is null",
				service.getById(relatie1.getId()));
		assertNull("Gedelete relatie2 is null",
				service.getById(relatie2.getId()));

	}

	@Test
	public void testGetAll() {
		service.save(relatie1);
		service.save(relatie2);
//		flushAndClear();

		List<Relatie> relatieLijst = service.getAll();

		assertTrue("relatieLijst bevat " + relatieLijst.size()
				+ " relaties, dit zou 2 moeten zijn", relatieLijst.size() == 2);
		Relatie[] relatieArray = new Relatie[relatieLijst.size()];
		Persoon returnRelatie1 = null;
		Organisatie returnRelatie2 = null;

		for (Relatie r : relatieLijst.toArray(relatieArray)) {
			if (r instanceof Persoon) {

				returnRelatie1 = (Persoon) r;
			} else if (r instanceof Organisatie) {
				returnRelatie2 = (Organisatie) r;
			}
		}

		assertTrue("Relatie1 uit lijst is niet gelijk aan relatie1",
				returnRelatie1.equals(relatie1));
		assertTrue("Relatie2 uit lijst is niet gelijk aan relatie2",
				returnRelatie2.equals(relatie2));
		service.delete(relatie1);
		service.delete(relatie2);
	}

	@Test
	public void testGetAllMetAdres() {
		service.save(relatie1);
		service.save(relatie2);
//		flushAndClear();

		List<Relatie> relatieLijst = service.getAllMetAdres();

		assertTrue("relatieLijst bevat " + relatieLijst.size()
				+ " relaties, dit zou 2 moeten zijn", relatieLijst.size() == 2);
		Relatie[] relatieArray = new Relatie[relatieLijst.size()];
		Persoon returnRelatie1 = null;
		Organisatie returnRelatie2 = null;

		for (Relatie r : relatieLijst.toArray(relatieArray)) {
			if (r instanceof Persoon) {

				returnRelatie1 = (Persoon) r;
			} else if (r instanceof Organisatie) {
				returnRelatie2 = (Organisatie) r;
			}
		}

		assertTrue("Relatie1 uit lijst is niet gelijk aan relatie1",
				returnRelatie1.equals(relatie1));
		assertTrue("Relatie2 uit lijst is niet gelijk aan relatie2",
				returnRelatie2.equals(relatie2));

		Set<Adres> relatie1Adressen = returnRelatie1.getAdressen();
		Set<Adres> relatie2Adressen = returnRelatie2.getAdressen();

		assertFalse("adressen Relatie1 zijn leeg ", relatie1Adressen.isEmpty());
		assertFalse("adressen Relatie2 zijn leeg ", relatie2Adressen.isEmpty());
		assertTrue("relatie1 adressen niet gelijk aan returnRelatie1Adressen",
				(relatie1.getAdressen()).equals(relatie1Adressen));
		assertTrue("relatie2 adressen niet gelijk aan returnRelatie2 Adressen",
				(relatie2.getAdressen()).equals(relatie2Adressen));
		service.delete(relatie1);
		service.delete(relatie2);
	}

	@Test
	public void testGetRelatieAdressen() {
		service.save(relatie1);
		service.save(relatie2);
//		flushAndClear();

		Set<Adres> relatie1Adressen = service.getRelatieAdressen(relatie1);
		Set<Adres> relatie2Adressen = service.getRelatieAdressen(relatie2);

		assertFalse("relatie1 AdressenSet is leeg ", relatie1Adressen.isEmpty());
		assertFalse("relatie2 AdressenSet is leeg ", relatie2Adressen.isEmpty());
		assertTrue(
				"relatie1 adressen set is niet gelijk aan relatie1.getadressen",
				relatie1Adressen.equals(relatie1.getAdressen()));
		assertTrue(
				"relatie2 adressen set is niet gelijk aan relatie1.getadressen",
				relatie2Adressen.equals(relatie2.getAdressen()));
		service.delete(relatie1);
		service.delete(relatie2);
	}

	@Test
	public void testGetByIdMetAdressen() {
		service.save(relatie1);
		service.save(relatie2);
//		flushAndClear();

		Persoon returnRelatie1MetAdres = (Persoon) service
				.getByIdMetAdres(relatie1.getId());
		Organisatie returnRelatie2MetAdres = (Organisatie) service
				.getByIdMetAdres(relatie2.getId());

		assertFalse(
				"ReturnRelatie1MetAdressen heeft geen adressen, moet wel zo zijn",
				returnRelatie1MetAdres.getAdressen().isEmpty());
		assertFalse("returnRelatie2MetAdressen heeft geen adressen",
				returnRelatie2MetAdres.getAdressen().isEmpty());

		Set<Adres> returnAdressen1 = returnRelatie1MetAdres.getAdressen();
		Set<Adres> returnAdressen2 = returnRelatie2MetAdres.getAdressen();
		Set<Adres> relatie1Adressen = relatie1.getAdressen();
		Set<Adres> relatie2Adressen = relatie2.getAdressen();
		assertTrue(
				"adressen returnRelatie1 zijn niet gelijk aan die van relatie1",
				returnAdressen1.equals(relatie1Adressen));
		assertTrue(
				"adressen returnRelatie2 zijn niet gelijk aan die van relatie2",
				returnAdressen2.equals(relatie2Adressen));
		service.delete(relatie1);
		service.delete(relatie2);
	}

}
