package com.hrms.backend.controllers;

import com.hrms.backend.dtos.responseDtos.DashboardResponseDto;
import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
    private final DashboardService dashboardService;
    @Autowired
    public DashboardController(DashboardService dashboardService){
        this.dashboardService = dashboardService;
    }
    @GetMapping("/dashboard")
    public ResponseEntity<GlobalResponseDto<DashboardResponseDto>> getDashBoardData(){
        DashboardResponseDto responseDto = dashboardService.getDashboardData();
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto,"Welcome ...!")
        );
    }
}
