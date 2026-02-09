package com.hrms.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class GameType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String game;
    private int slotDuration;
    private LocalTime openingHours;
    private LocalTime closingHours;
    private int maxNoOfPlayers;
    private Long slotCanBeBookedBefore;
    private boolean isInMaintenance;
    private int maxSlotPerDay;
    private int maxActiveSlotPerDay;
    @OneToMany(mappedBy = "gameType")
    @JsonIgnore
    private List<GameSlot> gameSlots;

    @OneToMany(mappedBy = "gameType")
    @JsonIgnore
    private  List<EmployeeWiseGameInterest> employeeWiseGameInterests;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
}
