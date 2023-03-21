package com.memoire.kital.raph.service.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class CantineDTO implements Serializable {
    private String id;
    private String libelle;
    private Integer nombreGroupe;

    public CantineDTO(String id) {
        this.id = id;
    }

    public CantineDTO(String id, String libelle, Integer nombreGroupe) {
        this.id = id;
        this.libelle = libelle;
        this.nombreGroupe = nombreGroupe;
    }
}
