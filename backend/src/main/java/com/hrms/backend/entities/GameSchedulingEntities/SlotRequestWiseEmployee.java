package com.hrms.backend.entities.GameSchedulingEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class SlotRequestWiseEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JsonIgnore
    private SlotRequest slotRequest;

    @ManyToOne()
    private Employee employee;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
}
