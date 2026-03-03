package com.hrms.backend.entities.TravelEntities;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Employee reviewedBy;

    @OneToMany(mappedBy = "expense")
    private List<ExpenseWiseDocument> proofs;

    @ManyToOne(fetch = FetchType.LAZY)
    private Travel travel;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
}
