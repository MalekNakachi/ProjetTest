package com.example.gestionAchat.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ConfigLdap.
 */
@Entity
@Table(name = "pm_config_ldap")
public class ConfigLdap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "base")
    private String base;

    @Column(name = "password")
    private String password;

    @Column(name = "user_dn")
    private String userDn;


    @Column(name = "name", unique = true)
    private String name;

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


    public ConfigLdap(Long id, String name, String url, String base, String userDn, String password, LocalDate dateupdate, LocalDate datecreate, Boolean status, Boolean test, String comment, String reader, String author, String createdby, String modifiedby) {
        this.id = id;
        this.url = url;
        this.base = base;
        this.password = password;
        this.userDn = userDn;
        this.name = name;
        this.comment = comment;
        this.status = status;
        this.test = test;
        this.dateupdate = dateupdate;
        this.datecreate = datecreate;
        this.reader = reader;
        this.author = author;
        this.createdby = createdby;
        this.modifiedby = modifiedby;
    }

    public ConfigLdap() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ConfigLdap url(String url) {
        this.url = url;
        return this;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public ConfigLdap base(String base) {
        this.base = base;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ConfigLdap password(String password) {
        this.password = password;
        return this;
    }

    public String getUserDn() {
        return userDn;
    }

    public void setUserDn(String userDn) {
        this.userDn = userDn;
    }

    public ConfigLdap userDn(String userDn) {
        this.userDn = userDn;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConfigLdap name(String name) {
        this.name = name;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ConfigLdap comment(String comment) {
        this.comment = comment;
        return this;
    }

    public Boolean isStatus() {
        return status;
    }

    public ConfigLdap status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isTest() {
        return test;
    }

    public ConfigLdap test(Boolean test) {
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

    public ConfigLdap dateupdate(LocalDate dateupdate) {
        this.dateupdate = dateupdate;
        return this;
    }

    public LocalDate getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
    }

    public ConfigLdap datecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
        return this;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public ConfigLdap reader(String reader) {
        this.reader = reader;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ConfigLdap author(String author) {
        this.author = author;
        return this;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public ConfigLdap createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public ConfigLdap modifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigLdap)) {
            return false;
        }
        return id != null && id.equals(((ConfigLdap) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ConfigLdap{" +
                "id=" + getId() +
                ", url='" + getUrl() + "'" +
                ", base='" + getBase() + "'" +
                ", password='" + getPassword() + "'" +
                ", userDn='" + getUserDn() + "'" +
                ", name='" + getName() + "'" +
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
