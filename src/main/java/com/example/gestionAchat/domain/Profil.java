package com.example.gestionAchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Profil.
 */
@Entity
@Table(name = "profil")
public class Profil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position")
    private String position;

    @Column(name = "salaire")
    private Double salaire;

    @ManyToMany
    @JoinTable(name = "profil_role",
               joinColumns = @JoinColumn(name = "profil_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "profil")
    @JsonIgnore
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public Profil position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getSalaire() {
        return salaire;
    }

    public Profil salaire(Double salaire) {
        this.salaire = salaire;
        return this;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Profil roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Profil addRole(Role role) {
        this.roles.add(role);
        role.getProfils().add(this);
        return this;
    }

    public Profil removeRole(Role role) {
        this.roles.remove(role);
        role.getProfils().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Profil utilisateur(Utilisateur utilisateur) {
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
        if (!(o instanceof Profil)) {
            return false;
        }
        return id != null && id.equals(((Profil) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profil{" +
            "id=" + getId() +
            ", position='" + getPosition() + "'" +
            ", salaire=" + getSalaire() +
            "}";
    }
}
