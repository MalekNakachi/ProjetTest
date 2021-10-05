package com.example.gestionAchat.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "pm_auth_affectation")
public class AuthAffectation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Application application;
    @ManyToOne
    private Profile profile;
    private String groupe;
    private int orders;
    @UpdateTimestamp
    private LocalDate dateupdate;
    @CreationTimestamp
    private LocalDate datecreate;
    private Boolean status;
    private Boolean test;
    private String comment;
    private String reader;
    private String author;
    @CreatedBy
    private String createdby;
    @LastModifiedBy
    private String modifiedby;

    public AuthAffectation(Long id, Application application, Profile profile, String groupe, int orders, LocalDate dateupdate,
                           LocalDate datecreate, Boolean status, Boolean test, String comment, String reader, String author,
                           String createdby, String modifiedby) {
        super();
        this.id = id;
        this.application = application;
        this.profile = profile;
        this.groupe = groupe;
        this.orders = orders;
        this.dateupdate = dateupdate;
        this.datecreate = datecreate;
        this.status = status;
        this.test = test;
        this.comment = comment;
        this.reader = reader;
        this.author = author;
        this.createdby = createdby;
        this.modifiedby = modifiedby;
    }

    public AuthAffectation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public LocalDate getDateupdate() {
        return dateupdate;
    }

    public void setDateupdate(LocalDate dateupdate) {
        this.dateupdate = dateupdate;
    }

    public LocalDate getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }


}
