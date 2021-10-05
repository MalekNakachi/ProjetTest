
package com.example.gestionAchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A BonCommande.
 */

@Entity
@Table(name = "bon_commande")
public class BonCommande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_commande")
    private Integer numCommande;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "identifiant")

    private String identifiant;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "listeArticles")
    private String listeArticles;

    @Column(name = "status")
    private String status;

    @Column(name = "tel")
    private Integer tel;

    @Column(name = "nomDemandeur")
    private String nomDemandeur;

    @Column(name = "date")
    private Date date;

    @OneToOne
    @JoinColumn(unique = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private Offre offre;

    @ManyToMany
    @NotFound(action= NotFoundAction.IGNORE)

    @JoinTable(name = "bon_commande_article",
            joinColumns = @JoinColumn(name = "bon_commande_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"))
    private Set<Article> articles = new HashSet<>();
    @OneToOne(mappedBy = "bonCommande")
    @JsonIgnore
    @NotFound(action= NotFoundAction.IGNORE)
    private Facture facture;

    @OneToOne(mappedBy = "bonCommande")
    @JsonIgnore
    @NotFound(action= NotFoundAction.IGNORE)
    private Processus processus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrix() {
        return prix;
    }

    public BonCommande prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public BonCommande quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Integer getNumCommande() {
        return numCommande;
    }

    public BonCommande numCommande(Integer numCommande) {
        this.numCommande = numCommande;
        return this;
    }

    public void setNumCommande(Integer numCommande) {
        this.numCommande = numCommande;
    }

    public String getStatus() {
        return status;
    }

    public BonCommande status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getTel() {
        return tel;
    }

    public BonCommande tel(Integer tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getListeArticles() {
        return listeArticles;
    }

    public BonCommande listeArticles(String listeArticles) {
        this.listeArticles = listeArticles;
        return this;
    }

    public void setListeArticles(String listeArticles) {
        this.listeArticles = listeArticles;
    }




    public String getIdentifiant() {

        return identifiant;

    }



    public BonCommande identifiant(String identifiant) {

        this.identifiant = identifiant;

        return this;

    }



    public void setIdentifiant(String identifiant) {

        this.identifiant = identifiant;

    }

    public String getNomDemandeur() {
        return nomDemandeur;
    }

    public BonCommande nomDemandeur(String nomDemandeur) {
        this.nomDemandeur = nomDemandeur;
        return this;
    }

    public void setNomDemandeur(String nomDemandeur) {
        this.nomDemandeur = nomDemandeur;
    }

    public Date getDate() {
        return date;
    }

    public BonCommande date(Date date) {
        this.date = date;
        return this;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Offre getOffre() {
        return offre;
    }

    public BonCommande offre(Offre offre) {
        this.offre = offre;
        return this;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public BonCommande articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public BonCommande addArticle(Article article) {
        this.articles.add(article);
        article.getBonCommandes().add(this);
        return this;
    }

    public BonCommande removeArticle(Article article) {
        this.articles.remove(article);
        article.getBonCommandes().remove(this);
        return this;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Facture getFacture() {
        return facture;
    }

    public BonCommande facture(Facture facture) {
        this.facture = facture;
        return this;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Processus getProcessus() {
        return processus;
    }

    public BonCommande processus(Processus processus) {
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
        if (!(o instanceof BonCommande)) {
            return false;
        }
        return id != null && id.equals(((BonCommande) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BonCommande{" +
                "id=" + getId() +
                ", numCommande=" + getNumCommande() +
                ", date='" + getDate() + "'" +
                ", status='" + getStatus() + "'" +
                ", listProduits='" + getListeArticles() + "'" +
                ", prix='" + getPrix() + "'" +
                ", quantite='" + getQuantite() + "'" +
                ", status='" + getTel() + "'" +
                ", listeArticles='" + getListeArticles() + "'" +

                "}";
    }
}




