package com.hrms.backend.services.TravelServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.ReviewTravelExpenseRequestDto;
import com.hrms.backend.dtos.requestDto.travel.AddUpdateTravelExpenseRequestDto;
import com.hrms.backend.dtos.requestParamDtos.TravelExpenseParamsDto;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelExpenseResponseDto;
import com.hrms.backend.emailTemplates.TravelAndExpenseEmailTemplates;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.ExpenseCategory;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelWiseExpense;
import com.hrms.backend.exceptions.InvalidActionException;
import com.hrms.backend.repositories.TravelRepositories.ExpenseCategoryRepository;
import com.hrms.backend.repositories.TravelRepositories.TravelWiseExpenseRepository;
import com.hrms.backend.services.EmailServices.EmailService;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import com.hrms.backend.services.NotificationServices.NotificationService;
import com.hrms.backend.specs.TravelExpenseSpecs;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TravelWiseExpenseService {
    private final TravelWiseExpenseRepository travelWiseExpenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final EmailService emailService;
    private final NotificationService notificationService;
    private final TravelWiseEmployeeDetailService travelWiseEmployeeDetailService;

    public TravelWiseExpenseService(
            TravelWiseExpenseRepository travelWiseExpenseRepository
            ,ExpenseCategoryRepository expenseCategoryRepository
            ,ModelMapper modelMapper
            ,EmployeeService employeeService
            ,NotificationService notificationService
            ,EmailService emailService
            ,TravelWiseEmployeeDetailService travelWiseEmployeeDetailService
    ){
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.travelWiseExpenseRepository = travelWiseExpenseRepository;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
        this.notificationService = notificationService;
        this.emailService = emailService;
        this.travelWiseEmployeeDetailService = travelWiseEmployeeDetailService;
    }

    public TravelExpenseResponseDto createTravelExpense(Travel travel, AddUpdateTravelExpenseRequestDto requestDto, String recieptPath){
        if(travel.getLastDateToSubmitExpense().isBefore(LocalDate.now())){
            throw new InvalidActionException("you can not submit new expenses");
        }
        if(travel.getEndDate().isBefore(requestDto.getDateOfExpense())){
            throw  new InvalidActionException("You can not submit expense with expense date after trip end date");
        }
        if(travel.getStartDate().isAfter(requestDto.getDateOfExpense())){
            throw  new InvalidActionException("You can not submit expense with expense date before trip start date");
        }
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
        travelWiseEmployeeDetailService.addAskedAmount(travel.getId(), requestDto.getAskedAmount());

        return modelMapper.map(travelWiseExpense, TravelExpenseResponseDto.class);
    }

    public TravelExpenseResponseDto updateTravelExpense(Travel travel, AddUpdateTravelExpenseRequestDto requestDto, String recieptPath){
        TravelWiseExpense travelWiseExpense = travelWiseExpenseRepository.findById(requestDto.getId()).orElseThrow(()->new RuntimeException("expense not found"));
        if(travelWiseExpense.getStatus().equals("approved") || travelWiseExpense.getStatus().equals("rejected")){
            throw new InvalidActionException("you can not update expense once it reviewed");
        }
        int oldAskedAmount = travelWiseExpense.getAskedAmount();
        ExpenseCategory category = expenseCategoryRepository.getReferenceById(requestDto.getCategoryId());
        travelWiseExpense.setCategory(category);
        travelWiseExpense.setReciept(recieptPath);
        travelWiseExpense.setAskedAmount(requestDto.getAskedAmount());
        travelWiseExpense.setDateOfExpense(requestDto.getDateOfExpense());
        travelWiseExpense.setDescription(requestDto.getDescription());
        travelWiseExpense = travelWiseExpenseRepository.save(travelWiseExpense);
        travelWiseEmployeeDetailService.updateAskedAmount(travel.getId(),oldAskedAmount, requestDto.getAskedAmount());
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
            log.error("Invalid action: can to aprrove greater then asked");
            throw new RuntimeException("Invalid action: can to aprrove greater then asked");
        }
        int oldAmount = expense.getAprrovedAmount();
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = employeeService.getReference(jwtInfoDto.getUserId());
        expense.setAprrovedAmount(requestDto.getAprrovedAmount());
        expense.setRemark(requestDto.getRemark());
        expense.setReviewedBy(employee);
        expense.setStatus(expense.getAprrovedAmount() == 0?"rejected":"reviewed");
        expense = travelWiseExpenseRepository.save(expense);
        travelWiseEmployeeDetailService.updateReimbursedAmount(expense.getTravel().getId(),expense.getEmployee().getId(),oldAmount,expense.getAprrovedAmount());
        List<EmployeeMinDetailsDto> hrs = employeeService.getEmployeeWhoHr();
        emailService.sendMail(
                "Reviewed Expense"
                ,TravelAndExpenseEmailTemplates.forReviewExpense(expense)
                ,new String[]{expense.getEmployee().getEmail()}
                ,hrs.stream().map(hr->hr.getEmail()).collect(Collectors.toUnmodifiableList()).toArray(new String[]{})
        );
        notificationService.notify("Expense reviewed ","Expense",new Long[]{expense.getEmployee().getId()});
        return modelMapper.map(expense, TravelExpenseResponseDto.class);
    }

}
