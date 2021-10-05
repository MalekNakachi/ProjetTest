package com.example.gestionAchat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Variable.
 */
@Entity
@Table(name = "pm_variable", uniqueConstraints = @UniqueConstraint(columnNames = {"application", "name"}))
public class Variable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "description")
    private String description;

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

    @ManyToOne(fetch = FetchType.EAGER)
    //@Cascade(CascadeType.ALL)
    @JsonIgnoreProperties("variables")
    @JoinColumn(name = "application", nullable = false)
    private Application application;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public Variable name(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Variable value(String value) {
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Variable description(String description) {
        this.description = description;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Variable comment(String comment) {
        this.comment = comment;
        return this;
    }

    public Boolean isStatus() {
        return status;
    }

    public Variable status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isTest() {
        return test;
    }

    public Variable test(Boolean test) {
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

    public Variable dateupdate(LocalDate dateupdate) {
        this.dateupdate = dateupdate;
        return this;
    }

    public LocalDate getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
    }

    public Variable datecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
        return this;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public Variable reader(String reader) {
        this.reader = reader;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Variable author(String author) {
        this.author = author;
        return this;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Variable createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Variable modifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
        return this;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Variable application(Application application) {
        this.application = application;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Variable)) {
            return false;
        }
        return id != null && id.equals(((Variable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", value='" + getValue() + "'" +
                ", description='" + getDescription() + "'" +
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
