package com.hrms.backend.services.TravelServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.travel.AddUpdateTravelExpenseRequestDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelExpenseResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.ExpenseCategory;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelWiseExpense;
import com.hrms.backend.repositories.TravelRepositories.ExpenseCategoryRepository;
import com.hrms.backend.repositories.TravelRepositories.TravelWiseExpenseRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelWiseExpenseService {
    private final TravelWiseExpenseRepository travelWiseExpenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;

    public TravelWiseExpenseService(
            TravelWiseExpenseRepository travelWiseExpenseRepository
            ,ExpenseCategoryRepository expenseCategoryRepository
            ,ModelMapper modelMapper,
            EmployeeService employeeService){
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.travelWiseExpenseRepository = travelWiseExpenseRepository;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
    }

    public TravelExpenseResponseDto createTravelExpense(Travel travel, AddUpdateTravelExpenseRequestDto requestDto, String recieptPath){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = employeeService.getReference(jwtInfoDto.getUserId());
        TravelWiseExpense travelWiseExpense = modelMapper.map(requestDto,TravelWiseExpense.class);
        travelWiseExpense.setTravel(travel);
        travelWiseExpense.setEmployee(employee);
        travelWiseExpense.setReciept(recieptPath);
        ExpenseCategory category = expenseCategoryRepository.getReferenceById(requestDto.getCategoryId());
        travelWiseExpense.setCategory(category);
        travelWiseExpense = travelWiseExpenseRepository.save(travelWiseExpense);
        return modelMapper.map(travelWiseExpense, TravelExpenseResponseDto.class);
    }

    public TravelExpenseResponseDto updateTravelExpense(Travel travel, AddUpdateTravelExpenseRequestDto requestDto, String recieptPath){
        TravelWiseExpense travelWiseExpense = travelWiseExpenseRepository.findById(requestDto.getId()).orElseThrow(()->new RuntimeException("expense not found"));
        if(travelWiseExpense.getReviewedBy() != null){
            throw new RuntimeException("you can not update expense once it reviewed");
        }
        ExpenseCategory category = expenseCategoryRepository.getReferenceById(requestDto.getCategoryId());
        travelWiseExpense.setCategory(category);
        travelWiseExpense.setReciept(recieptPath);
        travelWiseExpense.setAskedAmout(requestDto.getAskedAmout());
        travelWiseExpense.setDateOfExpense(requestDto.getDateOfExpense());
        travelWiseExpense = travelWiseExpenseRepository.save(travelWiseExpense);
        return modelMapper.map(travelWiseExpense, TravelExpenseResponseDto.class);
    }

    public List<TravelExpenseResponseDto> getExpenses(Long travelId){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TravelWiseExpense> expenses = travelWiseExpenseRepository.findAllByEmployee_IdAndTravel_Id(jwtInfoDto.getUserId(),travelId);
        return expenses.stream().map(expense -> modelMapper.map(expense, TravelExpenseResponseDto.class)).collect(Collectors.toUnmodifiableList());
    }

}
