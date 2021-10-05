package com.example.gestionAchat.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Immutable
public class UserIdentifiant {

        @Id
        @Column(name = "id")
        private Long id;


    @Column(name = "samaccountname")
    private String samaccountname;


    @Column(name = "displayname")
    private String displayname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
