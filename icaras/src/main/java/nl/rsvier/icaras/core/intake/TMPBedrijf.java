package nl.rsvier.icaras.core.intake;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name= "bedrijf")
public class TMPBedrijf implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long id;

	@Id
	@Column
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
