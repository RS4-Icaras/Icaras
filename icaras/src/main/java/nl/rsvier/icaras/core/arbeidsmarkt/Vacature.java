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
 * @author Mark van Meerten
 */

// TODO: Add Expertises

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

		if (this.organisatieMagWordenToegevoegd(organisatie)) {
			this.setOrganisatie(organisatie);
		}

		if (this.omschrijvingMagWordenToegevoegd(omschrijving)) {
			this.setOmschrijving(omschrijving);
		}

		if (this.organisatieIsToegevoegd() && this.omschrijvingIsToegevoegd()) {
			/*
			 * Add this Vacature IF AND ONLY IF Organisatie has been set!
			 * addVacature() will indirectly call this Vacature's hashCode() so
			 * you better be damn sure the business key fields have been
			 * properly initialized
			 */
			this.getOrganisatie().getBedrijf().addVacature(this);
		} else {
			/*
			 * Let's just make our business key immutable and be required to be
			 * set in the constructor
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
		if (this.organisatieIsToegevoegd()) {
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

	public static boolean organisatieConstraint(Organisatie organisatie) {
		/*
		 * De voorwaarden waar een Organisatie aan moet voldoen. Organisatie mag
		 * nooit null zijn en moet een bedrijfsrol bevatten
		 */
		return organisatie != null && organisatie.hasRol(Bedrijf.class);
	}

	public boolean organisatieMagWordenToegevoegd(Organisatie organisatie) {
		/*
		 * Om een Organisatie toe te voegen mag deze geen null zijn en moet deze
		 * aan de voorwaarde voldoen
		 */
		return !this.organisatieIsToegevoegd()
				&& Vacature.organisatieConstraint(organisatie);
	}

	public boolean organisatieIsToegevoegd() {
		/*
		 * Organisatie is immutable. Als deze reeds is toegevoegd mag deze niet
		 * meer worder aangepast
		 */
		return this.getOrganisatie() != null;
	}

	/*
	 * Aanbiedingen
	 */

	@OneToMany()
	public Set<Aanbieding> getAanbiedingen() {
		return this.aanbiedingen;
	}

	@SuppressWarnings("unused")
	private void setAanbiedingen(Set<Aanbieding> aanbiedingen) {
		this.aanbiedingen = aanbiedingen;
	}

	public boolean addAanbieding(Aanbieding aanbieding) {
		if (!this.getAanbiedingen().contains(aanbieding)) {
			return this.getAanbiedingen().add(aanbieding);
		}
		return false;
	}

	public boolean removeAanbieding(Aanbieding aanbieding) {
		if (aanbieding.getVacature() != null
				&& aanbieding.getVacature().equals(this)) {
			return this.getAanbiedingen().remove(aanbieding);
		}
		return false;
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

	public static boolean omschrijvingConstraint(String str) {
		/*
		 * De voorwaarden waar een Omschrijving aan moet voldoen
		 */
		return str != null && !str.equals("");
	}

	public boolean omschrijvingMagWordenToegevoegd(String str) {
		/*
		 * Om een Omschrijving toe te voegen mag deze geen null zijn en moet
		 * deze aan de voorwaarde voldoen
		 */
		return !this.omschrijvingIsToegevoegd()
				&& Vacature.omschrijvingConstraint(str);
	}

	public boolean omschrijvingIsToegevoegd() {
		/*
		 * Omschrijving is immutable. Als deze reeds is toegevoegd mag deze niet
		 * meer worder aangepast
		 */
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
		 * N.B. Overflow is gebruikelijk, standaard Object.hashCode() maakt ook
		 * gebruik van int
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
