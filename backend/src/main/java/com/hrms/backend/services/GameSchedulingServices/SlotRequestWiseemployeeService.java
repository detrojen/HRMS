package com.hrms.backend.services.GameSchedulingServices;

import com.hrms.backend.entities.GameSchedulingEntities.SlotRequest;
import com.hrms.backend.entities.GameSchedulingEntities.SlotRequestWiseEmployee;
import com.hrms.backend.repositories.GameSchedulingRepositories.SlotRequestWiseEmployeeRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlotRequestWiseemployeeService {
    private final SlotRequestWiseEmployeeRepository slotRequestWiseEmployeeRepository;
    private final EmployeeService employeeService;

    @Autowired
    public SlotRequestWiseemployeeService(SlotRequestWiseEmployeeRepository slotRequestWiseEmployeeRepository, EmployeeService employeeService){
        this.slotRequestWiseEmployeeRepository = slotRequestWiseEmployeeRepository;
        this.employeeService = employeeService;
    }

    public SlotRequestWiseEmployee mapSlotsToEmployee(Long id, SlotRequest slot){
        SlotRequestWiseEmployee slotRequestWiseEmployee = new SlotRequestWiseEmployee();
        slotRequestWiseEmployee.setSlotRequest(slot);
        slotRequestWiseEmployee.setEmployee(employeeService.getReference(id));
        return slotRequestWiseEmployeeRepository.save(slotRequestWiseEmployee);
    }


}
