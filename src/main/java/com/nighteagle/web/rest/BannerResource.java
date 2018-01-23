package com.nighteagle.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nighteagle.domain.Banner;

import com.nighteagle.repository.BannerRepository;
import com.nighteagle.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Banner.
 */
@RestController
@RequestMapping("/api")
public class BannerResource {

    private final Logger log = LoggerFactory.getLogger(BannerResource.class);

    private static final String ENTITY_NAME = "banner";

    private final BannerRepository bannerRepository;

    public BannerResource(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    /**
     * POST  /banners : Create a new banner.
     *
     * @param banner the banner to create
     * @return the ResponseEntity with status 201 (Created) and with body the new banner, or with status 400 (Bad Request) if the banner has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/banners")
    @Timed
    public ResponseEntity<Banner> createBanner(@Valid @RequestBody Banner banner) throws URISyntaxException {
        log.debug("REST request to save Banner : {}", banner);
        if (banner.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new banner cannot already have an ID")).body(null);
        }
        Banner result = bannerRepository.save(banner);
        return ResponseEntity.created(new URI("/api/banners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /banners : Updates an existing banner.
     *
     * @param banner the banner to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated banner,
     * or with status 400 (Bad Request) if the banner is not valid,
     * or with status 500 (Internal Server Error) if the banner couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/banners")
    @Timed
    public ResponseEntity<Banner> updateBanner(@Valid @RequestBody Banner banner) throws URISyntaxException {
        log.debug("REST request to update Banner : {}", banner);
        if (banner.getId() == null) {
            return createBanner(banner);
        }
        Banner result = bannerRepository.save(banner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, banner.getId().toString()))
            .body(result);
    }

    /**
     * GET  /banners : get all the banners.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of banners in body
     */
    @GetMapping("/banners")
    @Timed
    public List<Banner> getAllBanners() {
        log.debug("REST request to get all Banners");
        return bannerRepository.findAll();
        }


    /**
     * look for an activated banner.
     * @return
     */
    @GetMapping("/banners/activated")
    @Timed
    public ResponseEntity<Banner> getActivatedBanner() {
        log.debug("Rest request to get activated Banner");
        Banner banner = new Banner();
        banner.setActivated(true);
        Example<Banner> bannerExample = Example.of(banner);
        Banner activatedBanner = bannerRepository.findOne(bannerExample);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(activatedBanner));
    }

    /**
     * GET  /banners/:id : get the "id" banner.
     *
     * @param id the id of the banner to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the banner, or with status 404 (Not Found)
     */
    @GetMapping("/banners/{id}")
    @Timed
    public ResponseEntity<Banner> getBanner(@PathVariable Long id) {
        log.debug("REST request to get Banner : {}", id);
        Banner banner = bannerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(banner));
    }

    /**
     * DELETE  /banners/:id : delete the "id" banner.
     *
     * @param id the id of the banner to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/banners/{id}")
    @Timed
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        log.debug("REST request to delete Banner : {}", id);
        bannerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
