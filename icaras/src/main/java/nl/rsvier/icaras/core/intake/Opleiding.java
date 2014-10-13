package nl.rsvier.icaras.core.intake;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Opleiding implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private CV cv;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	@Column(name="OPLEIDING_ID", unique = true, nullable = false)
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
	 * @return the cv
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	public CV getCv() {
		return cv;
	}

	/**
	 * @param cv the cv to set
	 */
	public void setCv(CV cv) {
		this.cv = cv;
	}
	
	

}
