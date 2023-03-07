package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.GroupeCantineService;
import com.memoire.kital.raph.domain.GroupeCantine;
import com.memoire.kital.raph.repository.GroupeCantineRepository;
import com.memoire.kital.raph.service.dto.GroupeCantineDTO;
import com.memoire.kital.raph.service.mapper.GroupeCantineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupeCantine}.
 */
@Service
@Transactional
public class GroupeCantineServiceImpl implements GroupeCantineService {
    private final Logger log = LoggerFactory.getLogger(GroupeCantineServiceImpl.class);
    private final GroupeCantineRepository groupeCantineRepository;
    private final GroupeCantineMapper groupeCantineMapper;

    public GroupeCantineServiceImpl(GroupeCantineRepository groupeCantineRepository, GroupeCantineMapper groupeCantineMapper) {
        this.groupeCantineRepository = groupeCantineRepository;
        this.groupeCantineMapper = groupeCantineMapper;
    }
    @Override
    public GroupeCantineDTO save(GroupeCantineDTO groupeCantineDTO) {
        log.debug("Request to save GroupeCantine : {}", groupeCantineDTO);
        GroupeCantine groupeCantine = groupeCantineMapper.toEntity(groupeCantineDTO);
        groupeCantine = groupeCantineRepository.save(groupeCantine);
        return groupeCantineMapper.toDto(groupeCantine);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<GroupeCantineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupeCantines");
        return groupeCantineRepository.findAll(pageable)
            .map(groupeCantineMapper::toDto);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<GroupeCantineDTO> findOne(String id) {
        log.debug("Request to get GroupeCantine : {}", id);
        return groupeCantineRepository.findById(id)
            .map(groupeCantineMapper::toDto);
    }
    @Override
    public void delete(String id) {
        log.debug("Request to delete GroupeCantine : {}", id);
        groupeCantineRepository.deleteById(id);
    }
}
