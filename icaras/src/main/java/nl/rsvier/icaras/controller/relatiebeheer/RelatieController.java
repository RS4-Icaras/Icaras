package nl.rsvier.icaras.controller.relatiebeheer;

import javax.validation.Valid;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Adres;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.core.relatiebeheer.Relatie;
import nl.rsvier.icaras.form.relatiebeheer.AdresForm;
import nl.rsvier.icaras.form.relatiebeheer.OrganisatieForm;
import nl.rsvier.icaras.form.relatiebeheer.PersoonForm;
import nl.rsvier.icaras.form.relatiebeheer.PostbusForm;
import nl.rsvier.icaras.service.relatiebeheer.IRelatieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RelatieController {

	/**
	 * 
	 * N.B. The BindingResult must come right after the model object that is
	 * validated or else Spring will fail to validate the object and throw an
	 * exception.
	 * 
	 */

	// TODO: Ik geef het op.. Iemand anders mag uitvogelen waarom als je een
	// validatie error krijgt je het correspondentieadres niet meer kunt
	// aanvinken. relatie.heeftAdres(this) failed keihard ondanks dat _elk_
	// attribuut een gelijke waarde heeft, inclusief de hashCode.

	// TODO: Voeg Rollen toe
	// TODO: Validatie

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

	/*
	 * Get All
	 */

	@RequestMapping(value = { "/getAllRelaties", "/getAllPersonen",
			"/getAllOrganisaties" })
	public String getAllRelaties(Model model) {
		model.addAttribute("relaties", relatieService.getAll());
		return "getAllRelaties";
	}

	// @RequestMapping(value = "/getAllPersonen")
	// public String getAllPersonen(Model model) {
	// // model.addAttribute("relaties", relatieService.getAllPersonen()); //
	// // TODO: Servicelaag
	// return "getAllPersonen";
	// }

	// @RequestMapping(value = "/getAllOrganisaties")
	// public String getAllOrganisaties(Model model) {
	// // model.addAttribute("relaties", relatieService.getAllOrganisaties());
	// // // TODO: Servicelaag
	// return "getAllOrganisaties";
	// }

	/*
	 * Get Relatie
	 */

	@RequestMapping(value = { "/getRelatie/{relatie_id}",
			"/getPersoon/{relatie_id}", "/getOrganisatie/{relatie_id}" }, method = RequestMethod.GET)
	public String getRelatie(@PathVariable int relatie_id, Model model) {

		Relatie relatie = relatieService.getByIdMetAdres(relatie_id);

		if (relatie instanceof Persoon) {
			model.addAttribute("persoonForm",
					new PersoonForm((Persoon) relatie));
			model.addAttribute("relatie", relatie);
			return "getRelatie";
		}

		if (relatie instanceof Organisatie) {
			model.addAttribute("organisatieForm", new OrganisatieForm(
					(Organisatie) relatie));
			model.addAttribute("relatie", relatie);
			return "getRelatie";
		}

		// Stuur gebruiker zomaar zonder feedback terug?
		return "redirect:/getAllRelaties";

	}

	/*
	 * Update Relatie
	 */

	@RequestMapping(value = "/updatePersoon", method = RequestMethod.POST)
	public String updatePersoonSubmit(@Valid PersoonForm persoonForm,
			BindingResult persoonFormResult, Model model) {

		/**
		 * De persoon die we ontvangen vanuit het formulier heeft geen collectie
		 * adressen terwijl de bestaande persoon in de database dat misschien
		 * wel heeft. Een update zou de collectie adressen overschrijven met een
		 * lege collectie, en dus ook uit de database verwijderen. Het formulier
		 * vullen met alle adressen werkt ook niet, want dan zit je met
		 * hetzelfde probleem wanneer de relatie een collectie rollen bevat.
		 */

		Persoon persoon = (Persoon) relatieService.getByIdMetAdres(persoonForm
				.getId());

		if (!persoonFormResult.hasErrors()) {
			persoon.setVoornaam(persoonForm.getVoornaam());
			persoon.setTussenvoegsels(persoonForm.getTussenvoegsels());
			persoon.setAchternaam(persoonForm.getAchternaam());
			persoon.setGeboortedatum(persoonForm.getGeboortedatum());

			relatieService.update(persoon);
			return "redirect:/getRelatie/" + persoonForm.getId();
		}
		model.addAttribute("persoonForm", persoonForm);
		model.addAttribute("relatie", persoon); // Voor adressen
		return "getRelatie";
	}

	@RequestMapping(value = "/updateOrganisatie", method = RequestMethod.POST)
	public String updateOrganisatieSubmit(
			@Valid OrganisatieForm organisatieForm,
			BindingResult organisatieFormResult, Model model) {

		if (!organisatieFormResult.hasErrors()) {

			/**
			 * De organisatie die we ontvangen vanuit het formulier heeft geen
			 * collectie adressen terwijl de bestaande organisatie in de
			 * database dat misschien wel heeft. Een update zou de collectie
			 * adressen overschrijven met een lege collectie, en dus ook uit de
			 * database verwijderen. Het formulier vullen met alle adressen
			 * werkt ook niet, want dan zit je met hetzelfde probleem wanneer de
			 * relatie een collectie rollen bevat.
			 */

			Organisatie organisatie = (Organisatie) relatieService
					.getByIdMetAdres(organisatieForm.getId());
			organisatie.setNaam(organisatieForm.getNaam());

			relatieService.update(organisatie);
			return "redirect:/getRelatie/" + organisatieForm.getId();
		}
		model.addAttribute("organisatieForm", organisatieForm);
		return "updatePersoon";
	}

	/*
	 * Save Relatie
	 */

	@RequestMapping(value = "/voegPersoonToe", method = RequestMethod.GET)
	public String voegPersoonToeForm(Model model) {
		model.addAttribute("persoonForm", new PersoonForm());
		return "voegPersoonToe";
	}

	@RequestMapping(value = "/voegPersoonToe", method = RequestMethod.POST)
	public String voegPersoonToeSubmit(@Valid PersoonForm persoonForm,
			BindingResult persoonFormResult, Model model) {
		if (!persoonFormResult.hasErrors()) {
			Persoon persoon = new Persoon(persoonForm.getVoornaam(),
					persoonForm.getTussenvoegsels(),
					persoonForm.getAchternaam(), persoonForm.getGeboortedatum());
			relatieService.save(persoon);
			return "redirect:/getAllRelaties";
		}
		model.addAttribute("persoonForm", persoonForm);
		return "voegPersoonToe";
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
			@Valid OrganisatieForm organisatieForm,
			BindingResult organisatieFormResult, Model model) {

		if (!organisatieFormResult.hasErrors()) {
			try {
				Organisatie organisatie = new Organisatie(
						organisatieForm.getNaam());
				relatieService.save(organisatie);
				return "redirect:/getAllRelaties";
			} catch (InvalidBusinessKeyException e) {
				// Moeten we dit nog een tweede keer afvangen? Validatie zou
				// moeten voorkomen dat deze exception word gegooid
			}
		}
		model.addAttribute("organisatieForm", organisatieForm);
		return "voegOrganisatieToe";
	}

	/*
	 * OUDE ADRESSEN!!!!!!!!!!!!!!!
	 */

	@RequestMapping(value = { "/voegAdresToe/{relatie_id}" }, method = RequestMethod.GET)
	public String getAdresForm(@PathVariable int relatie_id, Model model) {
		model.addAttribute("relatie",
				relatieService.getByIdMetAdres(relatie_id));
		model.addAttribute("adresForm", new AdresForm(relatie_id));
		return "voegAdresToe";
	}

	@RequestMapping(value = { "/voegPostbusToe/{relatie_id}" }, method = RequestMethod.GET)
	public String getPostbusForm(@PathVariable int relatie_id, Model model) {
		model.addAttribute("relatie",
				relatieService.getByIdMetAdres(relatie_id));
		model.addAttribute("postbusForm", new PostbusForm(relatie_id));
		return "voegPostbusToe";
	}

	@RequestMapping(value = "/voegAdresToe/{relatie_id}", method = RequestMethod.POST)
	public String setAdresForm(@PathVariable int relatie_id,
			@Valid AdresForm adresForm, BindingResult adresFormResult,
			Model model) {
		Relatie relatie = relatieService.getByIdMetAdres(relatie_id);
		if (!adresFormResult.hasErrors()) {
			Adres adres = new Adres(adresForm.getCorrespondentieAdres(), false,
					adresForm.getPostcode(), adresForm.getHuisnummer(),
					adresForm.getPlaats(), adresForm.getStraat());
			adres.maakStraat();
			relatie.addAdres(adres);
			relatieService.update(relatie);
			return "redirect:/getRelatie/" + relatie_id;
		}
		model.addAttribute("relatie", relatie);
		model.addAttribute("adresForm", adresForm);
		return "voegAdresToe";
	}

	@RequestMapping(value = "/voegPostbusToe/{relatie_id}", method = RequestMethod.POST)
	public String setPostbusForm(@PathVariable int relatie_id,
			@Valid PostbusForm postbusForm, BindingResult postbusFormResult,
			Model model) {
		Relatie relatie = relatieService.getByIdMetAdres(relatie_id);
		if (!postbusFormResult.hasErrors()) {
			Adres adres = new Adres(postbusForm.getCorrespondentieAdres(),
					true, postbusForm.getPostcode(),
					postbusForm.getPostbusnummer(), postbusForm.getPlaats(),
					"nvt");
			adres.maakPostbus();
			relatie.addAdres(adres);
			relatieService.update(relatie);
			return "redirect:/getRelatie/" + relatie_id;
		}
		model.addAttribute("relatie", relatie);
		model.addAttribute("postbusForm", postbusForm);
		return "voegPostbusToe";
	}

	@RequestMapping(value = "/getAdres/{relatie_id}/{adres_id}", method = RequestMethod.GET)
	public String getAdresForm(@PathVariable int relatie_id,
			@PathVariable int adres_id, Model model) {
		AdresForm adresForm = new AdresForm();
		Relatie relatie = relatieService.getByIdMetAdres(relatie_id);
		adresForm.setRelatieId(relatie_id);
		for (Adres adres : relatie.getAdressen()) {
			if (adres.getId() == adres_id) {
				adresForm.setAdresId(adres_id);
				adresForm.setStraat(adres.getStraat());
				adresForm.setHuisnummer(adres.getHuisOfPostbusNummer());
				adresForm.setPostcode(adres.getPostcode());
				adresForm.setPlaats(adres.getPlaats());
				adresForm.setCorrespondentieAdres(adres
						.isCorrespondentieAdres());
				adresForm.setPostbus(false);
			}
		}
		model.addAttribute("adresForm", adresForm);
		return "getAdres";
	}

	@RequestMapping(value = "/getPostbus/{relatie_id}/{adres_id}", method = RequestMethod.GET)
	public String getPostbusForm(@PathVariable int relatie_id,
			@PathVariable int adres_id, Model model) {
		PostbusForm postbusForm = new PostbusForm();
		Relatie relatie = relatieService.getByIdMetAdres(relatie_id);
		postbusForm.setRelatieId(relatie_id);
		for (Adres adres : relatie.getAdressen()) {
			if (adres.getId() == adres_id) {
				postbusForm.setPostbusId(adres_id);
				postbusForm.setPostbusnummer(adres.getHuisOfPostbusNummer());
				postbusForm.setPostcode(adres.getPostcode());
				postbusForm.setPlaats(adres.getPlaats());
				postbusForm.setCorrespondentieAdres(adres
						.isCorrespondentieAdres());
			}
		}
		model.addAttribute("postbusForm", postbusForm);
		return "getPostbus";
	}

	@RequestMapping(value = "/getAdres", method = RequestMethod.POST)
	public String setAdresForm(@Valid AdresForm adresForm,
			BindingResult adresFormResult, Model model) {

		if (!adresFormResult.hasErrors()) {
			Relatie relatie = relatieService.getByIdMetAdres(adresForm
					.getRelatieId());
			for (Adres adres : relatie.getAdressen()) {
				if (adres.getId() == adresForm.getAdresId()) {
					adres.setHuisOfPostbusNummer(adresForm.getHuisnummer());
					adres.setPostcode(adresForm.getPostcode());
					adres.setPlaats(adresForm.getPlaats());
					adres.setStraat(adresForm.getStraat());
					adres.maakStraat();
					if (adresForm.getCorrespondentieAdres()) {
						System.out
								.println("!!!!!! TRYING TO MAKE CORRESPONDENTIEADRES");
						adres.maakCorrespondentieAdres(relatie);
					}
				}
			}
			relatieService.update(relatie);
			return "redirect:/getRelatie/" + adresForm.getRelatieId();
		}
		model.addAttribute("adresForm", adresForm);
		return "getAdres";
	}

	@RequestMapping(value = "/getPostbus", method = RequestMethod.POST)
	public String getPostbusForm(@Valid PostbusForm postbusForm,
			BindingResult postbusFormResult, Model model) {

		if (!postbusFormResult.hasErrors()) {
			Relatie relatie = relatieService.getByIdMetAdres(postbusForm
					.getRelatieId());
			for (Adres adres : relatie.getAdressen()) {
				if (adres.getId() == postbusForm.getPostbusId()) {
					adres.setHuisOfPostbusNummer(postbusForm.getPostbusnummer());
					adres.setPostcode(postbusForm.getPostcode());
					adres.setPlaats(postbusForm.getPlaats());
					adres.maakPostbus();
					if (postbusForm.getCorrespondentieAdres()) {
						System.out
								.println("!!!!!! TRYING TO MAKE CORRESPONDENTIEADRES");
						adres.maakCorrespondentieAdres(relatie);
					}
				}
			}
			relatieService.update(relatie);
			return "redirect:/getRelatie/" + postbusForm.getRelatieId();
		}
		model.addAttribute("postbusForm", postbusForm);
		return "getPostbus";
	}

}