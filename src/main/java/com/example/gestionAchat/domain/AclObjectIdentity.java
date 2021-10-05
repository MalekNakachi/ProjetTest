package com.example.gestionAchat.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="acl_object_identity")

public class AclObjectIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "object_id_class")
    private AclClass object_id_class;

    //        @OneToOne
    @Column(name = "object_id_identity")
    private Long objectIdentity;

    @Column
    private Integer parent_object;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name= "owner_sid")
    private AclSid owner_sid;

    @Column
    private boolean entries_inheriting;

    public Long getObjectIdentity() {
        return objectIdentity;
    }

    public void setObjectIdentity(Long objectIdentity) {
        this.objectIdentity = objectIdentity;
    }

    public boolean isEntries_inheriting() {
        return entries_inheriting;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AclClass getObject_id_class() {
        return object_id_class;
    }

    public void setObject_id_class(AclClass object_id_class) {
        this.object_id_class = object_id_class;
    }

//        public AclObjectIdentity getObjectIdentity() {
//                return objectIdentity;
//        }
//
//        public void setObjectIdentity(AclObjectIdentity objectIdentity) {
//                this.objectIdentity = objectIdentity;
//        }

    public Integer getParent_object() {
        return parent_object;
    }

    public void setParent_object(Integer parent_object) {
        this.parent_object = parent_object;
    }

    public AclSid getOwner_sid() {
        return owner_sid;
    }

    public void setOwner_sid(AclSid owner_sid) {
        this.owner_sid = owner_sid;
    }

    public boolean getEntries_inheriting() {
        return entries_inheriting;
    }

    public void setEntries_inheriting(boolean entries_inheriting) {
        this.entries_inheriting = entries_inheriting;
    }
}

