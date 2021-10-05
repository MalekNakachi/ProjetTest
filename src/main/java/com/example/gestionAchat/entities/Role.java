package com.example.gestionAchat.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Role.
 */
@Entity
@Table(name = "pm_role", uniqueConstraints = @UniqueConstraint(columnNames = {"application", "rolename"}))
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "rolename")
    private String rolename;

    @Column(name = "roledescription")
    private String roledescription;

    @Column(name = "comment")
    private String comment;

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

    @Column(name = "categorie")
    private String categorie;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getStatus() {
        return status;
    }

    public Boolean getTest() {
        return test;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @ManyToOne
    @JsonIgnoreProperties("roles")
    @JoinColumn(name = "application", nullable = false)
    private Application application;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<Profile> profiles = new HashSet<>();

    public Role(Long id, Application application, String rolename, String roledescription, LocalDate dateupdate, LocalDate datecreate, Boolean test, Boolean status, String comment, String reader, String author, String createdby, String modifiedby) {
        this.id = id;
        this.rolename = rolename;
        this.roledescription = roledescription;
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
    }

    public Role() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Role rolename(String rolename) {
        this.rolename = rolename;
        return this;
    }

    public String getRoledescription() {
        return roledescription;
    }

    public void setRoledescription(String roledescription) {
        this.roledescription = roledescription;
    }

    public Role roledescription(String roledescription) {
        this.roledescription = roledescription;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Role comment(String comment) {
        this.comment = comment;
        return this;
    }

    public Boolean isStatus() {
        return status;
    }

    public Role status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isTest() {
        return test;
    }

    public Role test(Boolean test) {
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

    public Role dateupdate(LocalDate dateupdate) {
        this.dateupdate = dateupdate;
        return this;
    }

    public LocalDate getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
    }

    public Role datecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
        return this;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public Role reader(String reader) {
        this.reader = reader;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Role author(String author) {
        this.author = author;
        return this;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Role createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Role modifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
        return this;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Role application(Application application) {
        this.application = application;
        return this;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }

    public Role profiles(Set<Profile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public Role addProfile(Profile profile) {
        this.profiles.add(profile);
        profile.getRoles().add(this);
        return this;
    }

    public Role removeProfile(Profile profile) {
        this.profiles.remove(profile);
        profile.getRoles().remove(this);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + getId() +
                ", rolename='" + getRolename() + "'" +
                ", roledescription='" + getRoledescription() + "'" +
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
