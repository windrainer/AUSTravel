package com.nighteagle.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nighteagle.domain.PageTourEntry;
import com.nighteagle.service.PageTourEntryService;
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
 * REST controller for managing PageTourEntry.
 */
@RestController
@RequestMapping("/api")
public class PageTourEntryResource {

    private final Logger log = LoggerFactory.getLogger(PageTourEntryResource.class);

    private static final String ENTITY_NAME = "pageTourEntry";

    private final PageTourEntryService pageTourEntryService;

    public PageTourEntryResource(PageTourEntryService pageTourEntryService) {
        this.pageTourEntryService = pageTourEntryService;
    }

    /**
     * POST  /page-tour-entries : Create a new pageTourEntry.
     *
     * @param pageTourEntry the pageTourEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pageTourEntry, or with status 400 (Bad Request) if the pageTourEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/page-tour-entries")
    @Timed
    public ResponseEntity<PageTourEntry> createPageTourEntry(@Valid @RequestBody PageTourEntry pageTourEntry) throws URISyntaxException {
        log.debug("REST request to save PageTourEntry : {}", pageTourEntry);
        if (pageTourEntry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pageTourEntry cannot already have an ID")).body(null);
        }
        PageTourEntry result = pageTourEntryService.save(pageTourEntry);
        return ResponseEntity.created(new URI("/api/page-tour-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /page-tour-entries : Updates an existing pageTourEntry.
     *
     * @param pageTourEntry the pageTourEntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pageTourEntry,
     * or with status 400 (Bad Request) if the pageTourEntry is not valid,
     * or with status 500 (Internal Server Error) if the pageTourEntry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/page-tour-entries")
    @Timed
    public ResponseEntity<PageTourEntry> updatePageTourEntry(@Valid @RequestBody PageTourEntry pageTourEntry) throws URISyntaxException {
        log.debug("REST request to update PageTourEntry : {}", pageTourEntry);
        if (pageTourEntry.getId() == null) {
            return createPageTourEntry(pageTourEntry);
        }
        PageTourEntry result = pageTourEntryService.save(pageTourEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pageTourEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /page-tour-entries : get all the pageTourEntries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pageTourEntries in body
     */
    @GetMapping("/page-tour-entries")
    @Timed
    public ResponseEntity<List<PageTourEntry>> getAllPageTourEntries(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PageTourEntries");
        Page<PageTourEntry> page = pageTourEntryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/page-tour-entries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /page-tour-entries/:id : get the "id" pageTourEntry.
     *
     * @param id the id of the pageTourEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pageTourEntry, or with status 404 (Not Found)
     */
    @GetMapping("/page-tour-entries/{id}")
    @Timed
    public ResponseEntity<PageTourEntry> getPageTourEntry(@PathVariable Long id) {
        log.debug("REST request to get PageTourEntry : {}", id);
        PageTourEntry pageTourEntry = pageTourEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pageTourEntry));
    }

    /**
     * DELETE  /page-tour-entries/:id : delete the "id" pageTourEntry.
     *
     * @param id the id of the pageTourEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/page-tour-entries/{id}")
    @Timed
    public ResponseEntity<Void> deletePageTourEntry(@PathVariable Long id) {
        log.debug("REST request to delete PageTourEntry : {}", id);
        pageTourEntryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
