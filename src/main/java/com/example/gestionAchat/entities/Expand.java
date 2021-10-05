package com.example.gestionAchat.entities;

import javax.persistence.*;

@Entity
@Table(name = "pm_expand")
public class Expand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int userId;
    private String departement;
    private String groupe;

    public Expand() {
        super();
    }

    public Expand(Long id, int userId, String departement, String groupe) {
        super();
        this.id = id;
        this.userId = userId;
        this.departement = departement;
        this.groupe = groupe;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the departement
     */
    public String getDepartement() {
        return departement;
    }

    /**
     * @param departement the departement to set
     */
    public void setDepartement(String departement) {
        this.departement = departement;
    }

    /**
     * @return the groupe
     */
    public String getGroupe() {
        return groupe;
    }

    /**
     * @param groupe the groupe to set
     */
    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Expand [id=" + id + ", userId=" + userId + ", departement=" + departement + ", groupe=" + groupe + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Expand other = (Expand) obj;
        if (departement == null) {
            if (other.departement != null)
                return false;
        } else if (!departement.equals(other.departement))
            return false;
        if (groupe == null) {
            if (other.groupe != null)
                return false;
        } else if (!groupe.equals(other.groupe))
            return false;
        if (id != other.id)
            return false;
        return userId == other.userId;
    }


}
