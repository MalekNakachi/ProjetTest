package com.example.gestionAchat.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Consultation.
 */
@Entity
@Table(name = "consultation")
public class Consultation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_de_produit")
    private String nomDeProduit;

    @Column(name = "date")
    private Instant date;

    @Column(name = "prix_unitaire")
    private Integer prixUnitaire;

    @OneToOne
    @JoinColumn(unique = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private DemandeAchat demandeAchat;

    @OneToMany(mappedBy = "consultation")
    @NotFound(action= NotFoundAction.IGNORE)
    private Set<Fournisseur> fournisseurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomDeProduit() {
        return nomDeProduit;
    }

    public Consultation nomDeProduit(String nomDeProduit) {
        this.nomDeProduit = nomDeProduit;
        return this;
    }

    public void setNomDeProduit(String nomDeProduit) {
        this.nomDeProduit = nomDeProduit;
    }

    public Instant getDate() {
        return date;
    }

    public Consultation date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Integer getPrixUnitaire() {
        return prixUnitaire;
    }

    public Consultation prixUnitaire(Integer prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        return this;
    }

    public void setPrixUnitaire(Integer prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public DemandeAchat getDemandeAchat() {
        return demandeAchat;
    }

    public Consultation demandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
        return this;
    }

    public void setDemandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public Set<Fournisseur> getFournisseurs() {
        return fournisseurs;
    }

    public Consultation fournisseurs(Set<Fournisseur> fournisseurs) {
        this.fournisseurs = fournisseurs;
        return this;
    }

    public Consultation addFournisseur(Fournisseur fournisseur) {
        this.fournisseurs.add(fournisseur);
        fournisseur.setConsultation(this);
        return this;
    }

    public Consultation removeFournisseur(Fournisseur fournisseur) {
        this.fournisseurs.remove(fournisseur);
        fournisseur.setConsultation(null);
        return this;
    }

    public void setFournisseurs(Set<Fournisseur> fournisseurs) {
        this.fournisseurs = fournisseurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consultation)) {
            return false;
        }
        return id != null && id.equals(((Consultation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + getId() +
                ", nomDeProduit='" + getNomDeProduit() + "'" +
                ", date='" + getDate() + "'" +
                ", prixUnitaire=" + getPrixUnitaire() +
                "}";
    }
}
