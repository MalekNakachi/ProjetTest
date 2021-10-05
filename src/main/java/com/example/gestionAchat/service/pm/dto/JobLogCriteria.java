package com.example.gestionAchat.service.pm.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;


public class JobLogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter pid;

    private StringFilter job;

    private StringFilter log;

    private StringFilter fatherPid;

    private StringFilter rootPid;

    private StringFilter systemPid;

    private StringFilter jobRepositoryId;

    private StringFilter jobVersion;

    private StringFilter context;

    private StringFilter origin;

    private StringFilter messageType;

    private StringFilter message;

    private StringFilter project;

    private LocalDateFilter moment;

    private LongFilter duration;

    private StringFilter objectClass;

    private LongFilter objectId;

    private StringFilter param;

    public JobLogCriteria() {
    }

    public JobLogCriteria(JobLogCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.pid = other.pid == null ? null : other.pid.copy();
        this.job = other.job == null ? null : other.job.copy();
        this.log = other.log == null ? null : other.log.copy();
        this.fatherPid = other.fatherPid == null ? null : other.fatherPid.copy();
        this.rootPid = other.rootPid == null ? null : other.rootPid.copy();
        this.systemPid = other.systemPid == null ? null : other.systemPid.copy();
        this.jobRepositoryId = other.jobRepositoryId == null ? null : other.jobRepositoryId.copy();
        this.jobVersion = other.jobVersion == null ? null : other.jobVersion.copy();
        this.context = other.context == null ? null : other.context.copy();
        this.origin = other.origin == null ? null : other.origin.copy();
        this.messageType = other.messageType == null ? null : other.messageType.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.project = other.project == null ? null : other.project.copy();
        this.moment = other.moment == null ? null : other.moment.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.objectClass = other.objectClass == null ? null : other.objectClass.copy();
        this.objectId = other.objectId == null ? null : other.objectId.copy();
        this.param = other.param == null ? null : other.param.copy();
    }

    @Override
    public JobLogCriteria copy() {
        return new JobLogCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPid() {
        return pid;
    }

    public void setPid(StringFilter pid) {
        this.pid = pid;
    }

    public StringFilter getJob() {
        return job;
    }

    public void setJob(StringFilter job) {
        this.job = job;
    }

    public StringFilter getLog() {
        return log;
    }

    public void setLog(StringFilter log) {
        this.log = log;
    }

    public StringFilter getFatherPid() {
        return fatherPid;
    }

    public void setFatherPid(StringFilter fatherPid) {
        this.fatherPid = fatherPid;
    }

    public StringFilter getRootPid() {
        return rootPid;
    }

    public void setRootPid(StringFilter rootPid) {
        this.rootPid = rootPid;
    }

    public StringFilter getSystemPid() {
        return systemPid;
    }

    public void setSystemPid(StringFilter systemPid) {
        this.systemPid = systemPid;
    }

    public StringFilter getJobRepositoryId() {
        return jobRepositoryId;
    }

    public void setJobRepositoryId(StringFilter jobRepositoryId) {
        this.jobRepositoryId = jobRepositoryId;
    }

    public StringFilter getJobVersion() {
        return jobVersion;
    }

    public void setJobVersion(StringFilter jobVersion) {
        this.jobVersion = jobVersion;
    }

    public StringFilter getContext() {
        return context;
    }

    public void setContext(StringFilter context) {
        this.context = context;
    }

    public StringFilter getOrigin() {
        return origin;
    }

    public void setOrigin(StringFilter origin) {
        this.origin = origin;
    }

    public StringFilter getMessageType() {
        return messageType;
    }

    public void setMessageType(StringFilter messageType) {
        this.messageType = messageType;
    }

    public StringFilter getMessage() {
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public StringFilter getProject() {
        return project;
    }

    public void setProject(StringFilter project) {
        this.project = project;
    }

    public LocalDateFilter getMoment() {
        return moment;
    }

    public void setMoment(LocalDateFilter moment) {
        this.moment = moment;
    }

    public LongFilter getDuration() {
        return duration;
    }

    public void setDuration(LongFilter duration) {
        this.duration = duration;
    }

    public StringFilter getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(StringFilter objectClass) {
        this.objectClass = objectClass;
    }

    public LongFilter getObjectId() {
        return objectId;
    }

    public void setObjectId(LongFilter objectId) {
        this.objectId = objectId;
    }

    public StringFilter getParam() {
        return param;
    }

    public void setParam(StringFilter param) {
        this.param = param;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JobLogCriteria that = (JobLogCriteria) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(pid, that.pid) &&
                        Objects.equals(job, that.job) &&
                        Objects.equals(log, that.log) &&
                        Objects.equals(fatherPid, that.fatherPid) &&
                        Objects.equals(rootPid, that.rootPid) &&
                        Objects.equals(systemPid, that.systemPid) &&
                        Objects.equals(jobRepositoryId, that.jobRepositoryId) &&
                        Objects.equals(jobVersion, that.jobVersion) &&
                        Objects.equals(context, that.context) &&
                        Objects.equals(origin, that.origin) &&
                        Objects.equals(messageType, that.messageType) &&
                        Objects.equals(message, that.message) &&
                        Objects.equals(project, that.project) &&
                        Objects.equals(moment, that.moment) &&
                        Objects.equals(duration, that.duration) &&
                        Objects.equals(objectClass, that.objectClass) &&
                        Objects.equals(objectId, that.objectId) &&
                        Objects.equals(param, that.param);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                pid,
                job,
                log,
                fatherPid,
                rootPid,
                systemPid,
                jobRepositoryId,
                jobVersion,
                context,
                origin,
                messageType,
                message,
                project,
                moment,
                duration,
                objectClass,
                objectId,
                param
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobLogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (pid != null ? "pid=" + pid + ", " : "") +
                (job != null ? "job=" + job + ", " : "") +
                (log != null ? "log=" + log + ", " : "") +
                (fatherPid != null ? "fatherPid=" + fatherPid + ", " : "") +
                (rootPid != null ? "rootPid=" + rootPid + ", " : "") +
                (systemPid != null ? "systemPid=" + systemPid + ", " : "") +
                (jobRepositoryId != null ? "jobRepositoryId=" + jobRepositoryId + ", " : "") +
                (jobVersion != null ? "jobVersion=" + jobVersion + ", " : "") +
                (context != null ? "context=" + context + ", " : "") +
                (origin != null ? "origin=" + origin + ", " : "") +
                (messageType != null ? "messageType=" + messageType + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (project != null ? "project=" + project + ", " : "") +
                (moment != null ? "moment=" + moment + ", " : "") +
                (duration != null ? "duration=" + duration + ", " : "") +
                (objectClass != null ? "objectClass=" + objectClass + ", " : "") +
                (objectId != null ? "objectId=" + objectId + ", " : "") +
                (param != null ? "param=" + param + ", " : "") +
                "}";
    }

}
