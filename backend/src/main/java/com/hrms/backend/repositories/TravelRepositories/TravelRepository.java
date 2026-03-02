package com.hrms.backend.repositories.TravelRepositories;

import com.hrms.backend.entities.TravelEntities.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TravelRepository extends JpaRepository<Travel,Long>, JpaSpecificationExecutor<Travel> {

}
