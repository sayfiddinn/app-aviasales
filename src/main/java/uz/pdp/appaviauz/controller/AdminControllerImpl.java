package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appaviauz.entity.Country;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.repository.CountryRepository;
import uz.pdp.appaviauz.service.AdminService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "admin-controller", description = "Admin controller management, admin and above can access")
@SecurityRequirement(name = "bearerAuth")
public class AdminControllerImpl implements AdminController {
    static final String MODERATOR = "/moderator";
    static final String ADD_MODERATOR = MODERATOR + "/{user_id}";
    static final String DELETE_MODERATOR = MODERATOR + "/{id}";
    static final String SHOW_MODERATORS = MODERATOR;
    static final String SHOW_MODERATOR = MODERATOR + "/{id}";
    static final String SHOW_USERS = "/users";
    static final String SHOW_USER = SHOW_USERS + "/{user_id}";
    public static final String TICKET = "/sold_ticket";
    static final String TICKET_BY_FLIGHT = TICKET + "/flight_by/{flight_id}";
    static final String TICKET_BY_DATE = TICKET + "/date_by";
    static final String TICKET_DATE_BETWEEN = TICKET + "/date_between";
    static final String TICKET_BY_DATE_AND_AERO = TICKET + "/aero_date_by/{aero_id}";
    static final String TICKET_DATE_BETWEEN_AND_AERO = TICKET + "/aero_date_between/{aero_id}";

    private final AdminService adminService;
    private final CountryRepository countryRepository;


    @Override
    public ResponseEntity<?> addModerator(UUID userId) {
        ResultMessage resultMessage = adminService.addModerator(userId);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.CREATED
                        : HttpStatus.NOT_ACCEPTABLE)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> deleteModerator(UUID id) {
        ResultMessage resultMessage = adminService.deleteModerator(id);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.OK
                        : HttpStatus.NOT_FOUND)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> showModerators(Integer page, Integer size) {
        return ResponseEntity.ok(adminService
                .showModerators(page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showModerator(UUID id) {
        return ResponseEntity.ok(adminService
                .showModerator(id)
                .getObject());
    }


    @Override
    public ResponseEntity<?> showSoldTicketsByFlight(UUID flightId,
                                                     Integer page, Integer size) {
        return ResponseEntity.ok(adminService
                .showSoldTicketsByFlight(flightId, page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showSoldTicketsByDate(Date time,
                                                   Integer page, Integer size) {
        return ResponseEntity.ok(adminService
                .showSoldTicketsByDate(time, page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showSoldTicketsByDateBetween(Date start, Date end,
                                                          Integer page, Integer size) {
        return ResponseEntity.ok(adminService
                .showSoldTicketsByDateBetween(start, end, page, size)
                .getObject());
    }


    @Override
    public ResponseEntity<?> showSoldTicketsByAeroAndDate(UUID aerodromeId, Date time,
                                                          Integer page, Integer size) {
        return ResponseEntity.ok(adminService
                .showSoldTicketsByAeroAndDate(aerodromeId, time, page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showSoldTicketsByAeroAndDateBetween(UUID aerodromeId,
                                                                 Date start, Date end,
                                                                 Integer page, Integer size) {
        return ResponseEntity.ok(adminService
                .showSoldTicketsByAeroAndDateBetween(aerodromeId, start, end, page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showUsers(Integer page, Integer size) {
        return ResponseEntity.ok(adminService
                .showUsers(page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showUser(UUID user_id) {
        return ResponseEntity.ok(adminService
                .showUser(user_id)
                .getObject());
    }

    @Override
    public ResponseEntity<?> getCountry() {
        List<Country> all = countryRepository.findAll();
        return ResponseEntity.ok(all);
    }
}
