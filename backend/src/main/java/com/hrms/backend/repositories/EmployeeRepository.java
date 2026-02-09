package com.hrms.backend.repositories;

import com.hrms.backend.entities.Employee;
import com.hrms.backend.entities.EmployeeWiseGameInterest;
import org.hibernate.query.criteria.JpaJoin;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> getEmployeeByEmailAndPassword(String email,String Password);

}
