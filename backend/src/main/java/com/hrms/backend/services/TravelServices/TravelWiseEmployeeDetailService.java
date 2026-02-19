package com.hrms.backend.services.TravelServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelWiseEmployee;
import com.hrms.backend.repositories.TravelRepositories.TravelWiseEmployeeDetailRepsitory;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public boolean addAskedAmount(Long travelId , int askedAmount){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TravelWiseEmployee employeeDetail  = travelWiseEmployeeDetailRepsitory.findByTravel_IdAndEmployee_Id(travelId,jwtInfoDto.getUserId());
        employeeDetail.setTotalsAskedAmount(employeeDetail.getTotalsAskedAmount()+askedAmount);
        travelWiseEmployeeDetailRepsitory.save(employeeDetail);
        return true;
    }
    public boolean updateAskedAmount(Long travelId  ,int oldASkedAmount, int askedAmount){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TravelWiseEmployee employeeDetail  = travelWiseEmployeeDetailRepsitory.findByTravel_IdAndEmployee_Id(travelId,jwtInfoDto.getUserId());
        employeeDetail.setTotalsAskedAmount(employeeDetail.getTotalsAskedAmount() - oldASkedAmount+askedAmount);
        travelWiseEmployeeDetailRepsitory.save(employeeDetail);
        return true;
    }


    public boolean updateReimbursedAmount(Long travelId,Long employeeId  ,int oldReimbursedAmount, int reimbursedAmount){
        TravelWiseEmployee employeeDetail  = travelWiseEmployeeDetailRepsitory.findByTravel_IdAndEmployee_Id(travelId,employeeId);
        employeeDetail.setReimbursedAmount(employeeDetail.getReimbursedAmount() - oldReimbursedAmount+reimbursedAmount);
        travelWiseEmployeeDetailRepsitory.save(employeeDetail);
        return true;
    }

}
