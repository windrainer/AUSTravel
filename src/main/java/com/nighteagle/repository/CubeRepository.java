package com.nighteagle.repository;

import com.nighteagle.domain.Cube;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Cube entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CubeRepository extends JpaRepository<Cube, Long> {

}
