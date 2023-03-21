package com.memoire.kital.raph.domain;

import com.memoire.kital.raph.utils.SizeMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cantine.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cantines")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cantine implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id",unique = true)
    private String id;

    @NotNull
    @Size(min = SizeMapper.SizeMapperCantine.MIN, max = SizeMapper.SizeMapperCantine.MAX)
    @Column(name = "libelle", length = SizeMapper.SizeMapperCantine.MAX, nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "nombre_groupe", nullable = false)
    private Integer nombreGroupe;

    @OneToMany(mappedBy = "cantine")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<GroupeCantine> groupecantines = new HashSet<>();
}
