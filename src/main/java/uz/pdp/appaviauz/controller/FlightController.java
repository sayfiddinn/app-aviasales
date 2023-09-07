package uz.pdp.appaviauz.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appaviauz.entity.enums.FlightStatus;
import uz.pdp.appaviauz.payload.FlightDTO;
import uz.pdp.appaviauz.util.Path;

import java.util.Date;
import java.util.UUID;

import static uz.pdp.appaviauz.controller.FlightControllerImpl.*;

@PreAuthorize("hasAnyAuthority('ADD_FLIGHT','EDITE_FLIGHT','SHOW_FLIGHT','CANCEL_FLIGHT')")
@RequestMapping(Path.BASE_PATH_FLIGHT)
public interface FlightController {
    @PostMapping
    ResponseEntity<?> addFlight(@RequestBody FlightDTO flightDTO);

    @PutMapping(ID)
    ResponseEntity<?> editeFlight(@PathVariable UUID id, @RequestBody FlightDTO flightDTO);

    @GetMapping(ID)
    ResponseEntity<?> showFlight(@PathVariable UUID id);

    @GetMapping
    ResponseEntity<?> showFlights(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(STATUS_BY)
    ResponseEntity<?> showFlightsByStatus(@RequestParam FlightStatus status,
                                          @RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size);

    @DeleteMapping(ID)
    ResponseEntity<?> cancelFlight(@PathVariable UUID id);

    //Show flights for analytic
    @GetMapping(AERO_BY)
    ResponseEntity<?> showFlightByAerodrome(@PathVariable UUID aero_id,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(AERO_BY_TIME)
    ResponseEntity<?> showFlightByAerodromeTime(@PathVariable UUID aero_id,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date time,
                                                @RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(AERO_BY_TIME_BETWEEN)
    ResponseEntity<?> showFlightByAerodromeTimeBetween(@PathVariable UUID aero_id,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end,
                                                       @RequestParam(defaultValue = "0") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(PLANE_BY)
    ResponseEntity<?> showFlightByPlane(@PathVariable UUID plane_id,
                                        @RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(PLANE_BY_TIME)
    ResponseEntity<?> showFlightByPlaneTime(@PathVariable UUID plane_id,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date time,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(PLANE_BY_TIME_BETWEEN)
    ResponseEntity<?> showFlightByPlaneTimeBetween(@PathVariable UUID plane_id,
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size);

}
