package com.bytecore.vitalcare.platform.iam.domain.model.entities;

import com.bytecore.vitalcare.platform.iam.domain.model.valueobjects.SupportTicketStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SupportTicket {
    private Long id;
    private String subject;
    private String message;
    private SupportTicketStatus status;

    public SupportTicket(String subject, String message) {
        this.subject = subject;
        this.message = message;
        this.status = SupportTicketStatus.OPEN;
    }
}
