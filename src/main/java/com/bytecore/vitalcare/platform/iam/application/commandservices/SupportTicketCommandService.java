package com.bytecore.vitalcare.platform.iam.application.commandservices;

import com.bytecore.vitalcare.platform.iam.domain.model.commands.CreateSupportTicketCommand;
import com.bytecore.vitalcare.platform.iam.domain.model.entities.SupportTicket;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;

public interface SupportTicketCommandService {
    Result<SupportTicket, ApplicationError> handle(CreateSupportTicketCommand command);
}
