package com.bytecore.vitalcare.platform.patients.interfaces.rest;

import com.bytecore.vitalcare.platform.patients.application.commandservices.LocationCommandService;
import com.bytecore.vitalcare.platform.patients.application.queryservices.LocationQueryService;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeleteLocationCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.RecordLocationCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdateLocationCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetLocationByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetLocationsByPatchIdQuery;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.CreateLocationResource;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.LocationResource;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.transform.LocationResourceFromEntityAssembler;
import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/locations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Locations", description = "Location management endpoints")
public class LocationsController {

    private final LocationCommandService commandService;
    private final LocationQueryService queryService;

    public LocationsController(LocationCommandService commandService, LocationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<LocationResource>> getByPatchId(@RequestParam Long patches_id) {
        var locations = queryService.handle(new GetLocationsByPatchIdQuery(patches_id)).stream()
                .map(LocationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResource> getById(@PathVariable Long id) {
        return queryService.handle(new GetLocationByIdQuery(id))
                .map(l -> ResponseEntity.ok(LocationResourceFromEntityAssembler.toResourceFromEntity(l)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateLocationResource resource) {
        var command = new RecordLocationCommand(resource.patches_id(), resource.latitude(), resource.longitude());
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, LocationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CreateLocationResource resource) {
        var command = new UpdateLocationCommand(id, resource.patches_id(), resource.latitude(), resource.longitude(), LocalDateTime.now());
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, LocationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var result = commandService.handle(new DeleteLocationCommand(id));
        return result instanceof com.bytecore.vitalcare.platform.shared.application.result.Result.Success<?, ?>
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
