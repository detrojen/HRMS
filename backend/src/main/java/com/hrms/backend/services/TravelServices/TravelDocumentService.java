package com.hrms.backend.services.TravelServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.travel.AddUpdateTravelDocumentRequestDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDocumentResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelDocument;
import com.hrms.backend.exceptions.InvalidActionException;
import com.hrms.backend.exceptions.ItemNotFoundExpection;
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

    public TravelDocumentResponseDto updateDocument(Travel travel, AddUpdateTravelDocumentRequestDto documentRequestDto){
        TravelDocument travelDocument = travelDocumentRepository.findById(documentRequestDto.getId()).orElseThrow(()->new ItemNotFoundExpection("Document not found"));
        travelDocument.setDocumentPath(documentRequestDto.getDocumentPath());
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(jwtInfoDto.getUserId() != travelDocument.getUploadedBy().getId()){
            throw new InvalidActionException("you can not update this document. Cause: you have not uploaded this document");
        }
        travelDocument.setDescription(documentRequestDto.getDescription());
        travelDocument.setType(documentRequestDto.getType());
        travelDocument.setDocumentPath(documentRequestDto.getDocumentPath());
        travelDocument = travelDocumentRepository.save(travelDocument);
        return modelMapper.map(travelDocument,TravelDocumentResponseDto.class);
    }
}
