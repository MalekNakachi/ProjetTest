package com.example.gestionAchat.entities;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserAccess.class)
public abstract class UserAccess_ {

	public static volatile SingularAttribute<UserAccess, Boolean> test;
	public static volatile SingularAttribute<UserAccess, String> reader;
	public static volatile SingularAttribute<UserAccess, String> author;
	public static volatile SingularAttribute<UserAccess, Profile> profile;
	public static volatile SingularAttribute<UserAccess, LocalDate> dateupdate;
	public static volatile SingularAttribute<UserAccess, LocalDate> datecreate;
	public static volatile SingularAttribute<UserAccess, Application> application;
	public static volatile SingularAttribute<UserAccess, String> createdby;
	public static volatile SingularAttribute<UserAccess, String> comment;
	public static volatile SingularAttribute<UserAccess, String> modifiedby;
	public static volatile SingularAttribute<UserAccess, Long> id;
	public static volatile SingularAttribute<UserAccess, User> user;
	public static volatile SingularAttribute<UserAccess, Boolean> status;

	public static final String TEST = "test";
	public static final String READER = "reader";
	public static final String AUTHOR = "author";
	public static final String PROFILE = "profile";
	public static final String DATEUPDATE = "dateupdate";
	public static final String DATECREATE = "datecreate";
	public static final String APPLICATION = "application";
	public static final String CREATEDBY = "createdby";
	public static final String COMMENT = "comment";
	public static final String MODIFIEDBY = "modifiedby";
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String STATUS = "status";

}

