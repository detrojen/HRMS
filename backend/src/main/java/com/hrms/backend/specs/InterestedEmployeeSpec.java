package com.hrms.backend.specs;

import com.hrms.backend.entities.Employee;
import com.hrms.backend.entities.EmployeeWiseGameInterest;
import com.hrms.backend.entities.GameType;
import com.hrms.backend.entities.SlotRequestWiseEmployee;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class InterestedEmployeeSpec {
    public static Specification<EmployeeWiseGameInterest> getSpecForIsInterestedAndByGameTypeIdAndNameLike(Long gameTypeId, String nameQuery){
        return ((root, query, criteriaBuilder) -> {
            Join<SlotRequestWiseEmployee, GameType> gameTypeJoin = root.join("gameType");
            Join<SlotRequestWiseEmployee, Employee> employeeJoin = root.join("employee");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(gameTypeJoin.get("id"), gameTypeId),
                    criteriaBuilder.equal(root.get("isInterested"), true),
                    criteriaBuilder.or(
                            criteriaBuilder.like(employeeJoin.get("firstName"),"%" + nameQuery + "%"),
                            criteriaBuilder.like(employeeJoin.get("lastName"),"%" + nameQuery + "%")
                    ));

        });
    }
}
