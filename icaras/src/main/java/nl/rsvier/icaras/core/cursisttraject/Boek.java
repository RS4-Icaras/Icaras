package nl.rsvier.icaras.core.cursisttraject;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Klasse om informatie over boeken in op te slaan.
 * 
 * @author Peter, Patricia, Pieter
 * 
 * @date 1 okt. 2014
 */
@Entity
@Table(name="Boek")
@AttributeOverrides({
    @AttributeOverride(name="prijs", column=@Column(name="prijs")),
    @AttributeOverride(name="oefenMateriaal", column=@Column(name="oefen_materiaal")),
    @AttributeOverride(name="inBezit", column=@Column(name="in_bezit")),
    @AttributeOverride(name="verzendDatum", column=@Column(name="verzend_datum"))
})

public class Boek extends Materiaal {
    private long isbn;
    private String titel;
    private String auteur;    
    private int druk;
       
    /**
     * @return the isbn
     */
    @Column (name = "isbn", unique = true, nullable = false)
    public long getIsbn() {
        return this.isbn;
    }
    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }
    /**
     * @return the titel
     */
    @Column(name = "titel")
    public String getTitel() {
        return this.titel;
    }
    /**
     * @param titel the titel to set
     */
    public void setTitel(String titel) {
        this.titel = titel;
    }
    /**
     * @return the auteur
     */
    @Column ( name = "auteur")
    public String getAuteur() {
        return this.auteur;
    }
    /**
     * @param auteur the auteur to set
     */
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    /**
     * @return the druk
     */
    @Column (name = "druk")
    public int getDruk() {
        return this.druk;
    }
    /**
     * @param druk the druk to set
     */
    public void setDruk(int druk) {
        this.druk = druk;
    }
    
    
    public boolean equals(Object obj) {
    	boolean isEqual = false;
    	if (obj instanceof Boek
    			&& this.auteur.equals(((Boek) obj).getAuteur())
    			&& this.druk == ((Boek) obj).getDruk()
    			&& this.isbn == ((Boek) obj).getIsbn()
    			&& this.titel.equals(((Boek) obj).getTitel())
    			&& this.getMateriaal_id() == ((Boek) obj).getMateriaal_id()) {
    		isEqual = true;
    	}
    	return isEqual;
    }

}
