package com.hrms.backend.repositories.JobListingRepositories;

import com.hrms.backend.entities.JobListingEntities.CvReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CvReviewRepository extends JpaRepository<CvReview,Long>, JpaSpecificationExecutor<CvReview> {
    Optional<CvReview> findByJobApplication_IdAndCvReviewer_Id(Long jobApplicationId, Long cvReviewerId);
}
