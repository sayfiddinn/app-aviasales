package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appaviauz.entity.enums.FlightStatus;
import uz.pdp.appaviauz.payload.FlightDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.service.FlightService;

import java.util.Date;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "flight-controller", description = "Flight controller management, moderator and above can access")
@SecurityRequirement(name = "bearerAuth")
public class FlightControllerImpl implements FlightController {
    static final String STATUS_BY = "/status_by";
    static final String ID = "/{id}";
    public static final String PLANE = "/plane_by";
    static final String PLANE_BY = PLANE + "/{plane_id}";
    static final String PLANE_BY_TIME = PLANE + "_time/{plane_id}";
    static final String PLANE_BY_TIME_BETWEEN = PLANE + "_time_between/{plane_id}";
    public static final String AERODROME = "/aerodrome_by";
    static final String AERO_BY = AERODROME + "/{aero_id}";
    static final String AERO_BY_TIME = AERODROME + "_time/{aero_id}";
    static final String AERO_BY_TIME_BETWEEN = AERODROME + "_time_between/{aero_id}";

    private final FlightService flightService;

    @Override
    public ResponseEntity<?> addFlight(FlightDTO flightDTO) {
        ResultMessage resultMessage = flightService.addFlight(flightDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> editeFlight(UUID id, FlightDTO flightDTO) {
        ResultMessage resultMessage = flightService.editFlight(id, flightDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> showFlight(UUID id) {
        return ResponseEntity.ok(flightService
                .showFlight(id)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showFlights(Integer page, Integer size) {
        return ResponseEntity.ok(flightService
                .showFlights(page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showFlightsByStatus(FlightStatus status,
                                                 Integer page, Integer size) {
        return ResponseEntity.ok(flightService
                .showFlightsByStatus(status, page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> cancelFlight(UUID id) {
        ResultMessage resultMessage = flightService.cancelFlight(id);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.ACCEPTED
                        : HttpStatus.NOT_MODIFIED)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> showFlightByAerodrome(UUID aero_id,
                                                   Integer page, Integer size) {
        return ResponseEntity.ok(flightService
                .showFlightByAerodrome(aero_id, page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showFlightByAerodromeTime(UUID aero_id, Date time,
                                                       Integer page, Integer size) {
        return ResponseEntity.ok(flightService
                .showFlightByAerodromeTime(aero_id, time, page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showFlightByAerodromeTimeBetween(UUID aero_id, Date start, Date end,
                                                              Integer page, Integer size) {
        return ResponseEntity.ok(flightService
                .showFlightByAerodromeTimeBetween(aero_id, start, end, page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showFlightByPlane(UUID plane_id, Integer page, Integer size) {
        return ResponseEntity.ok(flightService
                .showFlightByPlane(plane_id, page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showFlightByPlaneTime(UUID plane_id, Date time,
                                                   Integer page, Integer size) {
        return ResponseEntity.ok(flightService
                .showFlightByPlaneTime(plane_id, time, page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showFlightByPlaneTimeBetween(UUID plane_id, Date start, Date end,
                                                          Integer page, Integer size) {
        return ResponseEntity.ok(flightService
                .showFlightByPlaneTimeBetween(plane_id, start, end, page, size)
                .getObject());
    }

}
