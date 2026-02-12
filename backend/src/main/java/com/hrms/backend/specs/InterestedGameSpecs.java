//package com.hrms.backend.specs;
//
//import com.hrms.backend.entities.EmployeeEntities.Employee;
//import com.hrms.backend.entities.GameSchedulingEntities.EmployeeWiseGameInterest;
//import com.hrms.backend.entities.GameSchedulingEntities.GameType;
//
//import jakarta.persistence.criteria.Join;
//import org.springframework.data.jpa.domain.Specification;
//
//public class InterestedGameSpecs {
//    public static Specification<Employee> getGameInterestByEmployeeIdAndGameTypeID(Long employeeId, Long gameTypeId){
//        return (root,query,criteriaBuilder)->{
//            Join<Employee, EmployeeWiseGameInterest> employeesInterest = root.join("interestedGames");
//            Join<EmployeeWiseGameInterest, GameType> gameInterestGameTypeJoin = employeesInterest.join("gameType");
//            query.multiselect(
//                    root.get("id"),employeesInterest.get("id")
//            )
//            return criteriaBuilder.and(
//                    criteriaBuilder.equal(root.get("id"),employeeId),
//                    criteriaBuilder.equal(gameInterestGameTypeJoin.get("id"),gameTypeId)
//            );
//        };
//    }
//}
