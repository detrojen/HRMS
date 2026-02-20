package com.hrms.backend.dtos.requestDto.gameScheduling;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateGameInterestRequestDto {
    @NotNull
    @Positive
    private final Long gameTypeId;
    @NotNull
    private final boolean isInterested;
}
