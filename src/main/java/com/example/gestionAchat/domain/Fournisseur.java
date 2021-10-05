package com.example.gestionAchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fournisseur.
 */
@Entity
@Table(name = "fournisseur")
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "identifiant")
    private String identifiant;

    @Column(name = "Nom_Fournisseur")
    private String nom;

    @Column(name = "Tel")
    private Integer tel;

    @Column(name = "email")
    private String email;

    @Column(name = "Type")
    private String type;

    @Column(name = "Code_Postal")
    private Integer codePostal;

    @Column(name = "Fax")
    private Integer fax;

    @Column(name = "Region")
    private String region;

    @Column(name = "Note")
    private Integer note;

    @OneToMany(mappedBy = "fournisseur")

    @NotFound(action= NotFoundAction.IGNORE)
    Set<Offre> offres = new HashSet<>();


    @ManyToOne
    @JsonIgnoreProperties("fournisseurs")
    @NotFound(action= NotFoundAction.IGNORE)
    private Consultation consultation;

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

    public Fournisseur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public Fournisseur identifiant(String identifiant) {
        this.identifiant = identifiant;
        return this;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public Integer getTel() {
        return tel;
    }

    public Fournisseur tel(Integer tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }



    public String getEmail() {
        return email;
    }

    public Fournisseur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public Fournisseur type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public Fournisseur codePostal(Integer codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public Integer getFax() {
        return fax;
    }

    public Fournisseur fax(Integer fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(Integer fax) {
        this.fax = fax;
    }

    public String getRegion() {
        return region;
    }

    public Fournisseur region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getNote() {
        return note;
    }

    public Fournisseur note(Integer note) {
        this.note = note;
        return this;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Set<Offre> getOffres() {
        return offres;
    }

    public Fournisseur offres(Set<Offre> offres) {
        this.offres = offres;
        return this;
    }

    public Fournisseur addOffre(Offre offre) {
        this.offres.add(offre);
        offre.setFournisseur(this);
        return this;
    }

    public Fournisseur removeOffre(Offre offre) {
        this.offres.remove(offre);
        offre.setFournisseur(null);
        return this;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public Fournisseur consultation(Consultation consultation) {
        this.consultation = consultation;
        return this;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }




    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fournisseur)) {
            return false;
        }
        return id != null && id.equals(((Fournisseur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fournisseur{" +
                "id=" + getId() +
                ", nom='" + getNom() + "'" +
                ", tel=" + getTel() +
                ", email='" + getEmail() + "'" +
                ", type='" + getType() + "'" +
                ", codePostal='" + getCodePostal() + "'" +
                ", fax='" + getFax() + "'" +
                ", region='" + getRegion() + "'" +
                ", note=" + getNote() +
                "}";
    }
}
