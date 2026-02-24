package com.hrms.backend.specs;

import com.hrms.backend.entities.GameSchedulingEntities.GameSlot;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;

public class GameSlotSpecs {
    public static Specification<GameSlot> currentSlot(){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("slotDate"), LocalDate.now()),
                    criteriaBuilder.lessThanOrEqualTo(root.get("startsFrom"), LocalTime.now()),
                    criteriaBuilder.greaterThanOrEqualTo(root.get("endsAt"), LocalTime.now())
            );
        });
    }
}
