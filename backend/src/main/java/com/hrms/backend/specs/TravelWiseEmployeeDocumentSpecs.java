package com.hrms.backend.specs;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelWiseEmployeeWiseDocument;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class TravelWiseEmployeeDocumentSpecs {
    public static Specification<TravelWiseEmployeeWiseDocument> hasDocumnetUplodedBy(Long employeeId){
        return ((root, query, criteriaBuilder) ->{
            Join<TravelWiseEmployeeWiseDocument, Employee> employeeJoin = root.join("uploadedBy");
            return criteriaBuilder.equal(employeeJoin.get("id"),employeeId);
        }
        );
    }

    public static Specification<TravelWiseEmployeeWiseDocument> hasTravelId(Long travelId){
        return ((root, query, criteriaBuilder) ->{
            Join<TravelWiseEmployeeWiseDocument, Travel> travelJoin = root.join("travel");
            return criteriaBuilder.equal(travelJoin.get("id"),travelId);
        }
        );
    }

    public static Specification<TravelWiseEmployeeWiseDocument> hasManagerId(Long managerId){
        return ((root, query, criteriaBuilder) -> {
            Join<TravelWiseEmployeeWiseDocument, Employee> managerJoin = root.join("uploadedBy").join("manager");
            return criteriaBuilder.equal(managerJoin.get("id"),managerId);
        });
    }
}
