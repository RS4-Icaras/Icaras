package nl.rsvier.icaras.core.cursisttraject;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Proeftoets")
@AttributeOverrides({
    @AttributeOverride(name="prijs", column=@Column(name="prijs")),
    @AttributeOverride(name="isOefenMateriaal", column=@Column(name="is_oefen_materiaal")),
    @AttributeOverride(name="inBezit", column=@Column(name="in_bezit")),
    @AttributeOverride(name="verzendDatum", column=@Column(name="verzend_datum"))
})

public class Proeftoets extends Materiaal {
    
    
    private String beschrijving;
    private Date datum;
    private int score;
    private boolean isVoldoende;
    
    
    @Column(name="beschrijving")
    public String getBeschrijving() {
        return beschrijving;
    }
    
    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
    
    @Column(name="titel")
    public Date getDatum() {
        return datum;
    }
    
    public void setDatum(Date datum) {
        this.datum = datum;
    }
    
    @Column(name="score")
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    @Column(name="is_voldoende")
    public boolean isVoldoende() {
        return isVoldoende;
    }
    
    public void setVoldoende(boolean isVoldoende) {
        this.isVoldoende = isVoldoende;
    }
    
    public boolean equals(Object obj) {
    	boolean isEqual = false;
    	if (obj instanceof Proeftoets
    			&& this.beschrijving.equals(((Proeftoets) obj).getBeschrijving())
    			&& this.datum == ((Proeftoets) obj).getDatum()
    			&& this.score == ((Proeftoets) obj).getScore()
    			&& this.isVoldoende == ((Proeftoets) obj).isVoldoende()) {
    		isEqual = true;
    	}
    	return isEqual;
    }
    

} 