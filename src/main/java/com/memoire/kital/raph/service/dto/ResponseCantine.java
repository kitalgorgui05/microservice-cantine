package com.memoire.kital.raph.service.dto;

public class ResponseCantine {
    private String id;
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
        if (!(o instanceof ResponseCantine)) {
            return false;
        }

        return id != null && id.equals(((ResponseCantine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponseCantine{" +
            ", id='" + getId() +"'"+
            ", libelle='" + getLibelle() +
            "}";
    }
}
