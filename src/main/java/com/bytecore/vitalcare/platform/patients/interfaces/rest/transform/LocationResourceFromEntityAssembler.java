package com.bytecore.vitalcare.platform.patients.interfaces.rest.transform;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Location;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.LocationResource;

public class LocationResourceFromEntityAssembler {

    public static LocationResource toResourceFromEntity(Location location) {
        return new LocationResource(
                location.getId(),
                location.getLatitude(),
                location.getLongitude(),
                location.getRecordedAt() != null ? location.getRecordedAt().toString() : null,
                location.getPatchId()
        );
    }
}
