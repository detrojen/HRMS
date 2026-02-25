package com.hrms.backend.services;

import com.hrms.backend.dtos.responseDtos.DashboardResponseDto;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import com.hrms.backend.services.GameSchedulingServices.SlotRequestService;
import com.hrms.backend.services.TravelServices.TravelService;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final SlotRequestService slotRequestService;
    private final EmployeeService employeeService;
    private final TravelService travelService;
    public DashboardService(
            SlotRequestService slotRequestService
            ,EmployeeService employeeService
            ,TravelService travelService
    ){
        this.employeeService = employeeService;
        this.slotRequestService = slotRequestService;
        this.travelService = travelService;
    }

    public DashboardResponseDto getDashboardData() {
        DashboardResponseDto responseDto = new DashboardResponseDto(
                slotRequestService.getActiveSlotRequests()
                , employeeService.getBirthdayPersons()
                ,employeeService.getWorkAnniversaryPersons()
                ,travelService.getUpcomingTravels()
        );
        return responseDto;
    }
}
