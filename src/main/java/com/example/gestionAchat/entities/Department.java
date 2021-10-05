package com.example.gestionAchat.entities;

import com.example.gestionAchat.domain.enumeration.CategorieDept;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Department.
 */
@Entity
@Table(name = "pm_department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "managedby")
    private String managedby;

    @Column(name = "distinguishedname")
    private String distinguishedname;

    @Column(name = "description")
    private String description;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "dept_superieur")
    private String dept_superieur;

    @Column(name = "assistante")
    private String assistante;

    @Column(name = "interim")
    private String interim;

    @Column(name = "categorie")
    private CategorieDept categorie;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDept_superieur() {
        return dept_superieur;
    }

    public void setDept_superieur(String dept_superieur) {
        this.dept_superieur = dept_superieur;
    }



    public String getAssistante() {
        return assistante;
    }

    public void setAssistante(String assistante) {
        this.assistante = assistante;
    }

    public String getInterim() {
        return interim;
    }

    public CategorieDept getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDept categorie) {
        this.categorie = categorie;
    }

    public Boolean getStatus() {
        return status;
    }

    public Boolean getTest() {
        return test;
    }

    public void setInterim(String interim) {
        this.interim = interim;
    }

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
    @JoinColumn()
    private ConfigLdap configLdap;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not
    // remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department name(String name) {
        this.name = name;
        return this;
    }

    public String getManagedby() {
        return managedby;
    }

    public void setManagedby(String managedby) {
        this.managedby = managedby;
    }

    public Department managedby(String managedby) {
        this.managedby = managedby;
        return this;
    }

    public String getDistinguishedname() {
        return distinguishedname;
    }

    public void setDistinguishedname(String distinguishedname) {
        this.distinguishedname = distinguishedname;
    }

    public Department distinguishedname(String distinguishedname) {
        this.distinguishedname = distinguishedname;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Department description(String description) {
        this.description = description;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Department comment(String comment) {
        this.comment = comment;
        return this;
    }


    public Boolean isStatus() {
        return status;
    }

    public Department status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isTest() {
        return test;
    }

    public Department test(Boolean test) {
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

    public Department dateupdate(LocalDate dateupdate) {
        this.dateupdate = dateupdate;
        return this;
    }

    public LocalDate getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
    }

    public Department datecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
        return this;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public Department reader(String reader) {
        this.reader = reader;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Department author(String author) {
        this.author = author;
        return this;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Department createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Department modifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here, do not remove

    public ConfigLdap getConfigLdap() {
        return configLdap;
    }

    public void setConfigLdap(ConfigLdap configLdap) {
        this.configLdap = configLdap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        return id != null && id.equals(((Department) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Department{" + "id=" + getId() + ", name='" + getName() + "'" + ", managedby='" + getManagedby() + "'"
                + ", distinguishedname='" + getDistinguishedname() + "'" + ", description='" + getDescription() + "'"
                + ", comment='" + getComment() + "'" + ", status='" + isStatus() + "'" + ", test='" + isTest() + "'"
                + ", dateupdate='" + getDateupdate() + "'" + ", datecreate='" + getDatecreate() + "'" + ", reader='"
                + getReader() + "'" + ", author='" + getAuthor() + "'" + ", createdby='" + getCreatedby() + "'"
                + ", modifiedby='" + getModifiedby() + "'" + "}";
    }
}
