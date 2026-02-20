package com.hrms.backend.entities.GameSchedulingEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class GameSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate slotDate;
    private LocalTime startsFrom;
    private LocalTime endsAt;
    private boolean isAvailable;

    @ManyToOne()
    private GameType gameType;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    @OneToMany(mappedBy = "gameSlot")
    @JsonIgnore
    private Collection<SlotRequest> slotRequests;
}
