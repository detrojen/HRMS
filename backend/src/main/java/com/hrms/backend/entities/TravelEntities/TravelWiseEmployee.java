package com.hrms.backend.entities.TravelEntities;


import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class TravelWiseEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @ColumnDefault("0")
    private int totalsAskedAmount;

    @ManyToOne
    @JoinColumn()
    private Travel travel;

    @ManyToOne
    @JoinColumn()
    private Employee employee;
    @ColumnDefault("0")
    private int reimbursedAmount;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
}
