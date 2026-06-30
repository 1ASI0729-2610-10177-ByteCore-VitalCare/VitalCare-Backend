package com.bytecore.vitalcare.platform.iam.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.iam.domain.model.entities.SupportTicket;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.resources.SupportTicketResource;

public class SupportTicketResourceFromEntityAssembler {

    public static SupportTicketResource toResourceFromEntity(SupportTicket ticket, Long userId) {
        return new SupportTicketResource(
                ticket.getId(),
                ticket.getSubject(),
                ticket.getMessage(),
                ticket.getStatus().name(),
                userId
        );
    }
}
