package nl.rsvier.icaras.core.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse voor de core klasse: Leverancier
 * 
 * @author Mark van Meerten
 */

public class LeverancierTest {

	List<Organisatie> organisaties_correct;
	Organisatie sterlingcooper;
	Organisatie sterlingcooper_clone;
	Organisatie pearsonhardman;
	Organisatie pearsonhardman_clone;

	@Before
	public void setUp() throws InvalidBusinessKeyException {

		Leverancier leveranciersrol1 = new Leverancier();
		leveranciersrol1.setFunctie("Copy");
		sterlingcooper = new Organisatie("Sterling Cooper Advertising Agency");
		sterlingcooper.addRol(leveranciersrol1);
		sterlingcooper_clone = new Organisatie(
				"Sterling Cooper Advertising Agency");
		sterlingcooper_clone.addRol(leveranciersrol1);

		Leverancier leveranciersrol2 = new Leverancier();
		leveranciersrol2.setFunctie("Lawyers");
		pearsonhardman = new Organisatie("Pearson Hardman");
		pearsonhardman.addRol(leveranciersrol2);
		pearsonhardman_clone = new Organisatie("Pearson Hardman");
		pearsonhardman_clone.addRol(leveranciersrol2);

		organisaties_correct = new ArrayList<Organisatie>();
		organisaties_correct.addAll(Arrays.asList(sterlingcooper,
				sterlingcooper_clone, pearsonhardman, pearsonhardman_clone));
		
	}

	@Test
	public void test_equals() {

		/*
		 * Test of de equals methode werkt naar verwachting
		 */

		assertTrue("Vergeleken met zichzelf", sterlingcooper.getLeverancier()
				.equals(sterlingcooper.getLeverancier()));
		assertTrue(
				"Vergeleken met clone van zichzelf (gelijke business key)",
				sterlingcooper.getLeverancier().equals(
						sterlingcooper_clone.getLeverancier()));

		assertTrue("Vergeleken met zichzelf", pearsonhardman.getLeverancier()
				.equals(pearsonhardman.getLeverancier()));
		assertTrue(
				"Vergeleken met clone van zichzelf (gelijke business key)",
				pearsonhardman.getLeverancier().equals(
						pearsonhardman_clone.getLeverancier()));

		assertFalse(
				"Vergeleken met gelijke objecten (ongelijke business key)",
				sterlingcooper.getLeverancier().equals(
						pearsonhardman.getLeverancier()));

		assertFalse("Vergeleken met ander type object (String)", sterlingcooper
				.getLeverancier().equals(new String("willekeurige string")));

	}

}
