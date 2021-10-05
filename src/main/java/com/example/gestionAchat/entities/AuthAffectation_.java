package com.example.gestionAchat.entities;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AuthAffectation.class)
public abstract class AuthAffectation_ {

	public static volatile SingularAttribute<AuthAffectation, Boolean> test;
	public static volatile SingularAttribute<AuthAffectation, String> reader;
	public static volatile SingularAttribute<AuthAffectation, String> author;
	public static volatile SingularAttribute<AuthAffectation, Profile> profile;
	public static volatile SingularAttribute<AuthAffectation, LocalDate> dateupdate;
	public static volatile SingularAttribute<AuthAffectation, LocalDate> datecreate;
	public static volatile SingularAttribute<AuthAffectation, Application> application;
	public static volatile SingularAttribute<AuthAffectation, String> createdby;
	public static volatile SingularAttribute<AuthAffectation, Integer> orders;
	public static volatile SingularAttribute<AuthAffectation, String> comment;
	public static volatile SingularAttribute<AuthAffectation, String> modifiedby;
	public static volatile SingularAttribute<AuthAffectation, Long> id;
	public static volatile SingularAttribute<AuthAffectation, String> groupe;
	public static volatile SingularAttribute<AuthAffectation, Boolean> status;

	public static final String TEST = "test";
	public static final String READER = "reader";
	public static final String AUTHOR = "author";
	public static final String PROFILE = "profile";
	public static final String DATEUPDATE = "dateupdate";
	public static final String DATECREATE = "datecreate";
	public static final String APPLICATION = "application";
	public static final String CREATEDBY = "createdby";
	public static final String ORDERS = "orders";
	public static final String COMMENT = "comment";
	public static final String MODIFIEDBY = "modifiedby";
	public static final String ID = "id";
	public static final String GROUPE = "groupe";
	public static final String STATUS = "status";

}

