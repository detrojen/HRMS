package com.hrms.backend.services.TravelServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.travel.AddTravelDocumentRequestDto;
import com.hrms.backend.dtos.requestDto.travel.AddUpdateTravelExpenseRequestDto;
import com.hrms.backend.dtos.requestDto.travel.CreateTravelRequestDto;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelExpenseResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDetailResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDocumentResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelMinDetailResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.repositories.TravelRepositories.TravelRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import com.hrms.backend.specs.TravelSpecs;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

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
    @Autowired
    public TravelService(
            TravelRepository travelRepository
            ,EmployeeService employeeService
            ,TravelWiseEmployeeDetailService travelWiseEmployeeDetailService
            ,TravelDocumentService travelDocumentService
            ,TravelWiseEmployeeDocumentService travelWiseEmployeeDocumentService
            ,TravelWiseExpenseService travelWiseExpenseService
            ,ModelMapper modelMapper
    ){
        this.modelMapper = modelMapper;
        this.travelDocumentService = travelDocumentService;
        this.travelRepository = travelRepository;
        this.employeeService = employeeService;
        this.travelWiseEmployeeDocumentService = travelWiseEmployeeDocumentService;
        this.travelWiseEmployeeDetailService = travelWiseEmployeeDetailService;
        this.travelWiseExpenseService = travelWiseExpenseService;
    }
    @Transactional
    public TravelDetailResponseDto createTravel(CreateTravelRequestDto travelRequestDto){
        Travel travel = modelMapper.map(travelRequestDto,Travel.class);
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = employeeService.getEmployeeById(jwtInfoDto.getUserId());
        travel.setInitiatedBy(employee);
        Travel savedTravel = travelRepository.save(travel);
        List<EmployeeWithNameOnlyDto> employees = travelRequestDto.getEmployeeIds().stream().map(employeeId->travelWiseEmployeeDetailService.addEmployeeToTravel(savedTravel,employeeId)).collect(Collectors.toUnmodifiableList());
        TravelDetailResponseDto responseDto = modelMapper.map(travel,TravelDetailResponseDto.class);
        responseDto.setEmployees(employees);
        return responseDto;
    }
    @Transactional
    public TravelDocumentResponseDto addDocument(Long travelId, AddTravelDocumentRequestDto requestDto, String filePath){
        Travel travel = travelRepository.getReferenceById(travelId);
        if(travel == null){
            throw new RuntimeException("travel not found");
        }
        TravelDocumentResponseDto documentResponseDto = travelDocumentService.addDocument(travel,requestDto,filePath);
        return  documentResponseDto;
    }

    @Transactional
    public TravelDocumentResponseDto addEmployeeDocument(Long travelId, AddTravelDocumentRequestDto requestDto, String filePath){
        Travel travel = travelRepository.getReferenceById(travelId);
        if(travel == null){
            throw new RuntimeException("travel not found");
        }
        TravelDocumentResponseDto documentResponseDto = travelWiseEmployeeDocumentService.addDocument(travel,requestDto,filePath);
        return  documentResponseDto;
    }

    public TravelDetailResponseDto getTravelDetail(Long travelId){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Travel travel = travelRepository.findById(travelId).orElseThrow(()->new RuntimeException("travel detail not found"));
        TravelDetailResponseDto responseDto = modelMapper.map(travel, TravelDetailResponseDto.class);
        responseDto.setEmployees(travel.getTravelWiseEmployees().stream().map(travelWiseEmployee -> modelMapper.map(travelWiseEmployee.getEmployee(), EmployeeWithNameOnlyDto.class)).collect(Collectors.toUnmodifiableList()),jwtInfoDto.getUserId());
        List<TravelDocumentResponseDto> personalDocumnets = travelWiseEmployeeDocumentService.getPersonalDocumnets(travelId);
        List<TravelDocumentResponseDto> employeeDocuments = travelWiseEmployeeDocumentService.getEmployeesDocuments(travelId);
        responseDto.setPersonalDocumnets(personalDocumnets);
        responseDto.setEmployeeDocuments(employeeDocuments);
        if(responseDto.isInEmployeeList()){
            responseDto.setExpenses(travelWiseExpenseService.getExpenses(travelId));
        }
        return responseDto;
    }

    public List<TravelMinDetailResponseDto> getAssignedTravels(){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Specification<Travel> specs = TravelSpecs.hasEmployee(jwtInfoDto.getUserId());
        List<Travel> travels = travelRepository.findAll(specs);
        return travels.stream().map(travel -> modelMapper.map(travel, TravelMinDetailResponseDto.class)).collect(Collectors.toUnmodifiableList());
    }
    public TravelExpenseResponseDto addExpense(Long travelId, AddUpdateTravelExpenseRequestDto requestDto, String filePath){
        Travel travel = travelRepository.getReferenceById(travelId);
        return travelWiseExpenseService.createTravelExpense(travel,requestDto,filePath);
    }

    public TravelExpenseResponseDto updateExpense(Long travelId, AddUpdateTravelExpenseRequestDto requestDto, String filePath){
        Travel travel = travelRepository.getReferenceById(travelId);
        return travelWiseExpenseService.updateTravelExpense(travel,requestDto,filePath);
    }
}
