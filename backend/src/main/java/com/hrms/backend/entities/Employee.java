package com.hrms.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

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
    private Employee manager;
    private String designation;
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
    private String email;
    private String password;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
}
