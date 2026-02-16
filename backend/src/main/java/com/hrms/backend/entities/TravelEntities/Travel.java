package com.hrms.backend.entities.TravelEntities;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 8000)
    private String descripton;

    private int maxReimbursementAmountPerDay;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalDate lastDateToSubmitExpense;

    @ManyToOne
    @JoinColumn()
    private Employee initiatedBy;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
    @OneToMany(mappedBy = "travel")
    private Collection<TravelDocument> travelDocuments;

    @OneToMany(mappedBy = "travel")
    private Collection<TravelWiseEmployee> travelWiseEmployees;

    @OneToMany(mappedBy = "travel")
    private Collection<TravelWiseExpense> expenses;
    @OneToMany(mappedBy = "travel")
    private Collection<TravelWiseEmployeeWiseDocument> employeeDocuments;

}
