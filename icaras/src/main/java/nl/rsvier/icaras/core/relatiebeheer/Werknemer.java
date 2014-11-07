
package nl.rsvier.icaras.core.relatiebeheer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import nl.rsvier.icaras.core.arbeidsmarkt.Arbeidsovereenkomst;


/**
 * Deze klasse representeert de werknemersRol. Houdt een lijst van arbeidsovereenkomsten bij.
 */
@Entity
public class Werknemer extends PersoonsRol {
	
	
	private static final long serialVersionUID = 1L;
	private List<Arbeidsovereenkomst> arbeidsovereenkomsten = new ArrayList<Arbeidsovereenkomst>();
		
	@OneToMany(cascade = CascadeType.ALL)
	public List<Arbeidsovereenkomst> getArbeidsovereenkomsten() {
		return arbeidsovereenkomsten;
	}

	@SuppressWarnings("unused") //wordt wel gebruikt door Hibernate
	private void setArbeidsovereenkomsten(List<Arbeidsovereenkomst> arbeidsovereenkomsten) {
		this.arbeidsovereenkomsten = arbeidsovereenkomsten;
	}
	
	public boolean werktBijBedrijf(Bedrijf bedrijf) {
		for (Arbeidsovereenkomst a: getArbeidsovereenkomsten()){
			if (a.getOrganisatie().getBedrijf() == (bedrijf)) {
			return true;
			}
		}
		return false;
	}

	/**
	 * Voegt de arbeidsoverenkomst alleen toe aan deze werknemer 
	 * als aanbieding niet null is.
	 * 
	 * Roept de setpersoon aan van arbeidsovereenkomst 
	 * die als parameter de persoon van deze aanbieding meekrijgt.
	 * 
	 * @param Arbeidsovereenkomst die een aanbieding toegekend heeft gekregen.
	 */
	//TODO ook organisatie toekennen in deze methode

	public void addArbeidsovereenkomst(Arbeidsovereenkomst ao) {
		if(ao.getAanbieding() != null && ao.getAanbieding().getPersoon() != null){//gecheckt in Aanbieding
			//Dezelfde voorwaarde moet gecheckt worden voor Organisatie
			//nog checken op boolean isGetekend// Later checken, geen eis voor toevoegen
			if (!arbeidsovereenkomsten.contains(ao)){
				this.arbeidsovereenkomsten.add(ao);
			((Bedrijf)ao.getOrganisatie().getRol(Bedrijf.class)).addMedewerker(ao.getPersoon());
			}
		} 
		
	}
	
}
