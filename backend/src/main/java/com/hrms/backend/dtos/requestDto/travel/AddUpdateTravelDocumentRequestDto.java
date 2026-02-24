package com.hrms.backend.dtos.requestDto.travel;

import com.hrms.backend.dtos.markers.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddUpdateTravelDocumentRequestDto {
    @NotNull(message = "id should be provided in update request",groups = {OnUpdate.class})
    private Long id;
    @NotBlank
    private String type;
    @NotBlank
    private String description;
    @NotBlank(message = "documentPath should be provided in update request",groups = {OnUpdate.class})
    private String documentPath;
}
