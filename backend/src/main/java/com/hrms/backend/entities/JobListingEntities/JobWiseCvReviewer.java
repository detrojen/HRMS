package com.hrms.backend.entities.JobListingEntities;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class JobWiseCvReviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    private Employee reviewer;
    @ManyToOne()
    private Job job;
    @OneToMany()
    private List<CvReview> cvReviews;

    @CreatedDate
    private java.util.Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}
