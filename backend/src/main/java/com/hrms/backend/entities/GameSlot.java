package com.hrms.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
public class GameSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date slotDate;
    private LocalTime startsFrom;
    private LocalTime endsAt;
    private boolean isAvailable;

    @ManyToOne()
    private GameType gameType;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;

    @OneToMany(mappedBy = "gameSlot")
    private Collection<SlotRequest> slotRequests;

}
