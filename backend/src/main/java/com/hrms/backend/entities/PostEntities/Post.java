package com.hrms.backend.entities.PostEntities;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Data
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
