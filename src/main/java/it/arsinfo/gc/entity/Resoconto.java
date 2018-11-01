package it.arsinfo.gc.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Resoconto {

	public enum Tipologia {
		GIORNALIERA,
		SETTIMANALE,
		QUINDICINALE,
		MENSILE,
		BIMESTRALE,
		SEMESTRALE,
		ANNUALE
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Commessa commessa;
	
	@ManyToOne
	private VoceCosto voceCosto;
	
	private BigDecimal importo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
		
	@Enumerated(EnumType.STRING)
	private Tipologia tipologia;

	public Resoconto() {
            super();
	    importo = BigDecimal.ZERO;
	    data = new Date();
	    tipologia=Tipologia.MENSILE;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    public VoceCosto getVoceCosto() {
        return voceCosto;
    }

    public void setVoceCosto(VoceCosto voceCosto) {
        this.voceCosto = voceCosto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Commessa getCommessa() {
        return commessa;
    }

    public void setCommessa(Commessa commessa) {
        this.commessa = commessa;
    }
	
}
