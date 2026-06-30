package com.bytecore.vitalcare.platform.iam.interfaces.rest.resources;

public record CreateSupportTicketResource(
        Long users_id,
        String subject,
        String message
) {}
