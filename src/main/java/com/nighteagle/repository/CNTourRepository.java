package com.nighteagle.repository;

import com.nighteagle.domain.CNTour;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CNTour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CNTourRepository extends JpaRepository<CNTour, Long> {

}
