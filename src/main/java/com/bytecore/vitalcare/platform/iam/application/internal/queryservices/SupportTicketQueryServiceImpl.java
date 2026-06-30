package com.bytecore.vitalcare.platform.iam.application.internal.queryservices;

import com.bytecore.vitalcare.platform.iam.application.queryservices.SupportTicketQueryService;
import com.bytecore.vitalcare.platform.iam.domain.model.entities.SupportTicket;
import com.bytecore.vitalcare.platform.iam.domain.model.queries.GetSupportTicketsByUserIdQuery;
import com.bytecore.vitalcare.platform.iam.infrastructure.persistence.jpa.repositories.SupportTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportTicketQueryServiceImpl implements SupportTicketQueryService {

    private final SupportTicketRepository supportTicketRepository;

    public SupportTicketQueryServiceImpl(SupportTicketRepository supportTicketRepository) {
        this.supportTicketRepository = supportTicketRepository;
    }

    @Override
    public List<SupportTicket> handle(GetSupportTicketsByUserIdQuery query) {
        return supportTicketRepository.findByUserId(query.userId());
    }
}
