package nl.rsvier.icaras.core.relatiebeheer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
import nl.rsvier.icaras.core.arbeidsmarkt.Arbeidsovereenkomst;
import nl.rsvier.icaras.core.arbeidsmarkt.Vacature;

@Entity
public class Bedrijf extends OrganisatieRol {

	/*
	 * Attributen
	 */

	private static final long serialVersionUID = 1L;

	private Set<Arbeidsovereenkomst> arbeidsovereenkomsten;
	private Set<Vacature> vacatures;
	private Set<Aanbieding> aanbiedingen;
	private Set<Persoon> medewerkers;

	/*
	 * Constructoren
	 */

	public Bedrijf() {
		this.arbeidsovereenkomsten = new HashSet<Arbeidsovereenkomst>();
		this.vacatures = new HashSet<Vacature>();
		this.aanbiedingen = new HashSet<Aanbieding>();
		this.medewerkers = new HashSet<Persoon>();
	}

	/*
	 * Arbeidsovereenkomsten
	 */

	@OneToMany()
	public Set<Arbeidsovereenkomst> getArbeidsovereenkomst() {
		return arbeidsovereenkomsten;
	}

	public void setArbeidsovereenkomst(Set<Arbeidsovereenkomst> aanbiedingen) {
		this.arbeidsovereenkomsten = aanbiedingen;
	}

	public void addArbeidsovereenkomst(Arbeidsovereenkomst arbeidsovereenkomst) {
		if (arbeidsovereenkomst != null && !arbeidsovereenkomsten.contains(arbeidsovereenkomst)){
		this.arbeidsovereenkomsten.add(arbeidsovereenkomst);
		}
	}
	
	public boolean removeArbeidsovereenkomst(Arbeidsovereenkomst arbeidsovereenkomst) {
		if (arbeidsovereenkomst != null && arbeidsovereenkomsten.contains(arbeidsovereenkomst)) {
			//meerdere booleans, wanneer geeft deze methode true terug?
			removeMedewerker(arbeidsovereenkomst.getPersoon());
			removeAanbieding(arbeidsovereenkomst.getAanbieding());
			return arbeidsovereenkomsten.remove(arbeidsovereenkomst);
		}
		return false;
	}

	/*
	 * Vacatures
	 */

	@OneToMany(orphanRemoval = true, cascade = javax.persistence.CascadeType.ALL)
	public Set<Vacature> getVacatures() {
		return vacatures;
	}

	public void setVacatures(Set<Vacature> vacatures) {
		this.vacatures = vacatures;
	}

	/*
	 * Bij het maken van een nieuwe Aanbieding (die gedaan word n.a.v. een
	 * vacature) word de vacature al automatisch toegevoegd aan deze instantie
	 * van Bedrijf. Sta het dus niet toe om verder nog handmatig een aanroep te
	 * doen op deze methode zodat er geen tweede insert word gedaan (met als
	 * gevolg dat de insert een unique restraint breekt)
	 */
	public void addVacature(Vacature vacature) {
		if (vacature != null && !this.getVacatures().contains(vacature)) {
			this.vacatures.add(vacature);
		}
	}

	public boolean removeVacature(Vacature vacature) {
		return this.vacatures.remove(vacature);
	}

	/*
	 * Aanbiedingen
	 */

	@OneToMany(orphanRemoval = true, cascade = javax.persistence.CascadeType.ALL)
	public Set<Aanbieding> getAanbiedingen() {
		return aanbiedingen;
	}

	public void setAanbiedingen(Set<Aanbieding> aanbiedingen) {
		this.aanbiedingen = aanbiedingen;
	}

	public void addAanbieding(Aanbieding aanbieding) {
		if (aanbieding != null && !this.getAanbiedingen().contains(aanbieding)) {
			this.aanbiedingen.add(aanbieding);
		}
	}

	public boolean removeAanbieding(Aanbieding aanbieding) {
		return this.aanbiedingen.remove(aanbieding);
	}

	/*
	 * Medewerkers
	 */

	@OneToMany(orphanRemoval = true, cascade = javax.persistence.CascadeType.ALL)
	@NotNull
	public Set<Persoon> getMedewerkers() {
		return medewerkers;
	}

	@SuppressWarnings("unused")
	private void setMedewerkers(Set<Persoon> medewerkers) {
		this.medewerkers = medewerkers;
	}

	public boolean addMedewerker(Persoon medewerker) {
		if (medewerker != null && !getMedewerkers().contains(medewerker)
				&& medewerker.getWerknemer() != null && ((Werknemer) medewerker.getWerknemer()).werktBijBedrijf(this)){
		return this.medewerkers.add(medewerker);
		}
		return false;
	}
	//Mag deze methode public zijn?
	public boolean removeMedewerker(Persoon medewerker) {
		if (medewerker != null && medewerkers.contains(medewerker)){
			return medewerkers.remove(medewerker);
		}
		return false;
	}

	/*
	 * Utils
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 1;
		hash = prime * hash + this.getId();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !super.equals(obj) || !(obj instanceof Bedrijf)) {
			return false;
		} else {
			Bedrijf other = (Bedrijf) obj;
			if (this.getId() > 0 && other.getId() > 0
					&& this.getId() != other.getId()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Bedrijfsrol, subklasse van: " + super.toString();
	}

}
