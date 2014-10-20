package nl.rsvier.icaras.core.intake;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * 
 * @author Koen, Jelle
 *
 */
@Entity
public class CV implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Aanmelder aanmelder;
	private File cvDocument;
	private long id;
	
	private List<Werkervaringseenheid> werkervaringsEenheden = new ArrayList<Werkervaringseenheid>();
	private List<Opleiding> opleidingen = new ArrayList<Opleiding>();
	//private List<Expertise> expertises = new ArrayList<Expertise>();
	
	/**
	 * @return the aanmelder
	 */
	/**
	 * @return the id
	 */
	@Id
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	@OneToOne
	@PrimaryKeyJoinColumn
	public Aanmelder getAanmelder() {
		return aanmelder;
	}
	/**
	 * @param aanmelder the aanmelder to set
	 */
	public void setAanmelder(Aanmelder aanmelder) {
		this.aanmelder = aanmelder;
	}
	/**
	 * @return the cvDocument
	 */
	@Lob
	@Column
	public File getCvDocument() {
		return cvDocument;
	}
	/**
	 * @param cvDocument the cvDocument to set
	 */
	public void setCvDocument(File cvDocument) {
		this.cvDocument = cvDocument;
	}
	/**
	 * @return the werkervaringsEenheden
	 */
	@OneToMany(cascade=CascadeType.ALL, mappedBy="cv")
	public List<Werkervaringseenheid> getWerkervaringsEenheden() {
		return werkervaringsEenheden;
	}
	/**
	 * @param werkervaringsEenheden the werkervaringsEenheden to set
	 */
	public void setWerkervaringsEenheden(
			List<Werkervaringseenheid> werkervaringsEenheden) {
		this.werkervaringsEenheden = werkervaringsEenheden;
	}
	/**
	 * @return the opleidingen
	 */
	@OneToMany(cascade=CascadeType.ALL, mappedBy="cv")
	public List<Opleiding> getOpleidingen() {
		return opleidingen;
	}
	/**
	 * @param opleidingen the opleidingen to set
	 */
	public void setOpleidingen(List<Opleiding> opleidingen) {
		this.opleidingen = opleidingen;
	}
	/**
	 * @return the expertises
	 */
	//@OneToMany
	//public List<Expertise> getExpertises() {
	//	return expertises;
	//}
	/**
	 * @param expertises the expertises to set
	 */
	//public void setExpertises(List<Expertise> expertises) {
	//	this.expertises = expertises;
	//}
	
	//TODO Aanpassen als er meer atributen zijn voor CV.
	public boolean equals(Object obj){
		boolean isEqual = false;
		if(obj instanceof CV
				&& this.id == ((CV) obj).getId()
				//&& this.cvDocument.equals(((CV) obj).getCvDocument())
				&& this.aanmelder.equals(((CV) obj).getAanmelder())
				&& this.werkervaringsEenheden.equals(((CV) obj).getWerkervaringsEenheden())
				&& this.opleidingen.equals(((CV) obj).getOpleidingen())
				){
			isEqual = true;
		}
		return isEqual;
	}
	
	
	
	

}
