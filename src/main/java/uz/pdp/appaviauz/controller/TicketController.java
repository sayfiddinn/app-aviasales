package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appaviauz.payload.TicketSearchDTO;
import uz.pdp.appaviauz.util.Path;

import java.util.UUID;

import static uz.pdp.appaviauz.controller.TicketControllerImpl.*;

@RequestMapping(Path.BASE_PATH_TICKET)
public interface TicketController {
    @Operation(summary = "bearerAuth", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('SHOW_TICKET')")
    @GetMapping(SHOW_BY_FLIGHT)
    ResponseEntity<?> showTicketByFlight(@PathVariable UUID flight_id,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size);
    @Operation(summary = "bearerAuth", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('BUY_TICKET')")
    @PostMapping(BUY)
    ResponseEntity<?> byTicket(@PathVariable UUID ticket_id);
    @Operation(summary = "bearerAuth", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('CANCEL_TICKET')")
    @PutMapping(CANCEL)
    ResponseEntity<?> cancelTicket(@PathVariable UUID id);
    @Operation(summary = "from_id and to_id are <b>city</b> id's")
    @GetMapping(SEARCH)
    ResponseEntity<?> searchTickets(@RequestBody TicketSearchDTO ticketSearchDTO,
                                    @RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size);
}
