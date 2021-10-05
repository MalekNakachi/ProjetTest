package com.example.gestionAchat.entities;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ConfigElastic.class)
public abstract class ConfigElastic_ {

	public static volatile SingularAttribute<ConfigElastic, Boolean> test;
	public static volatile SingularAttribute<ConfigElastic, String> reader;
	public static volatile SingularAttribute<ConfigElastic, String> author;
	public static volatile SingularAttribute<ConfigElastic, LocalDate> dateupdate;
	public static volatile SingularAttribute<ConfigElastic, LocalDate> datecreate;
	public static volatile SingularAttribute<ConfigElastic, Application> application;
	public static volatile SingularAttribute<ConfigElastic, String> port;
	public static volatile SingularAttribute<ConfigElastic, String> createdby;
	public static volatile SingularAttribute<ConfigElastic, String> name;
	public static volatile SingularAttribute<ConfigElastic, String> hosturl;
	public static volatile SingularAttribute<ConfigElastic, String> comment;
	public static volatile SingularAttribute<ConfigElastic, String> modifiedby;
	public static volatile SingularAttribute<ConfigElastic, Long> id;
	public static volatile SingularAttribute<ConfigElastic, Boolean> status;

	public static final String TEST = "test";
	public static final String READER = "reader";
	public static final String AUTHOR = "author";
	public static final String DATEUPDATE = "dateupdate";
	public static final String DATECREATE = "datecreate";
	public static final String APPLICATION = "application";
	public static final String PORT = "port";
	public static final String CREATEDBY = "createdby";
	public static final String NAME = "name";
	public static final String HOSTURL = "hosturl";
	public static final String COMMENT = "comment";
	public static final String MODIFIEDBY = "modifiedby";
	public static final String ID = "id";
	public static final String STATUS = "status";

}

