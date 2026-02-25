package com.hrms.backend.dtos.requestDto.travel;

import lombok.Data;

import java.util.List;

@Data
public class AddEmployeesToTravelRequestDto {
    private List<Long> employeeIds;

}
