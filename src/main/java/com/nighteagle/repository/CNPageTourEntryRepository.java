package com.nighteagle.repository;

import com.nighteagle.domain.CNPageTourEntry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CNPageTourEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CNPageTourEntryRepository extends JpaRepository<CNPageTourEntry, Long> {

}
