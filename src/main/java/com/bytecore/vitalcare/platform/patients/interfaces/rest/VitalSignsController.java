package com.bytecore.vitalcare.platform.patients.interfaces.rest;

import com.bytecore.vitalcare.platform.patients.application.commandservices.VitalSignCommandService;
import com.bytecore.vitalcare.platform.patients.application.queryservices.VitalSignQueryService;
import com.bytecore.vitalcare.platform.patients.application.queryservices.PatchQueryService;
import com.bytecore.vitalcare.platform.patients.application.queryservices.PatientQueryService;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeleteVitalSignCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.RecordVitalSignCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdateVitalSignCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetVitalSignByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetVitalSignsByPatchIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatchByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatientByIdQuery;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.CreateVitalSignResource;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.VitalSignResource;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.transform.VitalSignResourceFromEntityAssembler;
import com.bytecore.vitalcare.platform.iam.infrastructure.security.UserDetailsImpl;
import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/vital_signs", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Vital Signs", description = "Vital signs management endpoints")
public class VitalSignsController {

    private final VitalSignCommandService commandService;
    private final VitalSignQueryService queryService;
    private final PatchQueryService patchQueryService;
    private final PatientQueryService patientQueryService;

    public VitalSignsController(VitalSignCommandService commandService, VitalSignQueryService queryService,
                                PatchQueryService patchQueryService, PatientQueryService patientQueryService) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.patchQueryService = patchQueryService;
        this.patientQueryService = patientQueryService;
    }

    /** True only if the patch -> patient chain ultimately belongs to the authenticated user. */
    private boolean ownsPatch(Long patchId, Long userId) {
        return patchQueryService.handle(new GetPatchByIdQuery(patchId))
                .flatMap(patch -> patientQueryService.handle(new GetPatientByIdQuery(patch.getPatientId())))
                .filter(patient -> patient.getUserId().equals(userId))
                .isPresent();
    }

    @GetMapping
    public ResponseEntity<List<VitalSignResource>> getByPatchId(@RequestParam Long patches_id,
                                                                @AuthenticationPrincipal UserDetailsImpl user) {
        if (!ownsPatch(patches_id, user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var signs = queryService.handle(new GetVitalSignsByPatchIdQuery(patches_id)).stream()
                .map(VitalSignResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(signs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VitalSignResource> getById(@PathVariable Long id,
                                                     @AuthenticationPrincipal UserDetailsImpl user) {
        return queryService.handle(new GetVitalSignByIdQuery(id))
                .filter(vs -> ownsPatch(vs.getPatchId(), user.getId()))
                .map(vs -> ResponseEntity.ok(VitalSignResourceFromEntityAssembler.toResourceFromEntity(vs)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** True only if the vital sign exists and its patch chain belongs to the authenticated user. */
    private boolean ownsVitalSign(Long vitalSignId, Long userId) {
        return queryService.handle(new GetVitalSignByIdQuery(vitalSignId))
                .filter(vs -> ownsPatch(vs.getPatchId(), userId))
                .isPresent();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateVitalSignResource resource,
                                    @AuthenticationPrincipal UserDetailsImpl user) {
        if (!ownsPatch(resource.patches_id(), user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var command = new RecordVitalSignCommand(
                resource.patches_id(), resource.glucose_level(), resource.lactate_concentration(),
                resource.alcohol_level(), resource.ketones(), resource.blood_pressure(),
                resource.temperature(), resource.oxygen_saturation(), resource.sodium_potassium(),
                resource.heart_rate(), resource.cytokines(), resource.t_cells(),
                resource.humidity(), resource.atmospheric_pressure(), resource.air_quality()
        );
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, VitalSignResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CreateVitalSignResource resource,
                                    @AuthenticationPrincipal UserDetailsImpl user) {
        if (!ownsVitalSign(id, user.getId())) {
            return ResponseEntity.notFound().build();
        }
        var command = new UpdateVitalSignCommand(
                id, resource.patches_id(), LocalDateTime.now(),
                resource.glucose_level(), resource.lactate_concentration(),
                resource.alcohol_level(), resource.ketones(), resource.blood_pressure(),
                resource.temperature(), resource.oxygen_saturation(), resource.sodium_potassium(),
                resource.heart_rate(), resource.cytokines(), resource.t_cells(),
                resource.humidity(), resource.atmospheric_pressure(), resource.air_quality()
        );
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, VitalSignResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetailsImpl user) {
        if (!ownsVitalSign(id, user.getId())) {
            return ResponseEntity.notFound().build();
        }
        var result = commandService.handle(new DeleteVitalSignCommand(id));
        return result instanceof com.bytecore.vitalcare.platform.shared.application.result.Result.Success<?, ?>
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
