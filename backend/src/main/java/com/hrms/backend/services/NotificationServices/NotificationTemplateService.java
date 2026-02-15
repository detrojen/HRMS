package com.hrms.backend.services.NotificationServices;

import com.hrms.backend.entities.Notification.NotificationTemplate;
import com.hrms.backend.repositories.NotificatioRepositories.NotificationTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationTemplateService {
    private final NotificationTemplateRepository notificationTemplateRepository;

    @Autowired
    public NotificationTemplateService(NotificationTemplateRepository notificationTemplateRepository){
        this.notificationTemplateRepository = notificationTemplateRepository;
    }

    public NotificationTemplate createTemplate(String message, String type){
        NotificationTemplate template = new NotificationTemplate();
        template.setMessage(message);
        template.setType(type);
        return notificationTemplateRepository.save(template);
    }
}
