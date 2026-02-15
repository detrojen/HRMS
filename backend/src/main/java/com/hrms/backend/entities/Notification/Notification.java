package com.hrms.backend.entities.Notification;

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
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private NotificationTemplate template;

    @ManyToOne()
    private Employee employee;

    private boolean isRead;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
}
