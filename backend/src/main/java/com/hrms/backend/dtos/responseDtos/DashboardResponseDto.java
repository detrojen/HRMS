package com.hrms.backend.dtos.responseDtos;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import com.hrms.backend.dtos.responseDtos.gameSheduling.SlotRequsetResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelMinDetailResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DashboardResponseDto {
    private List<SlotRequsetResponseDto> upcomingSlots;
    private List<EmployeeMinDetailsDto> todayBirthdayPersons;
    private List<EmployeeMinDetailsDto> todayWorkAnniversaryPersons;
    private List<TravelMinDetailResponseDto> upcomingTravels;
}
