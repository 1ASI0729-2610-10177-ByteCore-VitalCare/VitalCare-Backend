package com.bytecore.vitalcare.platform.patients.domain.model.commands;

import com.bytecore.vitalcare.platform.patients.domain.model.valueobjects.Gender;

import java.time.LocalDate;

public record CreatePatientCommand(String name, LocalDate birthDate, Gender gender, String photo, Long userId) {
}
