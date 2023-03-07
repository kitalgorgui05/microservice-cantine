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
public class GroupeCantineCriteria implements Serializable, Criteria {
    private StringFilter id;

    private StringFilter nom;

    private IntegerFilter nombreEleves;

    private StringFilter cantineId;

    public GroupeCantineCriteria() {
    }

    public GroupeCantineCriteria(GroupeCantineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.nombreEleves = other.nombreEleves == null ? null : other.nombreEleves.copy();
        this.cantineId = other.cantineId == null ? null : other.cantineId.copy();
    }

    @Override
    public GroupeCantineCriteria copy() {
        return new GroupeCantineCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public IntegerFilter getNombreEleves() {
        return nombreEleves;
    }

    public void setNombreEleves(IntegerFilter nombreEleves) {
        this.nombreEleves = nombreEleves;
    }

    public StringFilter getCantineId() {
        return cantineId;
    }

    public void setCantineId(StringFilter cantineId) {
        this.cantineId = cantineId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GroupeCantineCriteria that = (GroupeCantineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(nombreEleves, that.nombreEleves) &&
            Objects.equals(cantineId, that.cantineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        nombreEleves,
        cantineId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupeCantineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (nombreEleves != null ? "nombreEleves=" + nombreEleves + ", " : "") +
                (cantineId != null ? "cantineId=" + cantineId + ", " : "") +
            "}";
    }

}
