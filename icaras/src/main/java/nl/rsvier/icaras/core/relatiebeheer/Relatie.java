package nl.rsvier.icaras.core.relatiebeheer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nl.rsvier.icaras.core.IEntity;

/**
 * Abstracte klasse die het beheer van de adressen en Nfa's (niet fysieke
 * adressen) regelt voor de subklassen Persoon en Organisatie. Zorgt samen met
 * de klasse Adres voor de garantie van precies 1 correspondentieadres.
 * 
 * @author Gerben
 * @author Gordon
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Relatie implements IEntity {

	private static final long serialVersionUID = 1L;

	private int id;
	private boolean gearchiveerd = false;
	private String opmerking;
	private boolean priveRelatie = false;
	private Set<Nfa> nfaLijst = new HashSet<Nfa>();
	private Set<Adres> adressen = new HashSet<Adres>();

	/**
	 * @return the relatieId
	 */
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	/**
	 * @param relatieId
	 *            the relatieId to set
	 */
	public void setId(int relatieId) {
		this.id = relatieId;
	}

	public boolean isGearchiveerd() {// moeten de rollen van de te archiveren
										// persoon ook automatisch gearchiveerd
										// worden?
		return gearchiveerd;
	}

	public void setGearchiveerd(boolean gearchiveerd) {
		this.gearchiveerd = gearchiveerd;
	}

	public String getOpmerking() {
		return opmerking;
	}

	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}

	public boolean isPriveRelatie() {
		return priveRelatie;
	}

	public void setPriveRelatie(boolean priveRelatie) {
		this.priveRelatie = priveRelatie;
	}

	/**
	 * @return the nfaLijst
	 */
	@NotNull
	// Wat doet dit precies?
	@OneToMany(cascade = CascadeType.ALL)
	public Set<Nfa> getNfaLijst() {
		return nfaLijst;
	}

	@SuppressWarnings("unused")
	private void setNfaLijst(Set<Nfa> nfaLijst) {
		this.nfaLijst = nfaLijst;
	}

	@NotNull
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	public Set<Adres> getAdressen() {
		return adressen;
	}

	@SuppressWarnings("unused")
	private void setAdressen(Set<Adres> adressen) {
		this.adressen = adressen;
	}

	public boolean addNfa(Nfa nfa) {
		return getNfaLijst().add(nfa);
	}

	public boolean removeNfa(Nfa nfa) {
		return getNfaLijst().remove(nfa);
	}

	/**
	 * Voegt een adres toe aan de lijst. Garandeert dat er precies 1
	 * correspondentieadres aanwijzig is, tenzij de lijst leeg is.
	 * 
	 * Het toevoegen van een adresinstantie uit een andere relatie conflicteert
	 * met de garantie dat er maar 1 correspondentieadres is. Dit is dan ook
	 * niet toegestaan.
	 * 
	 * @param adres
	 *            Het toe te voegen adres.
	 * @return True als toevoegen gelukt is, anders false.
	 */
	public boolean addAdres(Adres adres) {
		if (adres != null && !heeftAdres(adres)) {
			if (getCorrespondentieAdres() == null) {// lijst is dan dus leeg
				getAdressen().add(adres);// moet in de lijst zitten voor:
				adres.maakCorrespondentieAdres(this);// doet niets als dit al
														// true is
				return heeftAdres(adres);
			}// als code hier komt: getCorrespondentieAdres != null
			else if (adres.isCorrespondentieAdres()) {
				Adres correspondentieAdresOud = getCorrespondentieAdres();
				getAdressen().add(adres);// nu dus 2 correspondentieadressen
				correspondentieAdresOud.geenCorrespondentieAdres(this);
				return heeftAdres(adres) && getCorrespondentieAdres() == adres;
			} else {
				return getAdressen().add(adres);
			}
		}
		return false;
	}

	/**
	 * Verwijderd een adres uit de lijst. Verwijderd enkel een gegeven adres
	 * indien dit niet het correspondentieadres is. Maak eerst van een ander
	 * adres uit de lijst een correspondentieadres als het te verwijderen adres
	 * nog het correspondentieadres is. Als het te verwijderen adres een
	 * correspondentieadres is en het is het enige adres van deze relatie, dan
	 * kan deze via de methode verwijderLaatsteAdres() worden verwijderd.
	 * 
	 * @param adres
	 *            Het toe te voegen adres, mag geen correspondentieadres zijn.
	 * @return True als toevoegen gelukt is, anders false.
	 */
	public boolean removeAdres(Adres adres) {
		if (adres.isCorrespondentieAdres() || !heeftAdres(adres)) {
			return false;
		} else {
			return getAdressen().remove(adres);
		}
	}

	/**
	 * Methode die enkel gebruikt kan worden voor het verwijderen van het
	 * laatste adres van deze relatie.
	 * 
	 * @return True als er maar 1 adres in de lijst voorkomt, anders false.
	 */
	public boolean verwijderLaatsteAdres() {
		if (getAdressen().size() == 1) {
			getAdressen().remove(this.getCorrespondentieAdres());
			return true;
		} else {
			return false;
		}
	}

	public boolean heeftAdres(Adres adres) {
		return getAdressen().contains(adres);
	}

	/**
	 * Methode die het enige adres uit de lijst dat correspondentieadres is
	 * teruggeeft.
	 * 
	 * @return Het correspondentieadres
	 */
	@Transient
	public Adres getCorrespondentieAdres() {
		for (Adres a : getAdressen()) {
			if (a.isCorrespondentieAdres()) {
				return a;
			}
		}
		return null;
	}

}
