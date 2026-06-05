package com.bytecore.vitalcare.platform.patients.domain.repositories;

import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Location;

import java.util.List;
import java.util.Optional;

public interface LocationRepository {
    Optional<Location> findById(Long id);
    List<Location> findByPatchId(Long patchId);
    Location save(Location location);
}
