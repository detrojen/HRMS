package com.hrms.backend.services.EmployeeServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.responseDtos.employee.*;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.exceptions.ItemNotFoundExpection;
import com.hrms.backend.repositories.EmployeeRepositories.EmployeeRepository;
import com.hrms.backend.repositories.EmployeeRepositories.RoleRepository;
import com.hrms.backend.specs.EmployeeSpecs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


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
        return employeeRepository.findById(id).orElseThrow(()-> new ItemNotFoundExpection("employee not found."));
    }

    public Employee getReference(Long id){
        return employeeRepository.getReferenceById(id);
    }
    public List<EmployeeWithNameOnlyDto> getEmployeesByNameQuery(String nameQuery){
        Specification<Employee> hasNameSpec = EmployeeSpecs.hasName(nameQuery);
        return employeeRepository.findAll(hasNameSpec).stream().map(employee->modelMapper.map(employee,EmployeeWithNameOnlyDto.class)).toList();
    }

    public EmployeeOneLevelReportResponseDto getOneLevelReport(Long employeeId){
        List<EmployeeWithManagerIdDto> employees = employeeRepository.getOneLevelReport(employeeId);
        EmployeeOneLevelReportResponseDto dto = new EmployeeOneLevelReportResponseDto(employees,employeeId);
        return dto;
    }

    public SelfDetailResponseDto getSelfDetails(){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = employeeRepository.findById(jwtInfoDto.getUserId()).orElseThrow(()->new ItemNotFoundExpection("user not found"));
        SelfDetailResponseDto responseDto = new SelfDetailResponseDto();//modelMapper.map(employee,SelfDetailResponseDto.class);
        responseDto.setFirstName(employee.getFirstName());
        responseDto.setLastName(employee.getLastName());
        responseDto.setEmail(employee.getEmail());
        responseDto.setId(employee.getId());
        responseDto.setRole(jwtInfoDto.getRoleTitle());
        responseDto.setRoles(employeeRepository.getRolesOfEmployee(jwtInfoDto.getUserId()));
        return responseDto;
    }

    public List<EmployeeMinDetailsDto> getBirthdayPersons(){
        List<Employee> employees = employeeRepository.findBy(EmployeeSpecs.hasBirthday(),q->q.all());
        return employees.stream().map(employee->modelMapper.map(employee,EmployeeMinDetailsDto.class)).toList();
    }
    public List<EmployeeMinDetailsDto> getWorkAnniversaryPersons(){
        List<EmployeeMinDetailsDto> employees = employeeRepository.findBy(EmployeeSpecs.hasWorkAnniversary(),q->q.stream().map(employee -> modelMapper.map(employee,EmployeeMinDetailsDto.class)).toList());
        return employees;
    }

    public List<EmployeeMinDetailsDto> getEmployeeWhoHr(){
        return employeeRepository.getEmployeeWhoHr();
    }

    public SelfDetailResponseDto switchRole(Long roleId){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        employeeRepository.existsByIdAndRole_Id(jwtInfoDto.getUserId(),roleId);
        Employee employee = employeeRepository.findById(jwtInfoDto.getUserId()).orElseThrow(()->new ItemNotFoundExpection("user not found"));
        SelfDetailResponseDto responseDto = new SelfDetailResponseDto();//modelMapper.map(employee,SelfDetailResponseDto.class);
        responseDto.setFirstName(employee.getFirstName());
        responseDto.setLastName(employee.getLastName());
        responseDto.setEmail(employee.getEmail());
        responseDto.setId(employee.getId());
        List<RoleResponseDto> roles = employeeRepository.getRolesOfEmployee(jwtInfoDto.getUserId());

        for(RoleResponseDto role : roles){
            if(role.getId().equals(roleId)){
                responseDto.setRole(role.getRoleTitle());
                break;
            }
        }
        responseDto.setRoles(roles);
        return responseDto;
    }
}
