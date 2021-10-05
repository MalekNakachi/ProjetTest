package com.example.gestionAchat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A Profile.
 */
@Entity
@Table(name = "pm_profile", uniqueConstraints = @UniqueConstraint(columnNames = {"application", "profilename"}))

public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotNull
    @Column(name = "profilename", nullable = false)
    private String profilename;

    @Column(name = "profildescription")
    private String profildescription;

    @Column(name = "comment")
    private String comment;

    @Column(name = "mail")
    private String mail;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "test")
    private Boolean test;

    @Column(name = "dateupdate")
    @UpdateTimestamp
    private LocalDate dateupdate;

    @Column(name = "datecreate")
    @CreationTimestamp
    private LocalDate datecreate;

    @Column(name = "reader")
    private String reader;

    @Column(name = "author")
    private String author;

    @Column(name = "createdby")
    private String createdby;

    @Column(name = "modifiedby")
    private String modifiedby;

    @ManyToOne
    @JsonIgnoreProperties("profiles")
    @JoinColumn(name = "application", nullable = false)
    private Application application;

    @ManyToMany
    @JoinTable(name = "pm_profile_role", joinColumns = @JoinColumn(name = "profile", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    public Profile() {
    }

    public Profile(Long id, Application application, @NotNull String profilename, List<Role> roles, String profildescription, LocalDate dateupdate, LocalDate datecreate, Boolean status, Boolean test, String comment, String reader, String author, String createdby, String modifiedby) {
        this.id = id;
        this.profilename = profilename;
        this.profildescription = profildescription;
        this.comment = comment;
        this.status = status;
        this.test = test;
        this.dateupdate = dateupdate;
        this.datecreate = datecreate;
        this.reader = reader;
        this.author = author;
        this.createdby = createdby;
        this.modifiedby = modifiedby;
        this.application = application;
        this.roles = roles;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public Profile profilename(String profilename) {
        this.profilename = profilename;
        return this;
    }

    public String getProfildescription() {
        return profildescription;
    }

    public void setProfildescription(String profildescription) {
        this.profildescription = profildescription;
    }

    public Profile profildescription(String profildescription) {
        this.profildescription = profildescription;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Profile comment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean isStatus() {
        return status;
    }

    public Profile status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isTest() {
        return test;
    }

    public Profile test(Boolean test) {
        this.test = test;
        return this;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public LocalDate getDateupdate() {
        return dateupdate;
    }

    public void setDateupdate(LocalDate dateupdate) {
        this.dateupdate = dateupdate;
    }

    public Profile dateupdate(LocalDate dateupdate) {
        this.dateupdate = dateupdate;
        return this;
    }

    public LocalDate getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
    }

    public Profile datecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
        return this;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public Profile reader(String reader) {
        this.reader = reader;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Profile author(String author) {
        this.author = author;
        return this;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Profile createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Profile modifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
        return this;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Profile application(Application application) {
        this.application = application;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Profile roles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Profile addRoles(Role role) {
        this.roles.add(role);
        role.getProfiles().add(this);
        return this;
    }

    public Profile removeRoles(Role role) {
        this.roles.remove(role);
        role.getProfiles().remove(this);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + getId() +
                ", profilename='" + getProfilename() + "'" +
                ", profildescription='" + getProfildescription() + "'" +
                ", comment='" + getComment() + "'" +
                ", status='" + isStatus() + "'" +
                ", test='" + isTest() + "'" +
                ", dateupdate='" + getDateupdate() + "'" +
                ", datecreate='" + getDatecreate() + "'" +
                ", reader='" + getReader() + "'" +
                ", author='" + getAuthor() + "'" +
                ", createdby='" + getCreatedby() + "'" +
                ", modifiedby='" + getModifiedby() + "'" +
                "}";
    }
}
