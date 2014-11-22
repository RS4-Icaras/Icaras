package nl.rsvier.icaras.form.relatiebeheer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import nl.rsvier.icaras.core.relatiebeheer.Adres;

@Entity
public class RelatieForm  {

	private int id;
	private Set<Adres> adressen = new HashSet<Adres>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Adres> getAdressen() {
		return adressen;
	}

	public void setAdressen(Set<Adres> adressen) {
		this.adressen = adressen;
	}
	
}
