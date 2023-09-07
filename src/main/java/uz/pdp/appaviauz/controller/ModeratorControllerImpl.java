package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appaviauz.payload.AeroFlightsHistoryDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.TicketDTO;
import uz.pdp.appaviauz.service.ModeratorService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "moderator-controller", description = "Moderator controller management, moderator and above can access")
@SecurityRequirement(name = "bearerAuth")
public class ModeratorControllerImpl implements ModeratorController {
    static final String TICKET = "/ticket";
    static final String ADD_TICKET = TICKET;
    static final String DELETE_TICKET = TICKET + "/{id}";
    static final String DELETE_TICKET_NOT_LEFT = TICKET + "/not_left";
    static final String SHOW_TICKET = TICKET;
    static final String SHOW_TICKET_NOT_LEFT = TICKET + "/not_left";
    public static final String HISTORY = "/history";
    static final String SHOW_HISTORY = HISTORY;
    static final String SHOW_HISTORY_BY_AERO = HISTORY + "/aerodrome_by/{aero_id}";
    static final String SHOW_HISTORY_BY_PLANE = HISTORY + "/plane_by/{plane_id}";
    static final String ADD_HISTORY = HISTORY;
    static final String UPDATE_HISTORY = HISTORY + "/{id}";

    private final ModeratorService moderatorService;


    @Override
    public ResponseEntity<?> addTicket(TicketDTO ticketDTO) {
        ResultMessage resultMessage = moderatorService.addTicket(ticketDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> deletedTicket(UUID id) {
        ResultMessage resultMessage = moderatorService.deletedTicket(id);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.ACCEPTED
                        : HttpStatus.NOT_MODIFIED)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> deletedTicketByNotLeft() {
        ResultMessage resultMessage = moderatorService.deletedTicketByNotLeft();
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.ACCEPTED
                        : HttpStatus.NOT_MODIFIED)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> showTickets(Integer page, Integer size, boolean Asc) {
        return ResponseEntity.ok(moderatorService
                .showTickets(page, size, Asc)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showTicketByNotLeft(Integer page, Integer size, boolean Asc) {
        return ResponseEntity.ok(moderatorService
                .showTicketByNotLeft(page, size, Asc)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showHistoryAll(Integer page, Integer size, boolean dateAsc) {
        return ResponseEntity.ok(moderatorService
                .showHistoryAll(page, size, dateAsc)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showHistoryByAerodrome(UUID aero_id, Integer page, Integer size, boolean dataAsc) {
        return ResponseEntity.ok(moderatorService
                .showHistoryByAerodrome(aero_id, page, size, dataAsc)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showHistoryByPlane(UUID plane_id, Integer page, Integer size, boolean dateAsc) {
        return ResponseEntity.ok(moderatorService
                .showHistoryByPlane(plane_id, page, size, dateAsc)
                .getObject());
    }

    @Override
    public ResponseEntity<?> addAerodromeFlightHistory(AeroFlightsHistoryDTO aeroFlightsHistoryDTO) {
        ResultMessage resultMessage = moderatorService.addAerodromeFlightHistory(aeroFlightsHistoryDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> editAerodromeFlightHistory(UUID id, boolean has_left, boolean is_arrived) {
        ResultMessage resultMessage = moderatorService.editAerodromeFlightHistory(id, has_left, is_arrived);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.ACCEPTED
                        : HttpStatus.NOT_MODIFIED)
                .body(resultMessage);
    }

}
