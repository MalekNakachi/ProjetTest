package com.example.gestionAchat.entities;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(JobLog.class)
public abstract class JobLog_ {

	public static volatile SingularAttribute<JobLog, String> log;
	public static volatile SingularAttribute<JobLog, String> origin;
	public static volatile SingularAttribute<JobLog, String> objectClass;
	public static volatile SingularAttribute<JobLog, String> project;
	public static volatile SingularAttribute<JobLog, String> pid;
	public static volatile SingularAttribute<JobLog, String> fatherPid;
	public static volatile SingularAttribute<JobLog, String> rootPid;
	public static volatile SingularAttribute<JobLog, String> message;
	public static volatile SingularAttribute<JobLog, String> jobVersion;
	public static volatile SingularAttribute<JobLog, LocalDate> moment;
	public static volatile SingularAttribute<JobLog, String> systemPid;
	public static volatile SingularAttribute<JobLog, Long> duration;
	public static volatile SingularAttribute<JobLog, String> messageType;
	public static volatile SingularAttribute<JobLog, String> param;
	public static volatile SingularAttribute<JobLog, String> jobRepositoryId;
	public static volatile SingularAttribute<JobLog, String> context;
	public static volatile SingularAttribute<JobLog, Long> id;
	public static volatile SingularAttribute<JobLog, String> job;
	public static volatile SingularAttribute<JobLog, Long> objectId;

	public static final String LOG = "log";
	public static final String ORIGIN = "origin";
	public static final String OBJECT_CLASS = "objectClass";
	public static final String PROJECT = "project";
	public static final String PID = "pid";
	public static final String FATHER_PID = "fatherPid";
	public static final String ROOT_PID = "rootPid";
	public static final String MESSAGE = "message";
	public static final String JOB_VERSION = "jobVersion";
	public static final String MOMENT = "moment";
	public static final String SYSTEM_PID = "systemPid";
	public static final String DURATION = "duration";
	public static final String MESSAGE_TYPE = "messageType";
	public static final String PARAM = "param";
	public static final String JOB_REPOSITORY_ID = "jobRepositoryId";
	public static final String CONTEXT = "context";
	public static final String ID = "id";
	public static final String JOB = "job";
	public static final String OBJECT_ID = "objectId";

}

