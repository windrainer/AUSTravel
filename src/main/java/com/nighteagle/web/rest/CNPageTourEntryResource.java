package com.nighteagle.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nighteagle.domain.CNPageTourEntry;

import com.nighteagle.repository.CNPageTourEntryRepository;
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
 * REST controller for managing CNPageTourEntry.
 */
@RestController
@RequestMapping("/api")
public class CNPageTourEntryResource {

    private final Logger log = LoggerFactory.getLogger(CNPageTourEntryResource.class);

    private static final String ENTITY_NAME = "cNPageTourEntry";

    private final CNPageTourEntryRepository cNPageTourEntryRepository;

    public CNPageTourEntryResource(CNPageTourEntryRepository cNPageTourEntryRepository) {
        this.cNPageTourEntryRepository = cNPageTourEntryRepository;
    }

    /**
     * POST  /c-n-page-tour-entries : Create a new cNPageTourEntry.
     *
     * @param cNPageTourEntry the cNPageTourEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cNPageTourEntry, or with status 400 (Bad Request) if the cNPageTourEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/c-n-page-tour-entries")
    @Timed
    public ResponseEntity<CNPageTourEntry> createCNPageTourEntry(@Valid @RequestBody CNPageTourEntry cNPageTourEntry) throws URISyntaxException {
        log.debug("REST request to save CNPageTourEntry : {}", cNPageTourEntry);
        if (cNPageTourEntry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cNPageTourEntry cannot already have an ID")).body(null);
        }
        CNPageTourEntry result = cNPageTourEntryRepository.save(cNPageTourEntry);
        return ResponseEntity.created(new URI("/api/c-n-page-tour-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-n-page-tour-entries : Updates an existing cNPageTourEntry.
     *
     * @param cNPageTourEntry the cNPageTourEntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cNPageTourEntry,
     * or with status 400 (Bad Request) if the cNPageTourEntry is not valid,
     * or with status 500 (Internal Server Error) if the cNPageTourEntry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/c-n-page-tour-entries")
    @Timed
    public ResponseEntity<CNPageTourEntry> updateCNPageTourEntry(@Valid @RequestBody CNPageTourEntry cNPageTourEntry) throws URISyntaxException {
        log.debug("REST request to update CNPageTourEntry : {}", cNPageTourEntry);
        if (cNPageTourEntry.getId() == null) {
            return createCNPageTourEntry(cNPageTourEntry);
        }
        CNPageTourEntry result = cNPageTourEntryRepository.save(cNPageTourEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cNPageTourEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-n-page-tour-entries : get all the cNPageTourEntries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cNPageTourEntries in body
     */
    @GetMapping("/c-n-page-tour-entries")
    @Timed
    public ResponseEntity<List<CNPageTourEntry>> getAllCNPageTourEntries(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CNPageTourEntries");
        Page<CNPageTourEntry> page = cNPageTourEntryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-n-page-tour-entries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-n-page-tour-entries/:id : get the "id" cNPageTourEntry.
     *
     * @param id the id of the cNPageTourEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cNPageTourEntry, or with status 404 (Not Found)
     */
    @GetMapping("/c-n-page-tour-entries/{id}")
    @Timed
    public ResponseEntity<CNPageTourEntry> getCNPageTourEntry(@PathVariable Long id) {
        log.debug("REST request to get CNPageTourEntry : {}", id);
        CNPageTourEntry cNPageTourEntry = cNPageTourEntryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cNPageTourEntry));
    }

    /**
     * DELETE  /c-n-page-tour-entries/:id : delete the "id" cNPageTourEntry.
     *
     * @param id the id of the cNPageTourEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/c-n-page-tour-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteCNPageTourEntry(@PathVariable Long id) {
        log.debug("REST request to delete CNPageTourEntry : {}", id);
        cNPageTourEntryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
