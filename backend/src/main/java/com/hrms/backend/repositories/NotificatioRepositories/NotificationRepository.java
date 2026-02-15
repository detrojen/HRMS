package com.hrms.backend.repositories.NotificatioRepositories;

import com.hrms.backend.dtos.responseDtos.notification.NotificationTemplateResponseDto;
import com.hrms.backend.entities.Notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> , JpaSpecificationExecutor<Notification> {
    Notification findByEmployee_IdAndTemplate_Id(Long employeeId, Long templateId);

    @Query(value = "select new com.hrms.backend.dtos.responseDtos.notification.NotificationTemplateResponseDto( t.id,t.message as message,t.type) from Notification n join n.template t join n.employee e where e.id =:employeeId and n.isRead = false ",nativeQuery = false)
    List<NotificationTemplateResponseDto> findAllNonReadNotifications(Long employeeId);
}
