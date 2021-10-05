package com.example.gestionAchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * A Pv.
 */
@Entity
@Table(name = "pv")
public class Pv implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  @Column(name = "identifiant")
  private String identifiant;

    @Column(name = "liste_des_produits")
    private String listeDesProduits;


    @Column(name = "nomDemandeur")
    private String nomDemandeur;

    @Column(name = "prix")
    private Integer prix;

    @Column(name = "lieu_de_livraison")
    private String lieuDeLivraison;

    @Column(name = "tel")
    private Integer tel;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private Date date;

    @OneToOne(mappedBy = "pv")
    @JsonIgnore
    @NotFound(action= NotFoundAction.IGNORE)
    private Facture facture;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListeDesProduits() {
        return listeDesProduits;
    }

    public Pv listeDesProduits(String listeDesProduits) {
        this.listeDesProduits = listeDesProduits;
        return this;
    }

    public void setListeDesProduits(String listeDesProduits) {
        this.listeDesProduits = listeDesProduits;
    }

    public Integer getTel() {
        return tel;
    }

    public Pv tel(Integer tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }


    public String getNomDemandeur() {
        return nomDemandeur;
    }

    public Pv nomDemandeur(String nomDemandeur) {
        this.nomDemandeur = nomDemandeur;
        return this;
    }

    public void setNomDemandeur(String nomDemandeur) {
        this.nomDemandeur = nomDemandeur;
    }

    public Integer getPrix() {
        return prix;
    }

    public Pv prix(Integer prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public String getLieuDeLivraison() {
        return lieuDeLivraison;
    }

    public Pv lieuDeLivraison(String lieuDeLivraison) {
        this.lieuDeLivraison = lieuDeLivraison;
        return this;
    }

    public void setLieuDeLivraison(String lieuDeLivraison) {
        this.lieuDeLivraison = lieuDeLivraison;
    }

    public String getStatus() {
        return status;
    }

    public Pv status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public Pv date(Date date) {
        this.date = date;
        return this;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Facture getFacture() {
        return facture;
    }

    public Pv facture(Facture facture) {
        this.facture = facture;
        return this;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pv)) {
            return false;
        }
        return id != null && id.equals(((Pv) o).id);
    }

  public String getIdentifiant() {
    return identifiant;
  }

  public void setIdentifiant(String identifiant) {
    this.identifiant = identifiant;
  }

  @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pv{" +
                "id=" + getId() +
                ", listeDesProduits='" + getListeDesProduits() + "'" +
                ", nomFournisseur='" + getNomDemandeur() + "'" +
                ", prix=" + getPrix() +
                ", lieuDeLivraison='" + getLieuDeLivraison() + "'" +
                ", date='" + getDate() + "'" +
                ", status='" + getStatus() + "'" +
                ", tel='" + getTel() + "'" +
                ", nomDemandeur='" + getNomDemandeur() + "'" +

                "}";
    }
}
