package com.hrms.backend.entities.JobListingEntities;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 8000)
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
    @OneToMany(mappedBy = "job")
    private Collection<JobApplication> jobApplications;

    public Collection<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(Collection<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }
}
