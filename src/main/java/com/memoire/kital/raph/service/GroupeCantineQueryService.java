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

import com.memoire.kital.raph.domain.GroupeCantine;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.GroupeCantineRepository;
import com.memoire.kital.raph.service.dto.GroupeCantineCriteria;
import com.memoire.kital.raph.service.dto.GroupeCantineDTO;
import com.memoire.kital.raph.service.mapper.GroupeCantineMapper;

/**
 * Service for executing complex queries for {@link GroupeCantine} entities in the database.
 * The main input is a {@link GroupeCantineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GroupeCantineDTO} or a {@link Page} of {@link GroupeCantineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GroupeCantineQueryService extends QueryService<GroupeCantine> {

    private final Logger log = LoggerFactory.getLogger(GroupeCantineQueryService.class);

    private final GroupeCantineRepository groupeCantineRepository;

    private final GroupeCantineMapper groupeCantineMapper;

    public GroupeCantineQueryService(GroupeCantineRepository groupeCantineRepository, GroupeCantineMapper groupeCantineMapper) {
        this.groupeCantineRepository = groupeCantineRepository;
        this.groupeCantineMapper = groupeCantineMapper;
    }

    /**
     * Return a {@link List} of {@link GroupeCantineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GroupeCantineDTO> findByCriteria(GroupeCantineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GroupeCantine> specification = createSpecification(criteria);
        return groupeCantineMapper.toDto(groupeCantineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GroupeCantineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupeCantineDTO> findByCriteria(GroupeCantineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GroupeCantine> specification = createSpecification(criteria);
        return groupeCantineRepository.findAll(specification, page)
            .map(groupeCantineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GroupeCantineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GroupeCantine> specification = createSpecification(criteria);
        return groupeCantineRepository.count(specification);
    }

    /**
     * Function to convert {@link GroupeCantineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GroupeCantine> createSpecification(GroupeCantineCriteria criteria) {
        Specification<GroupeCantine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GroupeCantine_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), GroupeCantine_.nom));
            }
            if (criteria.getNombreEleves() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNombreEleves(), GroupeCantine_.nombreEleves));
            }
            if (criteria.getCantineId() != null) {
                specification = specification.and(buildSpecification(criteria.getCantineId(),
                    root -> root.join(GroupeCantine_.cantine, JoinType.LEFT).get(Cantine_.id)));
            }
        }
        return specification;
    }
}
