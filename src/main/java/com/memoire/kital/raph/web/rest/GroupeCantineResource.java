package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.GroupeCantineService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.GroupeCantineDTO;
import com.memoire.kital.raph.service.dto.GroupeCantineCriteria;
import com.memoire.kital.raph.service.GroupeCantineQueryService;

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

@RestController
@RequestMapping("/api")
public class GroupeCantineResource {

    private final Logger log = LoggerFactory.getLogger(GroupeCantineResource.class);

    private static final String ENTITY_NAME = "cantineGroupeCantine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupeCantineService groupeCantineService;

    private final GroupeCantineQueryService groupeCantineQueryService;

    public GroupeCantineResource(GroupeCantineService groupeCantineService, GroupeCantineQueryService groupeCantineQueryService) {
        this.groupeCantineService = groupeCantineService;
        this.groupeCantineQueryService = groupeCantineQueryService;
    }

    @PostMapping("/groupe-cantines")
    public ResponseEntity<GroupeCantineDTO> createGroupeCantine(@Valid @RequestBody GroupeCantineDTO groupeCantineDTO) throws URISyntaxException {
        log.debug("REST request to save GroupeCantine : {}", groupeCantineDTO);
        if (groupeCantineDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupeCantine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupeCantineDTO result = groupeCantineService.save(groupeCantineDTO);
        return ResponseEntity.created(new URI("/api/groupe-cantines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/groupe-cantines")
    public ResponseEntity<GroupeCantineDTO> updateGroupeCantine(@Valid @RequestBody GroupeCantineDTO groupeCantineDTO) throws URISyntaxException {
        log.debug("REST request to update GroupeCantine : {}", groupeCantineDTO);
        if (groupeCantineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id null");
        }
        GroupeCantineDTO result = groupeCantineService.save(groupeCantineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupeCantineDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/groupe-cantines")
    public ResponseEntity<List<GroupeCantineDTO>> getAllGroupeCantines(GroupeCantineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GroupeCantines by criteria: {}", criteria);
        Page<GroupeCantineDTO> page = groupeCantineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/groupe-cantines/count")
    public ResponseEntity<Long> countGroupeCantines(GroupeCantineCriteria criteria) {
        log.debug("REST request to count GroupeCantines by criteria: {}", criteria);
        return ResponseEntity.ok().body(groupeCantineQueryService.countByCriteria(criteria));
    }

    @GetMapping("/groupe-cantines/{id}")
    public ResponseEntity<GroupeCantineDTO> getGroupeCantine(@PathVariable String id) {
        log.debug("REST request to get GroupeCantine : {}", id);
        Optional<GroupeCantineDTO> groupeCantineDTO = groupeCantineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupeCantineDTO);
    }

    @DeleteMapping("/groupe-cantines/{id}")
    public ResponseEntity<Void> deleteGroupeCantine(@PathVariable String id) {
        log.debug("REST request to delete GroupeCantine : {}", id);
        groupeCantineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
