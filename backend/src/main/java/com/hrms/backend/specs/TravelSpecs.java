package com.hrms.backend.specs;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.enums.TravelStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Date;

public class TravelSpecs {
    private TravelSpecs(){}
    public static Specification<Travel> hasEmployee(Long employeeId){
        return ((root, query, criteriaBuilder) ->{
            Join<Travel, Employee> employeeJoin = root.join("travelWiseEmployees").join("employee");
            return criteriaBuilder.equal(employeeJoin.get("id"),employeeId);
        }
        );
    }

    public static Specification<Travel> hasAnyEmployee(Long[] employeeIds){
        return ((root, query, criteriaBuilder) ->{
            Join<Travel, Employee> employeeJoin = root.join("travelWiseEmployees").join("employee");
            CriteriaBuilder.In<Long> inClause = criteriaBuilder.in(employeeJoin.get("id"));
            for (Long id : employeeIds) {
                inClause.value(id);
            }
            return inClause;
        }
        );
    }

    public static Specification<Travel> hasManger(Long managerId){
        return ((root, query, criteriaBuilder) -> {
            Join<Travel,Employee> managerJoin = root.join("travelWiseEmployees").join("employee").join("manager");
            return criteriaBuilder.equal(managerJoin.get("id"),managerId);
        });
    }

    public static Specification<Travel> isNotEnded(){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.or(

                criteriaBuilder.notEqual(root.get("status"), TravelStatus.ENDED),
                criteriaBuilder.notEqual(root.get("status"), TravelStatus.CANCELLED)
            )
        );
    }

    public static Specification<Travel> hasStatus(String status){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"),status));
    }

    public static Specification<Travel> hasTitleLike(String title){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("status"),"%"+title+"%"));
    }

    public static Specification<Travel> startFrom(LocalDate date){
        return ((root, query, criteriaBuilder) ->criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"),date));
    }
    public static Specification<Travel> endTo(LocalDate date){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("endDate"),date));
    }

    public static Specification<Travel> concatWithAnd(Specification<Travel> base,Specification<Travel> newSpecification){
        if(base!=null){
            return base.and(newSpecification);
        }
        return newSpecification;
    }

}
