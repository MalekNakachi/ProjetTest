package com.example.gestionAchat.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Affaire.
 */

@Entity
@Table(name = "affaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Affaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomaffaire")
    private String nomaffaire;

    @Column(name = "dateaffaire")
    private LocalDate dateaffaire;

    @Column(name = "objetaffaire")
    private String objetaffaire;

    @Column(name = "concerned")
    private String concerned;

    @Column(name = "responsable")
    private String responsable;

    @Column(name = "readers")
    private String readers;

    @Column(name = "authors")
    private String authors;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_update")
    private Instant dateUpdate;

    @Column(name = "is_test")
    private Boolean isTest;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_active")
    private Boolean isActive;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomaffaire() {
        return nomaffaire;
    }

    public Affaire nomaffaire(String nomaffaire) {
        this.nomaffaire = nomaffaire;
        return this;
    }

    public void setNomaffaire(String nomaffaire) {
        this.nomaffaire = nomaffaire;
    }

    public LocalDate getDateaffaire() {
        return dateaffaire;
    }

    public Affaire dateaffaire(LocalDate dateaffaire) {
        this.dateaffaire = dateaffaire;
        return this;
    }

    public void setDateaffaire(LocalDate dateaffaire) {
        this.dateaffaire = dateaffaire;
    }

    public String getObjetaffaire() {
        return objetaffaire;
    }

    public Affaire objetaffaire(String objetaffaire) {
        this.objetaffaire = objetaffaire;
        return this;
    }

    public void setObjetaffaire(String objetaffaire) {
        this.objetaffaire = objetaffaire;
    }

    public String getConcerned() {
        return concerned;
    }

    public Affaire concerned(String concerned) {
        this.concerned = concerned;
        return this;
    }

    public void setConcerned(String concerned) {
        this.concerned = concerned;
    }

    public String getResponsable() {
        return responsable;
    }

    public Affaire responsable(String responsable) {
        this.responsable = responsable;
        return this;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getReaders() {
        return readers;
    }

    public Affaire readers(String readers) {
        this.readers = readers;
        return this;
    }

    public void setReaders(String readers) {
        this.readers = readers;
    }

    public String getAuthors() {
        return authors;
    }

    public Affaire authors(String authors) {
        this.authors = authors;
        return this;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Affaire dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdate() {
        return dateUpdate;
    }

    public Affaire dateUpdate(Instant dateUpdate) {
        this.dateUpdate = dateUpdate;
        return this;
    }

    public void setDateUpdate(Instant dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Boolean isIsTest() {
        return isTest;
    }

    public Affaire isTest(Boolean isTest) {
        this.isTest = isTest;
        return this;
    }

    public void setIsTest(Boolean isTest) {
        this.isTest = isTest;
    }

    public String getComment() {
        return comment;
    }

    public Affaire comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Affaire isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getTest() {
        return isTest;
    }

    public void setTest(Boolean test) {
        isTest = test;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Affaire)) {
            return false;
        }
        return id != null && id.equals(((Affaire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Affaire{" +
            "id=" + getId() +
            ", nomaffaire='" + getNomaffaire() + "'" +
            ", dateaffaire='" + getDateaffaire() + "'" +
            ", objetaffaire='" + getObjetaffaire() + "'" +
            ", concerned='" + getConcerned() + "'" +
            ", responsable='" + getResponsable() + "'" +
            ", readers='" + getReaders() + "'" +
            ", authors='" + getAuthors() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdate='" + getDateUpdate() + "'" +
            ", isTest='" + isIsTest() + "'" +
            ", comment='" + getComment() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
