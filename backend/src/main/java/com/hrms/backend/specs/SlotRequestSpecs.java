package com.hrms.backend.specs;

import com.hrms.backend.entities.Employee;
import com.hrms.backend.entities.SlotRequest;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class SlotRequestSpecs {

    public static Specification<SlotRequest> getActiveSlotsSpecs(Long employeeId){
        return ((root, query, criteriaBuilder) ->{
            Join<SlotRequest, Employee> employeeJoin = root.join("requestedBy");
            return criteriaBuilder.and(
                    criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("status"), "Confirm"),
                            criteriaBuilder.equal(root.get("status"),"On hold")
                    ),
//                    criteriaBuilder.greaterThan(root.get("createdAt"), new Date()),
                    criteriaBuilder.equal(employeeJoin.get("id"),employeeId)

            );
        }

        );
    }
}
