package nl.rsvier.icaras.core.relatiebeheer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Rol {

	private int id;
	protected boolean gearchiveerd;
	protected String opmerking = "";

	/*
	 * Identifier
	 */

	@Id
	@Column(name = "rolId")
	@GeneratedValue(strategy = GenerationType.TABLE)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*
	 * Archief
	 */

	@Column(name = "isGearchiveerd")
	private boolean getGearchiveerd() {
		return this.gearchiveerd;
	}

	/*
	 * Reflection requires a getter, even though standard naming convention for
	 * boolean getters is: "isBoolean()", provide an "isGearchiveerd()" method
	 */
	public boolean isGearchiveerd() {
		return this.getGearchiveerd();
	}

	public void setGearchiveerd(boolean b) {
		this.gearchiveerd = b;
	}

	/*
	 * Opmerking
	 */

	public String getOpmerking() {
		return this.opmerking;
	}

	public void setOpmerking(String s) {
		this.opmerking = s;
	}

	/*
	 * Utils
	 */

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Rol)) {
			return false;
		} else {
			Rol other = (Rol) obj;
			if (!this.isGearchiveerd() == other.isGearchiveerd()) {
				return false;
			}
			if (!this.getOpmerking().equals(other.getOpmerking())) {
				return false;
			}
		}
		return true;
	}

}
