package nl.rsvier.icaras.core.relatiebeheer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
import nl.rsvier.icaras.core.arbeidsmarkt.CvGenerator;

@Entity
public class Kandidaat extends PersoonsRol {

	private static final long serialVersionUID = 1L;

	private Set<Aanbieding> aanbiedingen = new HashSet<Aanbieding>();

	private CvGenerator cvGenerator = new CvGenerator();

	/*
	 * Roep Destructotron aan om alle referenties naar deze Vacature
	 * verwijderen. Vervolgens kan de Vacature worden gedelete zonder dat er
	 * data achterblijft in de database die verwijst naar deze Vacature die niet
	 * langer bestaat.
	 */
	public boolean destructotron(Aanbieding aanbieding) {
		// TODO: Leroy, thomas, en gordon leveren deze klasse aan
		return false;
	}

	/*
	 * Aanbiedingen
	 */

	@OneToMany(orphanRemoval = true, cascade = javax.persistence.CascadeType.ALL)
	@Cascade(value = { CascadeType.SAVE_UPDATE })
	public Set<Aanbieding> getAanbiedingen() {
		return aanbiedingen;
	}

	public void setAanbiedingen(Set<Aanbieding> aanbiedingen) {
		this.aanbiedingen = aanbiedingen;
	}

	public boolean addAanbieding(Aanbieding aanbieding) {
		boolean isToegevoegd = false;
		if (!this.getAanbiedingen().contains(aanbieding)) {
			isToegevoegd = this.aanbiedingen.add(aanbieding);
			aanbieding.setPersoonReferentie(this.getCvGenerator().getPersoon());
		}
		return isToegevoegd;
	}

	public boolean removeAanbieding(Aanbieding aanbieding) {
		return this.aanbiedingen.remove(aanbieding);
	}
	@Embedded
	public CvGenerator getCvGenerator() {
		return cvGenerator ;
	}

	@SuppressWarnings("unused") //de methode wordt gebruikt door Hibernate
	private void setCvGenerator(CvGenerator cvGenerator) {
		this.cvGenerator = cvGenerator;
	}

	/*
	 * Utils
	 */

	public int hashCode() {
		final int prime = 67;
		int hash = 1;
		hash = prime * hash + this.getId();
		return hash;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || !super.equals(obj)
				|| !(obj instanceof Kandidaat)) {
			return false;
		} else {
			Kandidaat other = (Kandidaat) obj;
			if (this.getId() > 0 && other.getId() > 0
					&& this.getId() != other.getId()) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		return "Kandidatenrol, subklasse van: " + super.toString();
	}

}