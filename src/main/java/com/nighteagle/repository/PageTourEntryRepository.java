package com.nighteagle.repository;

import com.nighteagle.domain.PageTourEntry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PageTourEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageTourEntryRepository extends JpaRepository<PageTourEntry, Long> {

}
