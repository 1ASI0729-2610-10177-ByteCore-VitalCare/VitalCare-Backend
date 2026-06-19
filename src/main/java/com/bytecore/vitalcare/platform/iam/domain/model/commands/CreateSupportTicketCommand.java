package com.bytecore.vitalcare.platform.iam.domain.model.commands;

public record CreateSupportTicketCommand(Long userId, String subject, String message) {
}
