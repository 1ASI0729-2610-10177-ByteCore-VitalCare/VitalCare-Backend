package com.bytecore.vitalcare.platform.patients.interfaces.rest;

import com.bytecore.vitalcare.platform.patients.application.commandservices.PatientCommandService;
import com.bytecore.vitalcare.platform.patients.application.queryservices.PatientQueryService;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.CreatePatientCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeletePatientCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdatePatientCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatientByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatientsByUserIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.valueobjects.Gender;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.CreatePatientResource;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.PatientResource;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.transform.CreatePatientCommandFromResourceAssembler;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.transform.PatientResourceFromEntityAssembler;
import com.bytecore.vitalcare.platform.iam.infrastructure.security.UserDetailsImpl;
import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<List<PatientResource>> getPatients(@AuthenticationPrincipal UserDetailsImpl user) {
        var patients = queryService.handle(new GetPatientsByUserIdQuery(user.getId())).stream()
                .map(PatientResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResource> getPatientById(@PathVariable Long id,
                                                          @AuthenticationPrincipal UserDetailsImpl user) {
        return queryService.handle(new GetPatientByIdQuery(id))
                .filter(p -> p.getUserId().equals(user.getId()))
                .map(p -> ResponseEntity.ok(PatientResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** True only if the patient exists and belongs to the authenticated user. */
    private boolean owns(Long patientId, Long userId) {
        return queryService.handle(new GetPatientByIdQuery(patientId))
                .filter(p -> p.getUserId().equals(userId))
                .isPresent();
    }

    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody CreatePatientResource resource,
                                           @AuthenticationPrincipal UserDetailsImpl user) {
        // Ownership comes from the token, never from the request body.
        var command = new CreatePatientCommand(
                resource.name(),
                resource.birth_date() != null ? LocalDate.parse(resource.birth_date()) : null,
                resource.gender() != null ? Gender.valueOf(resource.gender().toUpperCase()) : null,
                resource.photo(),
                user.getId()
        );
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PatientResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody CreatePatientResource resource,
                                           @AuthenticationPrincipal UserDetailsImpl user) {
        if (!owns(id, user.getId())) {
            return ResponseEntity.notFound().build();
        }
        var command = new UpdatePatientCommand(
                id,
                resource.name(),
                resource.birth_date() != null ? LocalDate.parse(resource.birth_date()) : null,
                resource.gender() != null ? Gender.valueOf(resource.gender().toUpperCase()) : null,
                resource.photo(),
                user.getId()
        );
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PatientResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetailsImpl user) {
        if (!owns(id, user.getId())) {
            return ResponseEntity.notFound().build();
        }
        var result = commandService.handle(new DeletePatientCommand(id));
        return result instanceof com.bytecore.vitalcare.platform.shared.application.result.Result.Success<?, ?>
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
