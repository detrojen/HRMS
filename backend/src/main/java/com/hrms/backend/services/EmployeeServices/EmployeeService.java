package com.hrms.backend.services.EmployeeServices;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.repositories.EmployeeRepositories.EmployeeRepository;
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

    public Employee getReference(Long id){
        return _employeeRepository.getReferenceById(id);
    }

//    public Employee getEmployeeWithGameUsages(Long employeeId,Long gameTypeId){
//        Specification<Employee> specs = InterestedGameSpecs.getGameInterestByEmployeeIdAndGameTypeID(employeeId,gameTypeId);
//        var employees = _employeeRepository.findAll(specs);
//        return employees.get(0);
//    }
}
