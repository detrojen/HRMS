package com.hrms.backend.entities.JobListingEntities;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;


@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String applicantsName;
    private String applicantsEmail;
    private String applicantsPhone;
    private String details;
    private String cvPath;
    @ColumnDefault(value = "'pending'")
    private String status;
    @ColumnDefault(value = "''")
    private String remark;
    @ManyToOne
    private Employee reviewedBy;
    @ManyToOne()
    private Employee referedBy;
    @ManyToOne()
    private Job job;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
