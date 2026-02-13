package com.hrms.backend.entities.PostEntities;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    private String attachmentPath;
    private String tags;

    @ManyToOne()
    private Employee createdBy;

    private String remarkForDelete;
    @ManyToOne()
    private Employee deletedBy;
    private boolean isDeleted;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
}
