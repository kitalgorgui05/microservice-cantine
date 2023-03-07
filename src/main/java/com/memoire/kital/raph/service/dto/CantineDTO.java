package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

public class CantineDTO implements Serializable {
    private String id;
    @NotNull
    @Size(min = 3, max = 10)
    private String libelle;

    @NotNull
    private Integer nombreGroupe;

    //All constructor
    public CantineDTO(String id, String libelle, Integer nombreGroupe) {
        this.id = id;
        this.libelle = libelle;
        this.nombreGroupe = nombreGroupe;
    }
    public CantineDTO() {
    }
    public CantineDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getNombreGroupe() {
        return nombreGroupe;
    }

    public void setNombreGroupe(Integer nombreGroupe) {
        this.nombreGroupe = nombreGroupe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CantineDTO)) {
            return false;
        }

        return id != null && id.equals(((CantineDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CantineDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", nombreGroupe=" + getNombreGroupe() +
            "}";
    }
}
