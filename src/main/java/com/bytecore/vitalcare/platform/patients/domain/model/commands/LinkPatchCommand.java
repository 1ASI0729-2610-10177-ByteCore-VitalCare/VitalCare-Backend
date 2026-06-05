package com.bytecore.vitalcare.platform.patients.domain.model.commands;

public record LinkPatchCommand(Long patientId, String patchCode) {
}
