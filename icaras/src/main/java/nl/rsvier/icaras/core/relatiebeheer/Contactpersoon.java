package nl.rsvier.icaras.core.relatiebeheer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Contactpersoon extends PersoonsRol {

	private static final long serialVersionUID = 1L;

	private List<Organisatie> organisaties;
	private String functie;

	/*
	 * Constructor
	 */

	public Contactpersoon() {
		this.organisaties = new ArrayList<Organisatie>();
	}

	/*
	 * Organisaties vertegenwoordigt door deze contactpersoon
	 */

	@OneToMany()
	@NotNull
	public List<Organisatie> getOrganisaties() {
		return organisaties;
	}

	public void setOrganisaties(List<Organisatie> organisaties) {
		this.organisaties = organisaties;
	}

	public boolean addOrganisatie(Organisatie organisatie, Persoon persoon) {

		/*
		 * TODO: Controleer: Contactpersoon voegt geen persoon toe aan een
		 * bedrijf als medewerker, dat lijkt me de verantwoordelijkheid van de
		 * werknemersrol
		 */

		boolean a = false;
		boolean b = false;

		if (!this.organisatieMagWordenToegevoegd(organisatie)) {
			a = this.getOrganisaties().add(organisatie);
		}

		if (persoon != null
				&& !organisatie.getContactpersonen().contains(persoon)) {
			b = organisatie.addContactpersoon(persoon);
		}

		return a && b;

	}

	public void removeOrganisatie(Organisatie organisatie) {
		if (this.organisaties.contains(organisatie)) {
			this.organisaties.remove(organisatie);
		}
	}

	public void clearOrganisaties() {
		this.organisaties.clear();
	}

	public boolean organisatieIsToegevoegd(Organisatie organisatie) {
		return this.getOrganisaties().contains(organisatie);
	}

	public boolean organisatieConstraint(Organisatie organisatie) {
		return true;
	}

	public boolean organisatieMagWordenToegevoegd(Organisatie organisatie) {
		return !this.organisatieIsToegevoegd(organisatie)
				&& this.organisatieConstraint(organisatie);
	}

	/*
	 * Functie
	 */

	@Column(unique = true, updatable = false)
	@NotNull
	public String getFunctie() {
		return this.functie;
	}

	public void setFunctie(String str) {
		this.functie = str;
	}

	/*
	 * Utils
	 */

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		} else if (obj == null || !(obj instanceof Contactpersoon)) {
			return false;
		} else {
			Contactpersoon other = (Contactpersoon) obj;
			if (!this.getFunctie().equals(other.getFunctie())) {
				return false;
			}

		}
		return true;
	}

	/*
	 * TODO: Remove?!
	 */
	/*
	 * public void addOrganisatie2(Organisatie organisatie) {
	 * 
	 * // Voeg alleen toe wanneer dit nog niet eerder gedaan is om infinite //
	 * loops te voorkomen if (!this.organisaties.contains(organisatie)) {
	 * this.organisaties.add(organisatie); }
	 * 
	 * // Loop over de lijst contactpersonen om de juiste instantie van Persoon
	 * // eruit te vissen for (Persoon p : organisatie.getContactpersonen()) {
	 * // TODO: What if // Organisatie // does not have // contactpersonen //
	 * yet? // Hebben we de juiste Persoon al gevonden? if
	 * (p.getContactpersoon().equals(this)) { // Is de contactpersoon nog niet
	 * eerder toegevoegd? if (!organisatie.getContactpersonen().contains(p)) {
	 * // Voeg toe organisatie.addContactpersoon(p); } } }
	 * 
	 * }
	 */
}
