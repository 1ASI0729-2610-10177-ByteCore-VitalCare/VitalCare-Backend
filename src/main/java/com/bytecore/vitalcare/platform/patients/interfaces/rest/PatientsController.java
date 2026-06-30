package com.bytecore.vitalcare.platform.patients.interfaces.rest;

import com.bytecore.vitalcare.platform.patients.application.commandservices.PatientCommandService;
import com.bytecore.vitalcare.platform.patients.application.queryservices.PatientQueryService;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeletePatientCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdatePatientCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatientByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatientsByUserIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.valueobjects.Gender;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.CreatePatientResource;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.PatientResource;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.transform.CreatePatientCommandFromResourceAssembler;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.transform.PatientResourceFromEntityAssembler;
import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/patients", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Patients", description = "Patient management endpoints")
public class PatientsController {

    private final PatientCommandService commandService;
    private final PatientQueryService queryService;

    public PatientsController(PatientCommandService commandService, PatientQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResource>> getPatients(@RequestParam Long users_id) {
        var patients = queryService.handle(new GetPatientsByUserIdQuery(users_id)).stream()
                .map(PatientResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResource> getPatientById(@PathVariable Long id) {
        return queryService.handle(new GetPatientByIdQuery(id))
                .map(p -> ResponseEntity.ok(PatientResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody CreatePatientResource resource) {
        var command = CreatePatientCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PatientResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody CreatePatientResource resource) {
        var command = new UpdatePatientCommand(
                id,
                resource.name(),
                resource.birth_date() != null ? LocalDate.parse(resource.birth_date()) : null,
                resource.gender() != null ? Gender.valueOf(resource.gender().toUpperCase()) : null,
                resource.photo(),
                resource.users_id()
        );
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PatientResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        var result = commandService.handle(new DeletePatientCommand(id));
        return result instanceof com.bytecore.vitalcare.platform.shared.application.result.Result.Success<?, ?>
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
