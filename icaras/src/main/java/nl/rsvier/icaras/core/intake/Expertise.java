package nl.rsvier.icaras.core.intake;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import nl.rsvier.icaras.core.cursisttraject.Cursist;
/**
 * 
 * @author Koen, Jelle, Patricia
 *
 */
@Entity
@Table(name = "expertise")
public class Expertise implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private Set<Cursist> cursisten = new HashSet<Cursist>();
	private Set<CV> cvs = new HashSet<CV>();
	private String expertiseType;
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	@Column(name = "expertise_id", nullable = false)
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
	 * @return the cursisten
	 */
	
	@ManyToMany (cascade = CascadeType.ALL)
    //@JoinTable(name="expertise_cursist",
    //joinColumns={
	//	@JoinColumn(name="ex_id", referencedColumnName = "expertise_id")},
    //inverseJoinColumns={
	//	@JoinColumn(name="cu_id", referencedColumnName = "cursist_id")
	//})
	public Set<Cursist> getCursisten() {
		return cursisten;
	}
	/**
	 * @param cursisten the cursisten to set
	 */
	public void setCursisten(Set<Cursist> cursisten) {
		this.cursisten = cursisten;
	}
	/**
	 * @return the cvs
	 */
	@ManyToMany
	public Set<CV> getCvs() {
		return cvs;
	}
	/**
	 * @param cvs the cvs to set
	 */
	public void setCvs(Set<CV> cvs) {
		this.cvs = cvs;
	}
	/**
	 * @return the expertise
	 */
	public String getExpertise() {
		return expertiseType;
	}
	/**
	 * @param expertise the expertise to set
	 */
	public void setExpertise(String expertise) {
		this.expertiseType = expertise;
	}
	
	public boolean equals(Object obj) {
    	boolean isEqual = false;
    	if (obj instanceof Expertise
    			&& this.id == ((Expertise) obj).getId()
    			//&& this.cursisten == ((Expertise) obj).getCursisten()
    			//&& this.cvs == ((Expertise) obj).getCvs()
    			// toetsenlijst
    		    // materiaallijst
    			&& this.expertiseType == ((Expertise) obj).getExpertise()) {
    		isEqual = true;
    	}
    	return isEqual;
    }
	
	
	

}
