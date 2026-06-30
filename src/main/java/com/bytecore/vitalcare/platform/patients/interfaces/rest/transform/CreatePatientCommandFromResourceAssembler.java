package com.bytecore.vitalcare.platform.patients.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.patients.domain.model.commands.CreatePatientCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.valueobjects.Gender;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.CreatePatientResource;

import java.time.LocalDate;

public class CreatePatientCommandFromResourceAssembler {

    public static CreatePatientCommand toCommandFromResource(CreatePatientResource resource) {
        return new CreatePatientCommand(
                resource.name(),
                resource.birth_date() != null ? LocalDate.parse(resource.birth_date()) : null,
                resource.gender() != null ? Gender.valueOf(resource.gender().toUpperCase()) : null,
                resource.photo(),
                resource.users_id()
        );
    }
}
