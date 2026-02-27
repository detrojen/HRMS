package com.hrms.backend.controllers;

import com.hrms.backend.dtos.markers.OnUpdate;
import com.hrms.backend.dtos.requestDto.ReviewTravelExpenseRequestDto;
import com.hrms.backend.dtos.requestDto.travel.*;
import com.hrms.backend.dtos.requestParamDtos.TravelExpenseParamsDto;
import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.*;
import com.hrms.backend.services.TravelServices.ExpenseCategoryService;
import com.hrms.backend.services.TravelServices.TravelService;
import com.hrms.backend.services.TravelServices.TravelWiseExpenseService;
import com.hrms.backend.utils.FileUtility;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
public class TravelController {
    private final TravelService travelService;
    private final TravelWiseExpenseService expenseService;
    private final ExpenseCategoryService expenseCategoryService;
    @Autowired
    public  TravelController(TravelService travelService
            , TravelWiseExpenseService expenseService
            ,ExpenseCategoryService expenseCategoryService
    ){
        this.travelService = travelService;
        this.expenseService = expenseService;
        this.expenseCategoryService= expenseCategoryService;
    }

    @PostMapping(value = "/travels")
    public ResponseEntity<GlobalResponseDto<TravelDetailResponseDto>> createTravel(@RequestBody @Valid CreateTravelRequestDto requestDto){
        TravelDetailResponseDto responseDto = travelService.createTravel(requestDto);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }
    @PatchMapping(value = "/travels")
    public ResponseEntity<GlobalResponseDto<TravelDetailResponseDto>> updateTravel(@RequestBody @Valid UpdateTravelRequestDto requestDto){
        TravelDetailResponseDto responseDto = travelService.updateTravel(requestDto);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }

    @PatchMapping(value = "/travels/{travelId}/add-employees")
    public ResponseEntity<GlobalResponseDto<TravelMinDetailResponseDto>> addEmployeeToTravel(@PathVariable Long travelId, @RequestBody AddEmployeesToTravelRequestDto requestDto){
        TravelMinDetailResponseDto responseDto = travelService.addEmployeesToTravel(travelId,requestDto);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(responseDto,"Employees added"));
    }

    @PostMapping("/travels/{travelId}/documents")
    public ResponseEntity<GlobalResponseDto<TravelDocumentResponseDto>> addTravelDocument(@PathVariable Long travelId, @RequestPart(value = "documentDetails") AddUpdateTravelDocumentRequestDto documentDetails, @RequestPart(value = "file") MultipartFile file){
        String filePath = FileUtility.Save(file,"travel-documents");
        TravelDocumentResponseDto responseDto = travelService.addDocument(travelId,documentDetails,filePath);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto,"travel document successfully added")
        );
    }
    @PutMapping("/travels/{travelId}/documents")
    public ResponseEntity<GlobalResponseDto<TravelDocumentResponseDto>> updateTravelDocument(@PathVariable Long travelId, @RequestPart(value = "documentDetails") AddUpdateTravelDocumentRequestDto documentDetails, @RequestPart(value = "file") Optional<MultipartFile> file){
        if(file.isPresent()){
            String filePath = FileUtility.Save(file.get(),"travel-documents");
            documentDetails.setDocumentPath(filePath);
        }
        TravelDocumentResponseDto responseDto = travelService.updateDocument(travelId,documentDetails);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto, "travel document successfully updated")
        );
    }

    @PostMapping("/travels/{travelId}/employee-documents")
    public ResponseEntity<GlobalResponseDto<TravelDocumentResponseDto>> addEmployeeTravelDocument(@PathVariable Long travelId, @RequestPart(value = "documentDetails") AddUpdateTravelDocumentRequestDto documentDetails, @RequestPart(value = "file") MultipartFile file){
        String filePath = FileUtility.Save(file,"travel-documents");
        TravelDocumentResponseDto responseDto = travelService.addEmployeeDocument(travelId,documentDetails,filePath);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }
    @PutMapping("/travels/{travelId}/employee-documents")
    public ResponseEntity<GlobalResponseDto<TravelDocumentResponseDto>> updateEmployeeTravelDocument(@PathVariable Long travelId, @RequestPart(value = "documentDetails") AddUpdateTravelDocumentRequestDto documentDetails, @RequestPart(value = "file") Optional<MultipartFile> file){
        if(file.isPresent()){
            String filePath = FileUtility.Save(file.get(),"travel-documents");
            documentDetails.setDocumentPath(filePath);
        }
        TravelDocumentResponseDto responseDto = travelService.updateEmployeeDocument(travelId,documentDetails);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }

    @PostMapping("/travels/{travelId}/expenses")
    public ResponseEntity<GlobalResponseDto<TravelExpenseResponseDto>> addExpense(@PathVariable Long travelId, @RequestPart(value = "expenseDetails") @Valid AddUpdateTravelExpenseRequestDto expenseDetails, @RequestPart(value = "file") @NotNull(message = "Proof must be required") MultipartFile file){
        String filePath = FileUtility.Save(file,"expenses");
        TravelExpenseResponseDto responseDto = travelService.addExpense(travelId,expenseDetails,filePath);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }

    @PutMapping("/travels/{travelId}/expenses")
    public ResponseEntity<GlobalResponseDto<TravelExpenseResponseDto>> updateExpense(@PathVariable Long travelId, @RequestPart(value = "expenseDetails") @Validated(OnUpdate.class) AddUpdateTravelExpenseRequestDto expenseDetails, @RequestPart(value = "file") Optional<MultipartFile> file){
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
    @GetMapping("/travels/min-details/{travelId}")
    public ResponseEntity<GlobalResponseDto<TravelMinDetailResponseDto>> getTravelMinDetail(@PathVariable Long travelId){
        TravelMinDetailResponseDto travelDetail = travelService.getTravelMinDetail(travelId);
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

    @GetMapping("/travels/list/{getAsa}")
    public ResponseEntity<GlobalResponseDto<List<TravelMinDetailResponseDto>>> getTravelsAsManager(@PathVariable String getAsa){
        List<TravelMinDetailResponseDto> travels = travelService.getTravels(getAsa);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(travels)
        );
    }

    @PatchMapping("/travels/expenses")
    public ResponseEntity<GlobalResponseDto<TravelExpenseResponseDto>> reviewExpense(@RequestBody @Valid ReviewTravelExpenseRequestDto requestDto){
        TravelExpenseResponseDto expense = expenseService.reviewExpense(requestDto);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(expense)
        );
    }
    @GetMapping("/travels/expenses/categories")
    public ResponseEntity<GlobalResponseDto<List<ExpenseCategoryDto>>> getExpenseCategories(){
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(
                        expenseCategoryService.getExpenseCategories()
                )
        );
    }
}
