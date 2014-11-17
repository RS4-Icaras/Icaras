package nl.rsvier.icaras.controller.relatiebeheer;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.service.relatiebeheer.IRelatieService;
import nl.rsvier.icaras.util.relatiebeheer.InvulOrganisatie;

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

	@RequestMapping(value = "/relatielijst")
	public String personenlijst(Model model) {
		model.addAttribute("relaties", relatieService.getAll());
		return "relatielijst";
	}

	@RequestMapping(value = "/voegOrganisatieToe", method = RequestMethod.GET)
	public String voegOrganisatieToeForm(Model model) {
		InvulOrganisatie invulOrganisatie = new InvulOrganisatie();
		model.addAttribute("invulOrganisatie", invulOrganisatie);
		return "voegOrganisatieToe";
	}

	@RequestMapping(value = "/voegOrganisatieToe", method = RequestMethod.POST)
	public String voegOrganisatieToeSubmit(
			@ModelAttribute InvulOrganisatie invulOrganisatie, Model model) throws InvalidBusinessKeyException {
		Organisatie organisatie = new Organisatie(invulOrganisatie.getNaam());
		relatieService.save(organisatie);
		return "redirect:/relatielijst";
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
		return "redirect:/relatielijst";
	}

	@RequestMapping(value = "/getPersoon/{persoonID}", method = RequestMethod.GET)
	public String adresgegevens(@PathVariable int persoonID, /* HttpSession */
			Model model) {
		model.addAttribute("persoonAdres", relatieService.getById(persoonID));
		return "adresgegevens";
	}

}