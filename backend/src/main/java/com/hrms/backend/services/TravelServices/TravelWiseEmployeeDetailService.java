package com.hrms.backend.services.TravelServices;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelWiseEmployee;
import com.hrms.backend.repositories.TravelRepositories.TravelWiseEmployeeDetailRepsitory;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TravelWiseEmployeeDetailService {
    private final TravelWiseEmployeeDetailRepsitory travelWiseEmployeeDetailRepsitory;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    public TravelWiseEmployeeDetailService(TravelWiseEmployeeDetailRepsitory travelWiseEmployeeDetailRepsitory, EmployeeService employeeService,ModelMapper modelMapper){
        this.travelWiseEmployeeDetailRepsitory = travelWiseEmployeeDetailRepsitory;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
    }

    public EmployeeWithNameOnlyDto addEmployeeToTravel(Travel travel,Long employeeId){
        Employee employee = employeeService.getEmployeeById(employeeId);
        TravelWiseEmployee travelWiseEmployee = new TravelWiseEmployee();
        travelWiseEmployee.setEmployee(employee);
        travelWiseEmployee.setTravel(travel);
        travelWiseEmployeeDetailRepsitory.save(travelWiseEmployee);
        return modelMapper.map(employee,EmployeeWithNameOnlyDto.class);
    }

}
