package com.hrms.backend.dtos.requestDto.gameScheduling;

import lombok.Data;

import java.util.List;

@Data
public class BookSlotRequestDto {
    private Long slotId;
    private List<Long> otherPlayersId;
}
