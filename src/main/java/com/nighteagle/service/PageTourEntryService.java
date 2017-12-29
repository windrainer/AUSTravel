package com.nighteagle.service;

import com.nighteagle.domain.PageTourEntry;
import com.nighteagle.repository.PageTourEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PageTourEntry.
 */
@Service
@Transactional
public class PageTourEntryService {

    private final Logger log = LoggerFactory.getLogger(PageTourEntryService.class);

    private final PageTourEntryRepository pageTourEntryRepository;

    public PageTourEntryService(PageTourEntryRepository pageTourEntryRepository) {
        this.pageTourEntryRepository = pageTourEntryRepository;
    }

    /**
     * Save a pageTourEntry.
     *
     * @param pageTourEntry the entity to save
     * @return the persisted entity
     */
    public PageTourEntry save(PageTourEntry pageTourEntry) {
        log.debug("Request to save PageTourEntry : {}", pageTourEntry);
        return pageTourEntryRepository.save(pageTourEntry);
    }

    /**
     *  Get all the pageTourEntries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PageTourEntry> findAll(Pageable pageable) {
        log.debug("Request to get all PageTourEntries");
        return pageTourEntryRepository.findAll(pageable);
    }

    /**
     *  Get one pageTourEntry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PageTourEntry findOne(Long id) {
        log.debug("Request to get PageTourEntry : {}", id);
        return pageTourEntryRepository.findOne(id);
    }

    /**
     *  Delete the  pageTourEntry by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PageTourEntry : {}", id);
        pageTourEntryRepository.delete(id);
    }
}
