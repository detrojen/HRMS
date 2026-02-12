package com.hrms.backend.repositories.JobListingRepositories;

import com.hrms.backend.entities.JobListingEntities.JobWiseCvReviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobWiseCvReviewerRepository extends JpaRepository<JobWiseCvReviewer,Long> {
}
