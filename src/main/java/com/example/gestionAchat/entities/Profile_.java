package com.example.gestionAchat.entities;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Profile.class)
public abstract class Profile_ {

	public static volatile SingularAttribute<Profile, String> mail;
	public static volatile SingularAttribute<Profile, Boolean> test;
	public static volatile SingularAttribute<Profile, String> reader;
	public static volatile SingularAttribute<Profile, String> author;
	public static volatile ListAttribute<Profile, Role> roles;
	public static volatile SingularAttribute<Profile, LocalDate> dateupdate;
	public static volatile SingularAttribute<Profile, LocalDate> datecreate;
	public static volatile SingularAttribute<Profile, String> profildescription;
	public static volatile SingularAttribute<Profile, Application> application;
	public static volatile SingularAttribute<Profile, String> createdby;
	public static volatile SingularAttribute<Profile, String> profilename;
	public static volatile SingularAttribute<Profile, String> comment;
	public static volatile SingularAttribute<Profile, String> modifiedby;
	public static volatile SingularAttribute<Profile, Long> id;
	public static volatile SingularAttribute<Profile, Boolean> status;

	public static final String MAIL = "mail";
	public static final String TEST = "test";
	public static final String READER = "reader";
	public static final String AUTHOR = "author";
	public static final String ROLES = "roles";
	public static final String DATEUPDATE = "dateupdate";
	public static final String DATECREATE = "datecreate";
	public static final String PROFILDESCRIPTION = "profildescription";
	public static final String APPLICATION = "application";
	public static final String CREATEDBY = "createdby";
	public static final String PROFILENAME = "profilename";
	public static final String COMMENT = "comment";
	public static final String MODIFIEDBY = "modifiedby";
	public static final String ID = "id";
	public static final String STATUS = "status";

}

