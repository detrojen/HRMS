package com.hrms.backend.dtos.requestDto;

import lombok.Data;

import java.util.List;

@Data
public class BookSlotRequestDto {
    private Long slotId;
    private Long[] otherPlayersId;
}
