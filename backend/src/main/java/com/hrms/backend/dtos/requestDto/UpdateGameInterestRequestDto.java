package com.hrms.backend.dtos.requestDto;

import lombok.Data;

@Data
public class UpdateGameInterestRequestDto {
    private final Long gameTypeId;
    private final boolean isInterested;
}
