package com.hrms.backend.repositories.NotificatioRepositories;

import com.hrms.backend.entities.Notification.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate,Long> {
}
