package nl.rsvier.icaras.controller.relatiebeheer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
import nl.rsvier.icaras.core.relatiebeheer.Adres;
import nl.rsvier.icaras.core.relatiebeheer.Bedrijf;
import nl.rsvier.icaras.core.relatiebeheer.Email;
import nl.rsvier.icaras.core.relatiebeheer.Facebook;
import nl.rsvier.icaras.core.relatiebeheer.Fax;
import nl.rsvier.icaras.core.relatiebeheer.Kandidaat;
import nl.rsvier.icaras.core.relatiebeheer.LinkedIn;
import nl.rsvier.icaras.core.relatiebeheer.Nfa;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.OrganisatieRol;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.core.relatiebeheer.PersoonsRol;
import nl.rsvier.icaras.core.relatiebeheer.Relatie;
import nl.rsvier.icaras.core.relatiebeheer.Rol;
import nl.rsvier.icaras.core.relatiebeheer.TelefoonNummer;
import nl.rsvier.icaras.core.relatiebeheer.Twitter;
import nl.rsvier.icaras.core.relatiebeheer.Website;
import nl.rsvier.icaras.form.relatiebeheer.AdresForm;
import nl.rsvier.icaras.form.relatiebeheer.NfaForm;
import nl.rsvier.icaras.form.relatiebeheer.OrganisatieForm;
import nl.rsvier.icaras.form.relatiebeheer.PersoonForm;
import nl.rsvier.icaras.form.relatiebeheer.PostbusForm;
import nl.rsvier.icaras.service.relatiebeheer.IOrganisatieService;
import nl.rsvier.icaras.service.relatiebeheer.IPersoonService;
import nl.rsvier.icaras.service.relatiebeheer.IRelatieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	@Autowired
	private IPersoonService persoonService;

	@Autowired
	private IOrganisatieService organisatieService;

	/*
	 * Tijdelijke hulpmethode totdat relatieService een nfalijst initialiseert
	 */
	public boolean isPersoonsId(int relatie_id) {
		// TODO: Is het echt nodig twee keer de database aan te roepen zonder
		// relatieService?!
		Relatie relatie = relatieService.getById(relatie_id);
		if (relatie instanceof Persoon) {
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/start")
	public String start(Model model) throws InvalidBusinessKeyException {

		Adres aBurrow1 = new Adres(true, false, "2022PG", "1",
				"Ottery St. Catchpole", "on the outskirts of Devon");
		Adres aBurrow2 = new Adres(true, false, "2022PG", "1",
				"Ottery St. Catchpole", "on the outskirts of Devon");
		Adres aBurrow3 = new Adres(true, false, "2022PG", "1",
				"Ottery St. Catchpole", "on the outskirts of Devon");

		Adres aHogwarts = new Adres(true, true, "4501MG", "1402", "Edinburgh",
				"");
		Adres aPartyshop = new Adres(false, false, "0199TT", "93", "London",
				"Diagon Alley");

		Persoon harry = new Persoon("Harry", "Potter");
		harry.addAdres(new Adres(true, false, "1340DF", "4", "Little Whinging",
				"Privet Drive"));
		harry.addRol(new Kandidaat());

		// //////////////////////

		Nfa harry_twitter = new Twitter();
		harry_twitter.setNfaAdres("@harry");
		harry.addNfa(harry_twitter);

		Nfa harry_website = new Website();
		harry_website.setNfaAdres("http://www.pottermore.com");
		harry.addNfa(harry_website);

		Nfa harry_telefoon = new TelefoonNummer();
		harry_telefoon.setNfaAdres("+31634504345");
		harry.addNfa(harry_telefoon);

		// //////////////////////

		Persoon ron = new Persoon("Ron", "Weasley");
		ron.addAdres(aBurrow1);

		Persoon hermione = new Persoon("Hermione", "Granger");

		Organisatie hogwarts = new Organisatie(
				"Hogwarts School of Witchcraft and Wizardry");
		hogwarts.addAdres(aHogwarts);
		hogwarts.addRol(new Bedrijf());
		Organisatie gringotts = new Organisatie("Gringotts Wizarding Bank");
		gringotts.addRol(new Bedrijf());
		Organisatie leakycauldron = new Organisatie("The Leaky Cauldron");
		Organisatie ollivanders = new Organisatie("Ollivanders");
		Organisatie wizardwheezes = new Organisatie("Weasleys' Wizard Wheezes");
		wizardwheezes.addAdres(aPartyshop);
		wizardwheezes.addRol(new Bedrijf());

		Persoon hagrid = new Persoon("Rubeus", "Hagrid");

		Persoon twins_george = new Persoon("George", "Weasley");
		twins_george.addAdres(aBurrow2);

		Persoon twins_fred = new Persoon("Fred", "Weasley");
		twins_fred.addAdres(aBurrow3);

		persoonService.save(harry);
		persoonService.save(twins_fred);
		organisatieService.save(wizardwheezes);
		organisatieService.save(hogwarts);
		persoonService.save(ron);
		organisatieService.save(ollivanders);
		organisatieService.save(gringotts);
		persoonService.save(twins_george);
		organisatieService.save(leakycauldron);
		persoonService.save(hagrid);
		persoonService.save(hermione);

		Persoon profdumbledore = new Persoon("Albert", "Dumbledore");
		persoonService.save(profdumbledore);

		new Aanbieding(harry, hogwarts);
		persoonService.update(harry);
		organisatieService.update(hogwarts);

		new Aanbieding(harry, wizardwheezes);
		persoonService.update(harry);
		organisatieService.update(wizardwheezes);

		return "redirect:/getAllRelaties";
	}

	/*
	 * Get All
	 */

	@RequestMapping(value = { "/getAllRelaties" })
	public String getAllRelaties(Model model) {
		model.addAttribute("relaties", relatieService.getAll());
		return "getAllRelaties";
	}

	@RequestMapping(value = { "/getAllPersonen" })
	public String getAllPersonen(Model model) {
		model.addAttribute("personen", persoonService.getAllCompleet());
		return "getAllPersonen";
	}

	@RequestMapping(value = { "/getAllOrganisaties" })
	public String getAllOrganisaties(Model model) {
		model.addAttribute("organisaties", organisatieService.getAllCompleet());
		return "getAllOrganisaties";
	}

	/*
	 * Get Relatie
	 */

	@RequestMapping(value = { "/getPersoon/{relatie_id}" }, method = RequestMethod.GET)
	public String getPersoon(@PathVariable int relatie_id, Model model) {

		Persoon persoon = persoonService.getByIdCompleet(relatie_id);

		// SSSsssst!
		Map<String, PersoonsRol> rollenMap = new HashMap<String, PersoonsRol>();

		rollenMap.put("Kandidaat", persoon.getKandidaat());
		rollenMap.put("Contactpersoon", persoon.getContactpersoon());
		rollenMap.put("Werknemer", persoon.getWerknemer());
		rollenMap.put("Aanmelder", null);
		rollenMap.put("Cursist", null);

		model.addAttribute("persoonForm", new PersoonForm(persoon));
		model.addAttribute("persoon", persoon);
		model.addAttribute("rollenMap", rollenMap);
		return "getPersoon";
	}

	@RequestMapping(value = { "/getOrganisatie/{relatie_id}" }, method = RequestMethod.GET)
	public String getOrganisatie(@PathVariable int relatie_id, Model model) {

		Organisatie organisatie = organisatieService
				.getByIdCompleet(relatie_id);
		
		// SSSsssst!
		Map<String, OrganisatieRol> rollenMap = new HashMap<String, OrganisatieRol>();

		rollenMap.put("Bedrijf", organisatie.getBedrijf());
		rollenMap.put("Leverancier", organisatie.getLeverancier());
				
		model.addAttribute("organisatieForm", new OrganisatieForm(organisatie));
		model.addAttribute("organisatie", organisatie);
		model.addAttribute("rollenMap", rollenMap);
		return "getOrganisatie";
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
			return "redirect:/getPersoon/" + persoonForm.getId();
		}
		model.addAttribute("persoonForm", persoonForm);
		model.addAttribute("relatie", persoon); // Voor adressen
		return "getPersoon";
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

			Organisatie organisatie = organisatieService
					.getByIdCompleet(organisatieForm.getId());
			organisatie.setNaam(organisatieForm.getNaam());

			organisatieService.update(organisatie);
			return "redirect:/getOrganisatie/" + organisatieForm.getId();
		}
		model.addAttribute("organisatieForm", organisatieForm);
		return "updateOrganisatie";
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
			persoonService.save(persoon);
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
				organisatieService.save(organisatie);
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
	 * Adres & Postbus
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

	@RequestMapping(value = "/voegAdresToe", method = RequestMethod.POST)
	public String saveAdres(@Valid AdresForm adresForm,
			BindingResult adresFormResult, Model model) {

		if (!adresFormResult.hasErrors()) {
			if (isPersoonsId(adresForm.getRelatieId())) {
				Persoon persoon = persoonService.getByIdCompleet(adresForm
						.getRelatieId());
				Adres adres = new Adres(adresForm.getCorrespondentieAdres(),
						false, adresForm.getPostcode(),
						adresForm.getHuisnummer(), adresForm.getPlaats(),
						adresForm.getStraat());
				adres.maakStraat();
				persoon.addAdres(adres);
				persoonService.update(persoon);
				return "redirect:/getPersoon/" + adresForm.getRelatieId();
			} else {
				Organisatie organisatie = organisatieService
						.getByIdCompleet(adresForm.getRelatieId());
				Adres adres = new Adres(adresForm.getCorrespondentieAdres(),
						false, adresForm.getPostcode(),
						adresForm.getHuisnummer(), adresForm.getPlaats(),
						adresForm.getStraat());
				adres.maakStraat();
				organisatie.addAdres(adres);
				organisatieService.update(organisatie);
				return "redirect:/getOrganisatie/" + adresForm.getRelatieId();
			}
		}
		model.addAttribute("adresForm", adresForm);
		return "voegAdresToe";
	}

	@RequestMapping(value = "/voegPostbusToe", method = RequestMethod.POST)
	public String savePostbus(@Valid PostbusForm postbusForm,
			BindingResult postbusFormResult, Model model) {

		if (!postbusFormResult.hasErrors()) {
			if (isPersoonsId(postbusForm.getRelatieId())) {
				Persoon persoon = persoonService.getByIdCompleet(postbusForm
						.getRelatieId());
				Adres adres = new Adres(postbusForm.getCorrespondentieAdres(),
						true, postbusForm.getPostcode(),
						postbusForm.getPostbusnummer(),
						postbusForm.getPlaats(), "nvt");
				adres.maakPostbus();
				persoon.addAdres(adres);
				persoonService.update(persoon);
				return "redirect:/getPersoon/" + postbusForm.getRelatieId();
			} else {
				Organisatie organisatie = organisatieService
						.getByIdCompleet(postbusForm.getRelatieId());
				Adres adres = new Adres(postbusForm.getCorrespondentieAdres(),
						true, postbusForm.getPostcode(),
						postbusForm.getPostbusnummer(),
						postbusForm.getPlaats(), "nvt");
				adres.maakPostbus();
				organisatie.addAdres(adres);
				organisatieService.update(organisatie);
				return "redirect:/getOrganisatie/" + postbusForm.getRelatieId();
			}
		}
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
			if (isPersoonsId(adresForm.getRelatieId())) {
				Persoon persoon = persoonService.getByIdCompleet(adresForm
						.getRelatieId());
				for (Adres adres : persoon.getAdressen()) {
					if (adres.getId() == adresForm.getAdresId()) {
						/*
						 * maakCorrespondentieAdres controleerd of het adres wel
						 * gekoppeld is aan de relatie. Roep de methode dus aan
						 * voordat je het adres wijzigt, want anders kan de
						 * methode het adres niet meer vinden
						 */
						if (adresForm.getCorrespondentieAdres()) {
							adres.maakCorrespondentieAdres(persoon);
						}
						// Nu is het veilig het adres aan te passen
						adres.setHuisOfPostbusNummer(adresForm.getHuisnummer());
						adres.setPostcode(adresForm.getPostcode());
						adres.setPlaats(adresForm.getPlaats());
						adres.setStraat(adresForm.getStraat());
						adres.maakStraat();
					}
				}
				persoonService.update(persoon);
				return "redirect:/getPersoon/" + adresForm.getRelatieId();
			} else {
				Organisatie organisatie = organisatieService
						.getByIdCompleet(adresForm.getRelatieId());
				for (Adres adres : organisatie.getAdressen()) {
					if (adres.getId() == adresForm.getAdresId()) {
						/*
						 * maakCorrespondentieAdres controleerd of het adres wel
						 * gekoppeld is aan de relatie. Roep de methode dus aan
						 * voordat je het adres wijzigt, want anders kan de
						 * methode het adres niet meer vinden
						 */
						if (adresForm.getCorrespondentieAdres()) {
							adres.maakCorrespondentieAdres(organisatie);
						}
						// Nu is het veilig het adres aan te passen
						adres.setHuisOfPostbusNummer(adresForm.getHuisnummer());
						adres.setPostcode(adresForm.getPostcode());
						adres.setPlaats(adresForm.getPlaats());
						adres.setStraat(adresForm.getStraat());
						adres.maakStraat();
					}
				}
				organisatieService.update(organisatie);
				return "redirect:/getOrganisatie/" + adresForm.getRelatieId();
			}
		}
		model.addAttribute("adresForm", adresForm);
		return "getAdres";
	}

	@RequestMapping(value = "/getPostbus", method = RequestMethod.POST)
	public String getPostbusForm(@Valid PostbusForm postbusForm,
			BindingResult postbusFormResult, Model model) {

		if (!postbusFormResult.hasErrors()) {

			if (isPersoonsId(postbusForm.getRelatieId())) {
				Persoon persoon = persoonService.getByIdCompleet(postbusForm
						.getRelatieId());

				for (Adres adres : persoon.getAdressen()) {
					if (adres.getId() == postbusForm.getPostbusId()) {

						/*
						 * maakCorrespondentieAdres controleerd of het adres wel
						 * gekoppeld is aan de relatie. Roep de methode dus aan
						 * voordat je het adres wijzigt, want anders kan de
						 * methode het adres niet meer vinden
						 */
						if (postbusForm.getCorrespondentieAdres()) {
							adres.maakCorrespondentieAdres(persoon);
						}

						// Nu is het veilig het adres aan te passen
						adres.setHuisOfPostbusNummer(postbusForm
								.getPostbusnummer());
						adres.setPostcode(postbusForm.getPostcode());
						adres.setPlaats(postbusForm.getPlaats());
						adres.maakPostbus();

					}
				}
				persoonService.update(persoon);
				return "redirect:/getPersoon/" + postbusForm.getRelatieId();
			} else {
				Organisatie organisatie = organisatieService
						.getByIdCompleet(postbusForm.getRelatieId());

				for (Adres adres : organisatie.getAdressen()) {
					if (adres.getId() == postbusForm.getPostbusId()) {

						/*
						 * maakCorrespondentieAdres controleerd of het adres wel
						 * gekoppeld is aan de relatie. Roep de methode dus aan
						 * voordat je het adres wijzigt, want anders kan de
						 * methode het adres niet meer vinden
						 */
						if (postbusForm.getCorrespondentieAdres()) {
							adres.maakCorrespondentieAdres(organisatie);
						}

						// Nu is het veilig het adres aan te passen
						adres.setHuisOfPostbusNummer(postbusForm
								.getPostbusnummer());
						adres.setPostcode(postbusForm.getPostcode());
						adres.setPlaats(postbusForm.getPlaats());
						adres.maakPostbus();

					}
				}
				organisatieService.update(organisatie);
				return "redirect:/getOrganisatie/" + postbusForm.getRelatieId();
			}
		}
		model.addAttribute("postbusForm", postbusForm);
		return "getPostbus";
	}

	/*
	 * Nfa's
	 */

	@RequestMapping(value = "/voegNfaToe/{relatie_id}", method = RequestMethod.GET)
	public String getNfa(@PathVariable int relatie_id, Model model) {
		model.addAttribute("nfaForm", new NfaForm(relatie_id));
		return "voegNfaToe";
	}

	@RequestMapping(value = { "/voegNfaToe" }, method = RequestMethod.POST)
	public String saveNfa(@Valid NfaForm nfaForm, BindingResult nfaFormResult,
			Model model) {

		if (!nfaFormResult.hasErrors()) {

			Nfa nfa = null;
			switch (nfaForm.getNfaSoort()) {
			case EMAIL:
				nfa = new Email();
				break;
			case FACEBOOK:
				nfa = new Facebook();
				break;
			case FAX:
				nfa = new Fax();
				break;
			case LINKEDIN:
				nfa = new LinkedIn();
				break;
			case TELEFOONNUMMER:
				nfa = new TelefoonNummer();
				break;
			case TWITTER:
				nfa = new Twitter();
				break;
			case WEBSITE:
				nfa = new Website();
				break;
			default:
				break;
			}

			nfa.setNfaAdres(nfaForm.getNfaAdres());
			nfa.setExtraInfo(nfaForm.getExtraInfo());

			if (this.isPersoonsId(nfaForm.getRelatieId())) {
				Persoon persoon = persoonService.getByIdCompleet(nfaForm
						.getRelatieId());
				persoon.addNfa(nfa);
				persoonService.update(persoon);
				return "redirect:/getPersoon/" + nfaForm.getRelatieId();
			} else {
				Organisatie organisatie = organisatieService
						.getByIdCompleet(nfaForm.getRelatieId());
				organisatie.addNfa(nfa);
				organisatieService.update(organisatie);
				return "redirect:/getOrganisatie/" + nfaForm.getRelatieId();
			}

		}

		model.addAttribute("nfaForm", nfaForm);
		return "voegNfaToe";
	}

	@RequestMapping(value = { "/getNfa/{relatie_id}/{nfa_id}" }, method = RequestMethod.GET)
	public String getNfa(@PathVariable int relatie_id,
			@PathVariable int nfa_id, Model model) {

		NfaForm nfaForm = new NfaForm();
		nfaForm.setRelatieId(relatie_id);
		nfaForm.setNfaId(nfa_id);

		if (this.isPersoonsId(relatie_id)) {
			Persoon persoon = persoonService
					.getByIdMetAdressenEnNfaLijst(relatie_id);
			for (Nfa nfa : persoon.getNfaLijst()) {
				if (nfa.getId() == nfa_id) {
					nfaForm.setNfaAdres(nfa.getNfaAdres());
					nfaForm.setExtraInfo(nfa.getExtraInfo());
					nfaForm.setNfaSoort(nfa.getNfaSoort());
				}
			}
		} else {
			Organisatie organisatie = organisatieService
					.getByIdMetAdressenEnNfaLijst(relatie_id);
			for (Nfa nfa : organisatie.getNfaLijst()) {
				if (nfa.getId() == nfa_id) {
					nfaForm.setNfaAdres(nfa.getNfaAdres());
					nfaForm.setExtraInfo(nfa.getExtraInfo());
					nfaForm.setNfaSoort(nfa.getNfaSoort());
				}
			}
		}

		model.addAttribute("nfaForm", nfaForm);
		return "getNfa";
	}

	@RequestMapping(value = "/getNfa", method = RequestMethod.POST)
	public String updateNfa(@Valid NfaForm nfaForm,
			BindingResult nfaFormResult, Model model) {

		// TODO: Sessionvars instead of get params?

		if (!nfaFormResult.hasErrors()) {

			if (this.isPersoonsId(nfaForm.getRelatieId())) {
				Persoon persoon = persoonService
						.getByIdMetAdressenEnNfaLijst(nfaForm.getRelatieId());
				for (Nfa nfa : persoon.getNfaLijst()) {
					if (nfa.getId() == nfaForm.getNfaId()) {
						nfa.setNfaAdres(nfaForm.getNfaAdres());
						nfa.setExtraInfo(nfaForm.getExtraInfo());
					}
				}
				persoonService.update(persoon);
				return "redirect:/getPersoon/" + nfaForm.getRelatieId();
			} else {
				Organisatie organisatie = organisatieService
						.getByIdMetAdressenEnNfaLijst(nfaForm.getRelatieId());
				for (Nfa nfa : organisatie.getNfaLijst()) {
					if (nfa.getId() == nfaForm.getNfaId()) {
						nfa.setNfaAdres(nfaForm.getNfaAdres());
						nfa.setExtraInfo(nfaForm.getExtraInfo());
					}
				}
				organisatieService.update(organisatie);
				return "redirect:/getOrganisatie/" + nfaForm.getRelatieId();
			}

		}
		model.addAttribute("nfaForm", nfaForm);
		return "getNfa";
	}

}