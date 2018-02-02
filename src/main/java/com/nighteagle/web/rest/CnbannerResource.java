package com.nighteagle.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nighteagle.domain.Cnbanner;

import com.nighteagle.repository.CnbannerRepository;
import com.nighteagle.web.rest.util.HeaderUtil;
import com.nighteagle.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
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
 * REST controller for managing Cnbanner.
 */
@RestController
@RequestMapping("/api")
public class CnbannerResource {

    private final Logger log = LoggerFactory.getLogger(CnbannerResource.class);

    private static final String ENTITY_NAME = "cnbanner";

    private final CnbannerRepository cnbannerRepository;

    public CnbannerResource(CnbannerRepository cnbannerRepository) {
        this.cnbannerRepository = cnbannerRepository;
    }

    /**
     * POST  /cnbanners : Create a new cnbanner.
     *
     * @param cnbanner the cnbanner to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cnbanner, or with status 400 (Bad Request) if the cnbanner has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cnbanners")
    @Timed
    public ResponseEntity<Cnbanner> createCnbanner(@Valid @RequestBody Cnbanner cnbanner) throws URISyntaxException {
        log.debug("REST request to save Cnbanner : {}", cnbanner);
        if (cnbanner.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cnbanner cannot already have an ID")).body(null);
        }
        Cnbanner result = cnbannerRepository.save(cnbanner);
        return ResponseEntity.created(new URI("/api/cnbanners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cnbanners : Updates an existing cnbanner.
     *
     * @param cnbanner the cnbanner to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cnbanner,
     * or with status 400 (Bad Request) if the cnbanner is not valid,
     * or with status 500 (Internal Server Error) if the cnbanner couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cnbanners")
    @Timed
    public ResponseEntity<Cnbanner> updateCnbanner(@Valid @RequestBody Cnbanner cnbanner) throws URISyntaxException {
        log.debug("REST request to update Cnbanner : {}", cnbanner);
        if (cnbanner.getId() == null) {
            return createCnbanner(cnbanner);
        }
        Cnbanner result = cnbannerRepository.save(cnbanner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cnbanner.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cnbanners : get all the cnbanners.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cnbanners in body
     */
    @GetMapping("/cnbanners")
    @Timed
    public ResponseEntity<List<Cnbanner>> getAllCnbanners(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Cnbanners");
        Page<Cnbanner> page = cnbannerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cnbanners");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cnbanners/:id : get the "id" cnbanner.
     *
     * @param id the id of the cnbanner to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cnbanner, or with status 404 (Not Found)
     */
    @GetMapping("/cnbanners/{id}")
    @Timed
    public ResponseEntity<Cnbanner> getCnbanner(@PathVariable Long id) {
        log.debug("REST request to get Cnbanner : {}", id);
        Cnbanner cnbanner = cnbannerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cnbanner));
    }

    /**
     * DELETE  /cnbanners/:id : delete the "id" cnbanner.
     *
     * @param id the id of the cnbanner to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cnbanners/{id}")
    @Timed
    public ResponseEntity<Void> deleteCnbanner(@PathVariable Long id) {
        log.debug("REST request to delete Cnbanner : {}", id);
        cnbannerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * look for an activated banner.
     * @return
     */
    @GetMapping("/cnbanners/activated")
    @Timed
    public ResponseEntity<Cnbanner> getActivatedCnBanner() {
        log.debug("Rest request to get activated CnBanner");
        Cnbanner banner = new Cnbanner();
        banner.setActivated(true);
        Example<Cnbanner> bannerExample = Example.of(banner);
        Cnbanner activatedBanner = cnbannerRepository.findOne(bannerExample);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(activatedBanner));
    }
}
