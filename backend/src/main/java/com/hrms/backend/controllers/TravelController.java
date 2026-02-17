package com.hrms.backend.controllers;

import com.hrms.backend.dtos.requestDto.ReviewTravelExpenseRequestDto;
import com.hrms.backend.dtos.requestDto.travel.AddTravelDocumentRequestDto;
import com.hrms.backend.dtos.requestDto.travel.AddUpdateTravelExpenseRequestDto;
import com.hrms.backend.dtos.requestDto.travel.CreateTravelRequestDto;
import com.hrms.backend.dtos.requestParamDtos.TravelExpenseParamsDto;
import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelExpenseResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDetailResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDocumentResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelMinDetailResponseDto;
import com.hrms.backend.services.TravelServices.TravelService;
import com.hrms.backend.services.TravelServices.TravelWiseExpenseService;
import com.hrms.backend.utils.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
public class TravelController {
    private final TravelService travelService;
    private final TravelWiseExpenseService expenseService;
    @Autowired
    public  TravelController(TravelService travelService
            , TravelWiseExpenseService expenseService
    ){
        this.travelService = travelService;
        this.expenseService = expenseService;
    }

    @PostMapping(value = "/travels")
    public ResponseEntity<GlobalResponseDto<TravelDetailResponseDto>> createTravel(@RequestBody CreateTravelRequestDto requestDto){
        TravelDetailResponseDto responseDto = travelService.createTravel(requestDto);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }

    @PostMapping("/travels/{travelId}/documents")
    public ResponseEntity<GlobalResponseDto<TravelDocumentResponseDto>> addTravelDocument(@PathVariable Long travelId, @RequestPart(value = "documentDetails") AddTravelDocumentRequestDto documentDetails, @RequestPart(value = "file") MultipartFile file){
        String filePath = FileUtility.Save(file,"travel-documents");
        TravelDocumentResponseDto responseDto = travelService.addDocument(travelId,documentDetails,filePath);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }
    @PostMapping("/travels/{travelId}/employee-documents")
    public ResponseEntity<GlobalResponseDto<TravelDocumentResponseDto>> addEmployeeTravelDocument(@PathVariable Long travelId, @RequestPart(value = "documentDetails") AddTravelDocumentRequestDto documentDetails, @RequestPart(value = "file") MultipartFile file){
        String filePath = FileUtility.Save(file,"travel-documents");
        TravelDocumentResponseDto responseDto = travelService.addEmployeeDocument(travelId,documentDetails,filePath);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }

    @PostMapping("/travels/{travelId}/expenses")
    public ResponseEntity<GlobalResponseDto<TravelExpenseResponseDto>> addExpense(@PathVariable Long travelId, @RequestPart(value = "expenseDetails") AddUpdateTravelExpenseRequestDto expenseDetails, @RequestPart(value = "file") MultipartFile file){
        String filePath = FileUtility.Save(file,"expenses");
        TravelExpenseResponseDto responseDto = travelService.addExpense(travelId,expenseDetails,filePath);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }
    @PutMapping("/travels/{travelId}/expenses")
    public ResponseEntity<GlobalResponseDto<TravelExpenseResponseDto>> updateExpense(@PathVariable Long travelId, @RequestPart(value = "expenseDetails") AddUpdateTravelExpenseRequestDto expenseDetails, @RequestPart(value = "file") Optional<MultipartFile> file){
        String filePath = expenseDetails.getReciept();
        if(file.isPresent()){
            filePath = FileUtility.Save( file.get(),"expenses");
        }
        TravelExpenseResponseDto responseDto = travelService.updateExpense(travelId,expenseDetails,filePath);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }
    @GetMapping("/travels/{travelId}")
    public ResponseEntity<GlobalResponseDto<TravelDetailResponseDto>> getTravelDetail(@PathVariable Long travelId){
        TravelDetailResponseDto travelDetail = travelService.getTravelDetail(travelId);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(travelDetail)
        );
    }
    @GetMapping("/travels/{travelId}/expenses")
    public ResponseEntity<GlobalResponseDto<List<TravelExpenseResponseDto>>> getTravelExpenseDetail(@PathVariable Long travelId, @ModelAttribute TravelExpenseParamsDto paramsDto){
        List<TravelExpenseResponseDto> expenses = expenseService.getExpenses(travelId,paramsDto);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(expenses)
        );
    }
    @GetMapping("/travels/assigned-travels")
    public ResponseEntity<GlobalResponseDto<List<TravelMinDetailResponseDto>>> getAssignedTravels(){
        List<TravelMinDetailResponseDto> travels = travelService.getAssignedTravels();
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(travels)
        );
    }
    @PatchMapping("/travels/expenses")
    public ResponseEntity<GlobalResponseDto<TravelExpenseResponseDto>> reviewExpense(@RequestBody ReviewTravelExpenseRequestDto requestDto){
        TravelExpenseResponseDto expense = expenseService.reviewExpense(requestDto);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(expense)
        );
    }
}
