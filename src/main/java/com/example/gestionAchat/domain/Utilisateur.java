package com.example.gestionAchat.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom_utilisateur")
    private String nomUtilisateur;

    @Column(name = "email")
    private String email;

    @Column(name = "mdp")
    private String mdp;

    @OneToOne
    @JoinColumn(unique = true)
    private Profil profil;

    @OneToMany(mappedBy = "utilisateur")
    private Set<DepartmentS> departments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "utilisateur_group",
               joinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private Set<Group> groups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Utilisateur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Utilisateur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public Utilisateur nomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
        return this;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getEmail() {
        return email;
    }

    public Utilisateur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public Utilisateur mdp(String mdp) {
        this.mdp = mdp;
        return this;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Profil getProfil() {
        return profil;
    }

    public Utilisateur profil(Profil profil) {
        this.profil = profil;
        return this;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Set<DepartmentS> getDepartments() {
        return departments;
    }

    public Utilisateur departments(Set<DepartmentS> departments) {
        this.departments = departments;
        return this;
    }

    public Utilisateur addDepartment(DepartmentS department) {
        this.departments.add(department);
        department.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeDepartment(DepartmentS department) {
        this.departments.remove(department);
        department.setUtilisateur(null);
        return this;
    }

    public void setDepartments(Set<DepartmentS> departments) {
        this.departments = departments;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Utilisateur groups(Set<Group> groups) {
        this.groups = groups;
        return this;
    }

    public Utilisateur addGroup(Group group) {
        this.groups.add(group);
        group.getUtilisateurs().add(this);
        return this;
    }

    public Utilisateur removeGroup(Group group) {
        this.groups.remove(group);
        group.getUtilisateurs().remove(this);
        return this;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utilisateur)) {
            return false;
        }
        return id != null && id.equals(((Utilisateur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nomUtilisateur='" + getNomUtilisateur() + "'" +
            ", email='" + getEmail() + "'" +
            ", mdp='" + getMdp() + "'" +
            "}";
    }
}
