package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.CantineService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.CantineDTO;
import com.memoire.kital.raph.service.dto.CantineCriteria;
import com.memoire.kital.raph.service.CantineQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.memoire.kital.raph.domain.Cantine}.
 */
@RestController
@RequestMapping("/api")
public class CantineResource {

    private final Logger log = LoggerFactory.getLogger(CantineResource.class);

    private static final String ENTITY_NAME = "cantineCantine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CantineService cantineService;

    private final CantineQueryService cantineQueryService;

    public CantineResource(CantineService cantineService, CantineQueryService cantineQueryService) {
        this.cantineService = cantineService;
        this.cantineQueryService = cantineQueryService;
    }

    /**
     * {@code POST  /cantines} : Create a new cantine.
     *
     * @param cantineDTO the cantineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cantineDTO, or with status {@code 400 (Bad Request)} if the cantine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cantines")
    public ResponseEntity<CantineDTO> createCantine(@Valid @RequestBody CantineDTO cantineDTO) throws URISyntaxException {
        log.debug("REST request to save Cantine : {}", cantineDTO);
        if (cantineDTO.getId() != null) {
            throw new BadRequestAlertException("A new cantine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CantineDTO result = cantineService.save(cantineDTO);
        return ResponseEntity.created(new URI("/api/cantines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getLibelle().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cantines} : Updates an existing cantine.
     *
     * @param cantineDTO the cantineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cantineDTO,
     * or with status {@code 400 (Bad Request)} if the cantineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cantineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cantines")
    public ResponseEntity<CantineDTO> updateCantine(@Valid @RequestBody CantineDTO cantineDTO) throws URISyntaxException {
        log.debug("REST request to update Cantine : {}", cantineDTO);
        if (cantineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CantineDTO result = cantineService.save(cantineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cantineDTO.getLibelle().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cantines} : get all the cantines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cantines in body.
     */
    @GetMapping("/cantines")
    public ResponseEntity<List<CantineDTO>> getAllCantines(CantineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Cantines by criteria: {}", criteria);
        Page<CantineDTO> page = cantineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cantines/count} : count all the cantines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cantines/count")
    public ResponseEntity<Long> countCantines(CantineCriteria criteria) {
        log.debug("REST request to count Cantines by criteria: {}", criteria);
        return ResponseEntity.ok().body(cantineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cantines/:id} : get the "id" cantine.
     *
     * @param id the id of the cantineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cantineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cantines/{id}")
    public ResponseEntity<CantineDTO> getCantine(@PathVariable String id) {
        log.debug("REST request to get Cantine : {}", id);
        Optional<CantineDTO> cantineDTO = cantineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cantineDTO);
    }

    /**
     * {@code DELETE  /cantines/:id} : delete the "id" cantine.
     *
     * @param id the id of the cantineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cantines/{id}")
    public ResponseEntity<Void> deleteCantine(@PathVariable String id) {
        log.debug("REST request to delete Cantine : {}", id);
        cantineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, null)).build();
    }
}
