package com.hrms.backend.services.TravelServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.travel.AddTravelDocumentRequestDto;
import com.hrms.backend.dtos.requestDto.travel.CreateTravelRequestDto;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDetailResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDocumentResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelDocument;
import com.hrms.backend.repositories.TravelRepositories.TravelRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TravelService {
    private final TravelRepository travelRepository;
    private final TravelDocumentService travelDocumentService;
    private final TravelWiseEmployeeDetailService travelWiseEmployeeDetailService;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    @Autowired
    public TravelService(TravelRepository travelRepository,EmployeeService employeeService,TravelWiseEmployeeDetailService travelWiseEmployeeDetailService,TravelDocumentService travelDocumentService,ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.travelDocumentService = travelDocumentService;
        this.travelRepository = travelRepository;
        this.employeeService = employeeService;
        this.travelWiseEmployeeDetailService = travelWiseEmployeeDetailService;
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

    public TravelDetailResponseDto getTravelDetail(Long travelId){
        Travel travel = travelRepository.findById(travelId).orElseThrow(()->new RuntimeException("travel detail not found"));
        TravelDetailResponseDto responseDto = modelMapper.map(travel, TravelDetailResponseDto.class);
        responseDto.setEmployees(travel.getTravelWiseEmployees().stream().map(travelWiseEmployee -> modelMapper.map(travelWiseEmployee.getEmployee(), EmployeeWithNameOnlyDto.class)).collect(Collectors.toUnmodifiableList()));
        return responseDto;
    }

//    @Transactional
//    public List<TravelDocumentResponseDto> addMultipleDocumnets(Travel travel, List<AddTravelDocumentRequestDto> documents){
//        List<TravelDocumentResponseDto> documentResponseDtos = documents.stream().map(document -> travelDocumentService.addDocument(travel,document)).collect(Collectors.toUnmodifiableList());
//        return  documentResponseDtos;
//    }
//
//    @Transactional
//    public List<TravelDocumentResponseDto> addMultipleDocumnets(Long travelId, List<AddTravelDocumentRequestDto> documents){
//        Travel travel = travelRepository.getReferenceById(travelId);
//        if(travel == null){
//            throw new RuntimeException("travel not found");
//        }
//        List<TravelDocumentResponseDto> documentResponseDtos = documents.stream().map(document -> travelDocumentService.addDocument(travel,document)).collect(Collectors.toUnmodifiableList());
//        return  documentResponseDtos;
//    }
}
