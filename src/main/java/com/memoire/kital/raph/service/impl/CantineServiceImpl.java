package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.CantineService;
import com.memoire.kital.raph.domain.Cantine;
import com.memoire.kital.raph.repository.CantineRepository;
import com.memoire.kital.raph.service.dto.CantineDTO;
import com.memoire.kital.raph.service.mapper.CantineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cantine}.
 */
@Service
@Transactional
public class CantineServiceImpl implements CantineService {

    private final Logger log = LoggerFactory.getLogger(CantineServiceImpl.class);

    private final CantineRepository cantineRepository;

    private final CantineMapper cantineMapper;

    public CantineServiceImpl(CantineRepository cantineRepository, CantineMapper cantineMapper) {
        this.cantineRepository = cantineRepository;
        this.cantineMapper = cantineMapper;
    }

    @Override
    public CantineDTO save(CantineDTO cantineDTO) {
        log.debug("Request to save Cantine : {}", cantineDTO);
        Cantine cantine = cantineMapper.toEntity(cantineDTO);
        cantine = cantineRepository.save(cantine);
        return cantineMapper.toDto(cantine);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CantineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cantines");
        return cantineRepository.findAll(pageable)
            .map(cantineMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CantineDTO> findOne(Long id) {
        log.debug("Request to get Cantine : {}", id);
        return cantineRepository.findById(id)
            .map(cantineMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cantine : {}", id);
        cantineRepository.deleteById(id);
    }
}
