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
    Optional<TravelWiseExpense> getByIdAndEmployee_IdAndProofs_Id(Long expenseId, Long employeeId, Long expenseDocumentId);
    int countByTravel_Id(Long travelId);
    int countByTravel_IdAndStatus(Long travelId,String status);
    @Query(value = "select SUM(expense.askedAmount) from TravelWiseExpense expense join expense.travel travel where travel.id =:travelId")
    Optional<Integer> sumOfAskedAmountByTravelId(Long travelId);
    @Query(value = "select SUM(expense.aprrovedAmount) from TravelWiseExpense expense join expense.travel travel where travel.id =:travelId")
    Optional<Integer> sumOfApprovedAmountByTravelId(Long travelId);
}
