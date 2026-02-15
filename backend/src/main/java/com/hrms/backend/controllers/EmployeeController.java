package com.hrms.backend.controllers;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.responseDtos.EmployeeOneLevelReportResponseDto;
import com.hrms.backend.dtos.responseDtos.EmployeeWithNameOnlyDto;
import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<GlobalResponseDto<List<EmployeeWithNameOnlyDto>>> getEmployees(@RequestParam String nameLike){
        var employees = employeeService.getEmployeesByNameQuery(nameLike);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(employees)
        );
    }

    @GetMapping("/employees/one-level-report")
    public ResponseEntity<GlobalResponseDto<EmployeeOneLevelReportResponseDto>> oneLevelReport(@RequestParam(defaultValue = "0") Long employeeId){
        EmployeeOneLevelReportResponseDto responseDto;
        if(employeeId == 0){
            JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            responseDto = employeeService.getOneLevelReport(jwtInfoDto.getUserId());
        }else{
            responseDto = employeeService.getOneLevelReport(employeeId);
        }
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }
}
