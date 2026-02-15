package com.hrms.backend.entities.Notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class NotificationTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private String type;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
    @OneToMany(mappedBy = "template")
    @JsonIgnore
    private Collection<Notification> notifications;

}
