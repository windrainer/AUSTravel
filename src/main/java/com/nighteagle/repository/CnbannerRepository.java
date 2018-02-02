package com.nighteagle.repository;

import com.nighteagle.domain.Cnbanner;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Cnbanner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CnbannerRepository extends JpaRepository<Cnbanner, Long> {

}
