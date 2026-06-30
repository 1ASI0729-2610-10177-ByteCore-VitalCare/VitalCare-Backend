package com.bytecore.vitalcare.platform.patients.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Patient;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.PatientResource;

public class PatientResourceFromEntityAssembler {

    public static PatientResource toResourceFromEntity(Patient patient) {
        return new PatientResource(
                patient.getId(),
                patient.getName(),
                patient.getBirthDate() != null ? patient.getBirthDate().toString() : null,
                patient.getGender() != null ? patient.getGender().name() : null,
                patient.getPhoto(),
                patient.getUserId()
        );
    }
}
