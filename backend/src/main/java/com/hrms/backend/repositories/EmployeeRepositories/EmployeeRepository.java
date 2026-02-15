package com.hrms.backend.repositories.EmployeeRepositories;

import com.hrms.backend.dtos.EmployeeWithManagerIdDto;
import com.hrms.backend.dtos.responseDtos.EmployeeOneLevelReportResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> getEmployeeByEmailAndPassword(String email,String Password);
    @Query(value = "execute sp_getEmployeeWithOneLevelreport :employeeId",nativeQuery = true)
    List<EmployeeWithManagerIdDto> getOneLevelReport(Long employeeId);

}
