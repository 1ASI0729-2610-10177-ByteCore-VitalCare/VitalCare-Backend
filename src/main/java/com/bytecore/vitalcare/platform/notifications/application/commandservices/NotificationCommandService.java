package com.bytecore.vitalcare.platform.notifications.application.commandservices;

import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Notification;
import com.bytecore.vitalcare.platform.notifications.domain.model.commands.CreateNotificationCommand;
import com.bytecore.vitalcare.platform.notifications.domain.model.commands.DeleteNotificationCommand;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;

public interface NotificationCommandService {
    Result<Notification, ApplicationError> handle(CreateNotificationCommand command);
    Result<Void, ApplicationError> handle(DeleteNotificationCommand command);
}
