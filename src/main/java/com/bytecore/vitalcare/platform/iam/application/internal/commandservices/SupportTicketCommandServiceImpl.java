package com.bytecore.vitalcare.platform.iam.application.internal.commandservices;

import com.bytecore.vitalcare.platform.iam.application.commandservices.SupportTicketCommandService;
import com.bytecore.vitalcare.platform.iam.domain.model.commands.CreateSupportTicketCommand;
import com.bytecore.vitalcare.platform.iam.domain.model.entities.SupportTicket;
import com.bytecore.vitalcare.platform.iam.infrastructure.persistence.jpa.repositories.SupportTicketRepository;
import com.bytecore.vitalcare.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupportTicketCommandServiceImpl implements SupportTicketCommandService {

    private final UserRepository userRepository;
    private final SupportTicketRepository supportTicketRepository;

    public SupportTicketCommandServiceImpl(UserRepository userRepository,
                                           SupportTicketRepository supportTicketRepository) {
        this.userRepository = userRepository;
        this.supportTicketRepository = supportTicketRepository;
    }

    @Override
    @Transactional
    public Result<SupportTicket, ApplicationError> handle(CreateSupportTicketCommand command) {
        return userRepository.findById(command.userId()).map(user -> {
            var ticket = new SupportTicket(command.subject(), command.message());
            var saved = supportTicketRepository.save(ticket);
            user.addSupportTicket(saved);
            userRepository.save(user);
            return Result.<SupportTicket, ApplicationError>success(saved);
        }).orElseGet(() -> Result.failure(ApplicationError.notFound("User", command.userId().toString())));
    }
}
