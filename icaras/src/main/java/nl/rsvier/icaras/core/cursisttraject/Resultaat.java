package nl.rsvier.icaras.core.cursisttraject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Pieter
 * 
 * @date 1 okt. 2014
 */
@Entity
@Table (name = "resultaat")
public class Resultaat {
	
	private long id;
    private Date toetsDatum;
    private int cijfer;
    private Examen examen;
    
    
    @ManyToOne
    @JoinColumn(name="toets_id")
    public Examen getExamen() {
		return examen;
	}


	public void setExamen(Examen examen) {
		this.examen = examen;
	}


	@Id @GeneratedValue
    @Column (name = "resultaat_id", unique = true, nullable = false)
    public long getId() {
		return id;
	}
    
    
	public void setId(long id) {
		this.id = id;
	}
	/**
     * @return the toetsDatum
     */
	@Column (name = "toets_datum")
    public Date getToetsDatum() {
        return this.toetsDatum;
    }
    /**
     * @param toetsDatum the toetsDatum to set
     */
    public void setToetsDatum(Date toetsDatum) {
        this.toetsDatum = toetsDatum;
    }
    /**
     * @return the cijfer
     */
    @Column (name = "toets_cijfer")
    public int getCijfer() {
        return this.cijfer;
    }
    /**
     * @param cijfer the cijfer to set
     */
    public void setCijfer(int cijfer) {
        this.cijfer = cijfer;
    }
    
    public boolean equals(Object obj) {
    	boolean isEqual = false;
    	if (obj instanceof Resultaat
    			&& this.id == ((Resultaat) obj).getId()
    			&& this.toetsDatum == ((Resultaat) obj).getToetsDatum()
    			&& this.cijfer == ((Resultaat) obj).getCijfer()
    			&& this.examen == ((Resultaat) obj).getExamen()) {
    		isEqual = true;
    	}
    	return isEqual;
    }

}
