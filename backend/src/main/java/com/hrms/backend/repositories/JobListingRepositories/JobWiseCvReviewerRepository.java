package com.hrms.backend.repositories.JobListingRepositories;

import com.hrms.backend.entities.JobListingEntities.JobWiseCvReviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobWiseCvReviewerRepository extends JpaRepository<JobWiseCvReviewer,Long> {
    Optional<JobWiseCvReviewer> findByJob_IdAndReviewer_Id(Long jobId, Long employeeId);

}
