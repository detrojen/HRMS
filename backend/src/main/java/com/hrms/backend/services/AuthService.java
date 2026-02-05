package com.hrms.backend.services;

import com.hrms.backend.entities.Employee;
import com.hrms.backend.exceptions.InvalidCredentialsException;
import com.hrms.backend.repositories.EmployeeRepository;
import com.hrms.backend.specs.LoginSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
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
        return _jwtService.generateToken(employee.getId(),employee.getEmail(),employee.getRole().getRoleTitle());
    }
}
