package com.example.gestionAchat.config.audit;


import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.DefaultRevisionEntity;


import javax.persistence.*;
/*
@Entity

@RevisionEntity(AuditRevisionListener.class)

public class AuditRevisionEntity extends DefaultRevisionEntity {

    @Column(name = "username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
*/
// Entité utilisée pour la gestion des audits (création d'une révision)


@Entity
@RevisionEntity(AuditListener.class)

    public class Revision extends DefaultRevisionEntity {

    // Nom de l'utilisateur
   // @Column(name = "USERNAME")
   private String userName;

    // Getters / Setters

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
