package nl.rsvier.icaras.core.arbeidsmarkt;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import nl.rsvier.icaras.core.IEntity;
import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Bedrijf;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;

/**
 * Een vacature is een opname van een moment waarin een Organisatie op zoek is
 * naar kennis en/of expertise. De verantwoordelijkheid van een Vacature is het
 * vastleggen van de vraag, zodat Rob kan beslissen of er een aanbod is dat daar
 * op aansluit.
 * 
 * De levensduur van een Vacature is flexibel. Vacatures kunnen verlopen, worden
 * opgeruimd om plaats te maken, etc.
 * 
 * Wanneer je een Vacature probeert aan te maken met een gelijke Organisatie die
 * al bekend is bij de bedrijfsrol van die Organisatie volgt een
 * InvalidBusinessKeyException. Verder is het onmogelijk gemaakt om van een
 * vacature de Organiatie en/of Omschrijving aan te passen. Deze combinatie
 * zorgt ervoor dat Vacature immutable is. Mocht het in de toekomst nodig zijn
 * om meerdere indentieke Vacatures aan te kunnen maken, dan is een mogelijke
 * oplossing om een attribuut toe te voegen dat de datum van plaatsing bijhoud.
 * 
 * Waarom is de Vacature immutable? Omdat aanbiedingen gebruik kunnen maken van
 * deze klasse. Een vacature heeft slechts 2 interessante eigenschappen. De
 * Organisatie die op zoek is naar personeel, en de Omschrijving die zij
 * meesturen. Aanpassingen aan de Organisatie heeft gevolgen voor andere
 * klassen, en voor aanpassingen aan de Omschrijving kan een nieuwe Vacature
 * worden aangemaakt.
 * 
 * @author Mark van Meerten
 * 
 */

@Entity
public class Vacature implements IEntity {

	private static final long serialVersionUID = 1L;

	private int id;
	private Organisatie organisatie;
	private Set<Aanbieding> aanbiedingen;
	private String omschrijving;
	private URL website;

	/*
	 * Constructoren
	 */
	public Vacature(Organisatie organisatie, String omschrijving, String website)
			throws InvalidBusinessKeyException, MalformedURLException {
		this(organisatie, omschrijving);
		this.helperSetUrl(website);
	}

	public Vacature(Organisatie organisatie, String omschrijving)
			throws InvalidBusinessKeyException {
		this();

		if (this.organisatieMagWordenToegevoegd(organisatie)
				&& this.omschrijvingMagWordenToegevoegd(omschrijving)) {
			this.setOrganisatie(organisatie);
			this.setOmschrijving(omschrijving);
		}
		if (this.heeftOrganisatie() && this.heeftOmschrijving()) {
			/*
			 * Add this Vacature IF AND ONLY IF Organisatie has been set!
			 * addVacature() will indirectly call this Vacature's hashCode() so
			 * you better be sure the business key fields have been properly
			 * initialized
			 */
			if (!this.getOrganisatie().getBedrijf().addVacature(this)) {
				throw new InvalidBusinessKeyException("Vacature bestaat al");
			}
		} else {
			/*
			 * Let's just make our business key immutable and be required to be
			 * set in the constructor.
			 */
			throw new InvalidBusinessKeyException(
					"Vacature business key has not been properly initialized");
		}

	}

	private Vacature() {
		this.aanbiedingen = new HashSet<Aanbieding>();
	}

	/*
	 * Roep removeAllReferences() aan om alle referenties naar deze Vacature
	 * verwijderen. Vervolgens kan de Vacature worden gedelete zonder dat er
	 * data achterblijft in de database die verwijst naar deze Vacature die niet
	 * langer bestaat.
	 */
	public boolean removeAllReferences() {
		if (this.getAanbiedingen() != null) {
			/*
			 * Remove Vacature from every Aanbieding that has been sent out
			 * containing this Vacature
			 */
			ArrayList<Aanbieding> toRemove = new ArrayList<Aanbieding>();
			for (Aanbieding a : this.getAanbiedingen()) {
				if (a.getVacature() != null && a.getVacature().equals(this)) {
					// Set Vacature van Aanbieding op null
					a.removeVacature(this);
					// Verwijder aanbieding uit de collectie Aanbiedingen.
					// N.B. Voorkom ConcurrentModifierException door deze
					// verwijdering pas uit te voeren nadat we klaar zijn
					toRemove.add(a);
				}
			}
			// Verwijder alle aanbieding die gebruik maken van deze Vacature uit
			// de collectie Aanbiedingen.
			this.getAanbiedingen().removeAll(toRemove);
		}
		if (this.heeftOrganisatie()) {
			/*
			 * Remove this Vacature from the collection of Vacatures Bedrijf
			 * keeps.
			 * 
			 * Return true if Vacature has been removed from Bedrijf. Why not
			 * take Aanbiedingen into the equation? Because Aanbieding allows
			 * Vacature to be null, so we can't be sure if it failed or just
			 * didn't exist in the first place.
			 */
			return this.getOrganisatie().getBedrijf().removeVacature(this);
		}
		return false;
	}

	/*
	 * Identifier
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*
	 * Organisatie
	 */

	@OneToOne()
	@JoinColumn(referencedColumnName = "relatie_id")
	@NotNull
	public Organisatie getOrganisatie() {
		return this.organisatie;
	}

	private void setOrganisatie(Organisatie organisatie) {
		this.organisatie = organisatie;
	}

	public boolean organisatieConstraint(Organisatie organisatie) {
		return organisatie != null && organisatie.heeftRol(Bedrijf.class);
	}

	public boolean organisatieMagWordenToegevoegd(Organisatie organisatie) {
		return !this.heeftOrganisatie()
				&& this.organisatieConstraint(organisatie);
	}

	public boolean heeftOrganisatie() {
		return this.getOrganisatie() != null;
	}

	/*
	 * Aanbiedingen
	 */

	@OneToMany(orphanRemoval = true)
	public Set<Aanbieding> getAanbiedingen() {
		return this.aanbiedingen;
	}

	@SuppressWarnings("unused")
	private void setAanbiedingen(Set<Aanbieding> aanbiedingen) {
		this.aanbiedingen = aanbiedingen;
	}

	public synchronized boolean addAanbieding(Aanbieding aanbieding) {
		if (aanbieding == null) {
			// Voorkom NullpointerExceptions
			return false;
		}
		if (this.aanbiedingMagWordenToegevoegd(aanbieding)
				&& aanbieding.vacatureMagWordenGezet(this)) {
			this.getAanbiedingen().add(aanbieding);
			aanbieding.setVacatureReferentie(this);
			return this.heeftAanbieding(aanbieding)
					&& aanbieding.heeftVacature(this);
		}
		return false;
	}

	public synchronized boolean removeAanbieding(Aanbieding aanbieding) {
		if (aanbieding == null) {
			// Voorkom NullpointerExceptions
			return false;
		}
		if (this.heeftAanbieding(aanbieding) && aanbieding.heeftVacature(this)) {
			this.getAanbiedingen().remove(aanbieding);
			aanbieding.removeVacature(this);
			return !this.heeftAanbieding(aanbieding)
					&& !aanbieding.heeftVacature(this);
		}
		return false;
	}

	public boolean aanbiedingConstraint(Aanbieding aanbieding) {
		return aanbieding != null
				&& aanbieding.getOrganisatie().equals(this.getOrganisatie());
	}

	public boolean aanbiedingMagWordenToegevoegd(Aanbieding aanbieding) {
		return !this.heeftAanbieding(aanbieding)
				&& this.aanbiedingConstraint(aanbieding);
	}

	public boolean heeftAanbieding(Aanbieding aanbieding) {
		return this.getAanbiedingen().contains(aanbieding);
	}

	/*
	 * Omschrijving van de werkzaamheden
	 */

	@Column(unique = true, updatable = false)
	@NotNull
	public String getOmschrijving() {
		return this.omschrijving;
	}

	private void setOmschrijving(String s) {
		this.omschrijving = s;
	}

	public boolean omschrijvingConstraint(String str) {
		return str != null && !str.equals("");
	}

	public boolean omschrijvingMagWordenToegevoegd(String str) {
		return !this.heeftOmschrijving() && this.omschrijvingConstraint(str);
	}

	public boolean heeftOmschrijving() {
		return this.getOmschrijving() != null;
	}

	/*
	 * Url waar de vacature gevonden is
	 */

	@Column(length = 1024, nullable = true)
	public URL getWebsite() {
		return this.website;
	}

	private void setWebsite(URL url) {
		this.website = url;
	}

	/*
	 * Helper methode voor setWebsite(Url url) om een string om te zetten naar
	 * een URL zonder dat de MalformedURLException iedere keer moet worden
	 * afgevangen
	 */
	public void helperSetUrl(String s) throws MalformedURLException {
		this.setWebsite(new URL(s));
	}

	/*
	 * Utils
	 */

	@Override
	public int hashCode() {
		/*
		 * Null checks zijn niet nodig. Organisatie en Omschrijving zijn
		 * onderdeel van de businesskey en mogen dus niet null zijn.
		 * 
		 * N.B. Overflow is gebruikelijk, en zelfs wenselijk, standaard
		 * Object.hashCode() maakt ook gebruik van int
		 */
		final int prime = 32589;
		int hash = 1;
		hash = prime * hash + this.getOrganisatie().hashCode();
		hash = prime * hash + this.getOmschrijving().hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || !(obj instanceof Vacature)) {
			return false;
		} else {
			Vacature other = (Vacature) obj;
			if (!this.getOrganisatie().equals(other.getOrganisatie())) {
				return false;
			}
			if (!this.getOmschrijving().equals(other.getOmschrijving())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Vacature(id=" + this.getId() + ", hash=" + this.hashCode()
				+ ") geplaatst door Organisatie: " + this.getOrganisatie()
				+ ", met als omschrijving: " + this.getOmschrijving();
	}

}
