package com.hrms.backend.services.TravelServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.travel.AddUpdateTravelDocumentRequestDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDocumentResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelDocument;
import com.hrms.backend.repositories.TravelRepositories.TravelDocumentRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TravelDocumentService {
    private final TravelDocumentRepository travelDocumentRepository;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    public  TravelDocumentService(TravelDocumentRepository travelDocumentRepository,EmployeeService employeeService, ModelMapper modelMapper){
        this.travelDocumentRepository = travelDocumentRepository;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    public TravelDocumentResponseDto addDocument(Travel travel, AddUpdateTravelDocumentRequestDto documentRequestDto, String filePath){
        TravelDocument travelDocument = modelMapper.map(documentRequestDto,TravelDocument.class);
        travelDocument.setDocumentPath(filePath);
        travelDocument.setTravel(travel);
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee uploadedBy = employeeService.getEmployeeById(jwtInfoDto.getUserId());
        travelDocument.setUploadedBy(uploadedBy);
        travelDocument = travelDocumentRepository.save(travelDocument);
        return modelMapper.map(travelDocument,TravelDocumentResponseDto.class);
    }
}
