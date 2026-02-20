package com.hrms.backend.dtos.requestDto.gameScheduling;

import com.hrms.backend.dtos.markers.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalTime;

@Data
public class CreateUpdateGameTypeRequestDto {
    @Positive(message = "id should be provided in update request",groups = {OnUpdate.class})
    private Long id;
    @NotBlank(message = "game name not be blank")
    @NotNull
    private String game;
    @NotNull(message = "slot duration required")
    @Positive(message = "slot duration should be positive")
    private int slotDuration;
    @NotNull(message = "opening hours required")
    private LocalTime openingHours;
    @NotNull(message = "closing hours required")
    private LocalTime closingHours;
    @Positive(message = "max No Of Players should be positive")
    @NotNull
    private int maxNoOfPlayers;
    @Positive(message = "slot booking time contraint should be positive")
    @NotNull
    private Long slotCanBeBookedBefore;
    private boolean isInMaintenance;
    @Positive(message = "max slot per day should be positive")
    @NotNull
    private int maxSlotPerDay;
    @Positive(message = "max active slot per day should be positive")
    @NotNull
    private int maxActiveSlotPerDay;
}
