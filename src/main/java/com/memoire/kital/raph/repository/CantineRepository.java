package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Cantine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cantine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CantineRepository extends JpaRepository<Cantine, String>, JpaSpecificationExecutor<Cantine> {
}
