package com.hrms.backend.services.GameSchedulingServices;

import com.hrms.backend.dtos.EmployeeWiseGameTypeUsageDto;
import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.responseDtos.EmployeeWithNameOnlyDto;
import com.hrms.backend.dtos.responseDtos.EmployeeWiseGameInterestResponseDto;
import com.hrms.backend.entities.GameSchedulingEntities.EmployeeWiseGameInterest;
import com.hrms.backend.repositories.GameSchedulingRepositories.EmployeeWiseGameInterestRepository;
import com.hrms.backend.specs.InterestedEmployeeSpec;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public List<EmployeeWithNameOnlyDto> getEmployeeOfGameIntersetOf(Long gameTypeId, String nameLike){
        Specification<EmployeeWiseGameInterest> specs = InterestedEmployeeSpec.getSpecForIsInterestedAndByGameTypeIdAndNameLike(gameTypeId ,nameLike);
        List<EmployeeWiseGameInterest> employeeWiseGameInterests = employeeWiseGameInterestRepository.findAll(specs);
        List<EmployeeWithNameOnlyDto> employees = employeeWiseGameInterests.stream().map(e->modelMapper.map(e.getEmployee(), EmployeeWithNameOnlyDto.class)).collect(Collectors.toUnmodifiableList());
        return employees;
    }

    public List<EmployeeWiseGameInterestResponseDto> getEmployeeWiseGameInterests(){
        JwtInfoDto jwtInfo = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<EmployeeWiseGameInterest> games = employeeWiseGameInterestRepository.findAllByEmployee_Id(jwtInfo.getUserId());
        return games.stream().map(game->modelMapper.map(game,EmployeeWiseGameInterestResponseDto.class)).collect(Collectors.toUnmodifiableList());
    }

    public EmployeeWiseGameInterestResponseDto updateInterest(Long gameTypeId,boolean isInterested){
        JwtInfoDto jwtInfo = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeWiseGameInterest gameInterest =  employeeWiseGameInterestRepository.findByEmployee_IdAndGameType_Id(jwtInfo.getUserId(), gameTypeId);
        gameInterest.setInterested(isInterested);
        gameInterest =  employeeWiseGameInterestRepository.save(gameInterest);
        return modelMapper.map(gameInterest,EmployeeWiseGameInterestResponseDto.class);
    }
}
