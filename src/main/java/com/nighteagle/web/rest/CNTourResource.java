package com.nighteagle.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nighteagle.domain.CNTour;

import com.nighteagle.domain.Tour;
import com.nighteagle.repository.CNTourRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CNTour.
 */
@RestController
@RequestMapping("/api")
public class CNTourResource {

    private final Logger log = LoggerFactory.getLogger(CNTourResource.class);

    private static final String ENTITY_NAME = "cNTour";

    private final CNTourRepository cNTourRepository;

    public CNTourResource(CNTourRepository cNTourRepository) {
        this.cNTourRepository = cNTourRepository;
    }

    /**
     * POST  /c-n-tours : Create a new cNTour.
     *
     * @param cNTour the cNTour to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cNTour, or with status 400 (Bad Request) if the cNTour has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-n-tours")
    @Timed
    public ResponseEntity<CNTour> createCNTour(@Valid @RequestBody CNTour cNTour) throws URISyntaxException {
        log.debug("REST request to save CNTour : {}", cNTour);
        if (cNTour.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cNTour cannot already have an ID")).body(null);
        }
        CNTour result = cNTourRepository.save(cNTour);
        return ResponseEntity.created(new URI("/api/c-n-tours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-n-tours : Updates an existing cNTour.
     *
     * @param cNTour the cNTour to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cNTour,
     * or with status 400 (Bad Request) if the cNTour is not valid,
     * or with status 500 (Internal Server Error) if the cNTour couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-n-tours")
    @Timed
    public ResponseEntity<CNTour> updateCNTour(@Valid @RequestBody CNTour cNTour) throws URISyntaxException {
        log.debug("REST request to update CNTour : {}", cNTour);
        if (cNTour.getId() == null) {
            return createCNTour(cNTour);
        }
        CNTour result = cNTourRepository.save(cNTour);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cNTour.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-n-tours : get all the cNTours.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cNTours in body
     */
    @GetMapping("/c-n-tours")
    @Timed
    public ResponseEntity<List<CNTour>> getAllCNTours(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CNTours");
        Page<CNTour> page = cNTourRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-n-tours");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-n-tours/:id : get the "id" cNTour.
     *
     * @param id the id of the cNTour to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cNTour, or with status 404 (Not Found)
     */
    @GetMapping("/c-n-tours/{id}")
    @Timed
    public ResponseEntity<CNTour> getCNTour(@PathVariable Long id) {
        log.debug("REST request to get CNTour : {}", id);
        CNTour cNTour = cNTourRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cNTour));
    }

    /**
     * DELETE  /c-n-tours/:id : delete the "id" cNTour.
     *
     * @param id the id of the cNTour to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-n-tours/{id}")
    @Timed
    public ResponseEntity<Void> deleteCNTour(@PathVariable Long id) {
        log.debug("REST request to delete CNTour : {}", id);
        cNTourRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * A seperate method to get tour that everybody can call from main page.
     * @param id
     * @return
     */
    @GetMapping("/c-n-tours/page/{id}")
    @Timed
    public ResponseEntity<CNTour> getTourOnPage(@PathVariable Long id) {
        log.debug("REST request to get Tour : {}", id);
        CNTour tour = cNTourRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tour));
    }
}
