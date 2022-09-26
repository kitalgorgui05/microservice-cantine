package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.CantineDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Cantine}.
 */
public interface CantineService {

    /**
     * Save a cantine.
     *
     * @param cantineDTO the entity to save.
     * @return the persisted entity.
     */
    CantineDTO save(CantineDTO cantineDTO);

    /**
     * Get all the cantines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CantineDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cantine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CantineDTO> findOne(String id);

    /**
     * Delete the "id" cantine.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
