package com.example.gestionAchat.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Immutable
public class UsersActifs {

        @Id
        @Column(name = "userId")
        private Long userId;

        @Column(name = "samaccountname")
        private String samaccountname;

        @Column(name = "displayname")
        private String displayname;

        @Column(name = "appartenance")
        private String appartenance;

       @Column(name = "mail")
       private String mail;

        @Column(name = "profile")
        private String profile;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSamaccountname() {
        return samaccountname;
    }

    public void setSamaccountname(String samaccountname) {
        this.samaccountname = samaccountname;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getAppartenance() {
        return appartenance;
    }

    public void setAppartenance(String appartenance) {
        this.appartenance = appartenance;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
