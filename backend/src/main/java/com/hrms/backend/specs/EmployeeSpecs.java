package com.hrms.backend.specs;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EmployeeSpecs {
    public static Specification<Employee> hasName(String nameQuery){
        return ((root, query, criteriaBuilder) ->
            criteriaBuilder.or(
                    criteriaBuilder.like(root.get("firstName"), "%"+nameQuery+"%"),
                    criteriaBuilder.like(root.get("lastName"),"%"+nameQuery+"%")
            )
                );
    }

    public static Specification<Employee> hasBirthday(){
        return ((root, query, criteriaBuilder) -> {
            return  criteriaBuilder.and(
                    criteriaBuilder.equal(criteriaBuilder.function("day",Integer.class,root.get("dob")), LocalDate.now().getDayOfMonth())
                    ,criteriaBuilder.equal(criteriaBuilder.function("month",Integer.class,root.get("dob")), LocalDate.now().getMonthValue())
            );
        });
    }
    public static Specification<Employee> hasWorkAnniversary(){
        return ((root, query, criteriaBuilder) -> {
            return  criteriaBuilder.and(
                    criteriaBuilder.equal(criteriaBuilder.function("day",Integer.class,root.get("joinedAt")), LocalDate.now().getDayOfMonth())
                    ,criteriaBuilder.equal(criteriaBuilder.function("month",Integer.class,root.get("joinedAt")), LocalDate.now().getMonthValue())
            );
        });
    }
}
