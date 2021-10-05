package com.example.gestionAchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Department.
 */
@Entity
@Table(name = "department")
public class DepartmentS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "chef")
    private String chef;

    @Column(name = "abreviation")
    private String abreviation;

    @Column(name = "description")
    private String description;

    @Column(name = "depart_sup")
    private String departSup;

    @ManyToOne
    @JsonIgnoreProperties("departments")
    private Utilisateur utilisateur;

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

    public DepartmentS nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getChef() {
        return chef;
    }

    public DepartmentS chef(String chef) {
        this.chef = chef;
        return this;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public DepartmentS abreviation(String abreviation) {
        this.abreviation = abreviation;
        return this;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getDescription() {
        return description;
    }

    public DepartmentS description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartSup() {
        return departSup;
    }

    public DepartmentS departSup(String departSup) {
        this.departSup = departSup;
        return this;
    }

    public void setDepartSup(String departSup) {
        this.departSup = departSup;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public DepartmentS utilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        return this;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartmentS)) {
            return false;
        }
        return id != null && id.equals(((DepartmentS) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Department{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", chef='" + getChef() + "'" +
            ", abreviation='" + getAbreviation() + "'" +
            ", description='" + getDescription() + "'" +
            ", departSup='" + getDepartSup() + "'" +
            "}";
    }
}
