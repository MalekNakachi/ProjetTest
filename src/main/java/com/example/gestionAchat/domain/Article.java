package com.example.gestionAchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prix_ht")
    private Double prixHt;

    @Column(name = "staut")
    private String staut;
    @Column(name = "identifiant")
    private String identifiant;

    @Column(name = "quantite")
    private Integer quantite;

    @ManyToOne
    @JsonIgnoreProperties("articles")
    private DemandeAchat demandeAchat;

    @ManyToMany(mappedBy = "articles")
    @JsonIgnore
    private Set<BonCommande> bonCommandes = new HashSet<>();



    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Article nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getIdentifiant() {
        return identifiant;
    }

    public Article identifiant(String identifiant) {
        this.identifiant = identifiant;
        return this;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public Double getPrixHt() {
        return prixHt;
    }

    public Article prixHt(Double prixHt) {
        this.prixHt = prixHt;
        return this;
    }

    public void setPrixHt(Double prixHt) {
        this.prixHt = prixHt;
    }

    public String getStaut() {
        return staut;
    }

    public Article staut(String staut) {
        this.staut = staut;
        return this;
    }

    public void setStaut(String staut) {
        this.staut = staut;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public Article quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public DemandeAchat getDemandeAchat() {
        return demandeAchat;
    }

    public Article demandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
        return this;
    }

    public void setDemandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public Set<BonCommande> getBonCommandes() {
        return bonCommandes;
    }

    public Article bonCommandes(Set<BonCommande> bonCommandes) {
        this.bonCommandes = bonCommandes;
        return this;
    }

    public Article addBonCommande(BonCommande bonCommande) {
        this.bonCommandes.add(bonCommande);
        bonCommande.getArticles().add(this);
        return this;
    }

    public Article removeBonCommande(BonCommande bonCommande) {
        this.bonCommandes.remove(bonCommande);
        bonCommande.getArticles().remove(this);
        return this;
    }

    public void setBonCommandes(Set<BonCommande> bonCommandes) {
        this.bonCommandes = bonCommandes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return id != null && id.equals(((Article) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prixHt=" + getPrixHt() +
            ", staut='" + getStaut() + "'" +
            ", quantite=" + getQuantite() +
            "}";
    }
}
