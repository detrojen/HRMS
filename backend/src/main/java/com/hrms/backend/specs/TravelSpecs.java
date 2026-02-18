package com.hrms.backend.specs;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelWiseEmployee;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class TravelSpecs {

    public static Specification<Travel> hasEmployee(Long employeeId){
        return ((root, query, criteriaBuilder) ->{
            Join<Travel, Employee> employeeJoin = root.join("travelWiseEmployees").join("employee");
            return criteriaBuilder.equal(employeeJoin.get("id"),employeeId);
        }
        );
    }

    public static Specification<Travel> hasManger(Long managerId){
        return ((root, query, criteriaBuilder) -> {
            Join<Travel,Employee> managerJoin = root.join("travelWiseEmployees").join("employee").join("manager");
            return criteriaBuilder.equal(managerJoin.get("id"),managerId);
        });
    }


}
