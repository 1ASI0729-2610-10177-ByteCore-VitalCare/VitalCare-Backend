package com.bytecore.vitalcare.platform.notifications.application.internal.commandservices;

import com.bytecore.vitalcare.platform.notifications.application.commandservices.NotificationCommandService;
import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Notification;
import com.bytecore.vitalcare.platform.notifications.domain.model.commands.CreateNotificationCommand;
import com.bytecore.vitalcare.platform.notifications.domain.model.commands.DeleteNotificationCommand;
import com.bytecore.vitalcare.platform.notifications.infrastructure.persistence.jpa.repositories.NotificationJpaRepository;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationJpaRepository notificationRepository;

    public NotificationCommandServiceImpl(NotificationJpaRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public Result<Notification, ApplicationError> handle(CreateNotificationCommand command) {
        var notification = new Notification(
                command.nombre(), command.fecha(), command.descripcion(),
                command.patientId(), command.usersId()
        );
        return Result.success(notificationRepository.save(notification));
    }

    @Override
    @Transactional
    public Result<Void, ApplicationError> handle(DeleteNotificationCommand command) {
        if (!notificationRepository.existsById(command.notificationId())) {
            return Result.failure(ApplicationError.notFound("Notification", command.notificationId().toString()));
        }
        notificationRepository.deleteById(command.notificationId());
        return Result.success(null);
    }
}
