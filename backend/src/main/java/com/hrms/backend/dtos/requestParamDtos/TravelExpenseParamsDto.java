package com.hrms.backend.dtos.requestParamDtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class TravelExpenseParamsDto {
    private Optional<LocalDate> dateFrom = Optional.ofNullable(null);
    private Optional<LocalDate> dateTo = Optional.ofNullable(null);
    private Optional<Long> employeeId = Optional.ofNullable(null);
    private Optional<String> category = Optional.ofNullable(null);
}
