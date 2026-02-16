package com.hrms.backend.services.EmployeeServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithManagerIdDto;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeOneLevelReportResponseDto;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import com.hrms.backend.dtos.responseDtos.employee.SelfDetailResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.repositories.EmployeeRepositories.EmployeeRepository;
import com.hrms.backend.specs.EmployeeSpecs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper){
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    public Employee getReference(Long id){
        return employeeRepository.getReferenceById(id);
    }
    public List<EmployeeWithNameOnlyDto> getEmployeesByNameQuery(String nameQuery){
        Specification<Employee> hasNameSpec = EmployeeSpecs.hasName(nameQuery);
        return employeeRepository.findAll(hasNameSpec).stream().map(employee->modelMapper.map(employee,EmployeeWithNameOnlyDto.class)).collect(Collectors.toUnmodifiableList());
    }

    public EmployeeOneLevelReportResponseDto getOneLevelReport(Long employeeId){
        List<EmployeeWithManagerIdDto> employees = employeeRepository.getOneLevelReport(employeeId);
        EmployeeOneLevelReportResponseDto dto = new EmployeeOneLevelReportResponseDto(employees,employeeId);
        return dto;
    }

    public SelfDetailResponseDto getSelfDetails(){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = employeeRepository.findById(jwtInfoDto.getUserId()).orElseThrow(()->new RuntimeException("user not found"));
        SelfDetailResponseDto responseDto = modelMapper.map(employee,SelfDetailResponseDto.class);
        responseDto.setRole(employee.getRole().getRoleTitle());
        return responseDto;
    }

}
