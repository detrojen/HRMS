package com.hrms.backend.dtos.requestDto.gameScheduling;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class BookSlotRequestDto {
    @NotNull
    @Positive
    private Long slotId;
    private List<Long> otherPlayersId;
}
