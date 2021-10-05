package com.example.gestionAchat.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Processus.
 */
@Entity
@Table(name = "processus")
public class Processus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private BonCommande bonCommande;

    @OneToOne
    @JoinColumn(unique = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private DemandeAchat demandeAchat;

    @OneToOne
    @JoinColumn(unique = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private Facture facture;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BonCommande getBonCommande() {
        return bonCommande;
    }

    public Processus bonCommande(BonCommande bonCommande) {
        this.bonCommande = bonCommande;
        return this;
    }

    public void setBonCommande(BonCommande bonCommande) {
        this.bonCommande = bonCommande;
    }

    public DemandeAchat getDemandeAchat() {
        return demandeAchat;
    }

    public Processus demandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
        return this;
    }

    public void setDemandeAchat(DemandeAchat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public Facture getFacture() {
        return facture;
    }

    public Processus facture(Facture facture) {
        this.facture = facture;
        return this;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Processus)) {
            return false;
        }
        return id != null && id.equals(((Processus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Processus{" +
                "id=" + getId() +
                "}";
    }
}
