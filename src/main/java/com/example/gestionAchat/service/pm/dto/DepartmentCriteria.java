package com.example.gestionAchat.service.pm.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;


public class DepartmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter managedby;

    private StringFilter distinguishedname;

    private StringFilter description;

    private BooleanFilter isfromldap;

    private StringFilter comment;

    private BooleanFilter status;

    private BooleanFilter test;

    private LocalDateFilter dateupdate;

    private LocalDateFilter datecreate;

    private StringFilter reader;

    private StringFilter author;

    private StringFilter createdby;

    private StringFilter modifiedby;

    public DepartmentCriteria() {
    }

    public DepartmentCriteria(DepartmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.managedby = other.managedby == null ? null : other.managedby.copy();
        this.distinguishedname = other.distinguishedname == null ? null : other.distinguishedname.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.isfromldap = other.isfromldap == null ? null : other.isfromldap.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.test = other.test == null ? null : other.test.copy();
        this.dateupdate = other.dateupdate == null ? null : other.dateupdate.copy();
        this.datecreate = other.datecreate == null ? null : other.datecreate.copy();
        this.reader = other.reader == null ? null : other.reader.copy();
        this.author = other.author == null ? null : other.author.copy();
        this.createdby = other.createdby == null ? null : other.createdby.copy();
        this.modifiedby = other.modifiedby == null ? null : other.modifiedby.copy();
    }

    @Override
    public DepartmentCriteria copy() {
        return new DepartmentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getManagedby() {
        return managedby;
    }

    public void setManagedby(StringFilter managedby) {
        this.managedby = managedby;
    }

    public StringFilter getDistinguishedname() {
        return distinguishedname;
    }

    public void setDistinguishedname(StringFilter distinguishedname) {
        this.distinguishedname = distinguishedname;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getIsfromldap() {
        return isfromldap;
    }

    public void setIsfromldap(BooleanFilter isfromldap) {
        this.isfromldap = isfromldap;
    }

    public StringFilter getComment() {
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public BooleanFilter isStatus() {
        return status;
    }

    public void setStatus(BooleanFilter status) {
        this.status = status;
    }

    public BooleanFilter isTest() {
        return test;
    }

    public void setTest(BooleanFilter test) {
        this.test = test;
    }

    public LocalDateFilter getDateupdate() {
        return dateupdate;
    }

    public void setDateupdate(LocalDateFilter dateupdate) {
        this.dateupdate = dateupdate;
    }

    public LocalDateFilter getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(LocalDateFilter datecreate) {
        this.datecreate = datecreate;
    }

    public StringFilter getReader() {
        return reader;
    }

    public void setReader(StringFilter reader) {
        this.reader = reader;
    }

    public StringFilter getAuthor() {
        return author;
    }

    public void setAuthor(StringFilter author) {
        this.author = author;
    }

    public StringFilter getCreatedby() {
        return createdby;
    }

    public void setCreatedby(StringFilter createdby) {
        this.createdby = createdby;
    }

    public StringFilter getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(StringFilter modifiedby) {
        this.modifiedby = modifiedby;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DepartmentCriteria that = (DepartmentCriteria) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(name, that.name) &&
                        Objects.equals(managedby, that.managedby) &&
                        Objects.equals(distinguishedname, that.distinguishedname) &&
                        Objects.equals(description, that.description) &&
                        Objects.equals(isfromldap, that.isfromldap) &&
                        Objects.equals(comment, that.comment) &&
                        Objects.equals(status, that.status) &&
                        Objects.equals(test, that.test) &&
                        Objects.equals(dateupdate, that.dateupdate) &&
                        Objects.equals(datecreate, that.datecreate) &&
                        Objects.equals(reader, that.reader) &&
                        Objects.equals(author, that.author) &&
                        Objects.equals(createdby, that.createdby) &&
                        Objects.equals(modifiedby, that.modifiedby);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                name,
                managedby,
                distinguishedname,
                description,
                isfromldap,
                comment,
                status,
                test,
                dateupdate,
                datecreate,
                reader,
                author,
                createdby,
                modifiedby
        );
    }

    @Override
    public String toString() {
        return "DepartmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (managedby != null ? "managedby=" + managedby + ", " : "") +
                (distinguishedname != null ? "distinguishedname=" + distinguishedname + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (isfromldap != null ? "isfromldap=" + isfromldap + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (test != null ? "test=" + test + ", " : "") +
                (dateupdate != null ? "dateupdate=" + dateupdate + ", " : "") +
                (datecreate != null ? "datecreate=" + datecreate + ", " : "") +
                (reader != null ? "reader=" + reader + ", " : "") +
                (author != null ? "author=" + author + ", " : "") +
                (createdby != null ? "createdby=" + createdby + ", " : "") +
                (modifiedby != null ? "modifiedby=" + modifiedby + ", " : "") +
                "}";
    }

}
