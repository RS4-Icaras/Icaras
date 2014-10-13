package nl.rsvier.icaras.core.intake;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Koen, Jelle
 *
 */
@Entity
@Table (name= "interneovereenkomst")
public abstract class Interneovereenkomst extends Overeenkomst {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Aanmelder aanmelder;
	
	/**
	 * @return the aanmelder
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	public Aanmelder getAanmelder() {
		return aanmelder;
	}
	
	/**
	 * @param aanmelder the aanmelder to set
	 */
	public void setAanmelder(Aanmelder aanmelder) {
		this.aanmelder = aanmelder;
	}

}
