package com.hrms.backend.repositories.EmployeeRepositories;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithManagerIdDto;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> getEmployeeByEmailAndPassword(String email,String Password);
    @Query(value = "execute sp_getEmployeeWithOneLevelreport :employeeId",nativeQuery = true)
    List<EmployeeWithManagerIdDto> getOneLevelReport(Long employeeId);
    @Query(value = "select new com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto(e.id,e.firstName,e.lastName,e.email,e.designation) from Employee e join e.role r where r.roleTitle = 'HR'", nativeQuery = false)
    List<EmployeeMinDetailsDto> getEmployeeWhoHr();


}
