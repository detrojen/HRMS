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
import com.hrms.backend.entities.TravelEntities.ExpenseWiseDocument;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelWiseExpense;
import com.hrms.backend.enums.TravelExpenseStatus;
import com.hrms.backend.exceptions.InvalidActionException;
import com.hrms.backend.exceptions.ItemNotFoundExpection;
import com.hrms.backend.repositories.TravelRepositories.ExpenseCategoryRepository;
import com.hrms.backend.repositories.TravelRepositories.ExpenseWiseDocumentRepository;
import com.hrms.backend.repositories.TravelRepositories.TravelWiseExpenseRepository;
import com.hrms.backend.services.EmailServices.EmailService;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import com.hrms.backend.services.NotificationServices.NotificationService;
import com.hrms.backend.specs.TravelExpenseSpecs;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
public class TravelWiseExpenseService {
    private final TravelWiseExpenseRepository travelWiseExpenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseWiseDocumentRepository expenseWiseDocumentRepository;
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
            ,ExpenseWiseDocumentRepository expenseWiseDocumentRepository
    ){
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.travelWiseExpenseRepository = travelWiseExpenseRepository;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
        this.notificationService = notificationService;
        this.emailService = emailService;
        this.travelWiseEmployeeDetailService = travelWiseEmployeeDetailService;
        this.expenseWiseDocumentRepository = expenseWiseDocumentRepository;
    }

    public TravelExpenseResponseDto createTravelExpense(Travel travel, AddUpdateTravelExpenseRequestDto requestDto, String[] proofs){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(travel.getLastDateToSubmitExpense().isBefore(LocalDate.now())){
            throw new InvalidActionException("you can not submit new expenses");
        }
        if(travel.getStartDate().isAfter(LocalDate.now())){
            throw new InvalidActionException("travel not starts yet, you can not submit expense");
        }
        if(travel.getEndDate().isBefore(requestDto.getDateOfExpense())){
            throw  new InvalidActionException("You can not submit expense with expense date after trip end date");
        }
        if(travel.getStartDate().isAfter(requestDto.getDateOfExpense())){
            throw  new InvalidActionException("You can not submit expense with expense date before trip start date");
        }
        int totalExpenseRequested = travelWiseExpenseRepository.getTotalExpenseByEmployeeByTravelByDate(jwtInfoDto.getUserId(), travel.getId(), requestDto.getDateOfExpense()).orElse(0);
        if(travel.getMaxReimbursementAmountPerDay() == totalExpenseRequested){
            throw  new InvalidActionException("you have reached limit of Maximum reimbursement amount per day for date = " + requestDto.getDateOfExpense());
        }
        if(travel.getMaxReimbursementAmountPerDay() < totalExpenseRequested + requestDto.getAskedAmount()){
            throw new InvalidActionException("Maximum reimbursement amount limit reached. you can only asks for expense amount =" +(travel.getMaxReimbursementAmountPerDay()-totalExpenseRequested) + " for date = "+requestDto.getDateOfExpense() );
        }
        Employee employee = employeeService.getReference(jwtInfoDto.getUserId());
        TravelWiseExpense travelWiseExpense = modelMapper.map(requestDto,TravelWiseExpense.class);
        travelWiseExpense.setTravel(travel);
        travelWiseExpense.setEmployee(employee);
        travelWiseExpense.setReciept("");
        travelWiseExpense.setStatus(TravelExpenseStatus.PENDING.toString());
        ExpenseCategory category = expenseCategoryRepository.getReferenceById(requestDto.getCategoryId());
        travelWiseExpense.setCategory(category);
        travelWiseExpense = travelWiseExpenseRepository.save(travelWiseExpense);
        travelWiseEmployeeDetailService.addAskedAmount(travel.getId(), requestDto.getAskedAmount());
        TravelWiseExpense finalTravelWiseExpense = travelWiseExpense;
        List<ExpenseWiseDocument> expenseWiseDocuments = Arrays.stream(proofs).map(proof->new ExpenseWiseDocument(proof, finalTravelWiseExpense)).toList();
        expenseWiseDocuments = expenseWiseDocumentRepository.saveAll(expenseWiseDocuments);
        travelWiseExpense.setProofs(expenseWiseDocuments);
        return modelMapper.map(travelWiseExpense, TravelExpenseResponseDto.class);
    }

    public TravelExpenseResponseDto updateTravelExpense(Travel travel, AddUpdateTravelExpenseRequestDto requestDto, String[] proofs){
        TravelWiseExpense travelWiseExpense = travelWiseExpenseRepository.findById(requestDto.getId()).orElseThrow(()->new ItemNotFoundExpection("expense not found"));
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(travelWiseExpense.getStatus().equals(TravelExpenseStatus.APPROVED.toString()) || travelWiseExpense.getStatus().equals(TravelExpenseStatus.REJECTED.toString())) {
            throw new InvalidActionException("you can not update expense once it reviewed");
        }
        int totalExpenseRequested = travelWiseExpenseRepository.getTotalExpenseByEmployeeByTravelByDate(jwtInfoDto.getUserId(), travel.getId(), requestDto.getDateOfExpense()).orElse(0)-travelWiseExpense.getAskedAmount();
        if(travel.getMaxReimbursementAmountPerDay() == totalExpenseRequested){
            throw  new InvalidActionException("you have reached limit of Maximum reimbursement amount per day for date = " + requestDto.getDateOfExpense());
        }
        if(travel.getMaxReimbursementAmountPerDay() < totalExpenseRequested + requestDto.getAskedAmount()){
            throw new InvalidActionException("Maximum reimbursement amount limit reached. you can only asks for expense amount =" +(travel.getMaxReimbursementAmountPerDay()-totalExpenseRequested) + " for date = "+requestDto.getDateOfExpense() );
        }
        int oldAskedAmount = travelWiseExpense.getAskedAmount();
        ExpenseCategory category = expenseCategoryRepository.getReferenceById(requestDto.getCategoryId());
        travelWiseExpense.setCategory(category);
        travelWiseExpense.setAskedAmount(requestDto.getAskedAmount());
        travelWiseExpense.setDateOfExpense(requestDto.getDateOfExpense());
        travelWiseExpense.setDescription(requestDto.getDescription());
        travelWiseExpense = travelWiseExpenseRepository.save(travelWiseExpense);
        travelWiseEmployeeDetailService.updateAskedAmount(travel.getId(),oldAskedAmount, requestDto.getAskedAmount());
        TravelWiseExpense finalTravelWiseExpense = travelWiseExpense;
        List<ExpenseWiseDocument> expenseWiseDocuments = Arrays.stream(proofs).map(proof->new ExpenseWiseDocument(proof, finalTravelWiseExpense)).toList();
        expenseWiseDocuments = expenseWiseDocumentRepository.saveAll(expenseWiseDocuments);
        travelWiseExpense.setProofs(expenseWiseDocuments);
        return modelMapper.map(travelWiseExpense, TravelExpenseResponseDto.class);
    }

    public String deleteExpenseProofById(Long travelExpenseId, Long expenseDocumentId){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TravelWiseExpense expense = travelWiseExpenseRepository.getByIdAndEmployee_IdAndProofs_Id(travelExpenseId, jwtInfoDto.getUserId(), expenseDocumentId).orElseThrow(()->new InvalidActionException("You can not delete this record"));
        if(expense.getStatus().equals(TravelExpenseStatus.APPROVED.toString()) || expense.getStatus().equals(TravelExpenseStatus.REJECTED.toString())) {
            throw new InvalidActionException("you can not update expense once it reviewed");
        }
        if(expense.getTravel().getLastDateToSubmitExpense().isBefore(LocalDate.now())){
            throw new InvalidActionException("you can not delete or update proof");
        }else if (expense.getProofs().size() == 1) {
            throw new InvalidActionException("Each expense needs at least one proof for review . you can not delete");
        }
        String filePath = (expenseWiseDocumentRepository.findById(expenseDocumentId).orElseThrow(()->new ItemNotFoundExpection("Expense document not found")))
                .getDocumentPath();
        expenseWiseDocumentRepository.deleteById(expenseDocumentId);
        return filePath;
    }

    @Transactional
    public List<TravelExpenseResponseDto> getExpenses(Long travelId){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TravelWiseExpense> expenses = travelWiseExpenseRepository.findAllByEmployee_IdAndTravel_Id(jwtInfoDto.getUserId(),travelId);
        return expenses.stream().map(expense -> modelMapper.map(expense, TravelExpenseResponseDto.class)).toList();
    }

    public List<TravelExpenseResponseDto> getExpenses(Long travelId, TravelExpenseParamsDto params){
        Specification<TravelWiseExpense> specs = TravelExpenseSpecs.hasTravel(travelId);
        if(params.getCategory().isPresent()){
            specs = specs.and(TravelExpenseSpecs.hasCategory(params.getCategory().get()));
        }
        if(params.getDateFrom().isPresent()){
            specs = specs.and(TravelExpenseSpecs.hasDateFrom(params.getDateFrom().get()));
        }
        if(params.getDateTo().isPresent()){
            specs = specs.and(TravelExpenseSpecs.hasDateTo(params.getDateTo().get()));
        }
        if(params.getEmployeeId().isPresent()){
            specs = specs.and(TravelExpenseSpecs.hasEmployee(params.getEmployeeId().get()));
        }
        List<TravelWiseExpense> expenses = travelWiseExpenseRepository.findAll(specs);
        return expenses.stream().map(expense->modelMapper.map(expense,TravelExpenseResponseDto.class)).toList();
    }

    public TravelExpenseResponseDto reviewExpense(ReviewTravelExpenseRequestDto requestDto){
        TravelWiseExpense expense = travelWiseExpenseRepository.findById(requestDto.getId()).orElseThrow(()->new ItemNotFoundExpection("not found expense"));
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(requestDto.getAprrovedAmount() > expense.getAskedAmount()){
            throw new InvalidActionException("Invalid action: can to aprrove greater then asked");
        }
        else if(expense.getEmployee().getId().equals(jwtInfoDto.getUserId())){
            throw new InvalidActionException("You can not review your expense. It should be reviewed by other hr");
        }
        int oldAmount = expense.getAprrovedAmount();
        Employee employee = employeeService.getReference(jwtInfoDto.getUserId());
        expense.setAprrovedAmount(requestDto.getAprrovedAmount());
        expense.setRemark(requestDto.getRemark());
        expense.setReviewedBy(employee);
        expense.setStatus(expense.getAprrovedAmount() == 0?TravelExpenseStatus.REJECTED.toString():TravelExpenseStatus.APPROVED.toString());
        expense = travelWiseExpenseRepository.save(expense);
        travelWiseEmployeeDetailService.updateReimbursedAmount(expense.getTravel().getId(),expense.getEmployee().getId(),oldAmount,expense.getAprrovedAmount());
        List<EmployeeMinDetailsDto> hrs = employeeService.getEmployeeWhoHr();
        emailService.sendMail(
                "Reviewed Expense"
                ,TravelAndExpenseEmailTemplates.forReviewExpense(expense)
                ,new String[]{expense.getEmployee().getEmail()}
                ,hrs.stream().map(hr->hr.getEmail()).toList().toArray(new String[]{})
        );
        notificationService.notify("Expense reviewed ","Expense",new Long[]{expense.getEmployee().getId()});
        return modelMapper.map(expense, TravelExpenseResponseDto.class);
    }

}
