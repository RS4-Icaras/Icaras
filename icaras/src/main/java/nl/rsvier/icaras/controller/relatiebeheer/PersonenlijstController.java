package nl.rsvier.icaras.controller.relatiebeheer;

import java.util.HashMap;
import java.util.Map;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Adres;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.core.relatiebeheer.Relatie;
import nl.rsvier.icaras.form.relatiebeheer.AdresForm;
import nl.rsvier.icaras.form.relatiebeheer.OrganisatieForm;
import nl.rsvier.icaras.service.relatiebeheer.IRelatieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonenlijstController {

	@Autowired
	private IRelatieService relatieService;

	@RequestMapping(value = "/start")
	public String start(Model model) throws InvalidBusinessKeyException {

		Adres aBurrow = new Adres(true, false, "2022PG", "1",
				"Ottery St. Catchpole", "on the outskirts of Devon");
		Adres aHogwarts = new Adres(true, true, "4501MG", "1402", "Edinburgh",
				"");
		Adres aPartyshop = new Adres(false, false, "0199TT", "93", "London",
				"Diagon Alley");

		Persoon harry = new Persoon("Harry", "Potter");
		harry.addAdres(new Adres(true, false, "1340DF", "4", "Little Whinging",
				"Privet Drive"));

		Persoon ron = new Persoon("Ron", "Weasley");
		ron.addAdres(aBurrow);

		Persoon hermione = new Persoon("Hermione", "Granger");

		Organisatie hogwarts = new Organisatie(
				"Hogwarts School of Witchcraft and Wizardry");
		hogwarts.addAdres(aHogwarts);
		Organisatie gringotts = new Organisatie("Gringotts Wizarding Bank");
		Organisatie leakycauldron = new Organisatie("The Leaky Cauldron");
		Organisatie ollivanders = new Organisatie("Ollivanders");
		Organisatie wizardwheezes = new Organisatie("Weasleys' Wizard Wheezes");
		wizardwheezes.addAdres(aPartyshop);

		Persoon hagrid = new Persoon("Rubeus", "Hagrid");
		// hagrid.addAdres(aHogwarts);

		Persoon twins_george = new Persoon("George", "Weasley");
		// twins_george.addAdres(aBurrow);
		// twins_george.addAdres(aPartyshop);

		Persoon twins_fred = new Persoon("Fred", "Weasley");
		// twins_fred.addAdres(aBurrow);
		// twins_fred.addAdres(aPartyshop);

		relatieService.save(harry); // Voeg harry eerst toe
		relatieService.save(twins_fred);
		relatieService.save(wizardwheezes);
		relatieService.save(hogwarts);
		relatieService.save(ron);
		relatieService.save(ollivanders);
		relatieService.save(gringotts);
		relatieService.save(twins_george);
		relatieService.save(leakycauldron);
		relatieService.save(hagrid);
		relatieService.save(hermione);

		// TODO: Ik kan een adres niet aan 2 relaties toevoegen
		Persoon profdumbledore = new Persoon("Albert", "Dumbledore");
		// profdumbledore.addAdres(hogwarts.getCorrespondentieAdres());
		relatieService.save(profdumbledore);

		// TODO: Ik kan oneindig duplicaten van personen blijven toevoegen
		Persoon profdumbledore2 = new Persoon("Albert", "Dumbledore");
		relatieService.save(profdumbledore2);

		return "redirect:/getAllRelaties";
	}

	@RequestMapping(value = "/getAllRelaties")
	public String getAllRelaties(Model model) {
		model.addAttribute("relaties", relatieService.getAll());
		return "getAllRelaties";
	}

	@RequestMapping(value = "/getRelatie/{relatie_id}", method = RequestMethod.GET)
	public String getRelatie(@PathVariable int relatie_id, Model model) {
		Relatie relatie = relatieService.getByIdMetAdres(relatie_id);
		model.addAttribute("relatie", relatie);
		return "getRelatie";
	}

	@RequestMapping(value = "/updatePersoon", method = RequestMethod.POST)
	public String updatePersoonSubmit(@ModelAttribute Persoon persoon,
			Model model) {
		/**
		 * De persoon die we ontvangen vanuit het formulier heeft geen collectie
		 * adressen terwijl de bestaande persoon in de database dat misschien
		 * wel heeft. Een update zou de collectie adressen overschrijven met een
		 * lege collectie, en dus ook uit de database verwijderen. Het formulier
		 * vullen met alle adressen werkt ook niet, want dan zit je met
		 * hetzelfde probleem wanneer de relatie een collectie rollen bevat.
		 */
		Persoon tmp = (Persoon) relatieService.getByIdMetAdres(persoon.getId());
		tmp.setVoornaam(persoon.getVoornaam());
		tmp.setTussenvoegsels(persoon.getTussenvoegsels());
		tmp.setAchternaam(persoon.getAchternaam());
		tmp.setGeboortedatum(persoon.getGeboortedatum());
		// TODO: Validatie
		relatieService.update(tmp);
		return "redirect:/getRelatie/" + persoon.getId();
	}

	@RequestMapping(value = "/updateOrganisatie", method = RequestMethod.POST)
	public String updateOrganisatieSubmit(
			@ModelAttribute Organisatie organisatie, Model model) {
		relatieService.update(organisatie);
		return "redirect:/getRelatie/" + organisatie.getId();
	}

	@RequestMapping(value = "/wijzigAdres/{relatie_id}/{adres_id}", method = RequestMethod.GET)
	public String voegAdresToeForm(@PathVariable int relatie_id,
			@PathVariable int adres_id, Model model) {
		AdresForm adresForm = new AdresForm();
		Relatie relatie = relatieService.getByIdMetAdres(relatie_id);
		for (Adres adres : relatie.getAdressen()) {
			if (adres.getId() == adres_id) {
				adresForm.setId(adres_id);
				adresForm.setRelatie_id(relatie_id);

				adresForm.setStraat(adres.getStraat());
				adresForm
						.setHuisOfPostbusNummer(adres.getHuisOfPostbusNummer());
				adresForm.setPostcode(adres.getPostcode());
				adresForm.setPlaats(adres.getPlaats());

				adresForm.setCorrespondentieAdres(adres
						.isCorrespondentieAdres());
				adresForm.setPostbus(adres.isPostbus());

			}
		}
		model.addAttribute("adresForm", adresForm);
		return "wijzigAdres";
	}

	@RequestMapping(value = "/wijzigAdres/{relatie_id}/{adres_id}", method = RequestMethod.POST)
	public String wijzigAdresForm(@PathVariable int relatie_id,
			@PathVariable int adres_id, @ModelAttribute AdresForm adresForm,
			Model model) {

		// Tijdelijk handmatig voorbeeld van validatie
		Map<String, String> feedbackMap = new HashMap<String, String>();

		// Tijdelijke validatie
		if (adresForm.isValid()) {
			Relatie relatie = relatieService.getByIdMetAdres(relatie_id);
			for (Adres adres : relatie.getAdressen()) {
				if (adres.getId() == adres_id) {
					adres.setHuisOfPostbusNummer(adresForm
							.getHuisOfPostbusNummer());
					adres.setPostcode(adresForm.getPostcode());
					adres.setPlaats(adresForm.getPlaats());
					if (adresForm.getPostbus()) {
						adres.maakPostbus();
					} else {
						adres.maakStraat();
						adres.setStraat(adresForm.getStraat());
					}
					if (adresForm.getCorrespondentieAdres()) {
						adres.maakCorrespondentieAdres(relatie);
					}
				}
			}
			relatieService.update(relatie);
			return "redirect:/getRelatie/" + relatie_id;
		}

		if (!adresForm.isAllesIngevuld() && !adresForm.isStraatZonderStraat()) {
			feedbackMap.put("algemeen", "Hey joh, vul alle velden eens in!");
		}

		if (adresForm.isStraatZonderStraat()) {
			feedbackMap
					.put("straatzonderstraat",
							"Straat mag alleen leeg zijn wanneer het adres geen postbus is");
		}

		model.addAttribute("adresForm", adresForm);
		model.addAttribute("feedback", feedbackMap);

		return "wijzigAdres";

	}

	@RequestMapping(value = "/voegPersoonToe", method = RequestMethod.GET)
	public String voegPersoonToeForm(Model model) {
		Persoon persoon = new Persoon();
		model.addAttribute("persoon", persoon);
		return "voegPersoonToe";
	}

	@RequestMapping(value = "/voegPersoonToe", method = RequestMethod.POST)
	public String voegPersoonToeSubmit(@ModelAttribute Persoon persoon,
			Model model) {
		relatieService.save(persoon);
		return "redirect:/getAllRelaties";
	}

	@RequestMapping(value = "/voegOrganisatieToe", method = RequestMethod.GET)
	public String voegOrganisatieToeForm(Model model) {
		/**
		 * Het formulier wilt een Organisatie, maar die klasse vereist dat er
		 * een naam word opgegeven bij instantiatie. TODO: Spring voorziet
		 * hierin mbv een vergelijkbaar idee onder de noemer: commandObject /
		 * form backing object
		 */
		model.addAttribute("organisatieForm", new OrganisatieForm());
		return "voegOrganisatieToe";
	}

	@RequestMapping(value = "/voegOrganisatieToe", method = RequestMethod.POST)
	public String voegOrganisatieToeSubmit(
			@ModelAttribute OrganisatieForm organisatieForm, Model model) {
		try {
			System.out.println(organisatieForm.getNaam() + " ??????????????????? ");
			Organisatie organisatie = new Organisatie(organisatieForm.getNaam());
			relatieService.save(organisatie);
		} catch (InvalidBusinessKeyException e) {
			// Tijdelijk handmatig voorbeeld van validatie
			Map<String, String> feedbackMap = new HashMap<String, String>();
			feedbackMap.put("invalidBusinessKey", "Geen geldige naam");
			model.addAttribute("organisatieForm", organisatieForm);
			model.addAttribute("feedback", feedbackMap);
			return "voegOrganisatieToe";
		}
		return "redirect:/getAllRelaties";
	}

	@RequestMapping(value = "/voegAdresToe/{relatie_id}", method = RequestMethod.GET)
	public String voegAdresToeForm(@PathVariable int relatie_id, Model model) {
		model.addAttribute("relatie",
				relatieService.getByIdMetAdres(relatie_id));
		model.addAttribute("adresForm", new AdresForm());
		return "voegAdresToe";
	}

	@RequestMapping(value = "/voegAdresToe/{relatie_id}", method = RequestMethod.POST)
	public String voegAdresToeSubmit(@PathVariable int relatie_id,
			AdresForm adresForm, Model model) {

		/*
		 * TODO: Gebruik Hibernate validator om annotaties zoals @NotNull te
		 * gebruiken en automatisch feedback te tonen. Voeg hiervoor de
		 * parameter: "BindingResult result" toe aan deze methode, en annoteer
		 * AdresForm met "@Valid".
		 * 
		 * Daarvoor hebben we de volgende dependency nodig: <dependency>
		 * <groupId>org.hibernate</groupId>
		 * <artifactId>hibernate-validator</artifactId>
		 * <version>5.0.1.Final</version> </dependency>
		 */

		// Tijdelijk handmatig voorbeeld van validatie
		Map<String, String> feedbackMap = new HashMap<String, String>();

		if (adresForm.isValid()) {
			Relatie relatie = relatieService.getByIdMetAdres(relatie_id);
			Adres adres = new Adres(adresForm.getCorrespondentieAdres(),
					adresForm.getPostbus(), adresForm.getPostcode(),
					adresForm.getHuisOfPostbusNummer(), adresForm.getPlaats(),
					adresForm.getStraat());
			relatie.addAdres(adres);
			relatieService.update(relatie);
			return "redirect:/getRelatie/" + relatie_id;
		}

		feedbackMap.put("algemeen", "Hey joh, vul alle velden eens in!");

		model.addAttribute("relatie",
				relatieService.getByIdMetAdres(relatie_id));
		model.addAttribute("adresForm", adresForm);
		model.addAttribute("feedback", feedbackMap);

		return "voegAdresToe";

	}

}