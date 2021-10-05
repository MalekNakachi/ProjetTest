package com.example.gestionAchat.service.pm.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

public class ExpandCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter userId;

    private StringFilter departement;

    private StringFilter groupe;

    public ExpandCriteria() {
    }

    public ExpandCriteria(ExpandCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.departement = other.departement == null ? null : other.departement.copy();
        this.groupe = other.groupe == null ? null : other.groupe.copy();
    }

    @Override
    public ExpandCriteria copy() {
        return new ExpandCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getUserId() {
        return userId;
    }

    public void setUserId(IntegerFilter userId) {
        this.userId = userId;
    }

    public StringFilter getDepartement() {
        return departement;
    }

    public void setDepartement(StringFilter departement) {
        this.departement = departement;
    }

    public StringFilter getGroupe() {
        return groupe;
    }

    public void setGroupe(StringFilter groupe) {
        this.groupe = groupe;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExpandCriteria that = (ExpandCriteria) o;
        return
                Objects.equals(id, that.id) &&
                        Objects.equals(userId, that.userId) &&
                        Objects.equals(departement, that.departement) &&
                        Objects.equals(groupe, that.groupe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                userId,
                departement,
                groupe
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpandCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (departement != null ? "departement=" + departement + ", " : "") +
                (groupe != null ? "groupe=" + groupe + ", " : "") +
                "}";
    }

}
