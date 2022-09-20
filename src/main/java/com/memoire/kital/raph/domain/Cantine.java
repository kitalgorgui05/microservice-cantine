package com.memoire.kital.raph.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cantine.
 */
@Entity
@Table(name = "cantines")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cantine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 10)
    @Column(name = "libelle", length = 10, nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "nombre_groupe", nullable = false)
    private Integer nombreGroupe;

    @OneToMany(mappedBy = "cantine")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<GroupeCantine> groupecantines = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Cantine libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getNombreGroupe() {
        return nombreGroupe;
    }

    public Cantine nombreGroupe(Integer nombreGroupe) {
        this.nombreGroupe = nombreGroupe;
        return this;
    }

    public void setNombreGroupe(Integer nombreGroupe) {
        this.nombreGroupe = nombreGroupe;
    }

    public Set<GroupeCantine> getGroupecantines() {
        return groupecantines;
    }

    public Cantine groupecantines(Set<GroupeCantine> groupeCantines) {
        this.groupecantines = groupeCantines;
        return this;
    }

    public Cantine addGroupecantines(GroupeCantine groupeCantine) {
        this.groupecantines.add(groupeCantine);
        groupeCantine.setCantine(this);
        return this;
    }

    public Cantine removeGroupecantines(GroupeCantine groupeCantine) {
        this.groupecantines.remove(groupeCantine);
        groupeCantine.setCantine(null);
        return this;
    }

    public void setGroupecantines(Set<GroupeCantine> groupeCantines) {
        this.groupecantines = groupeCantines;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cantine)) {
            return false;
        }
        return id != null && id.equals(((Cantine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cantine{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", nombreGroupe=" + getNombreGroupe() +
            "}";
    }
}
