package com.example.gestionAchat.service.pm.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;


public class UsersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter samaccountname;

    private StringFilter mail;

    private StringFilter manager;

    private StringFilter displayname;

    private StringFilter distinguishedname;

    private StringFilter thumbnailphoto;

    private StringFilter description;

    private StringFilter memberof;

    private StringFilter password;

    private StringFilter comment;

    private BooleanFilter status;

    private BooleanFilter test;

    private LocalDateFilter dateupdate;

    private LocalDateFilter datecreate;

    private StringFilter reader;

    private StringFilter author;

    private StringFilter createdby;

    private StringFilter modifiedby;

    private StringFilter employeetype;

    private LongFilter configLdapId;

    public UsersCriteria() {
    }

    public UsersCriteria(UsersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.samaccountname = other.samaccountname == null ? null : other.samaccountname.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.manager = other.manager == null ? null : other.manager.copy();
        this.displayname = other.displayname == null ? null : other.displayname.copy();
        this.distinguishedname = other.distinguishedname == null ? null : other.distinguishedname.copy();
        this.thumbnailphoto = other.thumbnailphoto == null ? null : other.thumbnailphoto.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.memberof = other.memberof == null ? null : other.memberof.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.test = other.test == null ? null : other.test.copy();
        this.dateupdate = other.dateupdate == null ? null : other.dateupdate.copy();
        this.datecreate = other.datecreate == null ? null : other.datecreate.copy();
        this.reader = other.reader == null ? null : other.reader.copy();
        this.author = other.author == null ? null : other.author.copy();
        this.createdby = other.createdby == null ? null : other.createdby.copy();
        this.modifiedby = other.modifiedby == null ? null : other.modifiedby.copy();
        this.employeetype = other.employeetype == null ? null : other.employeetype.copy();
        this.configLdapId = other.configLdapId == null ? null : other.configLdapId.copy();
    }

    @Override
    public UsersCriteria copy() {
        return new UsersCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSamaccountname() {
        return samaccountname;
    }

    public void setSamaccountname(StringFilter samaccountname) {
        this.samaccountname = samaccountname;
    }

    public StringFilter getMail() {
        return mail;
    }

    public void setMail(StringFilter mail) {
        this.mail = mail;
    }

    public StringFilter getManager() {
        return manager;
    }

    public void setManager(StringFilter manager) {
        this.manager = manager;
    }

    public StringFilter getDisplayname() {
        return displayname;
    }

    public void setDisplayname(StringFilter displayname) {
        this.displayname = displayname;
    }

    public StringFilter getDistinguishedname() {
        return distinguishedname;
    }

    public void setDistinguishedname(StringFilter distinguishedname) {
        this.distinguishedname = distinguishedname;
    }

    public StringFilter getThumbnailphoto() {
        return thumbnailphoto;
    }

    public void setThumbnailphoto(StringFilter thumbnailphoto) {
        this.thumbnailphoto = thumbnailphoto;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getMemberof() {
        return memberof;
    }

    public void setMemberof(StringFilter memberof) {
        this.memberof = memberof;
    }

    public StringFilter getPassword() {
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
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

    public StringFilter getEmployeetype() {
        return employeetype;
    }

    public void setEmployeetype(StringFilter employeetype) {
        this.employeetype = employeetype;
    }

    public LongFilter getConfigLdapId() {
        return configLdapId;
    }

    public void setConfigLdapId(LongFilter configLdapId) {
        this.configLdapId = configLdapId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UsersCriteria that = (UsersCriteria) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(samaccountname, that.samaccountname) &&
                        Objects.equals(mail, that.mail) &&
                        Objects.equals(manager, that.manager) &&
                        Objects.equals(displayname, that.displayname) &&
                        Objects.equals(distinguishedname, that.distinguishedname) &&
                        Objects.equals(thumbnailphoto, that.thumbnailphoto) &&
                        Objects.equals(description, that.description) &&
                        Objects.equals(memberof, that.memberof) &&
                        Objects.equals(password, that.password) &&
                        Objects.equals(comment, that.comment) &&
                        Objects.equals(status, that.status) &&
                        Objects.equals(test, that.test) &&
                        Objects.equals(dateupdate, that.dateupdate) &&
                        Objects.equals(datecreate, that.datecreate) &&
                        Objects.equals(reader, that.reader) &&
                        Objects.equals(author, that.author) &&
                        Objects.equals(createdby, that.createdby) &&
                        Objects.equals(modifiedby, that.modifiedby) &&
                        Objects.equals(employeetype, that.employeetype) &&
                        Objects.equals(configLdapId, that.configLdapId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                samaccountname,
                mail,
                manager,
                displayname,
                distinguishedname,
                thumbnailphoto,
                description,
                memberof,
                password,
                comment,
                status,
                test,
                dateupdate,
                datecreate,
                reader,
                author,
                createdby,
                modifiedby,
                employeetype,
                configLdapId
        );
    }

    @Override
    public String toString() {
        return "UsersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (samaccountname != null ? "samaccountname=" + samaccountname + ", " : "") +
                (mail != null ? "mail=" + mail + ", " : "") +
                (manager != null ? "manager=" + manager + ", " : "") +
                (displayname != null ? "displayname=" + displayname + ", " : "") +
                (distinguishedname != null ? "distinguishedname=" + distinguishedname + ", " : "") +
                (thumbnailphoto != null ? "thumbnailphoto=" + thumbnailphoto + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (memberof != null ? "memberof=" + memberof + ", " : "") +
                (password != null ? "password=" + password + ", " : "") +
                (comment != null ? "comment=" + comment + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (test != null ? "test=" + test + ", " : "") +
                (dateupdate != null ? "dateupdate=" + dateupdate + ", " : "") +
                (datecreate != null ? "datecreate=" + datecreate + ", " : "") +
                (reader != null ? "reader=" + reader + ", " : "") +
                (author != null ? "author=" + author + ", " : "") +
                (createdby != null ? "createdby=" + createdby + ", " : "") +
                (modifiedby != null ? "modifiedby=" + modifiedby + ", " : "") +
                (employeetype != null ? "employeetype=" + employeetype + ", " : "") +
                (configLdapId != null ? "configLdapId=" + configLdapId + ", " : "") +
                "}";
    }

}
