package com.bytecore.vitalcare.platform.patients.interfaces.rest;

import com.bytecore.vitalcare.platform.patients.application.commandservices.PatchCommandService;
import com.bytecore.vitalcare.platform.patients.application.queryservices.PatchQueryService;
import com.bytecore.vitalcare.platform.patients.application.queryservices.PatientQueryService;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.DeletePatchCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.LinkPatchCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.commands.UpdatePatchCommand;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatchByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatchesByPatientIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.queries.GetPatientByIdQuery;
import com.bytecore.vitalcare.platform.patients.domain.model.valueobjects.PatchStatus;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.CreatePatchResource;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.resources.PatchResource;
import com.bytecore.vitalcare.platform.patients.interfaces.rest.transform.PatchResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/patches", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Patches", description = "Biosensor patch management endpoints")
public class PatchesController {

    private final PatchCommandService commandService;
    private final PatchQueryService queryService;
    private final PatientQueryService patientQueryService;

    public PatchesController(PatchCommandService commandService, PatchQueryService queryService,
                             PatientQueryService patientQueryService) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.patientQueryService = patientQueryService;
    }

    /** True only if the given patient exists and belongs to the authenticated user. */
    private boolean ownsPatient(Long patientId, Long userId) {
        return patientQueryService.handle(new GetPatientByIdQuery(patientId))
                .filter(p -> p.getUserId().equals(userId))
                .isPresent();
    }

    @GetMapping
    public ResponseEntity<List<PatchResource>> getByPatientId(@RequestParam Long patients_id,
                                                              @AuthenticationPrincipal UserDetailsImpl user) {
        if (!ownsPatient(patients_id, user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var patches = queryService.handle(new GetPatchesByPatientIdQuery(patients_id)).stream()
                .map(PatchResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(patches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatchResource> getById(@PathVariable Long id,
                                                 @AuthenticationPrincipal UserDetailsImpl user) {
        return queryService.handle(new GetPatchByIdQuery(id))
                .filter(p -> ownsPatient(p.getPatientId(), user.getId()))
                .map(p -> ResponseEntity.ok(PatchResourceFromEntityAssembler.toResourceFromEntity(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** True only if the patch exists and its patient belongs to the authenticated user. */
    private boolean ownsPatch(Long patchId, Long userId) {
        return queryService.handle(new GetPatchByIdQuery(patchId))
                .filter(patch -> ownsPatient(patch.getPatientId(), userId))
                .isPresent();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreatePatchResource resource,
                                    @AuthenticationPrincipal UserDetailsImpl user) {
        if (!ownsPatient(resource.patients_id(), user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var command = new LinkPatchCommand(resource.patients_id(), resource.patch_code());
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, PatchResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CreatePatchResource resource,
                                    @AuthenticationPrincipal UserDetailsImpl user) {
        if (!ownsPatch(id, user.getId())) {
            return ResponseEntity.notFound().build();
        }
        var status = resource.status() != null ? PatchStatus.valueOf(resource.status().toUpperCase()) : PatchStatus.ACTIVE;
        var command = new UpdatePatchCommand(id, resource.patch_code(), LocalDateTime.now(), status, resource.patients_id());
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, PatchResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetailsImpl user) {
        if (!ownsPatch(id, user.getId())) {
            return ResponseEntity.notFound().build();
        }
        var result = commandService.handle(new DeletePatchCommand(id));
        return result instanceof com.bytecore.vitalcare.platform.shared.application.result.Result.Success<?, ?>
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
