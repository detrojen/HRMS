package com.hrms.backend.services.TravelServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.ReviewTravelExpenseRequestDto;
import com.hrms.backend.dtos.requestDto.travel.AddUpdateTravelExpenseRequestDto;
import com.hrms.backend.dtos.requestParamDtos.TravelExpenseParamsDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelExpenseResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.ExpenseCategory;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelWiseExpense;
import com.hrms.backend.repositories.TravelRepositories.ExpenseCategoryRepository;
import com.hrms.backend.repositories.TravelRepositories.TravelWiseExpenseRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import com.hrms.backend.specs.TravelExpenseSpecs;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
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
        travelWiseExpense.setStatus("pending");
        ExpenseCategory category = expenseCategoryRepository.getReferenceById(requestDto.getCategoryId());
        travelWiseExpense.setCategory(category);
        travelWiseExpense = travelWiseExpenseRepository.save(travelWiseExpense);
        return modelMapper.map(travelWiseExpense, TravelExpenseResponseDto.class);
    }

    public TravelExpenseResponseDto updateTravelExpense(Travel travel, AddUpdateTravelExpenseRequestDto requestDto, String recieptPath){
        TravelWiseExpense travelWiseExpense = travelWiseExpenseRepository.findById(requestDto.getId()).orElseThrow(()->new RuntimeException("expense not found"));
        if(travelWiseExpense.getStatus().equals("approved") || travelWiseExpense.getStatus().equals("rejected")){
            throw new RuntimeException("you can not update expense once it reviewed");
        }
        ExpenseCategory category = expenseCategoryRepository.getReferenceById(requestDto.getCategoryId());
        travelWiseExpense.setCategory(category);
        travelWiseExpense.setReciept(recieptPath);
        travelWiseExpense.setAskedAmount(requestDto.getAskedAmount());
        travelWiseExpense.setDateOfExpense(requestDto.getDateOfExpense());
        travelWiseExpense.setDescription(requestDto.getDescription());
        travelWiseExpense = travelWiseExpenseRepository.save(travelWiseExpense);
        return modelMapper.map(travelWiseExpense, TravelExpenseResponseDto.class);
    }

    public List<TravelExpenseResponseDto> getExpenses(Long travelId){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TravelWiseExpense> expenses = travelWiseExpenseRepository.findAllByEmployee_IdAndTravel_Id(jwtInfoDto.getUserId(),travelId);
        return expenses.stream().map(expense -> modelMapper.map(expense, TravelExpenseResponseDto.class)).collect(Collectors.toUnmodifiableList());
    }

    public List<TravelExpenseResponseDto> getExpenses(Long travelId, TravelExpenseParamsDto params){
        Specification<TravelWiseExpense> specs = TravelExpenseSpecs.hasTravel(travelId);
        if(!params.getCategory().isEmpty()){
            specs = specs.and(TravelExpenseSpecs.hasCategory(params.getCategory().get()));
        }
        if(!params.getDateFrom().isEmpty()){
            specs = specs.and(TravelExpenseSpecs.hasDateFrom(params.getDateFrom().get()));
        }
        if(!params.getDateTo().isEmpty()){
            specs = specs.and(TravelExpenseSpecs.hasDateTo(params.getDateTo().get()));
        }
        if(!params.getEmployeeId().isEmpty()){
            specs = specs.and(TravelExpenseSpecs.hasEmployee(params.getEmployeeId().get()));
        }
        List<TravelWiseExpense> expenses = travelWiseExpenseRepository.findAll(specs);
        return expenses.stream().map(expense->modelMapper.map(expense,TravelExpenseResponseDto.class)).collect(Collectors.toUnmodifiableList());
    }

    public TravelExpenseResponseDto reviewExpense(ReviewTravelExpenseRequestDto requestDto){
        TravelWiseExpense expense = travelWiseExpenseRepository.findById(requestDto.getId()).orElseThrow(()->new RuntimeException("not found expense"));
        if(requestDto.getAprrovedAmount() > expense.getAskedAmount()){
            throw new RuntimeException("Invalid action: can to aprrove greater then asked");
        }
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = employeeService.getReference(jwtInfoDto.getUserId());
        expense.setAprrovedAmount(requestDto.getAprrovedAmount());
        expense.setRemark(requestDto.getRemark());
        expense.setReviewedBy(employee);
        expense.setStatus(expense.getAprrovedAmount() == 0?"rejected":"reviewed");
        expense = travelWiseExpenseRepository.save(expense);
        return modelMapper.map(expense, TravelExpenseResponseDto.class);
    }

}
