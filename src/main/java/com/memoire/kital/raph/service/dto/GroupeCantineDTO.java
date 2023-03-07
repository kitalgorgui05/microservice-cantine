package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

public class GroupeCantineDTO implements Serializable {
    private String id;
    private String nom;
    private Integer nombreEleves;
    private CantineDTO cantine;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNombreEleves() {
        return nombreEleves;
    }

    public void setNombreEleves(Integer nombreEleves) {
        this.nombreEleves = nombreEleves;
    }

    public CantineDTO getCantine() {
        return cantine;
    }

    public void setCantine(CantineDTO cantine) {
        this.cantine = cantine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupeCantineDTO)) {
            return false;
        }

        return id != null && id.equals(((GroupeCantineDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupeCantineDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nombreEleves=" + getNombreEleves() +
            ", cantine=" + getCantine() +
            "}";
    }
}
