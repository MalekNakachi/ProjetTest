package com.example.gestionAchat.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A Journals.
 */
@Entity
@Table(name = "journals")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Journals implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //    private int idObj;
    @Column(name="usertr")
    private String usertr;

    @Column(name="addIP")
    private String addIP;

    @Column(name="datetr")
    private Date datetr;

    @Column(name="navigator")
    private String navigator;

    @Column(name="typeOp")
    private String typeOp;

    @Column(name="desktopeClient")
    private String desktopeClient;

    //private long idClient;

//    @ManyToOne
//    private Client client;


    @ManyToOne
    //  @JsonIgnoreProperties("journals")
//    @JsonIgnore
    private Affaire affaire ;


    public void setId(long id) {
        this.id = id;
    }

    public String getUsertr() {
        return usertr;
    }

    public void setUsertr(String usertr) {
        this.usertr = usertr;
    }

    public Date getDatetr() {
        return datetr;
    }

    public void setDatetr(Date datetr) {
        this.datetr = datetr;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return usertr;
    }

    public void setUser(String user) {
        this.usertr = user;
    }

    public String getAddIP() {
        return addIP;
    }

    public void setAddIP(String addIP) {
        this.addIP = addIP;
    }

    public Date getDate() {
        return datetr;
    }

    public void setDate(Date date) {
        this.datetr = date;
    }

    public String getNavigator() {
        return navigator;
    }

    public void setNavigator(String navigator) {
        this.navigator = navigator;
    }

    public String getTypeOp() {
        return typeOp;
    }

    public void setTypeOp(String typeOp) {
        this.typeOp = typeOp;
    }

    //    public Client getClient() {
//        return client;
//    }

//    public void setClient(Client client) {
//        this.client = client;
//    }



    public Affaire getAffaire() {
        return affaire;
    }

    public void setAffaire(Affaire affaire) {
        this.affaire = affaire;
    }

    public String getDesktopeClient() {
        return desktopeClient;
    }

    public void setDesktopeClient(String desktopeClient) {
        this.desktopeClient = desktopeClient;
    }





    public Journals(String usertr, String addIP, Date datetr, String navigator, String typeOp, String desktopeClient) {
        this.usertr = usertr;
        this.addIP = addIP;
        this.datetr = datetr;
        this.navigator = navigator;
        this.typeOp = typeOp;
        this.desktopeClient = desktopeClient;
    }

    public Journals(String usertr, String addIP, Date datetr, String navigator, String typeOp, String desktopeClient, Affaire affaire) {
        this.usertr = usertr;
        this.addIP = addIP;
        this.datetr = datetr;
        this.navigator = navigator;
        this.typeOp = typeOp;
        this.desktopeClient = desktopeClient;
        this.affaire = affaire;
    }

    public Journals(){}
}
