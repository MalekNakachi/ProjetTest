package com.example.gestionAchat.entities;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Variable.class)
public abstract class Variable_ {

	public static volatile SingularAttribute<Variable, Boolean> test;
	public static volatile SingularAttribute<Variable, String> reader;
	public static volatile SingularAttribute<Variable, String> author;
	public static volatile SingularAttribute<Variable, String> description;
	public static volatile SingularAttribute<Variable, LocalDate> dateupdate;
	public static volatile SingularAttribute<Variable, LocalDate> datecreate;
	public static volatile SingularAttribute<Variable, Application> application;
	public static volatile SingularAttribute<Variable, String> createdby;
	public static volatile SingularAttribute<Variable, String> name;
	public static volatile SingularAttribute<Variable, String> comment;
	public static volatile SingularAttribute<Variable, String> modifiedby;
	public static volatile SingularAttribute<Variable, Long> id;
	public static volatile SingularAttribute<Variable, String> value;
	public static volatile SingularAttribute<Variable, Boolean> status;

	public static final String TEST = "test";
	public static final String READER = "reader";
	public static final String AUTHOR = "author";
	public static final String DESCRIPTION = "description";
	public static final String DATEUPDATE = "dateupdate";
	public static final String DATECREATE = "datecreate";
	public static final String APPLICATION = "application";
	public static final String CREATEDBY = "createdby";
	public static final String NAME = "name";
	public static final String COMMENT = "comment";
	public static final String MODIFIEDBY = "modifiedby";
	public static final String ID = "id";
	public static final String VALUE = "value";
	public static final String STATUS = "status";

}

