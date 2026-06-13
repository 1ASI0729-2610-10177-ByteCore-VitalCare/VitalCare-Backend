package com.bytecore.vitalcare.platform.notifications.application.internal.commandservices;

import com.bytecore.vitalcare.platform.notifications.application.commandservices.AlertCommandService;
import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Alert;
import com.bytecore.vitalcare.platform.notifications.domain.model.commands.CreateAlertCommand;
import com.bytecore.vitalcare.platform.notifications.domain.model.commands.MarkAlertAsReadCommand;
import com.bytecore.vitalcare.platform.notifications.domain.repositories.AlertRepository;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class AlertCommandServiceImpl implements AlertCommandService {

    private final AlertRepository alertRepository;

    public AlertCommandServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Result<Alert, ApplicationError> handle(CreateAlertCommand command) {
        var alert = new Alert(command.type(), command.description(), command.userId(), command.patientId());
        var saved = alertRepository.save(alert);
        return Result.success(saved);
    }

    @Override
    public Result<Alert, ApplicationError> handle(MarkAlertAsReadCommand command) {
        var alertOptional = alertRepository.findById(command.alertId());
        if (alertOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Alert", "id=" + command.alertId()));
        }
        var alert = alertOptional.get();
        alert.markAsRead();
        var saved = alertRepository.save(alert);
        return Result.success(saved);
    }
}
