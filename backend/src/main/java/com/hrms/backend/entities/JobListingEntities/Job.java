package com.hrms.backend.entities.JobListingEntities;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String workMode;
    private String jdPath;
    private String skills;
    private String status;
    private int vacancy;
    @ManyToOne
    @JoinColumn()
    private Employee hrOwner;
    @OneToMany(mappedBy = "job")
    private Collection<JobWiseCvReviewer> cvReviewers;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
}
