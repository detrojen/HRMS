package com.hrms.backend.specs;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecs {
    public static Specification<Employee> hasName(String nameQuery){
        return ((root, query, criteriaBuilder) ->
            criteriaBuilder.or(
                    criteriaBuilder.like(root.get("firstName"), "%"+nameQuery+"%"),
                    criteriaBuilder.like(root.get("lastName"),"%"+nameQuery+"%")
            )
                );
    }
}
