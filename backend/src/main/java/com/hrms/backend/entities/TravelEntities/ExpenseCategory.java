package com.hrms.backend.entities.TravelEntities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
public class ExpenseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String category;
    @OneToMany(mappedBy = "category")
    private Collection<TravelWiseExpense> expenses;

}
