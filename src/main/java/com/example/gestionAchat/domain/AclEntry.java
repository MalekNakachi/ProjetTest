package com.example.gestionAchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="acl_entry")
public class AclEntry {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @OneToOne
        @JsonIgnore
        @JoinColumn(name= "acl_object_identity")
        private AclObjectIdentity acl_object_identity;

        @Column
        private Integer ace_order;

        @OneToOne
        @JsonIgnore
        @JoinColumn(name="sid")
        private AclSid sid;

        @Column
        private Integer mask;
        @Column
        private Integer granting;
        @Column
        private Integer audit_success;
        @Column
        private Integer audit_failure;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public AclObjectIdentity getAcl_object_identity() {
                return acl_object_identity;
        }

        public void setAcl_object_identity(AclObjectIdentity acl_object_identity) {
                this.acl_object_identity = acl_object_identity;
        }

        public Integer getAce_order() {
                return ace_order;
        }

        public void setAce_order(Integer ace_order) {
                this.ace_order = ace_order;
        }

        public AclSid getSid() {
                return sid;
        }

        public void setSid(AclSid sid) {
                this.sid = sid;
        }

        public Integer getMask() {
                return mask;
        }

        public void setMask(Integer mask) {
                this.mask = mask;
        }

        public Integer getGranting() {
                return granting;
        }

        public void setGranting(Integer granting) {
                this.granting = granting;
        }

        public Integer getAudit_success() {
                return audit_success;
        }

        public void setAudit_success(Integer audit_success) {
                this.audit_success = audit_success;
        }

        public Integer getAudit_failure() {
                return audit_failure;
        }

        public void setAudit_failure(Integer audit_failure) {
                this.audit_failure = audit_failure;
        }

}
