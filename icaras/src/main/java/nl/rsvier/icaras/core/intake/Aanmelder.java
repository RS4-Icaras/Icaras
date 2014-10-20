package nl.rsvier.icaras.core.intake;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
/**
 * 
 * @author Koen, Jelle
 *
 */
//TODO Moet persoonsRol nog implementeren ipv persoon.
@Entity
@Table(name="aanmelder")
public class Aanmelder extends TMPPersoon implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private List<Interneovereenkomst> interneovereenkomsten = new ArrayList<Interneovereenkomst>();
	private CV cV;
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	@Column(nullable = false, name="AANMELDER_ID")
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the overeenkomsten
	 */
	//@Column
	//@OneToMany(mappedBy="aanmelder", cascade=CascadeType.ALL)
	@OneToMany(mappedBy="aanmelder", cascade=CascadeType.ALL)
	public List<Interneovereenkomst> getInterneovereenkomsten() {
		return interneovereenkomsten;
	}
	/**
	 * @param overeenkomsten the overeenkomsten to set
	 */
	public void setInterneovereenkomsten(List<Interneovereenkomst> interneovereenkomsten) {
		this.interneovereenkomsten = interneovereenkomsten;
	}
	/**
	 * @return the cV
	 */
	//@Column(name="CV")
	@OneToOne(mappedBy = "aanmelder", cascade = CascadeType.ALL)
	//@PrimaryKeyJoinColumn
	public CV getcV() {
		return cV;
	}
	/**
	 * @param cV the cV to set
	 */
	public void setcV(CV cV) {
		this.cV = cV;
	}
	
	//TODO Aanpassen als er meer atributen zijn voor Aanmelder.
	public boolean equals(Object obj){
		boolean isEqual = false;
		if(obj instanceof Aanmelder
				&& this.id == ((Aanmelder) obj).getId()
				//&& this.cV.equals(((Aanmelder) obj).getcV())
				//&& this.interneovereenkomsten.equals(((Aanmelder) obj).getInterneovereenkomsten())
				){
			isEqual = true;
		}
		return isEqual;
	}
	


}
