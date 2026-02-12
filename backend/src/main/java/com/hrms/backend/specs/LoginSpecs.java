package com.hrms.backend.specs;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class LoginSpecs {
    public static Specification<Employee> getUserByEmailAndPassword(String email, String password){
        return (root,query,criteriaBuilder)->{
            Predicate emailPredicate = criteriaBuilder.equal(root.get("email"),email);
            Predicate passwordPredicate = criteriaBuilder.equal(root.get("password"),password);
            return  criteriaBuilder.and(emailPredicate,passwordPredicate);
        };
    }
}
