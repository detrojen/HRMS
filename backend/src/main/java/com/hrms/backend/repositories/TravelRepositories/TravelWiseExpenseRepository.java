package com.hrms.backend.repositories.TravelRepositories;

import com.hrms.backend.entities.TravelEntities.TravelWiseExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TravelWiseExpenseRepository extends JpaRepository<TravelWiseExpense , Long> , JpaSpecificationExecutor<TravelWiseExpense> {
    List<TravelWiseExpense> findAllByEmployee_IdAndTravel_Id(Long employeeId, Long travelId);
    @Query(value = "select SUM(twe.askedAmount) from TravelWiseExpense twe join twe.employee emp join twe.travel travel where emp.id=:employeeId and travel.id = :travelId and twe.dateOfExpense =:dateOfExpense")
    Optional<Integer> getTotalExpenseByEmployeeByTravelByDate(Long employeeId, Long travelId, LocalDate dateOfExpense);
}
