package com.hrms.backend.dtos.requestDto.travel;

import lombok.Data;

@Data
public class AddUpdateTravelDocumentRequestDto {
    private Long id;
    private String type;
    private String description;
    private String documentPath;
}
