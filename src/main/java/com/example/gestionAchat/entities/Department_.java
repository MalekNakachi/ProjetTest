package com.example.gestionAchat.entities;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ {

	public static volatile SingularAttribute<Department, Boolean> test;
	public static volatile SingularAttribute<Department, ConfigLdap> configLdap;
	public static volatile SingularAttribute<Department, String> reader;
	public static volatile SingularAttribute<Department, String> author;
	public static volatile SingularAttribute<Department, String> description;
	public static volatile SingularAttribute<Department, LocalDate> dateupdate;
	public static volatile SingularAttribute<Department, LocalDate> datecreate;
	public static volatile SingularAttribute<Department, String> managedby;
	public static volatile SingularAttribute<Department, String> createdby;
	public static volatile SingularAttribute<Department, String> name;
	public static volatile SingularAttribute<Department, String> distinguishedname;
	public static volatile SingularAttribute<Department, String> comment;
	public static volatile SingularAttribute<Department, String> modifiedby;
	public static volatile SingularAttribute<Department, Long> id;
	public static volatile SingularAttribute<Department, Boolean> status;

	public static final String TEST = "test";
	public static final String CONFIG_LDAP = "configLdap";
	public static final String READER = "reader";
	public static final String AUTHOR = "author";
	public static final String DESCRIPTION = "description";
	public static final String DATEUPDATE = "dateupdate";
	public static final String DATECREATE = "datecreate";
	public static final String MANAGEDBY = "managedby";
	public static final String CREATEDBY = "createdby";
	public static final String NAME = "name";
	public static final String DISTINGUISHEDNAME = "distinguishedname";
	public static final String COMMENT = "comment";
	public static final String MODIFIEDBY = "modifiedby";
	public static final String ID = "id";
	public static final String STATUS = "status";

}

