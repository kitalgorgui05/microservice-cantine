package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A GroupeCantine.
 */
@Entity
@Table(name = "groupecantines")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupeCantine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "nom", length = 20, nullable = false)
    private String nom;

    @NotNull
    @Column(name = "nombre_eleves", nullable = false)
    private Integer nombreEleves;

    @ManyToOne
    @JsonIgnoreProperties(value = "groupecantines", allowSetters = true)
    private Cantine cantine;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public GroupeCantine nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNombreEleves() {
        return nombreEleves;
    }

    public GroupeCantine nombreEleves(Integer nombreEleves) {
        this.nombreEleves = nombreEleves;
        return this;
    }

    public void setNombreEleves(Integer nombreEleves) {
        this.nombreEleves = nombreEleves;
    }

    public Cantine getCantine() {
        return cantine;
    }

    public GroupeCantine cantine(Cantine cantine) {
        this.cantine = cantine;
        return this;
    }

    public void setCantine(Cantine cantine) {
        this.cantine = cantine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupeCantine)) {
            return false;
        }
        return id != null && id.equals(((GroupeCantine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupeCantine{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nombreEleves=" + getNombreEleves() +
            "}";
    }
}
