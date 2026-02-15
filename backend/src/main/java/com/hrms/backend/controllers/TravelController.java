package com.hrms.backend.controllers;

import com.hrms.backend.dtos.requestDto.travel.AddTravelDocumentRequestDto;
import com.hrms.backend.dtos.requestDto.travel.CreateTravelRequestDto;
import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDetailResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDocumentResponseDto;
import com.hrms.backend.services.TravelServices.TravelService;
import com.hrms.backend.utils.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TravelController {
    private final TravelService travelService;
    @Autowired
    public  TravelController(TravelService travelService){
        this.travelService = travelService;
    }

    @PostMapping(value = "/travels")
    public ResponseEntity<GlobalResponseDto<TravelDetailResponseDto>> createTravel(@RequestBody CreateTravelRequestDto requestDto){
        TravelDetailResponseDto responseDto = travelService.createTravel(requestDto);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }

    @PostMapping("/travel/{travelId}")
    public ResponseEntity<GlobalResponseDto<TravelDocumentResponseDto>> addTravelDocument(@PathVariable Long travelId, @RequestPart AddTravelDocumentRequestDto documentDetails, @RequestPart MultipartFile file){
        String filePath = FileUtility.Save(file,"travel-document");
        TravelDocumentResponseDto responseDto = travelService.addDocument(travelId,documentDetails,filePath);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(null)
        );
    }

    @GetMapping("/travel/{travelId}")
    public ResponseEntity<GlobalResponseDto<TravelDetailResponseDto>> getTravelDetail(@PathVariable Long travelId){
        TravelDetailResponseDto travelDetail = travelService.getTravelDetail(travelId);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(travelDetail)
        );
    }
}
