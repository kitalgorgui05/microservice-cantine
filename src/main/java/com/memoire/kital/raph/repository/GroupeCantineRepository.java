package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.GroupeCantine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GroupeCantine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupeCantineRepository extends JpaRepository<GroupeCantine, String>, JpaSpecificationExecutor<GroupeCantine> {
}
