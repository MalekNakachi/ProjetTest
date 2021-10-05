package com.example.gestionAchat.entities;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ConfigLdap.class)
public abstract class ConfigLdap_ {

	public static volatile SingularAttribute<ConfigLdap, Boolean> test;
	public static volatile SingularAttribute<ConfigLdap, String> reader;
	public static volatile SingularAttribute<ConfigLdap, String> author;
	public static volatile SingularAttribute<ConfigLdap, LocalDate> dateupdate;
	public static volatile SingularAttribute<ConfigLdap, LocalDate> datecreate;
	public static volatile SingularAttribute<ConfigLdap, String> url;
	public static volatile SingularAttribute<ConfigLdap, String> password;
	public static volatile SingularAttribute<ConfigLdap, String> createdby;
	public static volatile SingularAttribute<ConfigLdap, String> name;
	public static volatile SingularAttribute<ConfigLdap, String> comment;
	public static volatile SingularAttribute<ConfigLdap, String> modifiedby;
	public static volatile SingularAttribute<ConfigLdap, Long> id;
	public static volatile SingularAttribute<ConfigLdap, String> userDn;
	public static volatile SingularAttribute<ConfigLdap, String> base;
	public static volatile SingularAttribute<ConfigLdap, Boolean> status;

	public static final String TEST = "test";
	public static final String READER = "reader";
	public static final String AUTHOR = "author";
	public static final String DATEUPDATE = "dateupdate";
	public static final String DATECREATE = "datecreate";
	public static final String URL = "url";
	public static final String PASSWORD = "password";
	public static final String CREATEDBY = "createdby";
	public static final String NAME = "name";
	public static final String COMMENT = "comment";
	public static final String MODIFIEDBY = "modifiedby";
	public static final String ID = "id";
	public static final String USER_DN = "userDn";
	public static final String BASE = "base";
	public static final String STATUS = "status";

}

