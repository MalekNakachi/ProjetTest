package com.example.gestionAchat.entities;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pm_users", uniqueConstraints = {@UniqueConstraint(columnNames = {"samaccountname", "mail"})})
//@Document(indexName = "pm.user", createIndex = true, type = "user")

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotNull
    @Column(name = "samaccountname", nullable = false, unique = true)
    private String samaccountname;

    @NotNull
    @Email
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "manager")
    private String manager;

    @Column(name = "displayname",nullable = true)
    private String displayname;

    @Column(name = "distinguishedname")
    private String distinguishedname;

    @Column(name = "thumbnailphoto")
    private String thumbnailphoto;

    @Column(name = "description")
    private String description;

    @Column(name = "memberof")
    private String memberof;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

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


    @Column(name = "authentication_mode")
    private String authentication_mode;


    @Column(name = "reader")
    private String reader;

    @Column(name = "author")
    private String author;

    @Column(name = "createdby")
    private String createdby;

    @Column(name = "modifiedby")
    private String modifiedby;

    @Column(name = "employeetype")
    private String employeetype;

    @Column(name = "employeeid")
    private String employeeid;

    @Column(name = "apitoken",nullable = true,unique = true)
    private String apitoken;


    @Column(name = "notifieparemail")
    private Boolean notifieparemail;

    @Column(name = "appartenance")
    private String appartenance;

    @ManyToOne
    @JoinColumn()
    private ConfigLdap configLdap;

    public User(String displayname, String reader, String author) {
        super();
        this.displayname = displayname;
        this.reader = reader;
        this.author = author;

    }

//    @ManyToMany(mappedBy = "users")
@OneToMany(mappedBy = "user")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
private Set<GroupUser> groupUserSet = new HashSet<>();

    public Set<GroupUser> getGroupUserSet() {
        return groupUserSet;
    }

    public void setGroupUserSet(Set<GroupUser> groupUserSet) {
        this.groupUserSet = groupUserSet;
    }

    public Boolean getNotifieparemail() {
        return notifieparemail;
    }

    public void setNotifieparemail(Boolean notifieparemail) {
        this.notifieparemail = notifieparemail;
    }

    public String getAppartenance() {
        return appartenance;
    }

    public void setAppartenance(String appartenance) {
        this.appartenance = appartenance;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getStatus() {
        return status;
    }

    public Boolean getTest() {
        return test;
    }

    public String getAuthentication_mode() {
        return authentication_mode;
    }

    public void setAuthentication_mode(String authentication_mode) {
        this.authentication_mode = authentication_mode;
    }

    public User(Long id, @NotNull String samaccountname, @NotNull String mail, String manager, String displayname, String distinguishedname, String thumbnailphoto, String description, String memberof, LocalDate dateupdate, LocalDate datecreate, Boolean status, Boolean test, String comment, String reader, String author, String password, String createdby, String modifiedby, String mobile, String employeetype, ConfigLdap configLdap) {
        this.id = id;
        this.samaccountname = samaccountname;
        this.mail = mail;
        this.manager = manager;
        this.displayname = displayname;
        this.distinguishedname = distinguishedname;
        this.thumbnailphoto = thumbnailphoto;
        this.description = description;
        this.memberof = memberof;
        this.mobile = mobile;
        this.password = password;
        this.comment = comment;
        this.status = status;
        this.test = test;
        this.dateupdate = dateupdate;
        this.datecreate = datecreate;
        this.reader = reader;
        this.author = author;
        this.createdby = createdby;
        this.modifiedby = modifiedby;
        this.employeetype = employeetype;
        this.configLdap = configLdap;

    }

    public User(Long id, @NotNull String samaccountname, @NotNull @Email String mail, @NotNull String displayname, String description, String password, Boolean status, Boolean test, LocalDate dateupdate, LocalDate datecreate, String reader, String author, String createdby, String modifiedby, String apitoken) {
        this.id = id;
        this.samaccountname = samaccountname;
        this.mail = mail;
        this.displayname = displayname;
        this.description = description;
        this.password = password;
        this.status = status;
        this.test = test;
        this.dateupdate = dateupdate;
        this.datecreate = datecreate;
        this.reader = reader;
        this.author = author;
        this.createdby = createdby;
        this.modifiedby = modifiedby;
        this.apitoken = apitoken;
    }

    public User() {
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSamaccountname() {
        return samaccountname;
    }

    public void setSamaccountname(String samaccountname) {
        this.samaccountname = samaccountname;
    }

    public User samaccountname(String samaccountname) {
        this.samaccountname = samaccountname;
        return this;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public User mail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public User manager(String manager) {
        this.manager = manager;
        return this;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public User displayname(String displayname) {
        this.displayname = displayname;
        return this;
    }

    public String getDistinguishedname() {
        return distinguishedname;
    }

    public void setDistinguishedname(String distinguishedname) {
        this.distinguishedname = distinguishedname;
    }

    public User distinguishedname(String distinguishedname) {
        this.distinguishedname = distinguishedname;
        return this;
    }

    public String getThumbnailphoto() {
        return thumbnailphoto;
    }

    public void setThumbnailphoto(String thumbnailphoto) {
        this.thumbnailphoto = thumbnailphoto;
    }

    public User thumbnailphoto(String thumbnailphoto) {
        this.thumbnailphoto = thumbnailphoto;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User description(String description) {
        this.description = description;
        return this;
    }

    public String getMemberof() {
        return memberof;
    }

    public void setMemberof(String memberof) {
        this.memberof = memberof;
    }

    public User memberof(String memberof) {
        this.memberof = memberof;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User comment(String comment) {
        this.comment = comment;
        return this;
    }

    public Boolean isStatus() {
        return status;
    }

    public User status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isTest() {
        return test;
    }

    public User test(Boolean test) {
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

    public User dateupdate(LocalDate dateupdate) {
        this.dateupdate = dateupdate;
        return this;
    }

    public LocalDate getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
    }

    public User datecreate(LocalDate datecreate) {
        this.datecreate = datecreate;
        return this;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public User reader(String reader) {
        this.reader = reader;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public User author(String author) {
        this.author = author;
        return this;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public User createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public User modifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
        return this;
    }

    public String getEmployeetype() {
        return employeetype;
    }

    public void setEmployeetype(String employeetype) {
        this.employeetype = employeetype;
    }

    public User employeetype(String employeetype) {
        this.employeetype = employeetype;
        return this;
    }

    public ConfigLdap getConfigLdap() {
        return configLdap;
    }

    public void setConfigLdap(ConfigLdap configLdap) {
        this.configLdap = configLdap;
    }

    public User configLdap(ConfigLdap configLdap) {
        this.configLdap = configLdap;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getApitoken() {
        return apitoken;
    }

    public void setApitoken(String apitoken) {
        this.apitoken = apitoken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


}
