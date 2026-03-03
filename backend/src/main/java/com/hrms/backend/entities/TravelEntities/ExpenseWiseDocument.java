package com.hrms.backend.entities.TravelEntities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class ExpenseWiseDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String documentPath;
    @ManyToOne()
    private TravelWiseExpense expense;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;

    public ExpenseWiseDocument(){}
    public ExpenseWiseDocument(String documentPath,TravelWiseExpense expense){
        this.documentPath = documentPath;
        this.expense = expense;
    }
}
