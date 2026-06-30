package com.bytecore.vitalcare.platform.patients.application.queryservices;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.VitalSign;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetVitalSignByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetVitalSignsByPatchIdQuery;

import java.util.List;
import java.util.Optional;

public interface VitalSignQueryService {
    Optional<VitalSign> handle(GetVitalSignByIdQuery query);
    List<VitalSign> handle(GetVitalSignsByPatchIdQuery query);
}
