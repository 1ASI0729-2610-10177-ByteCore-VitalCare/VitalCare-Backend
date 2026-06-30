package com.bytecore.vitalcare.platform.patients.application.queryservices;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Patient;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatientByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatientsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface PatientQueryService {
    Optional<Patient> handle(GetPatientByIdQuery query);
    List<Patient> handle(GetPatientsByUserIdQuery query);
}
