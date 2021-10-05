package com.example.gestionAchat.entities;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

	public static volatile SingularAttribute<Role, Boolean> test;
	public static volatile SingularAttribute<Role, String> rolename;
	public static volatile SingularAttribute<Role, String> reader;
	public static volatile SingularAttribute<Role, String> author;
	public static volatile SetAttribute<Role, Profile> profiles;
	public static volatile SingularAttribute<Role, LocalDate> dateupdate;
	public static volatile SingularAttribute<Role, LocalDate> datecreate;
	public static volatile SingularAttribute<Role, String> roledescription;
	public static volatile SingularAttribute<Role, Application> application;
	public static volatile SingularAttribute<Role, String> createdby;
	public static volatile SingularAttribute<Role, String> comment;
	public static volatile SingularAttribute<Role, String> modifiedby;
	public static volatile SingularAttribute<Role, Long> id;
	public static volatile SingularAttribute<Role, Boolean> status;

	public static final String TEST = "test";
	public static final String ROLENAME = "rolename";
	public static final String READER = "reader";
	public static final String AUTHOR = "author";
	public static final String PROFILES = "profiles";
	public static final String DATEUPDATE = "dateupdate";
	public static final String DATECREATE = "datecreate";
	public static final String ROLEDESCRIPTION = "roledescription";
	public static final String APPLICATION = "application";
	public static final String CREATEDBY = "createdby";
	public static final String COMMENT = "comment";
	public static final String MODIFIEDBY = "modifiedby";
	public static final String ID = "id";
	public static final String STATUS = "status";

}

