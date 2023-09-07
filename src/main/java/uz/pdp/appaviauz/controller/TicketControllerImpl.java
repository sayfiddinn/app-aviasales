package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.TicketSearchDTO;
import uz.pdp.appaviauz.service.TicketService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "ticket-controller", description = "Ticket controller management")
public class TicketControllerImpl implements TicketController {
    static final String SHOW_BY_FLIGHT = "/flight_by/{flight_id}";
    static final String BUY = "/by/{ticket_id}";
    static final String CANCEL = "/cancel/{id}";
    static final String SEARCH = "/search";

    private final TicketService ticketService;

    @Override
    public ResponseEntity<?> showTicketByFlight(UUID flight_id,
                                                Integer page, Integer size) {
        return ResponseEntity.ok(ticketService
                .showTicketByFlight(flight_id, page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> byTicket(UUID ticket_id) {
        ResultMessage resultMessage = ticketService.byTicket(ticket_id);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.ACCEPTED
                        : HttpStatus.CONFLICT)
                .body(resultMessage);
    }


    @Override
    public ResponseEntity<?> cancelTicket(UUID id) {
        ResultMessage resultMessage = ticketService.cancelTicket(id);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.ACCEPTED
                        : HttpStatus.NOT_MODIFIED)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> searchTickets(TicketSearchDTO ticketSearchDTO,
                                           Integer page, Integer size) {
        return ResponseEntity.ok(ticketService
                .searchTickets(ticketSearchDTO, page, size)
                .getObject());
    }
}
