package com.hrms.backend.specs;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.JobListingEntities.JobApplication;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class JobApplicationSpecs {
    public static Specification<JobApplication> hasAssignedToReview(Long employeeId){
        return ((root, query, criteriaBuilder) -> {
            Join<JobApplication,Employee> reviewerJoin= root.join("job").join("cvReviewers").join("reviewer");
            return criteriaBuilder.equal(reviewerJoin.get("id"),employeeId);
        }
                );
    }

    public static Specification<JobApplication> hasRefered(Long employeeId){
        return ((root, query, criteriaBuilder) -> {
            Join<JobApplication,Employee> referedByJoin= root.join("referedBy");
            return criteriaBuilder.equal(referedByJoin.get("id"),employeeId);
        }
        );
    }

}
