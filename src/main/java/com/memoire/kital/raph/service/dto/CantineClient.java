package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CantineClient {
    private String id;

    @NotNull
    @Size(min = 3, max = 10)
    private String libelle;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CantineDTO)) {
            return false;
        }

        return id != null && id.equals(((CantineClient) o).id);
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
            ", libelle='" + getLibelle() +
            "}";
    }
}
