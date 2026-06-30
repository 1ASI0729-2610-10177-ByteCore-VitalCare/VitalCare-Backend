package com.bytecore.vitalcare.platform.patients.application.queryservices;

import com.bytecore.vitalcare.platform.patients.domain.model.entities.Patch;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatchByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatchesByPatientIdQuery;

import java.util.List;
import java.util.Optional;

public interface PatchQueryService {
    Optional<Patch> handle(GetPatchByIdQuery query);
    List<Patch> handle(GetPatchesByPatientIdQuery query);
}
