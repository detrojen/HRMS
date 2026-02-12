package com.hrms.backend.repositories.JobListingRepositories;

import com.hrms.backend.entities.JobListingEntities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long>, JpaSpecificationExecutor<JobApplication> {
}
