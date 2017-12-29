package com.nighteagle.service;

import com.nighteagle.domain.Cube;
import com.nighteagle.repository.CubeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Cube.
 */
@Service
@Transactional
public class CubeService {

    private final Logger log = LoggerFactory.getLogger(CubeService.class);

    private final CubeRepository cubeRepository;

    public CubeService(CubeRepository cubeRepository) {
        this.cubeRepository = cubeRepository;
    }

    /**
     * Save a cube.
     *
     * @param cube the entity to save
     * @return the persisted entity
     */
    public Cube save(Cube cube) {
        log.debug("Request to save Cube : {}", cube);
        return cubeRepository.save(cube);
    }

    /**
     *  Get all the cubes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Cube> findAll(Pageable pageable) {
        log.debug("Request to get all Cubes");
        return cubeRepository.findAll(pageable);
    }

    /**
     *  Get one cube by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Cube findOne(Long id) {
        log.debug("Request to get Cube : {}", id);
        return cubeRepository.findOne(id);
    }

    /**
     *  Delete the  cube by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cube : {}", id);
        cubeRepository.delete(id);
    }
}
