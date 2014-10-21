package nl.rsvier.icaras.core.cursisttraject;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//@Entity
@Table (name = "persoon")
//@Inheritance(strategy=InheritanceType.JOINED)
public class Persoon {
	private long id;
	private Cursist cursistRol;
	
	@Id @GeneratedValue	
	@Column(name="persoon_id")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@OneToOne(mappedBy="persoon", cascade=CascadeType.ALL)
	public Cursist getCursistRol() {
		return cursistRol;
	}
	
	public void setCursistRol(Cursist cursistRol) {
		this.cursistRol = cursistRol;
	}
	
	

}
