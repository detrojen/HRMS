package com.hrms.backend.entities.TravelEntities;

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
public class TravelDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String type;
    private String documentPath;
    private String description;
    @ManyToOne
    @JoinColumn()
    private Employee uploadedBy;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn()
    @JsonIgnore
    private Travel travel;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;

}
