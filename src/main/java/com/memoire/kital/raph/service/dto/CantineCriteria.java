package com.memoire.kital.raph.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.memoire.kital.raph.domain.Cantine} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.CantineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cantines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CantineCriteria implements Serializable, Criteria {

    private StringFilter id;

    private StringFilter libelle;

    private IntegerFilter nombreGroupe;

    private StringFilter groupecantinesId;

    public CantineCriteria() {
    }

    public CantineCriteria(CantineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.nombreGroupe = other.nombreGroupe == null ? null : other.nombreGroupe.copy();
        this.groupecantinesId = other.groupecantinesId == null ? null : other.groupecantinesId.copy();
    }

    @Override
    public CantineCriteria copy() {
        return new CantineCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public IntegerFilter getNombreGroupe() {
        return nombreGroupe;
    }

    public void setNombreGroupe(IntegerFilter nombreGroupe) {
        this.nombreGroupe = nombreGroupe;
    }

    public StringFilter getGroupecantinesId() {
        return groupecantinesId;
    }

    public void setGroupecantinesId(StringFilter groupecantinesId) {
        this.groupecantinesId = groupecantinesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CantineCriteria that = (CantineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(nombreGroupe, that.nombreGroupe) &&
            Objects.equals(groupecantinesId, that.groupecantinesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelle,
        nombreGroupe,
        groupecantinesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CantineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (nombreGroupe != null ? "nombreGroupe=" + nombreGroupe + ", " : "") +
                (groupecantinesId != null ? "groupecantinesId=" + groupecantinesId + ", " : "") +
            "}";
    }
}
