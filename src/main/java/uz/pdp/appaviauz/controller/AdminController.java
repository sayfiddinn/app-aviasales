package uz.pdp.appaviauz.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appaviauz.util.Path;

import java.util.Date;
import java.util.UUID;

import static uz.pdp.appaviauz.controller.AdminControllerImpl.*;

@RequestMapping(Path.BASE_PATH_ADMIN)
@PreAuthorize("hasAnyAuthority('ADD_MODERATOR','DELETE_MODERATOR','SHOW_MODERATOR','SHOW_SOLD_TICKET_DETAIL','SHOW_USER')")
public interface AdminController {

    @PostMapping(ADD_MODERATOR)
    ResponseEntity<?> addModerator(@PathVariable(name = "user_id") UUID userId);

    @DeleteMapping(DELETE_MODERATOR)
    ResponseEntity<?> deleteModerator(@PathVariable UUID id);

    @GetMapping(SHOW_MODERATORS)
    ResponseEntity<?> showModerators(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(SHOW_MODERATOR)
    ResponseEntity<?> showModerator(@PathVariable UUID id);

    @GetMapping(TICKET_BY_FLIGHT)
    ResponseEntity<?> showSoldTicketsByFlight(@PathVariable(name = "flight_id") UUID flightId,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(TICKET_BY_DATE)
    ResponseEntity<?> showSoldTicketsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date time,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(TICKET_DATE_BETWEEN)
    ResponseEntity<?> showSoldTicketsByDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(TICKET_BY_DATE_AND_AERO)
    ResponseEntity<?> showSoldTicketsByAeroAndDate(@PathVariable(name = "aero_id") UUID aerodromeId,
                                                   @RequestParam
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date time,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(TICKET_DATE_BETWEEN_AND_AERO)
    ResponseEntity<?> showSoldTicketsByAeroAndDateBetween(@PathVariable(name = "aero_id") UUID aerodromeId,
                                                          @RequestParam
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                          @RequestParam
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end,
                                                          @RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(SHOW_USERS)
    ResponseEntity<?> showUsers(@RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(SHOW_USER)
    ResponseEntity<?> showUser(@PathVariable UUID user_id);

    @GetMapping("/country")
    ResponseEntity<?> getCountry();
}
