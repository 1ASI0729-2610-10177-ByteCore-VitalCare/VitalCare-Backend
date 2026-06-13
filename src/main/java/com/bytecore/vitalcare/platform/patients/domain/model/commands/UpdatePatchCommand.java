package com.bytecore.vitalcare.platform.patients.domain.model.commands;

import com.bytecore.vitalcare.platform.patients.domain.model.valueobjects.PatchStatus;

import java.time.LocalDateTime;

public record UpdatePatchCommand(
        Long id,
        String patchCode,
        LocalDateTime linkedAt,
        PatchStatus status,
        Long patientId
) {
}
