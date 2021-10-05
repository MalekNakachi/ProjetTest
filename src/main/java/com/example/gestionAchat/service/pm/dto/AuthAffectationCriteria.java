package com.example.gestionAchat.service.pm.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;


public class AuthAffectationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter groupe;

    private IntegerFilter orders;

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

    private LongFilter profileId;

    private StringFilter applicationAppname;

    private StringFilter profileProfilename;

    public AuthAffectationCriteria() {
    }

    public AuthAffectationCriteria(AuthAffectationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.groupe = other.groupe == null ? null : other.groupe.copy();
        this.orders = other.orders == null ? null : other.orders.copy();
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
        this.profileId = other.profileId == null ? null : other.profileId.copy();
        this.applicationAppname = other.applicationAppname == null ? null : other.applicationAppname.copy();
        this.profileProfilename = other.profileProfilename == null ? null : other.profileProfilename.copy();
    }

    @Override
    public AuthAffectationCriteria copy() {
        return new AuthAffectationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getGroupe() {
        return groupe;
    }

    public void setGroupe(StringFilter groupe) {
        this.groupe = groupe;
    }

    public IntegerFilter getOrders() {
        return orders;
    }

    public void setOrders(IntegerFilter orders) {
        this.orders = orders;
    }

    public StringFilter getComment() {
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public BooleanFilter getStatus() {
        return status;
    }

    public void setStatus(BooleanFilter status) {
        this.status = status;
    }

    public BooleanFilter getTest() {
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

    public LongFilter getProfileId() {
        return profileId;
    }

    public void setProfileId(LongFilter profileId) {
        this.profileId = profileId;
    }

    public StringFilter getApplicationAppname() {
        return applicationAppname;
    }

    public void setApplicationAppname(StringFilter applicationAppname) {
        this.applicationAppname = applicationAppname;
    }

    public StringFilter getProfileProfilename() {
        return profileProfilename;
    }

    public void setProfileProfilename(StringFilter profileProfilename) {
        this.profileProfilename = profileProfilename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AuthAffectationCriteria that = (AuthAffectationCriteria) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(groupe, that.groupe) &&
                        Objects.equals(orders, that.orders) &&
                        Objects.equals(comment, that.comment) &&
                        Objects.equals(status, that.status) &&
                        Objects.equals(test, that.test) &&
                        Objects.equals(dateupdate, that.dateupdate) &&
                        Objects.equals(datecreate, that.datecreate) &&
                        Objects.equals(reader, that.reader) &&
                        Objects.equals(author, that.author) &&
                        Objects.equals(createdby, that.createdby) &&
                        Objects.equals(modifiedby, that.modifiedby) &&
                        Objects.equals(applicationId, that.applicationId) &&
                        Objects.equals(profileId, that.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                groupe,
                orders,
                comment,
                status,
                test,
                dateupdate,
                datecreate,
                reader,
                author,
                createdby,
                modifiedby,
                applicationId,
                profileId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthAffectationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (groupe != null ? "groupe=" + groupe + ", " : "") +
                (orders != null ? "orders=" + orders + ", " : "") +
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
                (profileId != null ? "profileId=" + profileId + ", " : "") +
                "}";
    }

}
