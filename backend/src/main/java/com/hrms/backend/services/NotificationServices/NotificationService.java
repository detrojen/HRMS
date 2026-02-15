package com.hrms.backend.services.NotificationServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.responseDtos.NotificationTemplateResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.Notification.Notification;
import com.hrms.backend.entities.Notification.NotificationTemplate;
import com.hrms.backend.repositories.NotificatioRepositories.NotificationRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final NotificationTemplateService notificationTemplateService;
    @Autowired
    public NotificationService (NotificationRepository notificationRepository,EmployeeService employeeService, ModelMapper modelMapper, NotificationTemplateService notificationTemplateService){
        this.notificationRepository = notificationRepository;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
        this.notificationTemplateService = notificationTemplateService;
    }

    public void notify(String message, String type, Long[] employeeIds){
        NotificationTemplate template = notificationTemplateService.createTemplate(message,type);
        List<Notification> notifications = Arrays.stream(employeeIds).toList().stream().map(
                id->{
                    Notification notification = new Notification();
                    notification.setTemplate(template);
                    notification.setEmployee(employeeService.getReference(id));
                    return notification;
                }
        ).collect(Collectors.toUnmodifiableList());
        notificationRepository.saveAll(notifications);
    }

    public void notify(String message, String type, List<Employee> employees){
        NotificationTemplate template = notificationTemplateService.createTemplate(message,type);
        List<Notification> notifications = employees.stream().map(
                employee->{
                    Notification notification = new Notification();
                    notification.setTemplate(template);
                    notification.setEmployee(employee);
                    return notification;
                }
        ).collect(Collectors.toUnmodifiableList());
        notificationRepository.saveAll(notifications);
    }

    public void setIsRead(Long templateId){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notification notification = notificationRepository.findByEmployee_IdAndTemplate_Id(jwtInfoDto.getUserId(), templateId);
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public List<NotificationTemplateResponseDto> getNonReadNotifications(){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return notificationRepository.findAllNonReadNotifications(jwtInfoDto.getUserId());
    }
}
