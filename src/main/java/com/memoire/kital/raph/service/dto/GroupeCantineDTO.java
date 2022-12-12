package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.GroupeCantine} entity.
 */
public class GroupeCantineDTO implements Serializable {

    private String id;

    @NotNull
    @Size(min = 2, max = 20)
    private String nom;

    @NotNull
    private Integer nombreEleves;


    private String cantineId;

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

    public String getCantineId() {
        return cantineId;
    }

    public void setCantineId(String cantineId) {
        this.cantineId = cantineId;
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
            ", cantineId=" + getCantineId() +
            "}";
    }
}
