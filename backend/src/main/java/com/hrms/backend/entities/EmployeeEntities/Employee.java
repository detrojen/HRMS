package com.hrms.backend.entities.EmployeeEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrms.backend.entities.GameSchedulingEntities.EmployeeWiseGameInterest;
import com.hrms.backend.entities.GameSchedulingEntities.SlotRequest;
import com.hrms.backend.entities.GameSchedulingEntities.SlotRequestWiseEmployee;
import com.hrms.backend.entities.JobListingEntities.Job;
import com.hrms.backend.entities.JobListingEntities.JobApplication;
import com.hrms.backend.entities.JobListingEntities.JobWiseCvReviewer;
import com.hrms.backend.entities.Notification.Notification;
import com.hrms.backend.entities.PostEntities.Post;
import com.hrms.backend.entities.PostEntities.PostComment;
import com.hrms.backend.entities.PostEntities.PostLike;
import com.hrms.backend.entities.TravelEntities.*;
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

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "EmployeeWiseRole")
    @JsonIgnore
    private List<Role> role;
    private String email;
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "createdBy",fetch=FetchType.LAZY)
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(mappedBy = "employee",fetch=FetchType.LAZY)
    @JsonIgnore
    private List<EmployeeWiseGameInterest> interestedGames;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;

    @OneToMany(mappedBy = "requestedBy",fetch=FetchType.LAZY)
    @JsonIgnore
    private Collection<SlotRequest> requestedSlots;

    public String getFullName(){
        return firstName + " " + lastName;
    }

    @OneToMany(mappedBy = "employee",fetch=FetchType.LAZY)
    @JsonIgnore
    private Collection<SlotRequestWiseEmployee> slotRequestWiseEmployee;

    @OneToMany(mappedBy = "reviewer",fetch=FetchType.LAZY)
    private Collection<JobWiseCvReviewer> jobsToReview;

    @OneToMany(mappedBy = "referedBy",fetch=FetchType.LAZY)
    private Collection<JobApplication> referedJobApplication;


    @OneToMany(mappedBy = "reviewedBy",fetch=FetchType.LAZY)
    private Collection<JobApplication> reviewedJobApplication;

    @OneToMany(mappedBy = "hrOwner",fetch=FetchType.LAZY)
    private Collection<Job> ownedJobs;


    @OneToMany(mappedBy = "employee",fetch=FetchType.LAZY)
    @JsonIgnore
    private Collection<Notification> notifications;

    @OneToMany(mappedBy = "initiatedBy",fetch=FetchType.LAZY)
    @JsonIgnore
    private Collection<Travel> initatedTravels;

    @OneToMany(mappedBy = "employee",fetch=FetchType.LAZY)
    @JsonIgnore
    private Collection<TravelWiseEmployee> travelDetails;

    @OneToMany(mappedBy = "reviewedBy",fetch=FetchType.LAZY)
    @JsonIgnore
    private Collection<TravelWiseExpense> aprrovedExpenses;


    @OneToMany(mappedBy = "employee",fetch=FetchType.LAZY)
    @JsonIgnore
    private Collection<TravelWiseExpense> travelExpenses;

    @OneToMany(mappedBy = "uploadedBy",fetch=FetchType.LAZY)
    @JsonIgnore
    private Collection<TravelDocument> travelDocuments;

    @OneToMany(mappedBy = "uploadedBy",fetch=FetchType.LAZY)
    @JsonIgnore
    private Collection<TravelWiseEmployeeWiseDocument> travelWisePesonalDocuments;

    @OneToMany(mappedBy = "likedBy",fetch=FetchType.LAZY)
    @JsonIgnore
    private Collection<PostLike> likedPosts;

    @OneToMany(mappedBy = "commentedBy",fetch=FetchType.LAZY)
    private Collection<PostComment> comments;

    @OneToMany(mappedBy = "DeletedBy",fetch=FetchType.LAZY)
    private Collection<PostComment> deletedComments;

}
