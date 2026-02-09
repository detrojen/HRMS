package com.hrms.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SlotRequestWiseEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private SlotRequest slotRequest;

    @ManyToOne()
    private Employee employee;
}
