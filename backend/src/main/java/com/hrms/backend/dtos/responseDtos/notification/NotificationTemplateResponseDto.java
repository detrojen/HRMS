package com.hrms.backend.dtos.responseDtos.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class NotificationTemplateResponseDto {
    private Long id;
    private String message;
    private String type;
    public NotificationTemplateResponseDto(Long id, String message, String type){
        this.id = id;
        this.message = message;
        this.type = type;
    }
}
