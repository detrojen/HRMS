package com.hrms.backend.dtos.requestParamDtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class SlotRequestHistoryParamsDto {
    private Optional<String> slots = Optional.ofNullable(null);
    private Optional<LocalDate> slotsFrom = Optional.ofNullable(null);
    private Optional<LocalDate> slotsTo = Optional.ofNullable(null);
}
