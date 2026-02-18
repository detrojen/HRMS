package com.hrms.backend.dtos.responseDtos.travel;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import lombok.Data;

@Data
public class TravelDocumentResponseDto {
    private  Long id;
    private String type;
    private String documentPath;
    private String description;
    private EmployeeWithNameOnlyDto uploadedBy;
//    private TravelMinDetailResponseDto travel;
}
