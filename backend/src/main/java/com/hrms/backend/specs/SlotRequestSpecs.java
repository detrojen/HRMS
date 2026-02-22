package com.hrms.backend.specs;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.GameSchedulingEntities.GameSlot;
import com.hrms.backend.entities.GameSchedulingEntities.SlotRequest;
import com.hrms.backend.utils.DateFormatter;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;


public class SlotRequestSpecs {
    private SlotRequestSpecs(){}
    public static Specification<SlotRequest> getActiveSlotsSpecs(Long employeeId){
        return ((root, query, criteriaBuilder) ->{
//            Join<SlotRequest, Employee> employeeJoin = root.join("requestedBy");
            Join<SlotRequest,Employee> slotRequestSlotRequestWiseEmployeeJoin = root.join("slotRequestWiseEmployee").join("employee");
            Join<SlotRequest, GameSlot> gameSlotJoin = root.join("gameSlot");
            java.sql.Date.valueOf(LocalDate.now());
            return criteriaBuilder.and(
                    criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("status"), "Confirm"),
                            criteriaBuilder.equal(root.get("status"),"On hold")
                    ),
                    criteriaBuilder.greaterThanOrEqualTo(gameSlotJoin.get("slotDate"), java.sql.Date.valueOf(LocalDate.now())),
//                    criteriaBuilder.greaterThan(gameSlotJoin.get("startsFrom"),LocalTime.parse(DateFormatter.timeFormate.format(new Date()))),
                    criteriaBuilder.equal(slotRequestSlotRequestWiseEmployeeJoin.get("id"),employeeId)

            );
        }

        );
    }
}
