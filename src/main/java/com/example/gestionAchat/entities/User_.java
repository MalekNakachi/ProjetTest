package com.example.gestionAchat.entities;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> employeetype;
	public static volatile SingularAttribute<User, String> mail;
	public static volatile SingularAttribute<User, String> manager;
	public static volatile SingularAttribute<User, Boolean> test;
	public static volatile SingularAttribute<User, ConfigLdap> configLdap;
	public static volatile SingularAttribute<User, String> reader;
	public static volatile SingularAttribute<User, String> author;
	public static volatile SingularAttribute<User, String> mobile;
	public static volatile SingularAttribute<User, String> description;
	public static volatile SingularAttribute<User, LocalDate> dateupdate;
	public static volatile SingularAttribute<User, LocalDate> datecreate;
	public static volatile SingularAttribute<User, String> employeeid;
	public static volatile SingularAttribute<User, String> samaccountname;
	public static volatile SingularAttribute<User, String> thumbnailphoto;
	public static volatile SingularAttribute<User, String> apitoken;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> createdby;
	public static volatile SingularAttribute<User, String> displayname;
	public static volatile SingularAttribute<User, String> distinguishedname;
	public static volatile SingularAttribute<User, String> comment;
	public static volatile SingularAttribute<User, String> modifiedby;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> memberof;
	public static volatile SingularAttribute<User, Boolean> status;

	public static final String EMPLOYEETYPE = "employeetype";
	public static final String MAIL = "mail";
	public static final String MANAGER = "manager";
	public static final String TEST = "test";
	public static final String CONFIG_LDAP = "configLdap";
	public static final String READER = "reader";
	public static final String AUTHOR = "author";
	public static final String MOBILE = "mobile";
	public static final String DESCRIPTION = "description";
	public static final String DATEUPDATE = "dateupdate";
	public static final String DATECREATE = "datecreate";
	public static final String EMPLOYEEID = "employeeid";
	public static final String SAMACCOUNTNAME = "samaccountname";
	public static final String THUMBNAILPHOTO = "thumbnailphoto";
	public static final String APITOKEN = "apitoken";
	public static final String PASSWORD = "password";
	public static final String CREATEDBY = "createdby";
	public static final String DISPLAYNAME = "displayname";
	public static final String DISTINGUISHEDNAME = "distinguishedname";
	public static final String COMMENT = "comment";
	public static final String MODIFIEDBY = "modifiedby";
	public static final String ID = "id";
	public static final String MEMBEROF = "memberof";
	public static final String STATUS = "status";

}

