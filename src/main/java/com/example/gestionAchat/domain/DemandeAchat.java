package com.example.gestionAchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.ibatis.annotations.One;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A DemandeAchat.
 */
@Entity
@Table(name = "demande_achat")
public class DemandeAchat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "listedesproduits")
    private String listedesproduits;

    @Column(name = "date")
    private Date date;

    @Column(name = "tel")
    private Integer tel;

    @Column(name = "status")
    private String status;
    @Column(name = "identifiant")
    private String identifiant;

    @Column(name = "nomDemandeur")
    private String nomDemandeur;

    @Column(name = "email")
    private String email;

    @Column(name = "readers")
    private String readers;

    @Column(name = "authors")
    private String authors;


    @Column(name = "serviceDep")
    private String serviceDep;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "quantite_commande")
    private Integer quantite_commande;

    @Column(name = "description")
    private String description;

        @Column(name = "process_instance_id")
        private String processInstanceId;

      @Column(name = "activity_id")
      private String activityId;

      @Column(name = "activity_Name")
      private String activityName;
    @OneToMany(mappedBy = "demandeAchat")
    @NotFound(action= NotFoundAction.IGNORE)
    private Set<Offre> offres = new HashSet<>();

    @ManyToMany
    @NotFound(action= NotFoundAction.IGNORE)

    @JoinTable(name = "demande_achat_article",
            joinColumns = @JoinColumn(name = "demande_achat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"))
    private Set<Article> articles = new HashSet<>();
//    @OneToMany(mappedBy = "demandeAchat")
//    @NotFound(action= NotFoundAction.IGNORE)
//    private Set<Article> articles = new HashSet<>();

    @OneToOne(mappedBy = "demandeAchat")
    @JsonIgnore
    @NotFound(action= NotFoundAction.IGNORE)
    private Consultation consultation;

    @OneToOne(mappedBy = "demandeAchat")
    @JsonIgnore
    private Processus processus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReaders() {
        return readers;
    }

    public DemandeAchat readers(String readers) {
        this.readers = readers;
        return this;
    }

    public void setReaders(String readers) {
        this.readers = readers;
    }

    public String getAuthors() {
        return authors;
    }

    public DemandeAchat authors(String authors) {
        this.authors = authors;
        return this;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getStatus() {
        return status;
    }

    public DemandeAchat status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomDemandeur() {
        return nomDemandeur;
    }

    public DemandeAchat nomDemandeur(String nomDemandeur) {
        this.nomDemandeur = nomDemandeur;
        return this;
    }

    public void setNomDemandeur(String nomDemandeur) {
        this.nomDemandeur = nomDemandeur;
    }


    public String getIdentifiant() {
        return identifiant;
    }

    public DemandeAchat identifiant(String identifiant) {
        this.identifiant = identifiant;
        return this;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
    public String getListedesproduits() {
        return listedesproduits;
    }

    public DemandeAchat listedesproduits(String nom) {
        this.listedesproduits = listedesproduits;
        return this;
    }

    public void setListedesproduits(String listedesproduits) {
        this.listedesproduits = listedesproduits;
    }


    public Integer getQuantiteCommande() {
        return quantite_commande;
    }

    public DemandeAchat quantite_commande(Integer quantite_commande) {
        this.quantite_commande = quantite_commande;
        return this;
    }

    public void setQuantiteCommande(Integer quantite_commande ) {
        this.quantite_commande = quantite_commande;
    }



    public Integer getQuantite() {
        return quantite;
    }

    public DemandeAchat quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite ) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public DemandeAchat description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public DemandeAchat date(Date date) {
        this.date = date;
        return this;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public String getServiceDep() {
        return serviceDep;
    }

    public DemandeAchat serviceDep(String serviceDep) {
        this.serviceDep = serviceDep;
        return this;
    }

    public void setServiceDep(String serviceDep) {
        this.serviceDep = serviceDep;
    }

    public Integer getTel() {
        return tel;
    }

    public DemandeAchat tel(Integer tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }



    public String getEmail() {
        return email;
    }

    public DemandeAchat email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }




    public Set<Offre> getOffres() {
        return offres;
    }

    public DemandeAchat offres(Set<Offre> offres) {
        this.offres = offres;
        return this;
    }

    public DemandeAchat addOffre(Offre offre) {
        this.offres.add(offre);
        offre.setDemandeAchat(this);
        return this;
    }

    public DemandeAchat removeOffre(Offre offre) {
        this.offres.remove(offre);
        offre.setDemandeAchat(null);
        return this;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public DemandeAchat articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public DemandeAchat addArticle(Article article) {
        this.articles.add(article);
        article.setDemandeAchat(this);
        return this;
    }

    public DemandeAchat removeArticle(Article article) {
        this.articles.remove(article);
        article.setDemandeAchat(null);
        return this;
    }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public String getActivityName() {
    return activityName;
  }

  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }




    public Consultation getConsultation() {
        return consultation;
    }

    public DemandeAchat consultation(Consultation consultation) {
        this.consultation = consultation;
        return this;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public Processus getProcessus() {
        return processus;
    }

    public DemandeAchat processus(Processus processus) {
        this.processus = processus;
        return this;
    }

    public void setProcessus(Processus processus) {
        this.processus = processus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeAchat)) {
            return false;
        }
        return id != null && id.equals(((DemandeAchat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DemandeAchat{" +
                "id=" + getId() +
                ", listedesproduits='" + getListedesproduits() + "'" +
                ", tel=" + getTel() +

                ", email='" + getEmail() + "'" +

                ", serviceDep='" + getServiceDep() + "'" +
                ", quantite='" + getQuantite() + "'" +
                ", quantite_commande='" + getQuantiteCommande() + "'" +
                ", description='" + getDescription() + "'" +
                ", status='" + getStatus() + "'" +
                ", nomDemandeur='" + getNomDemandeur() + "'" +
                ", date='" + getDate() + "'" +
                ", identifiant='" + getIdentifiant() + "'" +

                "}";
    }
}





