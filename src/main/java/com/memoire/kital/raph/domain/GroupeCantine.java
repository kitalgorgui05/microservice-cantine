package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.memoire.kital.raph.utils.SizeMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A GroupeCantine.
 */
@Entity
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "groupecantines")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupeCantine implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id",unique = true)
    private String id;

    @NotNull
    @Size(min = SizeMapper.SizeMapperGrpCantine.MIN, max = SizeMapper.SizeMapperGrpCantine.MAX)
    @Column(name = "nom", length = SizeMapper.SizeMapperGrpCantine.MAX, nullable = false)
    private String nom;

    @NotNull
    @Column(name = "nombre_eleves", nullable = false)
    private Integer nombreEleves;

    @ManyToOne
    @JsonIgnoreProperties(value = "groupecantines", allowSetters = true)
    private Cantine cantine;
}
