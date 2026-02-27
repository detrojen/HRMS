package com.hrms.backend.entities.JobListingEntities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CvReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String review;

    @ManyToOne
    private JobApplication jobApplication;

    @ManyToOne
    private JobWiseCvReviewer cvReviewer;

    @CreatedDate
    private java.util.Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}
