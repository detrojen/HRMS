package com.hrms.backend.services.AuthServices;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.exceptions.InvalidCredentialsException;
import com.hrms.backend.repositories.EmployeeRepositories.EmployeeRepository;
import com.hrms.backend.specs.LoginSpecs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    private final EmployeeRepository _employeeRepository;
    private final JwtService _jwtService;
    @Autowired
    public AuthService(EmployeeRepository employeeRepository, JwtService jwtService){
        _employeeRepository = employeeRepository;
        _jwtService = jwtService;
    }

    public String login(String email, String password){
        Specification<Employee> loginSpec = LoginSpecs.getUserByEmailAndPassword(email,password);
        Employee employee = _employeeRepository.getEmployeeByEmailAndPassword(email,password).orElse(null);
        if(employee == null){
            throw new InvalidCredentialsException();
        }
        log.info("{}has logged in.", employee.getFullName());
        return _jwtService.generateToken(employee.getId(),employee.getEmail(),employee.getRole().getRoleTitle(),employee.getFullName());
    }
    public String login(String email){
        Employee employee = _employeeRepository.getEmployeeByEmail(email).orElse(null);
        if(employee == null){
            throw new InvalidCredentialsException();
        }
        log.info("{} has been reauthenticate by refresh token");
        return _jwtService.generateToken(employee.getId(),employee.getEmail(),employee.getRole().getRoleTitle(),employee.getFullName());
    }
}
