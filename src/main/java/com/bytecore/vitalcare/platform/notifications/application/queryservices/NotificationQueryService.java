package com.bytecore.vitalcare.platform.notifications.application.queryservices;

import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Notification;
import com.bytecore.vitalcare.platform.notifications.domain.model.queries.GetNotificationsByUserIdQuery;

import java.util.List;

public interface NotificationQueryService {
    List<Notification> handle(GetNotificationsByUserIdQuery query);
}
