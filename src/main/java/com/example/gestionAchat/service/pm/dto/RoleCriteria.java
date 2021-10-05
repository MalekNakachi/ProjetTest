package com.example.gestionAchat.service.pm.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;


public class RoleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter rolename;

    private StringFilter roledescription;

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

    public RoleCriteria() {
    }

    public RoleCriteria(RoleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rolename = other.rolename == null ? null : other.rolename.copy();
        this.roledescription = other.roledescription == null ? null : other.roledescription.copy();
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
    }

    @Override
    public RoleCriteria copy() {
        return new RoleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRolename() {
        return rolename;
    }

    public void setRolename(StringFilter rolename) {
        this.rolename = rolename;
    }

    public StringFilter getRoledescription() {
        return roledescription;
    }

    public void setRoledescription(StringFilter roledescription) {
        this.roledescription = roledescription;
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

    public LongFilter getProfileId() {
        return profileId;
    }

    public void setProfileId(LongFilter profileId) {
        this.profileId = profileId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RoleCriteria that = (RoleCriteria) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(rolename, that.rolename) &&
                        Objects.equals(roledescription, that.roledescription) &&
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
                rolename,
                roledescription,
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

    @Override
    public String toString() {
        return "RoleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (rolename != null ? "rolename=" + rolename + ", " : "") +
                (roledescription != null ? "roledescription=" + roledescription + ", " : "") +
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
