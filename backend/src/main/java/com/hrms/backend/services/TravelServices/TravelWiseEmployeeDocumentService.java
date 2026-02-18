package com.hrms.backend.services.TravelServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.travel.AddUpdateTravelDocumentRequestDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDocumentResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.TravelEntities.Travel;
import com.hrms.backend.entities.TravelEntities.TravelWiseEmployeeWiseDocument;
import com.hrms.backend.repositories.TravelRepositories.TravelWiseEmployeeWiseDocumentRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import com.hrms.backend.specs.TravelWiseEmployeeDocumentSpecs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelWiseEmployeeDocumentService {
    private final TravelWiseEmployeeWiseDocumentRepository repository;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    @Autowired
    public TravelWiseEmployeeDocumentService(TravelWiseEmployeeWiseDocumentRepository repository, EmployeeService employeeService, ModelMapper modelMapper){
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
    }

    public List<TravelDocumentResponseDto> getPersonalDocumnets(Long travelId){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Specification<TravelWiseEmployeeWiseDocument> specification = TravelWiseEmployeeDocumentSpecs.hasDocumnetUplodedBy(jwtInfoDto.getUserId()).and(
                TravelWiseEmployeeDocumentSpecs.hasTravelId(travelId)
        );
        List<TravelWiseEmployeeWiseDocument> documnets = repository.findAll(specification);
        return documnets.stream().map(documnet->modelMapper.map(documnet, TravelDocumentResponseDto.class)).collect(Collectors.toUnmodifiableList());
    }

    public List<TravelDocumentResponseDto> getEmployeesDocuments(Long travelId){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TravelWiseEmployeeWiseDocument> documnets = null;
        Specification<TravelWiseEmployeeWiseDocument> specs =TravelWiseEmployeeDocumentSpecs.hasTravelId(travelId);
        if(jwtInfoDto.getRoleTitle().equals("HR")){
             documnets = repository.findAll(specs);
        } else if (jwtInfoDto.getRoleTitle().equals("Manager")) {
            specs = specs.and(
                    TravelWiseEmployeeDocumentSpecs.hasManagerId(jwtInfoDto.getUserId())
            );
            documnets = repository.findAll(specs);

        }
        return documnets==null ? new ArrayList<>()
                : documnets.stream().map(documnet->modelMapper.map(documnet, TravelDocumentResponseDto.class)).collect(Collectors.toUnmodifiableList());

    }

    public TravelDocumentResponseDto addDocument(Travel travel, AddUpdateTravelDocumentRequestDto documentRequestDto, String filePath){
        TravelWiseEmployeeWiseDocument travelDocument = modelMapper.map(documentRequestDto,TravelWiseEmployeeWiseDocument.class);
        travelDocument.setDocumentPath(filePath);
        travelDocument.setTravel(travel);
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee uploadedBy = employeeService.getEmployeeById(jwtInfoDto.getUserId());
        travelDocument.setUploadedBy(uploadedBy);
        travelDocument = repository.save(travelDocument);
        return modelMapper.map(travelDocument,TravelDocumentResponseDto.class);
    }
    public TravelDocumentResponseDto updateDocument(Travel travel, AddUpdateTravelDocumentRequestDto documentRequestDto){
        TravelWiseEmployeeWiseDocument travelDocument = repository.findById(documentRequestDto.getId()).orElseThrow(()->new RuntimeException("document details not found"));
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(travelDocument.getUploadedBy().getId() != jwtInfoDto.getUserId()){
            throw new RuntimeException("Invalid action");
        }
        travelDocument.setDocumentPath(documentRequestDto.getDocumentPath());
        travelDocument.setDescription(documentRequestDto.getDescription());
        travelDocument.setType(documentRequestDto.getType());
        travelDocument = repository.save(travelDocument);
        return modelMapper.map(travelDocument,TravelDocumentResponseDto.class);
    }
}
