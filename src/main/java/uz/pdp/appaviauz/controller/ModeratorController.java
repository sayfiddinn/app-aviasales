package uz.pdp.appaviauz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appaviauz.payload.AeroFlightsHistoryDTO;
import uz.pdp.appaviauz.payload.TicketDTO;
import uz.pdp.appaviauz.util.Path;

import java.util.UUID;

import static uz.pdp.appaviauz.controller.ModeratorControllerImpl.*;

@PreAuthorize("hasAnyAuthority('ADD_TICKET','ADD_HISTORY','SHOW_HISTORY','EDIT_HISTORY')")
@RequestMapping(Path.BASE_PATH_MODER)
public interface ModeratorController {

    @PostMapping(ADD_TICKET)
    ResponseEntity<?> addTicket(@RequestBody TicketDTO ticketDTO);

    @DeleteMapping(DELETE_TICKET)
    ResponseEntity<?> deletedTicket(@PathVariable UUID id);

    @DeleteMapping(DELETE_TICKET_NOT_LEFT)
    ResponseEntity<?> deletedTicketByNotLeft();

    @GetMapping(SHOW_TICKET)
    ResponseEntity<?> showTickets(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "10") Integer size,
                                  @RequestParam(defaultValue = "true") boolean Asc);

    @GetMapping(SHOW_TICKET_NOT_LEFT)
    ResponseEntity<?> showTicketByNotLeft(@RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          @RequestParam(defaultValue = "true") boolean Asc);

    @GetMapping(SHOW_HISTORY)
    ResponseEntity<?> showHistoryAll(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     @RequestParam(defaultValue = "true") boolean dateAsc);

    @GetMapping(SHOW_HISTORY_BY_AERO)
    ResponseEntity<?> showHistoryByAerodrome(@PathVariable UUID aero_id,
                                             @RequestParam(defaultValue = "0") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             @RequestParam(defaultValue = "true") boolean dateAsc);

    @GetMapping(SHOW_HISTORY_BY_PLANE)
    ResponseEntity<?> showHistoryByPlane(@PathVariable UUID plane_id,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         @RequestParam(defaultValue = "true") boolean dateAsc);

    @PostMapping(ADD_HISTORY)
    ResponseEntity<?> addAerodromeFlightHistory(
            @RequestBody AeroFlightsHistoryDTO aeroFlightsHistoryDTO
    );

    @PutMapping(UPDATE_HISTORY)
    ResponseEntity<?> editAerodromeFlightHistory(@PathVariable UUID id,
                                                 @RequestParam(defaultValue = "false") boolean has_left,
                                                 @RequestParam(defaultValue = "false") boolean is_arrived);


}
