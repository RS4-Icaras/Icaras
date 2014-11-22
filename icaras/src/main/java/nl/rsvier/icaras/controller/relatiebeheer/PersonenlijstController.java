package nl.rsvier.icaras.controller.relatiebeheer;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Adres;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.core.relatiebeheer.Relatie;
import nl.rsvier.icaras.form.relatiebeheer.organisatieForm;
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

		if (relatieService.getAll().size() == 0) {

			Adres aBurrow = new Adres(true, false, "2022PG", "1",
					"Ottery St. Catchpole", "on the outskirts of Devon");
			Adres aHogwarts = new Adres(true, true, "4501MG", "1402",
					"Edinburgh", "");
			Adres aPartyshop = new Adres(false, false, "0199TT", "93",
					"London", "Diagon Alley");

			Persoon harry = new Persoon("Harry", "Potter");
			harry.addAdres(new Adres(true, false, "Privet Drive", "4",
					"1340DF", "Little Whinging"));

			Persoon ron = new Persoon("Ron", "Weasley");
			ron.addAdres(aBurrow);

			Persoon hermione = new Persoon("Hermione", "Granger");

			Organisatie org1 = new Organisatie(
					"Hogwarts School of Witchcraft and Wizardry");
			org1.addAdres(aHogwarts);
			Organisatie org2 = new Organisatie("Gringotts Wizarding Bank");
			Organisatie org3 = new Organisatie("The Leaky Cauldron");
			Organisatie org4 = new Organisatie("Ollivanders");
			Organisatie org5 = new Organisatie("Weasleys' Wizard Wheezes");
			org5.addAdres(aPartyshop);

			Persoon professordumbledore = new Persoon("Albert", "Dumbledore");
			// professordumbledore.addAdres(aHogwarts);
			Persoon hagrid = new Persoon("Rubeus", "Hagrid");
			// hagrid.addAdres(aHogwarts);

			Persoon twins_george = new Persoon("George", "Weasley");
			// twins_george.addAdres(aBurrow);
			// twins_george.addAdres(aPartyshop);

			Persoon twins_fred = new Persoon("Fred", "Weasley");
			// twins_fred.addAdres(aBurrow);
			// twins_fred.addAdres(aPartyshop);

			// TODO: Ik kan oneindig duplikaten van personen blijven toevoegen
			// TODO: Ik kan een adres niet aan 2 relaties toevoegen
			relatieService.save(professordumbledore);
			relatieService.save(twins_fred);
			relatieService.save(org5);
			relatieService.save(org1);
			relatieService.save(harry);
			relatieService.save(ron);
			relatieService.save(org4);
			relatieService.save(org2);
			relatieService.save(twins_george);
			relatieService.save(org3);
			relatieService.save(hagrid);
			relatieService.save(hermione);

		}

		return "redirect:/getAllRelaties";
	}

	/*
	 * Toon alle relaties
	 */

	@RequestMapping(value = "/getAllRelaties")
	public String getAllRelaties(Model model) {
		model.addAttribute("relaties", relatieService.getAll());
		return "getAllRelaties";
	}

	/*
	 * Toon een relatie
	 */

	@RequestMapping(value = "/getRelatie/{id}", method = RequestMethod.GET)
	public String getRelatie(@PathVariable int id, Model model) {
		Relatie relatie = relatieService.getByIdMetAdres(id);
		model.addAttribute("relatie", relatie);
		return "getRelatie";
	}

	@RequestMapping(value = "/updatePersoon", method = RequestMethod.POST)
	public String updatePersoonSubmit(@ModelAttribute Persoon persoon,
			Model model) {
		relatieService.update(persoon);
		return "redirect:/getRelatie/" + persoon.getId();
	}

	@RequestMapping(value = "/updateOrganisatie", method = RequestMethod.POST)
	public String updateOrganisatieSubmit(
			@ModelAttribute Organisatie organisatie, Model model) {
		relatieService.update(organisatie);
		return "redirect:/getRelatie/" + organisatie.getId();
	}

	/*
	 * Show Form
	 */

	@RequestMapping(value = "/voegPersoonToe", method = RequestMethod.GET)
	public String voegPersoonToeForm(Model model) {
		Persoon persoon = new Persoon();
		model.addAttribute("persoon", persoon);
		return "voegPersoonToe";
	}

	@RequestMapping(value = "/voegOrganisatieToe", method = RequestMethod.GET)
	public String voegOrganisatieToeForm(Model model) {
		organisatieForm invulOrganisatie = new organisatieForm();
		model.addAttribute("invulOrganisatie", invulOrganisatie);
		return "voegOrganisatieToe";
	}

	@RequestMapping(value = "/voegAdresToe/{relatie_id}", method = RequestMethod.GET)
	public String voegAdresToeForm(@PathVariable int relatie_id, Model model) {
		Relatie relatie = relatieService.getByIdMetAdres(relatie_id);
		model.addAttribute("relatie", relatie);
		return "voegAdresToe";
	}

	/*
	 * Save Form
	 */

	@RequestMapping(value = "/voegOrganisatieToe", method = RequestMethod.POST)
	public String voegOrganisatieToeSubmit(
			@ModelAttribute organisatieForm invulOrganisatie, Model model) {
		try {
			Organisatie organisatie = new Organisatie(
					invulOrganisatie.getNaam());
			relatieService.save(organisatie);
		} catch (InvalidBusinessKeyException e) {
			System.out.println("invalid businesskey for Organisatie");
		}
		return "redirect:/getAllRelaties";
	}

	@RequestMapping(value = "/voegPersoonToe", method = RequestMethod.POST)
	public String voegPersoonToeSubmit(@ModelAttribute Persoon persoon,
			Model model) {
		relatieService.save(persoon);
		return "redirect:/getAllRelaties";
	}

	@RequestMapping(value = "/voegAdresToe/{relatie_id}", method = RequestMethod.POST)
	public String voegAdresToeSubmit(@PathVariable int relatie_id,
			@ModelAttribute Adres adres, Model model) {

		Relatie relatie = relatieService.getByIdMetAdres(relatie_id);
		// relatie.addAdres(adres);
		// Adres adres2 = new Adres();

		relatie.addAdres(adres);
		relatieService.update(relatie);
		return "redirect:/getRelatie/" + relatie_id;
	}

}