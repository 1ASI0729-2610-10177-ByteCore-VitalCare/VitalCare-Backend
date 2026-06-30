package com.bytecore.vitalcare.platform.patients.application.queryservices;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Location;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetLocationByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetLocationsByPatchIdQuery;

import java.util.List;
import java.util.Optional;

public interface LocationQueryService {
    Optional<Location> handle(GetLocationByIdQuery query);
    List<Location> handle(GetLocationsByPatchIdQuery query);
}
