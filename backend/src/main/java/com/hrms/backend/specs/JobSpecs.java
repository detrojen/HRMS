package com.hrms.backend.specs;

import com.hrms.backend.entities.JobListingEntities.Job;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;

public class JobSpecs {
    private JobSpecs(){}
    public static Specification<Job> getJobByStatus(String status){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"),status)
                );
    }
}
