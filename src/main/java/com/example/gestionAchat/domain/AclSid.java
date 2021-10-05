package com.example.gestionAchat.domain;


import javax.persistence.*;


@Entity
@Table(name="acl_sid")
public class AclSid {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private boolean principal;
    @Column(unique=true)
    private String sid;




    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getId() {

        return id;
    }

    public boolean getPrincipal() {
        return principal;
    }

    public boolean isPrincipal() {
        return principal;
    }



    public String getSid() {
        return sid;
    }


    public AclSid(boolean principal, String sid) {
        this.principal=principal;
        this.sid = sid;

    }

    public AclSid() {
    }
}
