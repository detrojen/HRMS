package com.hrms.backend.dtos.requestDto.travel;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddTravelDocumentRequestDto {
    private Long id;
    private String type;
    private String description;
}
