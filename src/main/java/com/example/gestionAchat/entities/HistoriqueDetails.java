package com.example.gestionAchat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A HistoriqueDetails.
 */
@Entity
@Table(name = "pm_historique_details")
public class HistoriqueDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "newvalue")
    private String newvalue;

    @Column(name = "oldvalue")
    private String oldvalue;

    @Column(name = "property")
    private String property;

    @ManyToOne
    @JsonIgnoreProperties("historiqueDetails")
    private Historique historique;
    
	public HistoriqueDetails(Historique his_id, String property, String oldvalue, String newvalue) {
		super();
		this.historique = his_id;
		this.property = property;
		this.oldvalue = oldvalue;
		this.newvalue = newvalue;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewvalue() {
        return newvalue;
    }

    public HistoriqueDetails newvalue(String newvalue) {
        this.newvalue = newvalue;
        return this;
    }

    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }

    public String getOldvalue() {
        return oldvalue;
    }

    public HistoriqueDetails oldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
        return this;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    public String getProperty() {
        return property;
    }

    public HistoriqueDetails property(String property) {
        this.property = property;
        return this;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Historique getHistorique() {
        return historique;
    }

    public HistoriqueDetails historique(Historique historique) {
        this.historique = historique;
        return this;
    }

    public void setHistorique(Historique historique) {
        this.historique = historique;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoriqueDetails)) {
            return false;
        }
        return id != null && id.equals(((HistoriqueDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HistoriqueDetails{" +
            "id=" + getId() +
            ", newvalue='" + getNewvalue() + "'" +
            ", oldvalue='" + getOldvalue() + "'" +
            ", property='" + getProperty() + "'" +
            "}";
    }
}
