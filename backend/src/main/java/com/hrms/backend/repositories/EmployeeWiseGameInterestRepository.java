package com.hrms.backend.repositories;

import com.hrms.backend.dtos.EmployeeWiseGameTypeUsageDto;
import com.hrms.backend.entities.EmployeeWiseGameInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeWiseGameInterestRepository extends JpaRepository<EmployeeWiseGameInterest,Long>, JpaSpecificationExecutor<EmployeeWiseGameInterest> {
    @Query(value = "select e.gameType.id as gameTypeId , e.employee.id as employeeId,  e.noOfSlotConsumed , e.currentCyclesSlotConsumed , e.isInterested, e.isBlocked from EmployeeWiseGameInterest e where e.employee.id = :empoyeeId and e.gameType.id = :gameTypeId",nativeQuery = false)
    EmployeeWiseGameTypeUsageDto getEmployyeGameUsage(Long empoyeeId, Long gameTypeId);


    EmployeeWiseGameInterest findByEmployee_IdAndGameType_Id(Long employeeId, Long gameTypeId);
    List<EmployeeWiseGameInterest> findAllByEmployee_Id(Long employeeId);
}

// e.currentCyclesSlotConsumed, e.isInterested, e.isBlocked,e.noOfSlotConsumed
