package com.hrms.backend.repositories.TravelRepositories;

import com.hrms.backend.entities.TravelEntities.TravelWiseExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TravelWiseExpenseRepository extends JpaRepository<TravelWiseExpense , Long> , JpaSpecificationExecutor<TravelWiseExpense> {
    List<TravelWiseExpense> findAllByEmployee_IdAndTravel_Id(Long employeeId, Long travelId);
}
