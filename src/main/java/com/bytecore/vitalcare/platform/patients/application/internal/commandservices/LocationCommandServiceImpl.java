package com.bytecore.vitalcare.platform.patients.application.internal.commandservices;

import com.bytecore.vitalcare.platform.patients.application.commandservices.LocationCommandService;
import com.bytecore.vitalcare.platform.patients.domain.model.aggregates.Location;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeleteLocationCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.RecordLocationCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdateLocationCommand;
import com.bytecore.vitalcare.platform.patients.infrastructure.persistence.jpa.repositories.LocationJpaRepository;
import com.bytecore.vitalcare.platform.shared.application.result.ApplicationError;
import com.bytecore.vitalcare.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationCommandServiceImpl implements LocationCommandService {

    private final LocationJpaRepository locationRepository;

    public LocationCommandServiceImpl(LocationJpaRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional
    public Result<Location, ApplicationError> handle(RecordLocationCommand command) {
        var location = new Location(command.latitude(), command.longitude(), command.patchId());
        return Result.success(locationRepository.save(location));
    }

    @Override
    @Transactional
    public Result<Location, ApplicationError> handle(UpdateLocationCommand command) {
        return locationRepository.findById(command.id()).map(location -> {
            location.setLatitude(command.latitude());
            location.setLongitude(command.longitude());
            location.setPatchId(command.patchId());
            location.setRecordedAt(command.recordedAt());
            return Result.<Location, ApplicationError>success(locationRepository.save(location));
        }).orElseGet(() -> Result.failure(ApplicationError.notFound("Location", command.id().toString())));
    }

    @Override
    @Transactional
    public Result<Void, ApplicationError> handle(DeleteLocationCommand command) {
        if (!locationRepository.existsById(command.locationId())) {
            return Result.failure(ApplicationError.notFound("Location", command.locationId().toString()));
        }
        locationRepository.deleteById(command.locationId());
        return Result.success(null);
    }
}
