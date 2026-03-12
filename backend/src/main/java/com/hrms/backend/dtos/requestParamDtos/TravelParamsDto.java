package com.hrms.backend.dtos.requestParamDtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class TravelParamsDto {
    private Optional<LocalDate> startDate = Optional.ofNullable(null);
    private Optional<LocalDate> endDate = Optional.ofNullable(null);
    private Optional<Long[]> employees = Optional.ofNullable(null);
    private Optional<String> title = Optional.ofNullable(null);
    private Optional<String> status = Optional.ofNullable(null);
}
