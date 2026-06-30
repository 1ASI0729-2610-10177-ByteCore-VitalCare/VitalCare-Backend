package com.bytecore.vitalcare.platform.iam.infrastructure.persistence.jpa.repositories;

import com.bytecore.vitalcare.platform.iam.domain.model.entities.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    @Query("SELECT t FROM SupportTicket t JOIN User u ON t MEMBER OF u.supportTickets WHERE u.id = :userId")
    List<SupportTicket> findByUserId(@Param("userId") Long userId);
}
