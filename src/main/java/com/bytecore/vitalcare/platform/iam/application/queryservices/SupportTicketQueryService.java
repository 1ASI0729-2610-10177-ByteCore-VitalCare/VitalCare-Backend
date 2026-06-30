package com.bytecore.vitalcare.platform.iam.application.queryservices;

import com.bytecore.vitalcare.platform.iam.domain.model.entities.SupportTicket;
import com.bytecore.vitalcare.platform.iam.domain.model.queries.GetSupportTicketsByUserIdQuery;

import java.util.List;

public interface SupportTicketQueryService {
    List<SupportTicket> handle(GetSupportTicketsByUserIdQuery query);
}
