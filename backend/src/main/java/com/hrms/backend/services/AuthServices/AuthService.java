package com.hrms.backend.services.AuthServices;

import com.hrms.backend.dtos.responseDtos.employee.RoleResponseDto;
import com.hrms.backend.dtos.responseDtos.employee.SelfDetailResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.exceptions.InvalidCredentialsException;
import com.hrms.backend.repositories.EmployeeRepositories.EmployeeRepository;
import com.hrms.backend.specs.LoginSpecs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuthService {
    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;
    @Autowired
    public AuthService(EmployeeRepository employeeRepository, JwtService jwtService){
        this.employeeRepository = employeeRepository;
        this.jwtService = jwtService;
    }

    public String login(String email, String password){
        Employee employee = employeeRepository.getEmployeeByEmail(email).orElseThrow(()-> new InvalidCredentialsException());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if(passwordEncoder.matches(password,employee.getPassword())){
            throw new InvalidCredentialsException();
        }
        log.info("{} has logged in.", employee.getFullName());
        return jwtService.generateToken(employee.getId(),employee.getEmail(),employee.getRole().get(0).getRoleTitle(),employee.getFullName());
    }
    public String reLogin(String email,String role){
        Employee employee = employeeRepository.getEmployeeByEmail(email).orElse(null);
        if(employee == null){
            throw new InvalidCredentialsException();
        }
        log.info("{} has been reauthenticate by refresh token",employee.getFullName());
        return jwtService.generateToken(employee.getId(),employee.getEmail(),role,employee.getFullName());
    }

    public String login(SelfDetailResponseDto dto){
        return jwtService.generateToken(dto.getId(),dto.getEmail(),dto.getRole(),dto.getFirstName()+ " " + dto.getLastName());
    }
}
