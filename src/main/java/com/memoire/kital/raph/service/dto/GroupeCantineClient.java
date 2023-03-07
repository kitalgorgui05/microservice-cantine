package com.memoire.kital.raph.service.dto;

import java.io.Serializable;

public class GroupeCantineClient implements Serializable {
    private String id;
    private String nom;
    private Integer nombreEleves;
    private ResponseCantine cantine;

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

    public ResponseCantine getCantine() {
        return cantine;
    }

    public void setCantine(ResponseCantine cantine) {
        this.cantine = cantine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupeCantineClient)) {
            return false;
        }

        return id != null && id.equals(((GroupeCantineClient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupeCantineClient{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nombreEleves=" + getNombreEleves() +
            ", cantine=" + getCantine() +
            "}";
    }
}
