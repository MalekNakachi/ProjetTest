package com.example.gestionAchat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/*import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;*/

@Entity
//@Document(indexName = "pm.user_access", createIndex = true, type = "user_access")
@Table(name = "pm_user_access", uniqueConstraints = @UniqueConstraint(columnNames = {"application", "Profile", "users"}))
public class UserAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

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

    @ManyToOne
    @JsonIgnoreProperties("userAccesses")
    @JoinColumn(name = "application", nullable = false)
    private Application application;

    @ManyToOne
    @JsonIgnoreProperties("userAccesses")
    @JoinColumn(name = "profile", nullable = false)
    private Profile profile;

    @ManyToOne
    @JsonIgnoreProperties("userAccesses")
    @JoinColumn(name = "users", nullable = false)
    private User user;


    public UserAccess() {
    }

    public UserAccess(Long id, User user, Profile profile, Application application, LocalDate dateupdate, LocalDate datecreate, Boolean status, Boolean test, String comment, String reader, String author, String createdby, String modifiedby) {
        this.id = id;
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
        this.profile = profile;
        this.user = user;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserAccess comment(String comment) {
        this.comment = comment;
        return this;
    }

    public Boolean isStatus() {
        return status;
    }

    public UserAccess status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isTest() {
        return test;
    }

    public UserAccess test(Boolean test) {
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

    public UserAccess dateupdate(LocalDate dateupdate) {
        this.dateupdate = dateupdate;
        return this;
    }

    public LocalDate getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
    }

    public UserAccess datecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
        return this;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public UserAccess reader(String reader) {
        this.reader = reader;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public UserAccess author(String author) {
        this.author = author;
        return this;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public UserAccess createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public UserAccess modifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
        return this;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public UserAccess application(Application application) {
        this.application = application;
        return this;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public UserAccess profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User users) {
        this.user = users;
    }

    public UserAccess users(User users) {
        this.user = users;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAccess)) {
            return false;
        }
        return id != null && id.equals(((UserAccess) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserAccess{" +
                "id=" + getId() +
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
