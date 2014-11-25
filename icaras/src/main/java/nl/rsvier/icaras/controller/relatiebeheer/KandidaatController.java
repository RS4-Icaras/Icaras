package nl.rsvier.icaras.controller.relatiebeheer;

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
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.core.relatiebeheer.Relatie;
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
public class KandidaatController {

	@Autowired
	private IRelatieService relatieService;

	@Autowired
	private IPersoonService persoonService;

	@Autowired
	private IOrganisatieService organisatieService;

	
	
	
	
	@RequestMapping(value = "/getKandidaat/{persoon_id}", method = RequestMethod.GET)
	public String oops2(@PathVariable int persoon_id, Model model) {

		Persoon persoon = persoonService.getByIdCompleet(persoon_id);
		model.addAttribute("persoon", persoon);
		model.addAttribute("kandidaat", persoon.getKandidaat());

		return "getKandidaat";
	}

	
	@RequestMapping(value = "/updateKandidaat/gegevens", method = RequestMethod.POST)
	public String oops3(@ModelAttribute("blaat") Persoon persoon, Model model) {

		if (persoon.getKandidaat() != null) {
			System.out.println("ja");
		}

		System.out.println(persoon);

		System.out.println(persoon.getId());
		System.out.println(persoon.getOpmerking());
		System.out.println(persoon.isGearchiveerd());

		model.addAttribute("persoon", persoon);
		return "getKandidaat";
	}

}