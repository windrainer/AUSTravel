package com.nighteagle.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nighteagle.domain.Cube;
import com.nighteagle.service.CubeService;
import com.nighteagle.web.rest.util.HeaderUtil;
import com.nighteagle.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cube.
 */
@RestController
@RequestMapping("/api")
public class CubeResource {

    private final Logger log = LoggerFactory.getLogger(CubeResource.class);

    private static final String ENTITY_NAME = "cube";

    private final CubeService cubeService;

    public CubeResource(CubeService cubeService) {
        this.cubeService = cubeService;
    }

    /**
     * POST  /cubes : Create a new cube.
     *
     * @param cube the cube to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cube, or with status 400 (Bad Request) if the cube has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cubes")
    @Timed
    public ResponseEntity<Cube> createCube(@RequestBody Cube cube) throws URISyntaxException {
        log.debug("REST request to save Cube : {}", cube);
        if (cube.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cube cannot already have an ID")).body(null);
        }
        Cube result = cubeService.save(cube);
        return ResponseEntity.created(new URI("/api/cubes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cubes : Updates an existing cube.
     *
     * @param cube the cube to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cube,
     * or with status 400 (Bad Request) if the cube is not valid,
     * or with status 500 (Internal Server Error) if the cube couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cubes")
    @Timed
    public ResponseEntity<Cube> updateCube(@RequestBody Cube cube) throws URISyntaxException {
        log.debug("REST request to update Cube : {}", cube);
        if (cube.getId() == null) {
            return createCube(cube);
        }
        Cube result = cubeService.save(cube);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cube.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cubes : get all the cubes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cubes in body
     */
    @GetMapping("/cubes")
    @Timed
    public ResponseEntity<List<Cube>> getAllCubes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Cubes");
        Page<Cube> page = cubeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cubes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cubes/:id : get the "id" cube.
     *
     * @param id the id of the cube to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cube, or with status 404 (Not Found)
     */
    @GetMapping("/cubes/{id}")
    @Timed
    public ResponseEntity<Cube> getCube(@PathVariable Long id) {
        log.debug("REST request to get Cube : {}", id);
        Cube cube = cubeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cube));
    }

    /**
     * DELETE  /cubes/:id : delete the "id" cube.
     *
     * @param id the id of the cube to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cubes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCube(@PathVariable Long id) {
        log.debug("REST request to delete Cube : {}", id);
        cubeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
