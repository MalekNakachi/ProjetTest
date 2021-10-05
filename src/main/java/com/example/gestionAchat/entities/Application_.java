package com.example.gestionAchat.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Application.class)
public abstract class Application_ {

	public static volatile SingularAttribute<Application, Boolean> test;
	public static volatile SingularAttribute<Application, String> reader;
	public static volatile SingularAttribute<Application, String> author;
	public static volatile SingularAttribute<Application, LocalDate> dateupdate;
	public static volatile SingularAttribute<Application, LocalDate> datecreate;
	public static volatile SingularAttribute<Application, String> appdescription;
	public static volatile SingularAttribute<Application, String> appname;
	public static volatile SingularAttribute<Application, String> createdby;
	public static volatile SingularAttribute<Application, String> alias;
	public static volatile SingularAttribute<Application, String> comment;
	public static volatile SingularAttribute<Application, String> modifiedby;
	public static volatile SingularAttribute<Application, Long> id;
	public static volatile SingularAttribute<Application, Boolean> status;

	public static final String TEST = "test";
	public static final String READER = "reader";
	public static final String AUTHOR = "author";
	public static final String DATEUPDATE = "dateupdate";
	public static final String DATECREATE = "datecreate";
	public static final String APPDESCRIPTION = "appdescription";
	public static final String APPNAME = "appname";
	public static final String CREATEDBY = "createdby";
	public static final String ALIAS = "alias";
	public static final String COMMENT = "comment";
	public static final String MODIFIEDBY = "modifiedby";
	public static final String ID = "id";
	public static final String STATUS = "status";

}

