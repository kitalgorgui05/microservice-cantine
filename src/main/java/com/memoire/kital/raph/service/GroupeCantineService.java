package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.GroupeCantineDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.GroupeCantine}.
 */
public interface GroupeCantineService {

    /**
     * Save a groupeCantine.
     *
     * @param groupeCantineDTO the entity to save.
     * @return the persisted entity.
     */
    GroupeCantineDTO save(GroupeCantineDTO groupeCantineDTO);

    /**
     * Get all the groupeCantines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupeCantineDTO> findAll(Pageable pageable);


    /**
     * Get the "id" groupeCantine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupeCantineDTO> findOne(Long id);

    /**
     * Delete the "id" groupeCantine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
