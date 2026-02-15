package com.hrms.backend.controllers;

import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.dtos.responseDtos.NotificationTemplateResponseDto;
import com.hrms.backend.services.NotificationServices.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public ResponseEntity<GlobalResponseDto<List<NotificationTemplateResponseDto>>> getAllNotifications(){
        List<NotificationTemplateResponseDto> notifications = notificationService.getNonReadNotifications();
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(notifications)
        );
    }
}
