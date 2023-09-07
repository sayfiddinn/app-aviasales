package uz.pdp.appaviauz.service;

import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.TicketSearchDTO;

import java.util.UUID;

public interface TicketService {

    ResultMessage showTicketByFlight(UUID flight_id, Integer page, Integer size);

    ResultMessage byTicket(UUID ticketId);

    ResultMessage cancelTicket(UUID id);


    ResultMessage searchTickets(TicketSearchDTO ticketSearchDTO, Integer page, Integer size);

}
