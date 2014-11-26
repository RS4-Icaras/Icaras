package nl.rsvier.icaras.controller.relatiebeheer;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
import nl.rsvier.icaras.core.relatiebeheer.Bedrijf;
import nl.rsvier.icaras.core.relatiebeheer.Kandidaat;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.service.relatiebeheer.IOrganisatieService;
import nl.rsvier.icaras.service.relatiebeheer.IPersoonService;
import nl.rsvier.icaras.service.relatiebeheer.IRelatieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class KandidaatController {

	@Autowired
	private IRelatieService relatieService;

	@Autowired
	private IPersoonService persoonService;

	@Autowired
	private IOrganisatieService organisatieService;

	/*
	 * Rollen
	 */

	@RequestMapping(value = "/voegKandidaatToe/{persoon_id}", method = RequestMethod.GET)
	public String oops1(@PathVariable int persoon_id, Model model) {

		Persoon persoon = persoonService.getByIdCompleet(persoon_id);
		persoon.addRol(new Kandidaat()); // TODO: verantwoordelijkheid van
											// service of core
		persoonService.update(persoon);
		return "redirect:/getKandidaat/" + persoon_id;
	}

	@RequestMapping(value = "/getKandidaat/{persoon_id}", method = RequestMethod.GET)
	public String oops2(@PathVariable int persoon_id, Model model) {

		Persoon persoon = persoonService.getByIdCompleet(persoon_id);
		model.addAttribute("persoon", persoon);
		model.addAttribute("organisaties",
				organisatieService.getAllAanTeBiedenOrganisaties(persoon));

		return "getKandidaat";
	}

	@RequestMapping(value = "/biedPersoonAan/{persoon_id}/{organisatie_id}", method = RequestMethod.GET)
	public String oops3(@PathVariable int persoon_id,
			@PathVariable int organisatie_id, Model model) {

		Persoon persoon = persoonService.getByIdCompleet(persoon_id);
		Organisatie organisatie = organisatieService
				.getByIdCompleet(organisatie_id);

		try {
			new Aanbieding(persoon, organisatie);
			persoonService.update(persoon);
			organisatieService.update(organisatie);
		} catch (InvalidBusinessKeyException e) {

		}

		model.addAttribute("persoon", persoon);
		model.addAttribute("organisaties",
				organisatieService.getAllAanTeBiedenOrganisaties(persoon));

		return "getKandidaat";
	}

	// @RequestMapping(value =
	// "/biedOrganisatieAan/{persoon_id}/{organisatie_id}", method =
	// RequestMethod.GET)
	// public String oops4(@PathVariable int persoon_id, @PathVariable int
	// organisatie_id, Model model) {
	//
	// Persoon persoon = persoonService.getByIdCompleet(persoon_id);
	// Organisatie organisatie =
	// organisatieService.getByIdCompleet(organisatie_id);
	//
	// try {
	// new Aanbieding(persoon, organisatie);
	// persoonService.update(persoon);
	// organisatieService.update(organisatie);
	// } catch (InvalidBusinessKeyException e) {
	//
	// }
	//
	// model.addAttribute("persoon", persoon);
	// model.addAttribute("organisaties",
	// organisatieService.getAllMetBedrijfsrol());
	//
	// return "getKandidaat";
	// }

	@RequestMapping(value = "/voegBedrijfToe/{organisatie_id}", method = RequestMethod.GET)
	public String oops5(@PathVariable int organisatie_id, Model model) {

		Organisatie organisatie = organisatieService
				.getByIdCompleet(organisatie_id);
		organisatie.addRol(new Bedrijf()); // TODO: verantwoordelijkheid van
											// service of core
		organisatieService.update(organisatie);
		return "redirect:/getBedrijf/" + organisatie_id;
	}
	
	@RequestMapping(value = "/getBedrijf/{organisatie_id}", method = RequestMethod.GET)
	public String oops6(@PathVariable int organisatie_id, Model model) {

		Organisatie organisatie = organisatieService.getByIdCompleet(organisatie_id);
		model.addAttribute("organisatie", organisatie);

		return "getBedrijf";
	}

}