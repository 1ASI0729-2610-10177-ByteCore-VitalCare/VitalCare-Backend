package com.bytecore.vitalcare.platform.iam.domain.repositories;

import com.bytecore.vitalcare.platform.iam.domain.model.entities.SupportTicket;

import java.util.List;
import java.util.Optional;

public interface SupportTicketRepository {
    Optional<SupportTicket> findById(Long id);
    List<SupportTicket> findByUserId(Long userId);
    SupportTicket save(SupportTicket ticket);
}
