package com.hrms.backend.services;

import com.hrms.backend.entities.GameSlot;
import com.hrms.backend.entities.SlotRequest;
import com.hrms.backend.entities.SlotRequestWiseEmployee;
import com.hrms.backend.repositories.SlotRequestWiseEmployeeRepository;
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
