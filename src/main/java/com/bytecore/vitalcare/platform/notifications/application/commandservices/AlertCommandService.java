package com.bytecore.vitalcare.platform.notifications.application.commandservices;

import com.bytecore.vitalcare.platform.notifications.domain.model.aggregates.Alert;
import com.bytecore.vitalcare.platform.notifications.domain.model.commands.CreateAlertCommand;
import com.bytecore.vitalcare.platform.notifications.domain.model.commands.MarkAlertAsReadCommand;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;

public interface AlertCommandService {
    Result<Alert, ApplicationError> handle(CreateAlertCommand command);
    Result<Alert, ApplicationError> handle(MarkAlertAsReadCommand command);
}
