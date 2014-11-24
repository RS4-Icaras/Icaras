package nl.rsvier.icaras.core.relatiebeheer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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
	 * Colllectie: Arbeidsovereenkomsten
	 */

	@OneToMany(orphanRemoval = true, cascade = javax.persistence.CascadeType.ALL)
	public Set<Arbeidsovereenkomst> getArbeidsovereenkomsten() {
		return arbeidsovereenkomsten;
	}

	public boolean heeftArbeidsovereenkomst(
			Arbeidsovereenkomst arbeidsovereenkomst) {
		return this.getArbeidsovereenkomsten().contains(arbeidsovereenkomst);
	}

	@SuppressWarnings("unused")
	private void setArbeidsovereenkomsten(Set<Arbeidsovereenkomst> aanbiedingen) {
		this.arbeidsovereenkomsten = aanbiedingen;
	}

	/**
	 * Voegt de gegeven arbeidsovereenkomst toe aan dit bedrijf. De
	 * arbeidsovereenkomst wordt alleen toegevoegd als deze niet null is, nog
	 * niet voorkomt in de lijst en aan zowel een persoon met werknemer(rol) als
	 * aan de juiste organisatie refereert.
	 * 
	 * Voegt tevens deze arbeidsovereenkomst toe aan de betreffende
	 * werknemer(rol) van de persoon van deze arbeidsovereenkomst. Die persoon
	 * wordt ook toegevoegd aan de lijst van werknemers van dit bedrijf.
	 * 
	 * @param arbeidsovereenkomst
	 *            De toe te voegen arbeidsovereenkomst.
	 * @return True als na afloop de arbeidsovereenkomst in zowel de lijst van
	 *         dit bedrijf als die van de betreffende werknemer voorkomt en
	 *         bovendien de persoon van de arbeidsovereenkomst medewerker is bij
	 *         dit bedrijf. Als aan een van deze eisen niet wordt voldaan, wordt
	 *         false teruggegeven.
	 */
	public synchronized boolean addArbeidsovereenkomst(
			Arbeidsovereenkomst arbeidsovereenkomst) {
		if (arbeidsovereenkomst != null
				&& !heeftArbeidsovereenkomst(arbeidsovereenkomst)) {
			Persoon medewerker = arbeidsovereenkomst.getPersoon();
			Werknemer werknemer = medewerker.getWerknemer();
			Organisatie organisatie = arbeidsovereenkomst.getOrganisatie();
			if (medewerker != null && werknemer != null
					// controleer of het de juiste organisatie is
					&& organisatie.getBedrijf().equals(this)
					&& this.getArbeidsovereenkomsten().add(arbeidsovereenkomst)) {
				werknemer.addArbeidsovereenkomst(arbeidsovereenkomst);
				this.addMedewerker(medewerker);
				return this.heeftArbeidsovereenkomst(arbeidsovereenkomst)
						&& this.isMedewerker(medewerker)
						&& werknemer
								.heeftArbeidsovereenkomst(arbeidsovereenkomst);
			}
		}
		return false;
	}

	/**
	 * Verwijderd de opgegeven arbeidsovereenkomst bij dit bedrijf. De
	 * arbeidsovereenkomst wordt alleen verwijderd als deze niet null is en naar
	 * zowel een persoon als een organisatie refereert.
	 * 
	 * Verwijderd tevens deze arbeidsovereenkomst bij de betreffende
	 * werknemer(rol) van de persoon van deze arbeidsovereenkomst. Als die
	 * persoon zonder de gegeven arbeidsovereenkomst geen medewerker meer is van
	 * dit bedrijf, dan wordt de medewerker verwijderd uit de lijst van
	 * werknemers van dit bedrijf.
	 * 
	 * @param arbeidsovereenkomst
	 *            De te verwijderen arbeidsovereenkomst.
	 * @return True als na afloop de gegeven arbeidsovereenkomst niet voorkomt
	 *         in noch de lijst van dit bedrijf en die van de betreffende
	 *         werknemer. Als dit de enige arbeidsovereenkomst was die de
	 *         betreffende persoon had met dit bedrijf, mag die tevens geen
	 *         medewerker meer zijn bij dit bedrijf. Als aan een van deze eisen
	 *         niet wordt voldaan, wordt false teruggegeven.
	 */
	public synchronized boolean removeArbeidsovereenkomst(
			Arbeidsovereenkomst arbeidsovereenkomst) {
		if (arbeidsovereenkomst != null
				&& this.heeftArbeidsovereenkomst(arbeidsovereenkomst)) {
			Persoon medewerker = arbeidsovereenkomst.getPersoon();
			Werknemer werknemer = medewerker.getWerknemer();
			if (medewerker != null && werknemer != null) {
				this.getArbeidsovereenkomsten().remove(arbeidsovereenkomst);
				werknemer.removeArbeidsovereenkomst(arbeidsovereenkomst);
				this.removeMedewerker(medewerker);
				return !this.heeftArbeidsovereenkomst(arbeidsovereenkomst)
						&& !werknemer
								.heeftArbeidsovereenkomst(arbeidsovereenkomst);
			}
		}
		return false;
	}

	/*
	 * Collectie: Vacatures
	 */

	@OneToMany(orphanRemoval = true, cascade = javax.persistence.CascadeType.ALL)
	public Set<Vacature> getVacatures() {
		return vacatures;
	}

	@SuppressWarnings("unused")
	private void setVacatures(Set<Vacature> vacatures) {
		this.vacatures = vacatures;
	}

	/**
	 * Creëer bi-directionele relatie tussen Vacature > Bedrijf > Organisatie
	 * 
	 * @param vacature
	 *            Vacature waar dit bedrijf aan moet worden gekoppeld
	 */
	public synchronized boolean addVacature(Vacature vacature) {
		if (this.vacatureMagWordenToegevoegd(vacature)) {
			this.getVacatures().add(vacature);
			return this.heeftVacature(vacature);
		}
		return false;
	}

	/**
	 * Verbreek bi-directionele relatie tussen Vacature > Bedrijf > Organisatie
	 * 
	 * @param vacature
	 *            Vacature waar dit bedrijf van moet worden losgekoppeld
	 */
	public synchronized boolean removeVacature(Vacature vacature) {
		if (this.vacatureMagWordenVerwijderd(vacature)) {
			this.getVacatures().remove(vacature);
			return !this.heeftVacature(vacature);
		}
		return false;
	}

	public boolean heeftVacature(Vacature vacature) {
		return this.getVacatures() != null
				&& this.getVacatures().contains(vacature);
	}

	public boolean organisatieConstraint(Vacature vacature) {
		return vacature.getOrganisatie() != null
				&& vacature.getOrganisatie().getBedrijf() != null
				&& vacature.getOrganisatie().getBedrijf().equals(this);
	}

	public boolean vacatureMagWordenToegevoegd(Vacature vacature) {
		return vacature != null && !this.heeftVacature(vacature)
				&& this.organisatieConstraint(vacature);
	}

	public boolean vacatureMagWordenVerwijderd(Vacature vacature) {
		return vacature != null && this.heeftVacature(vacature)
				&& this.organisatieConstraint(vacature);
	}

	/*
	 * Collectie: Aanbiedingen
	 */

	@OneToMany(orphanRemoval = true, cascade = javax.persistence.CascadeType.ALL)
	public Set<Aanbieding> getAanbiedingen() {
		return aanbiedingen;
	}

	@SuppressWarnings("unused")
	private void setAanbiedingen(Set<Aanbieding> aanbiedingen) {
		this.aanbiedingen = aanbiedingen;
	}

	/**
	 * Voegt de gegeven aanbieding toe aan de lijst van dit bedrijf. De
	 * aanbieding wordt alleen toegevoegd als deze niet null is, nog niet in de
	 * lijst voorkomt en aan zowel een persoon met kandidaat(rol) als aan de
	 * juiste organisatie refereert.
	 * 
	 * De aanbieding wordt tevens toegevoegd aan de kandidaat van de persoon van
	 * deze aanbieding.
	 * 
	 * @param aanbieding
	 *            De toe te voegen aanbieding. Deze aanbieding moet een
	 *            referentie hebben naar de organisatie die eigenaar is van het
	 *            bedrijf waarop deze methode wordt aangeroepen. De aanbieding
	 *            moet ook een referentie hebben naar een persoon met een
	 *            kandidaat(rol).
	 * @return true als na afloop de aanbieding voorkomt in de aanbiedingen van
	 *         zowel het bedrijf als de kandidaat. Anders false.
	 */
	public synchronized boolean addAanbieding(Aanbieding aanbieding) {
		if (aanbieding == null || aanbieding.getPersoon() == null
				|| aanbieding.getOrganisatie() == null
				|| this.heeftAanbieding(aanbieding)) {
			return false;
		}
		Kandidaat kandidaat = aanbieding.getPersoon().getKandidaat();
		if (kandidaat != null
				&& aanbieding.getOrganisatie().getBedrijf().equals(this)) {
			this.getAanbiedingen().add(aanbieding);
			kandidaat.addAanbieding(aanbieding);
			return this.heeftAanbieding(aanbieding)
					&& kandidaat.getAanbiedingen().contains(aanbieding);
		}
		return false;
	}

	/**
	 * TODO bij het verwijderen van een aanbieding die ook in een
	 * arbeidsovereenkomst zit, dient deze ook verwijderd te worden! Verwijderd
	 * de gegeven aanbieding uit de lijst van dit bedrijf indien aanwezig. De
	 * aanbieding wordt tevens verwijderd bij de kandidaat van de persoon van
	 * deze aanbieding.
	 * 
	 * @param aanbieding
	 *            De te verwijderen aanbieding. Deze aanbieding moet een
	 *            referentie hebben naar de organisatie die eigenaar is van het
	 *            bedrijf waarop deze methode wordt aangeroepen. De aanbieding
	 *            moet ook een referentie hebben naar een persoon met een
	 *            kandidaat(rol).
	 * @return true als na afloop de aanbieding niet voorkomt in de aanbiedingen
	 *         van zowel het bedrijf als de kandidaat. Anders false.
	 */
	public synchronized boolean removeAanbieding(Aanbieding aanbieding) {
		if (aanbieding == null || aanbieding.getPersoon() == null
				|| aanbieding.getOrganisatie() == null
				|| !this.heeftAanbieding(aanbieding)) {
			return false;
		}
		Kandidaat kandidaat = aanbieding.getPersoon().getKandidaat();
		Bedrijf bedrijf = aanbieding.getOrganisatie().getBedrijf();
		if (kandidaat != null && bedrijf != null && bedrijf.equals(this)) {
			this.getAanbiedingen().remove(aanbieding);
			kandidaat.removeAanbieding(aanbieding);
			return !this.heeftAanbieding(aanbieding)
					&& !kandidaat.getAanbiedingen().contains(aanbieding);
		}
		return false;
	}

	public boolean heeftAanbieding(Aanbieding aanbieding) {
		return this.getAanbiedingen().contains(aanbieding);
	}

	/*
	 * Collectie: Medewerkers
	 */

	@ManyToMany(cascade = javax.persistence.CascadeType.ALL)
	@NotNull
	public Set<Persoon> getMedewerkers() {
		return medewerkers;
	}

	@SuppressWarnings("unused")
	private void setMedewerkers(Set<Persoon> medewerkers) {
		this.medewerkers = medewerkers;
	}

	/**
	 * Voegt een persoon toe aan de lijst van medewerkers van dit bedrijf indien
	 * deze nog niet in de lijst voorkomt.
	 * 
	 * Toevoegen gebeurt enkel wanneer de persoon een arbeidsovereenkomst heeft
	 * met dit bedrijf in zijn werknemer(rol). Deze dient dus eerst toegevoegd
	 * te worden via addArbeidsovereenkomst en die methode roept deze methode
	 * dan ook automatisch aan. Dit geld zowel voor de methode
	 * addArbeidsovereenkomst in bedrijf als in werknemer.
	 * 
	 * @param medewerker
	 *            persoon die moet worden toegevoegd
	 * @return True als aan alle voorwaarden is voldaan en de persoon aan de
	 *         lijst is toegevoegd.
	 */
	public synchronized boolean addMedewerker(Persoon medewerker) {
		if (medewerker != null && !this.getMedewerkers().contains(medewerker)
				&& medewerker.getWerknemer() != null
				&& medewerker.getWerknemer().werktBijBedrijf(this)) {
			return this.getMedewerkers().add(medewerker);
		}
		return false;
	}

	/**
	 * Verwijderd een medewerker indien deze voorkomt in de lijst medewerkers en
	 * deze geen arbeidsovereenkomst heeft met dit bedrijf.
	 * 
	 * 
	 * @param medewerker
	 *            Persoon instantie die verwijderd moet worden
	 * @return True als de medewerker kan worden verwijderd en alle betreffende
	 *         arbeidsovereenkomsten na afloop ook niet meer voorkomen in de
	 *         lijst van de betreffende werknemer en dit bedrijf.
	 */
	public synchronized boolean removeMedewerker(Persoon medewerker) {
		if (medewerker != null && isMedewerker(medewerker)) {
			for (Arbeidsovereenkomst a : this.getArbeidsovereenkomsten()) {
				if (a.getPersoon().equals(medewerker)) {
					return false;
				}
			}
			return this.getMedewerkers().remove(medewerker);
		}
		return false;
	}

	// TODO dit is een soort removeAllReferences methode, is deze zinvol?
	// Deze methode is gevaarlijk voor delete in db, want dan worden
	// krijg je orphan arbeidsovereenkomsten. Orphan removal lost dit op?
	/**
	 * Verwijderd een medewerker indien deze voorkomt in de lijst medewerkers
	 * 
	 * Verwijderd tevens alle arbeidsovereenkomsten uit de lijst van zowel dit
	 * bedrijf als van de betreffende werknemer.
	 * 
	 * @param medewerker
	 *            Persoon instantie die verwijderd moet worden
	 * @return True als de medewerker kan worden verwijderd en alle betreffende
	 *         arbeidsovereenkomsten na afloop ook niet meer voorkomen in de
	 *         lijst van de betreffende werknemer en dit bedrijf.
	 */
	public synchronized boolean removeMedewerkerMetAlleArbeidsovereenkomsten(
			Persoon medewerker) {
		if (medewerker != null && this.getMedewerkers().remove(medewerker)) {
			Werknemer werknemer = medewerker.getWerknemer();
			boolean stopWhileLoop = false; // om infinite loop uit te sluiten
			while (werknemer.werktBijBedrijf(this) && !stopWhileLoop) {
				Arbeidsovereenkomst arbeidsovereenkomst = null;
				for (Arbeidsovereenkomst a : this.getArbeidsovereenkomsten()) {
					if (a.getPersoon() == medewerker) {
						arbeidsovereenkomst = a;
					}
				}
				if (arbeidsovereenkomst != null
						&& werknemer
								.removeArbeidsovereenkomst(arbeidsovereenkomst)
						&& this.removeArbeidsovereenkomst(arbeidsovereenkomst)) {
					arbeidsovereenkomst = null;
				} else {
					stopWhileLoop = true;// in geval verwijderen van gevonden
											// arbeidsovereenkomst mislukt wordt
											// de loop toch gestopt
				}
			}
		}
		return !this.heeftMedewerker(medewerker)
				&& !medewerker.getWerknemer().werktBijBedrijf(this)
				&& this.isMedewerker(medewerker);
	}

	/**
	 * Kijkt of de opgegeven persoon voorkomt in de set van medewerkers
	 * 
	 * @param medewerker
	 *            persoon waarvan gevraagd wordt of deze werkzaam is bij dit
	 *            bedrijf
	 * @return True als de opgegeven medewerker voorkomt in de lijst, anders
	 *         false
	 */
	public boolean isMedewerker(Persoon medewerker) {
		if (medewerker != null) {
			for (Persoon p : this.getMedewerkers())
				if (medewerker.equals(p)) { // TODO equals of ==?
					return true;
				}
		}
		return false;
	}

	public boolean heeftMedewerker(Persoon medewerker) {
		return this.getMedewerkers().contains(medewerker);
	}

	/*
	 * Utils
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || !(obj instanceof Bedrijf)) {
			return false;
		} else {
			Bedrijf other = (Bedrijf) obj;
			if (this.getArbeidsovereenkomsten().size() > 0
					&& !this.getArbeidsovereenkomsten().equals(
							other.getArbeidsovereenkomsten())) {
				return false;
			}
			if (this.getVacatures().size() > 0
					&& !this.getVacatures().equals(other.getVacatures())) {
				return false;
			}
			if (this.getAanbiedingen().size() > 0
					&& !this.getAanbiedingen().equals(other.getAanbiedingen())) {
				return false;
			}
			if (this.getMedewerkers().size() > 0
					&& !this.getMedewerkers().equals(other.getMedewerkers())) {
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
