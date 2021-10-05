package com.example.gestionAchat.entities;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Historique.class)
public abstract class Historique_ {

	public static volatile SingularAttribute<Historique, LocalDate> date;
	public static volatile SingularAttribute<Historique, Long> typeId;
	public static volatile SingularAttribute<Historique, Long> id;
	public static volatile SingularAttribute<Historique, String> type;
	public static volatile SingularAttribute<Historique, String> forwarded;
	public static volatile SingularAttribute<Historique, String> username;
	public static volatile SingularAttribute<Historique, String> remoteAddr;
	public static volatile SingularAttribute<Historique, String> decs;

	public static final String DATE = "date";
	public static final String TYPE_ID = "typeId";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String FORWARDED = "forwarded";
	public static final String USERNAME = "username";
	public static final String REMOTE_ADDR = "remoteAddr";
	public static final String DECS = "decs";

}

