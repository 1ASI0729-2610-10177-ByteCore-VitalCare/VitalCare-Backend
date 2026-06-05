package com.bytecore.vitalcare.platform.patients.domain.model.commands;

import com.bytecore.vitalcare.platform.patients.domain.model.valueobjects.PatchStatus;

public record UpdatePatchStatusCommand(Long patchId, PatchStatus status) {
}
