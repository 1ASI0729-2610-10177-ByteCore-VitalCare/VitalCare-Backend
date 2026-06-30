package com.bytecore.vitalcare.platform.notifications.application.internal.queryservices;

import com.bytecore.vitalcare.platform.notifications.application.queryservices.NotificationQueryService;
import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Notification;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetNotificationsByUserIdQuery;
import com.bytecore.vitalcare.platform.notifications.infrastructure.persistence.jpa.repositories.NotificationJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationJpaRepository notificationRepository;

    public NotificationQueryServiceImpl(NotificationJpaRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> handle(GetNotificationsByUserIdQuery query) {
        return notificationRepository.findByUsersId(query.usersId());
    }
}
