package com.example.gestionAchat.domain;

import javax.persistence.*;


@Entity
@Table(name="acl_class")
public class AclClass {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="class", unique = true)
    private String classe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public AclClass(String classe) {
        this.classe = classe;
    }


    public AclClass() {
    }
}

