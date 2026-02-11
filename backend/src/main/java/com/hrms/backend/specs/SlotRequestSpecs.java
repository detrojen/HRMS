package com.hrms.backend.specs;

import com.hrms.backend.entities.Employee;
import com.hrms.backend.entities.GameSlot;
import com.hrms.backend.entities.SlotRequest;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;


public class SlotRequestSpecs {
    private SlotRequestSpecs(){}
    public static Specification<SlotRequest> getActiveSlotsSpecs(Long employeeId){
        return ((root, query, criteriaBuilder) ->{
//            Join<SlotRequest, Employee> employeeJoin = root.join("requestedBy");
            Join<SlotRequest,Employee> slotRequestSlotRequestWiseEmployeeJoin = root.join("slotRequestWiseEmployee").join("employee");
            Join<SlotRequest, GameSlot> gameSlotJoin = root.join("gameSlot");

            return criteriaBuilder.and(
                    criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("status"), "Confirm"),
                            criteriaBuilder.equal(root.get("status"),"On hold")
                    ),
//                    criteriaBuilder.equal(gameSlotJoin.get("slotDate"), "2026-02-11"),
                    criteriaBuilder.equal(slotRequestSlotRequestWiseEmployeeJoin.get("id"),employeeId)

            );
        }

        );
    }
}
