package com.hrms.backend.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
public class EmployeeWiseGameInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private Employee employee;

    @ManyToOne()
    private GameType gameType;

    private boolean isInterested;

    private int noOfSlotConsumed;

    private boolean isBlocked;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;

}
