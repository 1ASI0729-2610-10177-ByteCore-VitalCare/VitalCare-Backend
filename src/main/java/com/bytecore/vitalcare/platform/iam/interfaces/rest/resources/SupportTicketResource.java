package com.bytecore.vitalcare.platform.iam.interfaces.rest.resources;

public record SupportTicketResource(
        Long id,
        String subject,
        String message,
        String status,
        Long users_id
) {}
