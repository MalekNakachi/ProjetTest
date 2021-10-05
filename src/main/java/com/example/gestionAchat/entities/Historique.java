package com.example.gestionAchat.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Historique.
 */
@Entity
@Table(name = "pm_historique")
public class Historique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "username")
    private String username;

    @Column(name = "forwarded")
    private String forwarded;

    @Column(name = "remote_addr")
    private String remoteAddr;

    @Column(name = "decs")
    private String decs;

    @Column(name = "date")
    private LocalDate date;

    
    public Historique(final String type, final Long type_id, final String username, final String clientAdress, final String serverAdress, final String decs, final LocalDate date) {
        this.type = type;
        this.typeId = type_id;
        this.username = username;
        this.forwarded = clientAdress;
        this.remoteAddr = serverAdress;
        this.decs = decs;
        this.date = date;
    }  
	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Historique type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Historique typeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getUser() {
        return username;
    }

    public Historique user(String user) {
        this.username = user;
        return this;
    }

    public void setUser(String user) {
        this.username = user;
    }

    public String getForwarded() {
        return forwarded;
    }

    public Historique forwarded(String forwarded) {
        this.forwarded = forwarded;
        return this;
    }

    public void setForwarded(String forwarded) {
        this.forwarded = forwarded;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public Historique remoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
        return this;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getDecs() {
        return decs;
    }

    public Historique decs(String decs) {
        this.decs = decs;
        return this;
    }

    public void setDecs(String decs) {
        this.decs = decs;
    }

    public LocalDate getDate() {
        return date;
    }

    public Historique date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Historique)) {
            return false;
        }
        return id != null && id.equals(((Historique) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Historique{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", typeId='" + getTypeId() + "'" +
            ", user='" + getUser() + "'" +
            ", forwarded='" + getForwarded() + "'" +
            ", remoteAddr='" + getRemoteAddr() + "'" +
            ", decs='" + getDecs() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
