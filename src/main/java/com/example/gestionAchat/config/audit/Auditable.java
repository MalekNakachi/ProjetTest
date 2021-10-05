package com.example.gestionAchat.config.audit;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Table(schema = "audit")
@EntityListeners({AuditingEntityListener.class})
public abstract class Auditable<String> {

    @Column(name = "sysdate_created")
    @CreationTimestamp
    //@CreatedDate
    //@Temporal(TIMESTAMP)
    protected ZonedDateTime sysdateCreated;

    @Column(name = "sysdate_updated")
    @UpdateTimestamp
    //@LastModifiedDate
    //@Temporal(TIMESTAMP)
    protected ZonedDateTime sysdateUpdated;

    @Size(min = 0, max = 50)
    @Column(name = "syscreated_by", length = 50)
    private java.lang.String syscreatedBy;

    @Size(min = 0, max = 50)
    @Column(name = "sysupdated_by", length = 50)
    private java.lang.String sysupdatedBy;


    public ZonedDateTime getSysdateCreated() {
        return sysdateCreated;
    }
    public void setSysdateCreated(ZonedDateTime sysdateCreated) {
        this.sysdateCreated = sysdateCreated;
    }


    public java.lang.String getSyscreatedBy() {
        return syscreatedBy;
    }

    public void setSyscreatedBy(java.lang.String syscreatedBy) {
        this.syscreatedBy = syscreatedBy;
    }

    public java.lang.String getSysupdatedBy() {
        return sysupdatedBy;
    }

    public void setSysupdatedBy(java.lang.String sysupdatedBy) {
        this.sysupdatedBy = sysupdatedBy;
    }

    public ZonedDateTime getSysdateUpdated() {
        return sysdateUpdated;
    }
    public void setSysdateUpdated(ZonedDateTime sysdateUpdated) {
        this.sysdateUpdated = sysdateUpdated;
    }





}
