package nl.rsvier.icaras.core.relatiebeheer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.arbeidsmarkt.Vacature;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse voor de core klasse: Bedrijf
 * 
 * @author Mark van Meerten
 */

public class BedrijfTest {

	List<Organisatie> organisaties_correct = new ArrayList<Organisatie>();
	Organisatie sterlingcooper;
	Organisatie pearsonhardman;

	List<Vacature> vacatures_correct = new ArrayList<Vacature>();
	Vacature vacature1;
	Vacature vacature2;
	Vacature vacature3;
	Vacature vacature4;

	@Before
	public void setUp() throws InvalidBusinessKeyException {

		sterlingcooper = new Organisatie("Sterling Cooper Advertising Agency");
		sterlingcooper.addRol(new Bedrijf());
		pearsonhardman = new Organisatie("Pearson Hardman");
		pearsonhardman.addRol(new Bedrijf());

		vacature1 = new Vacature(sterlingcooper, "looking for a copy writer");
		vacature2 = new Vacature(sterlingcooper,
				"looking for a new head of television");
		vacature3 = new Vacature(pearsonhardman, "looking for interns");
		vacature4 = new Vacature(pearsonhardman, "looking for cheaters");

		vacatures_correct.addAll(Arrays.asList(vacature1, vacature2, vacature3,
				vacature4));

	}

	private Vacature newRandomTestVacature() throws InvalidBusinessKeyException {
		Random r = new Random();
		Organisatie o = new Organisatie(String.valueOf(r.nextInt(1000000)));
		o.addRol(new Bedrijf());
		Vacature v = new Vacature(o, String.valueOf(r.nextInt(1000000)));
		return v;
	}

	private Organisatie newRandomTestOrganisatie()
			throws InvalidBusinessKeyException {
		Random r = new Random();
		Organisatie o = new Organisatie(String.valueOf(r.nextInt(1000000)));
		o.addRol(new Bedrijf());
		return o;
	}

	/*
	 * Methode Tests
	 */

	@Test
	public void test_addVacature() throws InvalidBusinessKeyException {

		for (Vacature v : vacatures_correct) {

			// Try to break some rules

			assertFalse("Vacature mag geen duplicaat zijn", v.getOrganisatie()
					.getBedrijf().addVacature(v));

			assertTrue(String.format(
					"Bedrijf heeft nog een referentie naar Vacature: %s", v), v
					.getOrganisatie().getBedrijf().heeftVacature(v));

			assertTrue(String.format("Vacature: %s is verwijdert", v), v
					.getOrganisatie().getBedrijf().removeVacature(v));

			assertFalse(
					String.format(
							"Bedrijf heeft niet langer een referentie naar Vacature: %s",
							v), v.getOrganisatie().getBedrijf()
							.heeftVacature(v));

			assertFalse(String.format(
					"Bedrijf heeft geen referentie naar Vacature: %s", v), v
					.getOrganisatie().getBedrijf().heeftVacature(v));

			assertFalse("Vacature mag niet null zijn", v.getOrganisatie()
					.getBedrijf().addVacature(null));

			assertFalse(String.format(
					"Bedrijf heeft geen referentie naar Vacature: %s", v), v
					.getOrganisatie().getBedrijf().heeftVacature(v));

			Vacature vacature_imposter = new Vacature(
					this.newRandomTestOrganisatie(), "Bedrieger");
			assertFalse(
					"Vacature moet aan de Organisatie gedaan zijn die houder is van deze bedrijfsrol",
					v.getOrganisatie().getBedrijf()
							.addVacature(vacature_imposter));

			assertFalse(String.format(
					"Bedrijf heeft geen referentie naar Vacature: %s", v), v
					.getOrganisatie().getBedrijf().heeftVacature(v));

			// Follow the rules 
			
			assertTrue(String.format("Vacature: %s is weer toegevoegd", v), v
					.getOrganisatie().getBedrijf().addVacature(v));

			assertTrue(String.format(
					"Bedrijf heeft weer een referentie naar Vacature: %s", v),
					v.getOrganisatie().getBedrijf().heeftVacature(v));

		}

	}

	@Test
	public void test_removeVacature() throws InvalidBusinessKeyException {

		for (Vacature v : vacatures_correct) {

			// Try to break some rules

			assertTrue(
					"Er is al een referentie van Vacature toegevoegd aan Bedrijf "
							+ "via de constructor van Vacature", v
							.getOrganisatie().getBedrijf().heeftVacature(v));

			Vacature fail_vacature = null;
			assertFalse(
					String.format(
							"Vacature: %s kan niet worden verwijdert, mag niet null zijn",
							fail_vacature), v.getOrganisatie().getBedrijf()
							.removeVacature(fail_vacature));

			fail_vacature = this.newRandomTestVacature();
			assertFalse(
					String.format(
							"Vacature: %s kan niet worden verwijdert, bedrijf kent de Vacature niet",
							fail_vacature), v.getOrganisatie().getBedrijf()
							.removeVacature(fail_vacature));

			// Deze test word nu al eerder afgevangen met een return false. Toch
			// laat ik hem staan voor het geval dat iemand de methode aanpast
			fail_vacature = this.newRandomTestVacature();
			assertFalse(
					String.format(
							"Vacature: %s kan niet worden verwijdert, bedrijf (en dus organisatie) komen niet overeen",
							fail_vacature), v.getOrganisatie().getBedrijf()
							.removeVacature(fail_vacature));

			// Follow the rules

			assertTrue(
					String.format(
							"De referentie van Bedrijf naar Vacature: %s bestaat nog steeds",
							v), v.getOrganisatie().getBedrijf()
							.heeftVacature(v));

			assertTrue("Vacature is verwijdert", v.getOrganisatie()
					.getBedrijf().removeVacature(v));

			assertFalse(
					String.format(
							"Bedrijf heeft niet langer een referentie naar Vacature: %s",
							v), v.getOrganisatie().getBedrijf()
							.heeftVacature(v));

		}

	}
}
