package com.hrms.backend.entities.GameSchedulingEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Data
public class EmployeeWiseGameInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JsonIgnore
    private Employee employee;

    @ManyToOne()
    @JsonIgnore
    private GameType gameType;

    private boolean isInterested;

    private int noOfSlotConsumed;

    private int currentCyclesSlotConsumed;

    private boolean isBlocked;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;

}
