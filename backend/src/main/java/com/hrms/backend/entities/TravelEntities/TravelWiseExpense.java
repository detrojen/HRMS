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
    private String expenseType;
    private int reimbursedAmout;
    private int askedAmout;
    private int aprrovedAmout;
    private String remark;
    private String reciept;
    private LocalDate dateOfExpense;
    @ManyToOne
    @JoinColumn()
    private Employee employee;
    @ManyToOne
    @JoinColumn()
    private Employee aprrovedBy;
    @ManyToOne
    @JoinColumn()
    private Travel travel;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
}
