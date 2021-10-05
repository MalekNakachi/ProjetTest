//package com.example.gestionAchat.domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * A Role.
// */
//@Entity
//@Table(name = "demande_articele")
//public class DemandeArticle implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @EmbeddedId
//    private GroupUserPK groupUserPK;
//
//    @ManyToOne
//    @JsonIgnoreProperties(value = "groupUserSet", allowSetters = true)
//    @JoinColumn(name = "demande_id", nullable = false, insertable = false, updatable = false)
//    private Group group;
//
//    @ManyToOne
//    @JsonIgnoreProperties(value = "groupUserSet", allowSetters = true)
//    @JoinColumn(name = "article_id", nullable = false, insertable = false, updatable = false)
//    private DemandeArticle DemandeArticle;
//
//    public DemandeArticle() {
//    }
//
//    public DemandeArticle(GroupUserPK groupUserPK, DemandeAchat demande_achat, Article article ) {
//        this.groupUserPK = groupUserPK;
//        this.demande_achat=demande_achat;
//        this.article=article;
//    }
//
//    public GroupUserPK getdDemandearticle() {
//        return groupUserPK;
//    }
//
//    public void setGroupUserPK(GroupUserPK groupUserPK) {
//        this.groupUserPK = groupUserPK;
//    }
//
//    public DemandeAchat getDemandeAchat() {
//        return demande_achat;
//    }
//
//    public void setDemandeAchat(DemandeAchat demande_achat) {
//        this.group = group;
//    }
//
//    public Article getArticle() {
//        return article;
//    }
//
//    public void setArticle(Article article) {
//        this.article = article;
//    }
//
//    @Override
//    public String toString() {
//        return "GroupUser{" +
//                "groupUserPK=" + groupUserPK +
//                ", demande_achat=" + demande_achat +
//                ", article=" + article +
//                '}';
//    }
//}
