package nl.rsvier.icaras.core.cursisttraject;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Abstracte klasse als basis voor materialen.
 * 
 * @author Peter, Patricia, Pieter
 * 
 * 
 * @date 1 okt. 2014
 */
@Entity
@Table(name="MATERIAAL")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Materiaal {
    
    private long materiaal_id;
	private double prijs;
    private boolean isOefenMateriaal;
	private boolean isInBezit;
    private Date verzendDatum;
    private Cursist cursist;
    
    private List<TrajectEenheid> trajecteenhedenLijst;
    
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="materiaal_id")
    public long getMateriaal_id() {
		return materiaal_id;
	}
	public void setMateriaal_id(long materiaal_id) {
		this.materiaal_id = materiaal_id;
	}

	/**
     * @return the prijs
     */
	@Column(name="prijs")
    public double getPrijs() {
        return this.prijs;
    }
    /**
     * @param prijs the prijs to set
     */
    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }
    /**
     * @return the isOefenMateriaal
     */
	
	@Column(name="oefen_materiaal")
    public boolean isOefenMateriaal() {
        return this.isOefenMateriaal;
    }
    /**
     * @param isOefenMateriaal the isOefenMateriaal to set
     */
    public void setOefenMateriaal(boolean isOefenMateriaal) {
        this.isOefenMateriaal = isOefenMateriaal;
    }
    /**
     * @return the isInBezit
     */
    @Column(name="in_bezit")
    public boolean isInBezit() {
        return this.isInBezit;
    }
    /**
     * @param isInBezit the isInBezit to set
     */
    public void setInBezit(boolean isInBezit) {
        this.isInBezit = isInBezit;
    }
    /**
     * @return the verzendDatum
     */
    @Column(name="verzend_datum")
    public Date getVerzendDatum() {
        return this.verzendDatum;
    }
    /**
     * @param verzendDatum the verzendDatum to set
     */
    public void setVerzendDatum(Date verzendDatum) {
        this.verzendDatum = verzendDatum;
    }
    
    @ManyToOne
    @JoinColumn(name = "cursist_id")
	public Cursist getCursist() {
		return cursist;
	}
	public void setCursist(Cursist cursist) {
		this.cursist = cursist;
	}
    
	@OneToMany (mappedBy = "trajectMateriaalLijst")
 	public List<TrajectEenheid> getTrajecteenheden() {
		return trajecteenhedenLijst;
	}
	public void setTrajecteenheden(List<TrajectEenheid> trajecteenhedenLijst) {
		this.trajecteenhedenLijst = trajecteenhedenLijst;
	}

}
