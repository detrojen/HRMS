package com.hrms.backend.entities.EmployeeEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleTitle;
    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<Employee> employees;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
}
