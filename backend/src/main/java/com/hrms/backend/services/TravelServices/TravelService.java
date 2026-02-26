package com.hrms.backend.services.TravelServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.travel.*;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelExpenseResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDetailResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDocumentResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelMinDetailResponseDto;
import com.hrms.backend.emailTemplates.TravelAndExpenseEmailTemplates;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelWiseEmployeeWiseDocument;
import com.hrms.backend.exceptions.InvalidActionException;
import com.hrms.backend.exceptions.ItemNotFoundExpection;
import com.hrms.backend.repositories.TravelRepositories.TravelRepository;
import com.hrms.backend.services.EmailServices.EmailService;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import com.hrms.backend.services.NotificationServices.NotificationService;
import com.hrms.backend.specs.TravelSpecs;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TravelService {
    private final TravelRepository travelRepository;
    private final TravelDocumentService travelDocumentService;
    private final TravelWiseEmployeeDetailService travelWiseEmployeeDetailService;
    private final TravelWiseExpenseService travelWiseExpenseService;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final TravelWiseEmployeeDocumentService travelWiseEmployeeDocumentService;
    private final EmailService emailService;
    private final NotificationService notificationService;
    @Autowired
    public TravelService(
            TravelRepository travelRepository
            ,EmployeeService employeeService
            ,TravelWiseEmployeeDetailService travelWiseEmployeeDetailService
            ,TravelDocumentService travelDocumentService
            ,TravelWiseEmployeeDocumentService travelWiseEmployeeDocumentService
            ,TravelWiseExpenseService travelWiseExpenseService
            ,ModelMapper modelMapper
            ,EmailService emailService
            ,NotificationService notificationService
    ){
        this.modelMapper = modelMapper;
        this.travelDocumentService = travelDocumentService;
        this.travelRepository = travelRepository;
        this.employeeService = employeeService;
        this.travelWiseEmployeeDocumentService = travelWiseEmployeeDocumentService;
        this.travelWiseEmployeeDetailService = travelWiseEmployeeDetailService;
        this.travelWiseExpenseService = travelWiseExpenseService;
        this.emailService = emailService;
        this.notificationService = notificationService;
    }
    @Transactional
    public TravelDetailResponseDto createTravel(CreateTravelRequestDto travelRequestDto){
        if(travelRequestDto.getStartDate().isAfter(travelRequestDto.getEndDate()) || travelRequestDto.getStartDate().isEqual(travelRequestDto.getEndDate())){
            throw new InvalidActionException("Start date must be before of end date");
        }
        if(travelRequestDto.getEndDate().isAfter(travelRequestDto.getLastDateToSubmitExpense())){
            throw new InvalidActionException("last date for submit expense must be after travel ends date");
        }
        if(travelRequestDto.getStartDate().isBefore(LocalDate.now())){
            throw new InvalidActionException("You can not set start date of past");
        }
        Travel travel = modelMapper.map(travelRequestDto,Travel.class);
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = employeeService.getEmployeeById(jwtInfoDto.getUserId());
        travel.setInitiatedBy(employee);
        Travel savedTravel = travelRepository.save(travel);
        List<EmployeeMinDetailsDto> employees = travelRequestDto.getEmployeeIds().stream().map(employeeId->travelWiseEmployeeDetailService.addEmployeeToTravel(savedTravel,employeeId)).collect(Collectors.toUnmodifiableList());
        TravelDetailResponseDto responseDto = modelMapper.map(travel,TravelDetailResponseDto.class);
        responseDto.setEmployees(employees);
        emailService.sendMail(
                "Travel - " + travel.getTitle()
                , TravelAndExpenseEmailTemplates.forTravelDetail(travel)
                ,employees.stream().map(employeeTemp->employeeTemp.getEmail()).toList().toArray(new String[]{})
                ,new String[]{}
        );
        notificationService.notify(
                "You are added in travel - " + travel.getTitle() + " by " + employee.getFullName()
                ,"Travel"
                , travelRequestDto.getEmployeeIds().toArray(new Long[]{})
        );
        return responseDto;

    }
    @Transactional
    public TravelDetailResponseDto updateTravel(UpdateTravelRequestDto travelRequestDto){
        if(travelRequestDto.getStartDate().isAfter(travelRequestDto.getEndDate()) || travelRequestDto.getStartDate().isEqual(travelRequestDto.getEndDate())){
            throw new InvalidActionException("Start date must be before of end date");
        }
        if(travelRequestDto.getEndDate().isAfter(travelRequestDto.getLastDateToSubmitExpense())){
            throw new InvalidActionException("last date for submit expense must be after travel ends date");
        }
        Travel travel = travelRepository.findById(travelRequestDto.getId()).orElseThrow(()->new ItemNotFoundExpection("Travel not found"));
        if(travel.getStartDate().isBefore(LocalDate.now())){
            throw new InvalidActionException("travel has already began , you can not update travel after starting of travel.");
        }if(travelRequestDto.getStartDate().isBefore(LocalDate.now())){
            throw new InvalidActionException("You can not set start date of past");
        }
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        travel.setDescripton(travelRequestDto.getDescripton());
        travel.setTitle(travelRequestDto.getTitle());
        travel.setStartDate(travelRequestDto.getStartDate());
        travel.setEndDate(travelRequestDto.getEndDate());
        travel.setLastDateToSubmitExpense(travelRequestDto.getLastDateToSubmitExpense());
        Travel savedTravel = travelRepository.save(travel);
        TravelDetailResponseDto responseDto = modelMapper.map(travel,TravelDetailResponseDto.class);
        var employees = travel.getTravelWiseEmployees().stream().map(travelWiseEmployee->modelMapper.map(travelWiseEmployee.getEmployee(),EmployeeMinDetailsDto.class)).toList();
        responseDto.setEmployees(employees);
        emailService.sendMail(
                "Update :- Travel - " + travel.getTitle()
                , TravelAndExpenseEmailTemplates.forTravelDetail(savedTravel)
                ,employees.stream().map(EmployeeMinDetailsDto::getEmail).toList().toArray(new String[]{})
                ,new String[]{}
        );
        notificationService.notify(
                "You are added in travel - " + travel.getTitle() + " by " + employeeService.getEmployeeById(jwtInfoDto.getUserId()).getFullName()
                ,"Travel"
                ,employees.stream().map(EmployeeMinDetailsDto::getId).toList().toArray(new Long[]{})
        );
        return responseDto;
    }
    @Transactional
    public TravelDocumentResponseDto addDocument(Long travelId, AddUpdateTravelDocumentRequestDto requestDto, String filePath){
        Travel travel = travelRepository.findById(travelId).orElseThrow(()->new ItemNotFoundExpection("travel not found"));
        TravelDocumentResponseDto documentResponseDto = travelDocumentService.addDocument(travel,requestDto,filePath);
        emailService.sendMail(
                "Travel Document uploaded"
                        , TravelAndExpenseEmailTemplates.forTravleDocumnet(documentResponseDto, modelMapper.map(travel, TravelMinDetailResponseDto.class))
                    ,travel.getTravelWiseEmployees().stream().map(employee->employee.getEmployee().getEmail()).collect(Collectors.toUnmodifiableList()).toArray(new String[]{})
                ,new String[]{}
                ,"travel-documents"
                ,documentResponseDto.getDocumentPath()
                );
        notificationService.notify(
                "new Document added in travel - " + travel.getTitle() + " for " + documentResponseDto.getDescription()
                ,"travel"
                ,travel.getTravelWiseEmployees().stream().map(te->te.getEmployee().getId()).collect(Collectors.toUnmodifiableList()).toArray(new Long[]{})
        );
        return  documentResponseDto;
    }

    @Transactional
    public TravelDocumentResponseDto updateDocument(Long travelId, AddUpdateTravelDocumentRequestDto requestDto){
        Travel travel = travelRepository.getReferenceById(travelId);
        if(travel == null){
            throw new ItemNotFoundExpection("travel not found");
        }
        TravelDocumentResponseDto documentResponseDto = travelDocumentService.updateDocument(travel,requestDto);
        emailService.sendMail(
                "Travel Document updated"
                , TravelAndExpenseEmailTemplates.forTravleDocumnet(documentResponseDto, modelMapper.map(travel, TravelMinDetailResponseDto.class))
                ,travel.getTravelWiseEmployees().stream().map(employee->employee.getEmployee().getEmail()).collect(Collectors.toUnmodifiableList()).toArray(new String[]{})
                ,new String[]{}
                ,"travel-documents"
                ,documentResponseDto.getDocumentPath()
        );
        notificationService.notify(
                "new Document update in travel - " + travel.getTitle() + " for " + documentResponseDto.getDescription()
                ,"travel"
                ,travel.getTravelWiseEmployees().stream().map(te->te.getEmployee().getId()).collect(Collectors.toUnmodifiableList()).toArray(new Long[]{})
        );
        return  documentResponseDto;
    }

    public TravelMinDetailResponseDto addEmployeesToTravel(Long travelId, AddEmployeesToTravelRequestDto requestDto){
        Travel travel = travelRepository.getReferenceById(travelId);
        if(travel == null){
            throw new ItemNotFoundExpection("travel not found");
        }
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Long> assignedEmployeeId = travel.getTravelWiseEmployees().stream().map(emp->emp.getEmployee().getId()).collect(Collectors.toUnmodifiableList());
        requestDto.getEmployeeIds().removeAll(assignedEmployeeId);
        List<EmployeeMinDetailsDto> employees = requestDto.getEmployeeIds().stream().map(employeeId->travelWiseEmployeeDetailService.addEmployeeToTravel(travel,employeeId)).collect(Collectors.toUnmodifiableList());
        TravelMinDetailResponseDto responseDto = modelMapper.map(travel,TravelMinDetailResponseDto.class);
        emailService.sendMail(
                "Travel - " + travel.getTitle()
               , TravelAndExpenseEmailTemplates.forTravelDetail(travel)
                ,employees.stream().map(employee->employee.getEmail()).toList().toArray(new String[]{})
                ,new String[]{}
        );
        notificationService.notify(
                "You are added in travel - " + travel.getTitle() + " by " + employeeService.getEmployeeById(jwtInfoDto.getUserId()).getFullName()
                ,"Travel"
                , requestDto.getEmployeeIds().toArray(new Long[]{})
                );
        return responseDto;
    }

    @Transactional
    public TravelDocumentResponseDto addEmployeeDocument(Long travelId, AddUpdateTravelDocumentRequestDto requestDto, String filePath){
        Travel travel = travelRepository.getReferenceById(travelId);
        if(travel == null){
            throw new ItemNotFoundExpection("travel not found");
        }
        TravelDocumentResponseDto documentResponseDto = travelWiseEmployeeDocumentService.addDocument(travel,requestDto,filePath);
        List<EmployeeMinDetailsDto> hrs = employeeService.getEmployeeWhoHr();

        emailService.sendMail(
                "Travel Documnet uploaded"
                , TravelAndExpenseEmailTemplates.forEmployeeDocumnetUpload(documentResponseDto,modelMapper.map(travel,TravelMinDetailResponseDto.class))
                ,hrs.stream().map(hr->hr.getEmail()).collect(Collectors.toUnmodifiableList()).toArray(new String[]{})
                ,new String[]{}
                ,"travel-documents"
                ,documentResponseDto.getDocumentPath()
        );
        notificationService.notify(
                documentResponseDto.getUploadedBy().getFirstName() +" "+ documentResponseDto.getUploadedBy().getLastName() + " has uploaded personla documnet in travel - " + travel.getTitle()
                ,"Travel"
                ,hrs.stream().map(hr->hr.getId()).collect(Collectors.toUnmodifiableList()).toArray(new Long[]{})
        );
        return  documentResponseDto;
    }
    public TravelDocumentResponseDto updateEmployeeDocument(Long travelId, AddUpdateTravelDocumentRequestDto requestDto){
        Travel travel = travelRepository.getReferenceById(travelId);
        if(travel == null){
            throw new ItemNotFoundExpection("travel not found");
        }
        TravelDocumentResponseDto documentResponseDto = travelWiseEmployeeDocumentService.updateDocument(travel,requestDto);
        List<EmployeeMinDetailsDto> hrs = employeeService.getEmployeeWhoHr();
        emailService.sendMail(
                "Travel document Updated"
                , TravelAndExpenseEmailTemplates.forEmployeeDocumnetUpload(documentResponseDto,modelMapper.map(travel,TravelMinDetailResponseDto.class))
                ,hrs.stream().map(hr->hr.getEmail()).collect(Collectors.toUnmodifiableList()).toArray(new String[]{})
                ,new String[]{}
                ,"travel-documents"
                ,documentResponseDto.getDocumentPath()
        );
        notificationService.notify(
                documentResponseDto.getUploadedBy().getFirstName() +" "+ documentResponseDto.getUploadedBy().getLastName() + " has uploaded personla documnet in travel - " + travel.getTitle()
                ,"Travel"
                ,hrs.stream().map(hr->hr.getId()).collect(Collectors.toUnmodifiableList()).toArray(new Long[]{})
        );
        return  documentResponseDto;
    }

    public TravelDetailResponseDto getTravelDetail(Long travelId){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Travel travel = travelRepository.findById(travelId).orElseThrow(()->new RuntimeException("travel detail not found"));
        TravelDetailResponseDto responseDto = modelMapper.map(travel, TravelDetailResponseDto.class);

        responseDto.setEmployees(travel.getTravelWiseEmployees().stream().map(travelWiseEmployee -> modelMapper.map(travelWiseEmployee.getEmployee(), EmployeeMinDetailsDto.class)).collect(Collectors.toUnmodifiableList()),jwtInfoDto.getUserId());
        List<TravelDocumentResponseDto> personalDocumnets = travelWiseEmployeeDocumentService.getPersonalDocumnets(travelId);
        List<TravelDocumentResponseDto> employeeDocuments = travelWiseEmployeeDocumentService.getEmployeesDocuments(travelId);
        responseDto.setPersonalDocumnets(personalDocumnets);
        responseDto.setEmployeeDocuments(employeeDocuments);
        if(responseDto.isInEmployeeList()){
            responseDto.setExpensesMadeByMe(travelWiseExpenseService.getExpenses(travelId));
        }
        return responseDto;
    }

    public List<TravelMinDetailResponseDto> getTravels(String getAsa){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(getAsa.equals("as-a-manager")){
            Specification<Travel> specs = TravelSpecs.hasManger(jwtInfoDto.getUserId());
            List<Travel> travels = travelRepository.findAll(specs);
            return travels.stream().map(travel -> modelMapper.map(travel, TravelMinDetailResponseDto.class)).collect(Collectors.toUnmodifiableList());
        }else if(getAsa.equals("assigned")){
            Specification<Travel> specs = TravelSpecs.hasEmployee(jwtInfoDto.getUserId());
            List<Travel> travels = travelRepository.findAll(specs);
            return travels.stream().map(travel -> modelMapper.map(travel, TravelMinDetailResponseDto.class)).collect(Collectors.toUnmodifiableList());
        } else if (getAsa.equals("hr")) {
            List<Travel> travels = travelRepository.findAll();
            return travels.stream().map(travel -> modelMapper.map(travel, TravelMinDetailResponseDto.class)).collect(Collectors.toUnmodifiableList());
        }
        return null;
    }


    @Transactional
    public TravelExpenseResponseDto addExpense(Long travelId, AddUpdateTravelExpenseRequestDto requestDto, String filePath){
        Travel travel = travelRepository.findById(travelId).orElseThrow(()->new RuntimeException("travel not found"));
        if(travel.getLastDateToSubmitExpense().isBefore(LocalDate.now())){
            throw new RuntimeException("Can not submit or update expense now");
        }
        if(travel.getEndDate().isBefore(requestDto.getDateOfExpense())){
            throw new RuntimeException("invalid date of expense now");
        }
        TravelExpenseResponseDto expense = travelWiseExpenseService.createTravelExpense(travel,requestDto,filePath);
        List<EmployeeMinDetailsDto> hrs = employeeService.getEmployeeWhoHr();
        emailService.sendMail(
                "Expense requested for reimbursement"
                , TravelAndExpenseEmailTemplates.forExpense(expense,modelMapper.map(travel, TravelMinDetailResponseDto.class),false)
                ,hrs.stream().map(hr->hr.getEmail()).collect(Collectors.toUnmodifiableList()).toArray(new String[]{})
                ,new String[]{}
                ,"expenses"
                ,expense.getReciept()
        );
        notificationService.notify(
                expense.getEmployee().getFirstName() +" "+ expense.getEmployee().getLastName() + " has requested for reimbursement expense of  " + expense.getAskedAmount()+ "for travel - " + travel.getTitle()
                ,"Expense"
                ,hrs.stream().map(hr->hr.getId()).collect(Collectors.toUnmodifiableList()).toArray(new Long[]{})
        );
        return expense;
    }

    public TravelExpenseResponseDto updateExpense(Long travelId, AddUpdateTravelExpenseRequestDto requestDto, String filePath){
        Travel travel = travelRepository.findById(travelId).orElseThrow(()->new RuntimeException("travel not found"));
        if(travel.getLastDateToSubmitExpense().isBefore(LocalDate.now())){
            throw new RuntimeException("Can not submit or update expense now");
        }
        if(travel.getEndDate().isBefore(requestDto.getDateOfExpense())){
            throw new RuntimeException("invalid date of expense now");
        }
        TravelExpenseResponseDto expense = travelWiseExpenseService.updateTravelExpense(travel,requestDto,filePath);
        List<EmployeeMinDetailsDto> hrs = employeeService.getEmployeeWhoHr();
        emailService.sendMail(
                "Expense request updated:- "
                , TravelAndExpenseEmailTemplates.forExpense(expense,modelMapper.map(travel, TravelMinDetailResponseDto.class),false)
                ,hrs.stream().map(hr->hr.getEmail()).collect(Collectors.toUnmodifiableList()).toArray(new String[]{})
                ,new String[]{}
                ,"expenses"
                ,expense.getReciept()
        );
        notificationService.notify(
                expense.getEmployee().getFirstName() +" "+ expense.getEmployee().getLastName() + " has update request for reimbursement expense of  " + expense.getAskedAmount()+ "for travel - " + travel.getTitle()
                ,"Expense"
                ,hrs.stream().map(hr->hr.getId()).collect(Collectors.toUnmodifiableList()).toArray(new Long[]{})
        );
        return expense;
    }

    public List<TravelMinDetailResponseDto> getUpcomingTravels(){

        Specification<Travel> specs = TravelSpecs.isNotStartedOrNotEnded();
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!jwtInfoDto.getRoleTitle().equals("HR")){
            specs = specs.and(
                    TravelSpecs.hasEmployee(jwtInfoDto.getUserId()).or(TravelSpecs.hasManger(jwtInfoDto.getUserId()))
            );
        }
        return travelRepository.findBy(specs,q->q.stream().distinct().map(travel -> modelMapper.map(travel, TravelMinDetailResponseDto.class)).toList());
    }

    public TravelMinDetailResponseDto getTravelMinDetail(Long travelId){
        Travel travel = travelRepository.findById(travelId).orElseThrow(()->new ItemNotFoundExpection("travel not found"));
        return modelMapper.map(travel,TravelMinDetailResponseDto.class);
    }
}
