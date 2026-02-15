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

    @OneToMany(mappedBy = "referedBy")
    private Collection<JobApplication> referedJobApplication;


    @OneToMany(mappedBy = "hrOwner")
    private Collection<Job> ownedJobs;


    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private Collection<Notification> notifications;

    @OneToMany(mappedBy = "initiatedBy")
    private Collection<Travel> initatedTravels;

    public Collection<Travel> getInitatedTravels() {
        return initatedTravels;
    }

    public void setInitatedTravels(Collection<Travel> initatedTravels) {
        this.initatedTravels = initatedTravels;
    }

    @OneToMany(mappedBy = "employee")
    private Collection<TravelWiseEmployee> travelDetails;

    public Collection<TravelWiseEmployee> getTravelDetails() {
        return travelDetails;
    }

    public void setTravelDetails(Collection<TravelWiseEmployee> travelDetails) {
        this.travelDetails = travelDetails;
    }

    @OneToMany(mappedBy = "aprrovedBy")
    private Collection<TravelWiseExpense> aprrovedExpenses;

    public Collection<TravelWiseExpense> getAprrovedExpenses() {
        return aprrovedExpenses;
    }

    public void setAprrovedExpenses(Collection<TravelWiseExpense> aprrovedExpenses) {
        this.aprrovedExpenses = aprrovedExpenses;
    }

    @OneToMany(mappedBy = "employee")
    private Collection<TravelWiseExpense> travelExpenses;

    public Collection<TravelWiseExpense> getTravelExpenses() {
        return travelExpenses;
    }

    public void setTravelExpenses(Collection<TravelWiseExpense> travelExpenses) {
        this.travelExpenses = travelExpenses;
    }

    @OneToMany(mappedBy = "uploadedBy")
    private Collection<TravelDocument> travelDocuments;

    public Collection<TravelDocument> getTravelDocuments() {
        return travelDocuments;
    }

    public void setTravelDocuments(Collection<TravelDocument> travelDocuments) {
        this.travelDocuments = travelDocuments;
    }

    @OneToMany(mappedBy = "uploadedBy")
    private Collection<TravelWiseEmployeeWiseDocument> travelWisePesonalDocuments;

    public Collection<TravelWiseEmployeeWiseDocument> getTravelWisePesonalDocuments() {
        return travelWisePesonalDocuments;
    }

    public void setTravelWisePesonalDocuments(Collection<TravelWiseEmployeeWiseDocument> travelWisePesonalDocuments) {
        this.travelWisePesonalDocuments = travelWisePesonalDocuments;
    }
}
