package com.memoire.kital.raph.service.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class GroupeCantineDTO implements Serializable {
    private String id;
    private String nom;
    private Integer nombreEleves;
    private CantineDTO cantine;
}
