package com.hrms.backend.services;

import com.hrms.backend.entities.Employee;
import com.hrms.backend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository _employeeRepository;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        _employeeRepository = employeeRepository;
    }

    public Employee getEmployeeById(Long id){
        return _employeeRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }
}
