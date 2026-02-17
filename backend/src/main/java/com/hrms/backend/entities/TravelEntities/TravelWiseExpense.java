package com.hrms.backend.entities.TravelEntities;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class TravelWiseExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @ManyToOne
    @JoinColumn()
    private ExpenseCategory category;
    private String description;
    private int askedAmount;
    private int aprrovedAmount;
    private String remark;
    private String reciept;
    private LocalDate dateOfExpense;
    private String status;
    @ManyToOne
    @JoinColumn()
    private Employee employee;
    @ManyToOne
    @JoinColumn()
    private Employee reviewedBy;
    @ManyToOne
    @JoinColumn()
    private Travel travel;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
}
