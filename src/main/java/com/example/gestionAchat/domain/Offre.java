
package com.example.gestionAchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * A Offre.
 */

@Entity
@Table(name = "offre")
public class Offre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(name = "identifiant")
    private String identifiant;

    @Column(name = "liste_des_produits")
    private String listeDesProduits;

    @Column(name = "description")
    private String description;

    @Column(name = "nom_fournisseur")
    private String nomFournisseur;

    @Column(name = "prix")
    private Integer prix;

    @Column(name = "lieu_de_livraison")
    private String lieuDeLivraison;

    @Column(name = "date")
    private Date date;

    @OneToOne
    @JsonIgnore
    @NotFound(action=NotFoundAction.IGNORE)
    private BonCommande bonCommande;

    @ManyToOne
    @JsonIgnoreProperties("offres")
    @NotFound(action=NotFoundAction.IGNORE)
    private DemandeAchat demandeAchat;

    @ManyToOne
    @JsonIgnoreProperties("offres")
    @NotFound(action= NotFoundAction.IGNORE)
    private Fournisseur fournisseur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Offre description (String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getListeDesProduits() {
        return listeDesProduits;
    }

    public Offre listeDesProduits(String listeDesProduits) {
        this.listeDesProduits = listeDesProduits;
        return this;
    }

    public void setListeDesProduits(String listeDesProduits) {
        this.listeDesProduits = listeDesProduits;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public Offre nomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
        return this;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public Offre identifiant(String identifiant) {
        this.identifiant = identifiant;
        return this;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public Integer getPrix() {
        return prix;
    }

    public Offre prix(Integer prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public String getLieuDeLivraison() {
        return lieuDeLivraison;
    }

    public Offre lieuDeLivraison(String lieuDeLivraison) {
        this.lieuDeLivraison = lieuDeLivraison;
        return this;
    }

    public void setLieuDeLivraison(String lieuDeLivraison) {
        this.lieuDeLivraison = lieuDeLivraison;
    }

    public Date getDate() {
        return date;
    }

    public Offre date(Date date) {
        this.date = date;
        return this;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BonCommande getBonCommande() {
        return bonCommande;
    }

    public Offre bonCommande(BonCommande bonCommande) {
        this.bonCommande = bonCommande;
        return this;
    }

    public void setBonCommande(BonCommande bonCommande) {
        this.bonCommande = bonCommande;
    }

    public DemandeAchat getDemandeAchat() {
        return demandeAchat;
    }

    public Offre demandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
        return this;
    }

    public void setDemandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public Offre fournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
        return this;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offre)) {
            return false;
        }
        return id != null && id.equals(((Offre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Offre{" +
                "id=" + getId() +
                ", listeDesProduits='" + getListeDesProduits() + "'" +
                ", nomFournisseur='" + getNomFournisseur() + "'" +
                ", prix=" + getPrix() +
                ", lieuDeLivraison='" + getLieuDeLivraison() + "'" +
                ", date='" + getDate() + "'" +
                ", identifiant='" + getIdentifiant() + "'" +
                ", description='" + getDescription() + "'" +

                "}";
    }
}
