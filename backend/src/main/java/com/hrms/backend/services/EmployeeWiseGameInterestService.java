package com.hrms.backend.services;

import com.hrms.backend.dtos.EmployeeWiseGameTypeUsageDto;
import com.hrms.backend.dtos.responseDtos.EmployeeResponseDto;
import com.hrms.backend.entities.Employee;
import com.hrms.backend.entities.EmployeeWiseGameInterest;
import com.hrms.backend.repositories.EmployeeWiseGameInterestRepository;
import com.hrms.backend.specs.InterestedEmployeeSpec;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeWiseGameInterestService {
    private final EmployeeWiseGameInterestRepository employeeWiseGameInterestRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public EmployeeWiseGameInterestService(EmployeeWiseGameInterestRepository employeeWiseGameInterestRepository, ModelMapper modelMapper){
        this.employeeWiseGameInterestRepository = employeeWiseGameInterestRepository;
        this.modelMapper= modelMapper;
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

    public List<EmployeeResponseDto> getEmployeeOfGameIntersetOf(Long gameTypeId, String nameLike){
        Specification<EmployeeWiseGameInterest> specs = InterestedEmployeeSpec.getSpecForIsInterestedAndByGameTypeIdAndNameLike(gameTypeId ,nameLike);
        List<EmployeeWiseGameInterest> employeeWiseGameInterests = employeeWiseGameInterestRepository.findAll(specs);
        List<EmployeeResponseDto> employees = employeeWiseGameInterests.stream().map(e->modelMapper.map(e.getEmployee(),EmployeeResponseDto.class)).collect(Collectors.toUnmodifiableList());
        return employees;
    }
}
