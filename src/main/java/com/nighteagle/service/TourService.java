package com.nighteagle.service;

import com.nighteagle.domain.Tour;
import com.nighteagle.repository.TourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Tour.
 */
@Service
@Transactional
public class TourService {

    private final Logger log = LoggerFactory.getLogger(TourService.class);

    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    /**
     * Save a tour.
     *
     * @param tour the entity to save
     * @return the persisted entity
     */
    public Tour save(Tour tour) {
        log.debug("Request to save Tour : {}", tour);
        return tourRepository.save(tour);
    }

    /**
     *  Get all the tours.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tour> findAll(Pageable pageable) {
        log.debug("Request to get all Tours");
        return tourRepository.findAll(pageable);
    }

    /**
     *  Get one tour by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tour findOne(Long id) {
        log.debug("Request to get Tour : {}", id);
        return tourRepository.findOne(id);
    }

    /**
     *  Delete the  tour by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tour : {}", id);
        tourRepository.delete(id);
    }
}
