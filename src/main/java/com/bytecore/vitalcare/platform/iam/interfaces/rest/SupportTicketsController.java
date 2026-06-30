package com.bytecore.vitalcare.platform.iam.interfaces.rest;

import com.bytecore.vitalcare.platform.iam.application.commandservices.SupportTicketCommandService;
import com.bytecore.vitalcare.platform.iam.application.queryservices.SupportTicketQueryService;
import com.bytecore.vitalcare.platform.iam.domain.model.commands.CreateSupportTicketCommand;
import com.bytecore.vitalcare.platform.iam.domain.model.queries.GetSupportTicketsByUserIdQuery;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.resources.CreateSupportTicketResource;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.resources.SupportTicketResource;
import com.bytecore.vitalcare.platform.iam.interfaces.rest.transform.SupportTicketResourceFromEntityAssembler;
import com.bytecore.vitalcare.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/support_tickets", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Support Tickets", description = "Support ticket management endpoints")
public class SupportTicketsController {

    private final SupportTicketCommandService commandService;
    private final SupportTicketQueryService queryService;

    public SupportTicketsController(SupportTicketCommandService commandService,
                                    SupportTicketQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<SupportTicketResource>> getTicketsByUser(@RequestParam Long users_id) {
        var query = new GetSupportTicketsByUserIdQuery(users_id);
        var tickets = queryService.handle(query).stream()
                .map(t -> SupportTicketResourceFromEntityAssembler.toResourceFromEntity(t, users_id))
                .toList();
        return ResponseEntity.ok(tickets);
    }

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody CreateSupportTicketResource resource) {
        var command = new CreateSupportTicketCommand(resource.users_id(), resource.subject(), resource.message());
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                t -> SupportTicketResourceFromEntityAssembler.toResourceFromEntity(t, resource.users_id()),
                HttpStatus.CREATED
        );
    }
}
