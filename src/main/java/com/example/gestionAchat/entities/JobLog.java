package com.example.gestionAchat.entities;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "pm_job_logTalend")
public class JobLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pid;
    private String job;
    private String log;
    private String fatherPid;
    private String rootPid;
    private String systemPid;
    private String jobRepositoryId;
    private String jobVersion;
    private String context;
    private String origin;
    private String messageType;
    private String message;
    private String project;
    //    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    @JsonFormat(pattern = "ddd MMM DD HH:mm:ss WAT YYYY")
    private LocalDate moment;
    private Long duration;
    private String objectClass;
    private Long objectId;
    private String param;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getFatherPid() {
        return fatherPid;
    }

    public void setFatherPid(String fatherPid) {
        this.fatherPid = fatherPid;
    }

    public String getRootPid() {
        return rootPid;
    }

    public void setRootPid(String rootPid) {
        this.rootPid = rootPid;
    }

    public String getSystemPid() {
        return systemPid;
    }

    public void setSystemPid(String systemPid) {
        this.systemPid = systemPid;
    }

    public String getJobRepositoryId() {
        return jobRepositoryId;
    }

    public void setJobRepositoryId(String jobRepositoryId) {
        this.jobRepositoryId = jobRepositoryId;
    }

    public String getJobVersion() {
        return jobVersion;
    }

    public void setJobVersion(String jobVersion) {
        this.jobVersion = jobVersion;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public LocalDate getMoment() {
        return moment;
    }

    public void setMoment(LocalDate moment) {
        this.moment = moment;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
