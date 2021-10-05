package com.example.gestionAchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Reference.
 */
@Entity
@Table(name = "reference")
public class Reference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;
    @Column(name = "Nomenclature")
    private String Nomenclature;
    @Column(name = "compteur")
    private Integer compteur;
    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public String getNomenclature() {
        return Nomenclature;
    }
    public Integer getCompteur() {
        return compteur;
    }


    public void setType(String type) {
        this.type = type;
    }
    public void setNomenclature (String Nomenclature) {
        this.Nomenclature = Nomenclature;
    }
    public void setCompteur(Integer compteur) {
        this.compteur = compteur;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "id=" + getId() +
                ", type=" + getType() +
                ", Nomenclature='" + getNomenclature() + "'" +
                ", compteur='" + getCompteur() + "'" +
                "}";
    }
}






