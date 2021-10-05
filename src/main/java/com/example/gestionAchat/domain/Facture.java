package com.example.gestionAchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Facture.
 */
@Entity
@Table(name = "facture")
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_facture")
    private Integer numFacture;

    @Column(name = "ref")
    private String ref;

    @Column(name = "listeProduits")
    private String listeProduits;

    @Column(name = "date_creation")
    private Instant dateCreation;

    @Column(name = "date_limite")
    private Instant dateLimite;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "etat")
    private String etat;

    @Column(name = "prestation")
    private String prestation;

    @Column(name = "remise")
    private Double remise;

    @OneToOne
    @JoinColumn(unique = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private BonCommande bonCommande;

    @OneToOne
    @JoinColumn(unique = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private Pv pv;

    @OneToOne(mappedBy = "facture")
    @JsonIgnore
    private Processus processus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumFacture() {
        return numFacture;
    }

    public Facture numFacture(Integer numFacture) {
        this.numFacture = numFacture;
        return this;
    }

    public void setNumFacture(Integer numFacture) {
        this.numFacture = numFacture;
    }


    public String getListeProduits() {
        return listeProduits;
    }

    public Facture listeProduits(String listeProduits) {
        this.listeProduits = listeProduits;
        return this;
    }

    public void setListeProduits(String listeProduits) {
        this.listeProduits = listeProduits;
    }


    public String getEtat() {
        return etat;
    }

    public Facture etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getPrestation() {
        return prestation;
    }

    public Facture prestation(String prestation) {
        this.prestation = prestation;
        return this;
    }

    public void setPrestation(String prestation) {
        this.prestation = prestation;
    }

    public String getRef() {
        return ref;
    }

    public Facture ref(String ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Instant getDateCreation() {
        return dateCreation;
    }

    public Facture dateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Instant getDateLimite() {
        return dateLimite;
    }

    public Facture dateLimite(Instant dateLimite) {
        this.dateLimite = dateLimite;
        return this;
    }

    public void setDateLimite(Instant dateLimite) {
        this.dateLimite = dateLimite;
    }

    public Double getMontant() {
        return montant;
    }

    public Facture montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getRemise() {
        return remise;
    }

    public Facture remise(Double remise) {
        this.remise = remise;
        return this;
    }

    public void setRemise(Double remise) {
        this.remise = remise;
    }

    public BonCommande getBonCommande() {
        return bonCommande;
    }

    public Facture bonCommande(BonCommande bonCommande) {
        this.bonCommande = bonCommande;
        return this;
    }

    public void setBonCommande(BonCommande bonCommande) {
        this.bonCommande = bonCommande;
    }

    public Pv getPv() {
        return pv;
    }

    public Facture pv(Pv pv) {
        this.pv = pv;
        return this;
    }

    public void setPv(Pv pv) {
        this.pv = pv;
    }

    public Processus getProcessus() {
        return processus;
    }

    public Facture processus(Processus processus) {
        this.processus = processus;
        return this;
    }

    public void setProcessus(Processus processus) {
        this.processus = processus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facture)) {
            return false;
        }
        return id != null && id.equals(((Facture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "id=" + getId() +
                ", numFacture=" + getNumFacture() +
                ", ref='" + getRef() + "'" +
                ", dateCreation='" + getDateCreation() + "'" +
                ", dateLimite='" + getDateLimite() + "'" +
                ", montant=" + getMontant() +
                ", remise=" + getRemise() +
                ", prestation=" + getPrestation()+
                ", etat=" + getEtat() +
                "}";
    }
}

