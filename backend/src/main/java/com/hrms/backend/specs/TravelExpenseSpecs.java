package com.hrms.backend.specs;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.ExpenseCategory;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelWiseExpense;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TravelExpenseSpecs {

    public static Specification<TravelWiseExpense> hasTravel(Long travelId){
        return ((root, query, criteriaBuilder) -> {
            Join<TravelWiseExpense, Travel> travelJoin = root.join("travel");
           return criteriaBuilder.equal(travelJoin.get("id"),travelId);
        });
    }
    public static Specification<TravelWiseExpense> isPending(){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("reviewedBy"),null);
        });
    }

    public static Specification<TravelWiseExpense> hasCategory(String category){
        return ((root, query, criteriaBuilder) -> {
            Join<TravelWiseExpense, ExpenseCategory> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("category"),category);
        });
    }

    public static Specification<TravelWiseExpense> hasEmployee(Long employeeId){
        return ((root, query, criteriaBuilder) -> {
           Join<TravelWiseExpense, Employee> employeeJoin = root.join("employee");
           return criteriaBuilder.equal(employeeJoin.get("id"),employeeId);
        });
    }
    public static Specification<TravelWiseExpense> hasDateFrom(LocalDate date){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfExpense"),date);
        });
    }

    public static Specification<TravelWiseExpense> hasDateTo(LocalDate date){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("dateOfExpense"),date);
        });
    }

}
