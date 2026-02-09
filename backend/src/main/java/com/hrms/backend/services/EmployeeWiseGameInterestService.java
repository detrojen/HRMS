package com.hrms.backend.services;

import com.hrms.backend.dtos.EmployeeWiseGameTypeUsageDto;
import com.hrms.backend.entities.EmployeeWiseGameInterest;
import com.hrms.backend.repositories.EmployeeWiseGameInterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeWiseGameInterestService {
    private final EmployeeWiseGameInterestRepository employeeWiseGameInterestRepository;

    @Autowired
    public EmployeeWiseGameInterestService(EmployeeWiseGameInterestRepository employeeWiseGameInterestRepository){
        this.employeeWiseGameInterestRepository = employeeWiseGameInterestRepository;
    }

    public EmployeeWiseGameTypeUsageDto getEmployyeGameUsage(Long employeeId, Long gameTypeId){
        return this.employeeWiseGameInterestRepository.getEmployyeGameUsage(employeeId,gameTypeId);
    }

    public void addConsumedSlotCount(Long employeeId, Long gameTypeId){
        EmployeeWiseGameInterest gameInterest =  employeeWiseGameInterestRepository.findByEmployee_IdAndGameType_Id(employeeId,gameTypeId);
        gameInterest.setNoOfSlotConsumed(gameInterest.getNoOfSlotConsumed()+1);
        gameInterest.setCurrentCyclesSlotConsumed(gameInterest.getCurrentCyclesSlotConsumed()+1);
        employeeWiseGameInterestRepository.save(gameInterest);
    }

    public void deductConsumedSlotCount(Long employeeId, Long gameTypeId){
        EmployeeWiseGameInterest gameInterest =  employeeWiseGameInterestRepository.findByEmployee_IdAndGameType_Id(employeeId,gameTypeId);
        gameInterest.setNoOfSlotConsumed(gameInterest.getNoOfSlotConsumed()-1);
        gameInterest.setCurrentCyclesSlotConsumed(gameInterest.getCurrentCyclesSlotConsumed()-1);
        employeeWiseGameInterestRepository.save(gameInterest);
    }
}
