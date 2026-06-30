package com.bytecore.vitalcare.platform.notifications.infrastructure.persistence.jpa.repositories;

import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUsersId(Long usersId);
}
