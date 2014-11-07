// TODO Zinvolle test schrijven

//package nl.rsvier.icaras.core.relatiebeheer;
//
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//
//import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
//
//import org.junit.Before;
//import org.junit.Test;
//
//public class AanbiedingTest {
//	
//	private Persoon testPersoon1;
//	private Persoon testPersoon2;
//	
//	private Kandidaat testKandidaat1;
//	private Kandidaat testKandidaat2;
//	
//	private Aanbieding aanbieding1 = new Aanbieding();
//	private Aanbieding aanbieding2 = new Aanbieding();
//	private Aanbieding testaanbieding = new Aanbieding();
//	
//	@Before
//	public void setUp() {
//		testPersoon1 = maakTestPersoonZonderId("Thomas", "", "Slippens",
//				new GregorianCalendar(1986, 3, 25));
//		testPersoon2 = maakTestPersoonZonderId("Leroy", "van den",
//				"Hoogen", new GregorianCalendar(1988, 0, 21));
//		testKandidaat1 = maakTestKandidaatZonderId(true, "setup - opmerking");
//		testKandidaat2 = maakTestKandidaatZonderId(false, "random - opmerking");
//		
//		testPersoon1.addRol(testKandidaat1);
//		testPersoon2.addRol(testKandidaat2);
//		testKandidaat1.addAanbieding(aanbieding1);
//		testKandidaat2.addAanbieding(aanbieding2);
//
//		
//	}
//	
//	private Kandidaat maakTestKandidaatZonderId(boolean isGearchiveerd, String opmerking) {
//		Kandidaat k = new Kandidaat();
//		k.setGearchiveerd(isGearchiveerd);
//		k.setOpmerking(opmerking);
//		return k;
//	}
//	
//	private Persoon maakTestPersoonZonderId(String voornaam,
//			String tussenvoegsels, String achternaam, Calendar geboortedatum) {
//		Persoon p = new Persoon();
//		p.setVoornaam(voornaam);
//		p.setTussenvoegsels(tussenvoegsels);
//		p.setAchternaam(achternaam);
//		p.setGeboortedatum(geboortedatum);
//		return p;
//	}
//	
//	@Test
//	public void testKandidaat(){
//		System.out.println(testKandidaat1.getOpmerking());
//		System.out.println("HIER ZIT DIE INT " + testKandidaat1.getAanbiedingen().size());
//	}
//}
