package it.arsinfo.gc.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Commessa {

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
	private Long m_id;
	
	private String m_nome;
	
	private String m_descr;
	
	private BigDecimal m_importo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date m_inizio;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date m_fine;
	
	@Enumerated(EnumType.STRING)
	private Tipologia m_tipologia;

	public Commessa() {
		super();
	}

    public Long getId() {
        return m_id;
    }

    public void setId(Long id) {
        m_id = id;
    }

    public String getNome() {
        return m_nome;
    }

    public void setNome(String nome) {
        m_nome = nome;
    }

    public String getDescr() {
        return m_descr;
    }

    public void setDescr(String descr) {
        m_descr = descr;
    }

    public BigDecimal getImporto() {
        return m_importo;
    }

    public void setImporto(BigDecimal importo) {
        m_importo = importo;
    }

    public Date getInizio() {
        return m_inizio;
    }

    public void setInizio(Date inizio) {
        m_inizio = inizio;
    }

    public Date getFine() {
        return m_fine;
    }

    public void setFine(Date fine) {
        m_fine = fine;
    }

    public Tipologia getTipologia() {
        return m_tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        m_tipologia = tipologia;
    }

	
	
	
	
	
}
