package com.memoire.kital.raph.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.memoire.kital.raph.domain.Cantine;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.CantineRepository;
import com.memoire.kital.raph.service.dto.CantineCriteria;
import com.memoire.kital.raph.service.dto.CantineDTO;
import com.memoire.kital.raph.service.mapper.CantineMapper;

/**
 * Service for executing complex queries for {@link Cantine} entities in the database.
 * The main input is a {@link CantineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CantineDTO} or a {@link Page} of {@link CantineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CantineQueryService extends QueryService<Cantine> {

    private final Logger log = LoggerFactory.getLogger(CantineQueryService.class);

    private final CantineRepository cantineRepository;

    private final CantineMapper cantineMapper;

    public CantineQueryService(CantineRepository cantineRepository, CantineMapper cantineMapper) {
        this.cantineRepository = cantineRepository;
        this.cantineMapper = cantineMapper;
    }

    /**
     * Return a {@link List} of {@link CantineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CantineDTO> findByCriteria(CantineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cantine> specification = createSpecification(criteria);
        return cantineMapper.toDto(cantineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CantineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CantineDTO> findByCriteria(CantineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cantine> specification = createSpecification(criteria);
        return cantineRepository.findAll(specification, page)
            .map(cantineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CantineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cantine> specification = createSpecification(criteria);
        return cantineRepository.count(specification);
    }

    /**
     * Function to convert {@link CantineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cantine> createSpecification(CantineCriteria criteria) {
        Specification<Cantine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Cantine_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Cantine_.libelle));
            }
            if (criteria.getNombreGroupe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNombreGroupe(), Cantine_.nombreGroupe));
            }
            if (criteria.getGroupecantinesId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupecantinesId(),
                    root -> root.join(Cantine_.groupecantines, JoinType.LEFT).get(GroupeCantine_.id)));
            }
        }
        return specification;
    }
}
