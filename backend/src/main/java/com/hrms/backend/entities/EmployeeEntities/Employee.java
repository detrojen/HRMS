package com.hrms.backend.entities.EmployeeEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrms.backend.entities.GameSchedulingEntities.EmployeeWiseGameInterest;
import com.hrms.backend.entities.GameSchedulingEntities.SlotRequest;
import com.hrms.backend.entities.GameSchedulingEntities.SlotRequestWiseEmployee;
import com.hrms.backend.entities.JobListingEntities.Job;
import com.hrms.backend.entities.JobListingEntities.JobApplication;
import com.hrms.backend.entities.JobListingEntities.JobWiseCvReviewer;
import com.hrms.backend.entities.PostEntities.Post;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Date dob;
    private Date joinedAt;
    @ManyToOne
    @JoinColumn(name = "mangerId")
    @JsonIgnore
    private Employee manager;
    private String designation;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
    private String email;
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<EmployeeWiseGameInterest> interestedGames;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;

    @OneToMany(mappedBy = "requestedBy")
    @JsonIgnore
    private Collection<SlotRequest> requestedSlots;

    public String getFullName(){
        return firstName + " " + lastName;
    }

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private Collection<SlotRequestWiseEmployee> slotRequestWiseEmployee;

    @OneToMany(mappedBy = "reviewer")
    private Collection<JobWiseCvReviewer> jobsToReview;

    public Collection<JobWiseCvReviewer> getJobsToReview() {
        return jobsToReview;
    }

    public void setJobsToReview(Collection<JobWiseCvReviewer> jobsToReview) {
        this.jobsToReview = jobsToReview;
    }

    @OneToMany(mappedBy = "referedBy")
    private Collection<JobApplication> referedJobApplication;

    public Collection<JobApplication> getReferedJobApplication() {
        return referedJobApplication;
    }

    public void setReferedJobApplication(Collection<JobApplication> referedJobApplication) {
        this.referedJobApplication = referedJobApplication;
    }

    @OneToMany(mappedBy = "hrOwner")
    private Collection<Job> ownedJobs;

    public Collection<Job> getOwnedJobs() {
        return ownedJobs;
    }

    public void setOwnedJobs(Collection<Job> ownedJobs) {
        this.ownedJobs = ownedJobs;
    }
}
