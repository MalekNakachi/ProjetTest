package com.example.gestionAchat.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A JournalDetails.
 */
@Entity
@Table(name = "journal_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JournalDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="field")
    private String field;

    @Size(max = 200000)
    @Column(name="oldValue")
    private String oldValue;

    @Size(max = 200000)
    @Column(name="newValue")
    private String newValue;

    @Column(name="motifsuspension")
    private String motifsuspension;


    @Column(name="observation")
    private String observation;

    @Column(name="datedebutsuspension")
    private LocalDate datedebutsuspension;


    @Column(name="datefinsuspension")
    private LocalDate datefinsuspension;

    @Column(name="idbc")
    private String idbc;


    @ManyToOne
    private Journals journal;

    public JournalDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Journals getJournal() {
        return journal;
    }

    public void setJournal(Journals journal) {
        this.journal = journal;
    }

    public String getMotifsuspension() {
        return motifsuspension;
    }

    public void setMotifsuspension(String motifsuspension) {
        this.motifsuspension = motifsuspension;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDate getDatedebutsuspension() {
        return datedebutsuspension;
    }

    public void setDatedebutsuspension(LocalDate datedebutsuspension) {
        this.datedebutsuspension = datedebutsuspension;
    }

    public LocalDate getDatefinsuspension() {
        return datefinsuspension;
    }

    public void setDatefinsuspension(LocalDate datefinsuspension) {
        this.datefinsuspension = datefinsuspension;
    }


    public String getIdbc() {
        return idbc;
    }

    public void setIdbc(String idbc) {
        this.idbc = idbc;
    }


    public JournalDetails(String field, String oldValue, String newValue, Journals journal) {
        this.field = field;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.journal = journal;
    }
}
