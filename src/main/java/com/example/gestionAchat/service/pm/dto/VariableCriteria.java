package com.example.gestionAchat.service.pm.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;


public class VariableCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter value;

    private StringFilter description;

    private StringFilter comment;

    private BooleanFilter status;

    private BooleanFilter test;

    private LocalDateFilter dateupdate;

    private LocalDateFilter datecreate;

    private StringFilter reader;

    private StringFilter author;

    private StringFilter createdby;

    private StringFilter modifiedby;

    private LongFilter applicationId;

    public VariableCriteria() {
    }

    public VariableCriteria(VariableCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.test = other.test == null ? null : other.test.copy();
        this.dateupdate = other.dateupdate == null ? null : other.dateupdate.copy();
        this.datecreate = other.datecreate == null ? null : other.datecreate.copy();
        this.reader = other.reader == null ? null : other.reader.copy();
        this.author = other.author == null ? null : other.author.copy();
        this.createdby = other.createdby == null ? null : other.createdby.copy();
        this.modifiedby = other.modifiedby == null ? null : other.modifiedby.copy();
        this.applicationId = other.applicationId == null ? null : other.applicationId.copy();
    }

    @Override
    public VariableCriteria copy() {
        return new VariableCriteria(this);
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

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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

    public LongFilter getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(LongFilter applicationId) {
        this.applicationId = applicationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VariableCriteria that = (VariableCriteria) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(name, that.name) &&
                        Objects.equals(value, that.value) &&
                        Objects.equals(description, that.description) &&
                        Objects.equals(comment, that.comment) &&
                        Objects.equals(status, that.status) &&
                        Objects.equals(test, that.test) &&
                        Objects.equals(dateupdate, that.dateupdate) &&
                        Objects.equals(datecreate, that.datecreate) &&
                        Objects.equals(reader, that.reader) &&
                        Objects.equals(author, that.author) &&
                        Objects.equals(createdby, that.createdby) &&
                        Objects.equals(modifiedby, that.modifiedby) &&
                        Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                name,
                value,
                description,
                comment,
                status,
                test,
                dateupdate,
                datecreate,
                reader,
                author,
                createdby,
                modifiedby,
                applicationId
        );
    }

    @Override
    public String toString() {
        return "VariableCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (test != null ? "test=" + test + ", " : "") +
                (dateupdate != null ? "dateupdate=" + dateupdate + ", " : "") +
                (datecreate != null ? "datecreate=" + datecreate + ", " : "") +
                (reader != null ? "reader=" + reader + ", " : "") +
                (author != null ? "author=" + author + ", " : "") +
                (createdby != null ? "createdby=" + createdby + ", " : "") +
                (modifiedby != null ? "modifiedby=" + modifiedby + ", " : "") +
                (applicationId != null ? "applicationId=" + applicationId + ", " : "") +
                "}";
    }

}
