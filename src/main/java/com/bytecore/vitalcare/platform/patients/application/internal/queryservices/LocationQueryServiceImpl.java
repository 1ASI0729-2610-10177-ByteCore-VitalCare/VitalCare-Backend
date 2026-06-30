package com.bytecore.vitalcare.platform.patients.application.internal.queryservices;

import com.bytecore.vitalcare.platform.patients.application.queryservices.LocationQueryService;
import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Location;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetLocationByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetLocationsByPatchIdQuery;
import com.bytecore.vitalcare.platform.patients.infrastructure.persistence.jpa.repositories.LocationJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationQueryServiceImpl implements LocationQueryService {

    private final LocationJpaRepository locationRepository;

    public LocationQueryServiceImpl(LocationJpaRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Optional<Location> handle(GetLocationByIdQuery query) {
        return locationRepository.findById(query.locationId());
    }

    @Override
    public List<Location> handle(GetLocationsByPatchIdQuery query) {
        return locationRepository.findByPatchId(query.patchId());
    }
}
