package uz.pdp.appaviauz.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appaviauz.entity.PurchasedTicket;
import uz.pdp.appaviauz.entity.Ticket;
import uz.pdp.appaviauz.entity.enums.TicketStatus;
import uz.pdp.appaviauz.exception.ConflictException;
import uz.pdp.appaviauz.exception.NotFoundException;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.TicketSearchDTO;
import uz.pdp.appaviauz.repository.CityRepository;
import uz.pdp.appaviauz.repository.FlightScheduleRepository;
import uz.pdp.appaviauz.repository.PurchasedTicketRepo;
import uz.pdp.appaviauz.repository.TicketRepository;
import uz.pdp.appaviauz.util.Util;
import uz.pdp.appaviauz.util.Validation;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static uz.pdp.appaviauz.util.Path.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final PurchasedTicketRepo purchasedTicketRepo;
    private final FlightScheduleRepository flightScheduleRepository;
    private final CityRepository cityRepository;
    private final BaseService baseService;


    @Override
    public ResultMessage showTicketByFlight(UUID flight_id,
                                            Integer page, Integer size) {
        flightScheduleRepository.findById(flight_id)
                .orElseThrow(() -> new NotFoundException("Flight" + NOT_FOUND));
        Validation.checkPage(page, size, ticketRepository.countByFlight(flight_id));
        List<Ticket> all = ticketRepository
                .findByFlight(flight_id,
                        Util.getExtraPageable(page, size, "creation_timestamp", false));
        return new ResultMessage(true, all);
    }

    @Transactional
    @Override
    public ResultMessage byTicket(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket" + NOT_FOUND));
        if (ticket.getCount() < 1)
            return new ResultMessage(false, "Sorry, this is not a ticket");
        ticket.setCount(ticket.getCount() - 1);
        ticketRepository.save(ticket);
        PurchasedTicket purchasedTicket = makerPurchasedTicket(ticket, new PurchasedTicket());
        purchasedTicketRepo.save(purchasedTicket);
        return new ResultMessage(true, "Success purchased ticket");
    }

    @Transactional
    @Override
    public ResultMessage cancelTicket(UUID id) {
        PurchasedTicket purchasedTicket = purchasedTicketRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket" + NOT_FOUND));
        if (!Objects.equals(purchasedTicket.getUser().getId(), baseService.getUser().getId())) {
            throw new ConflictException("User and ticket are inconsistent");
        }
        if (Objects.equals(purchasedTicket.getTicketStatus(), TicketStatus.CANCELED)) {
            return new ResultMessage(false, "This ticket already canceled");
        }
        purchasedTicket.setTicketStatus(TicketStatus.CANCELED);
        purchasedTicketRepo.save(purchasedTicket);

        Ticket ticket = purchasedTicket.getTicket();
        ticket.setCount(ticket.getCount() + 1);
        ticketRepository.save(ticket);
        return new ResultMessage(true, "success canceled");
    }

    @Override
    public ResultMessage searchTickets(TicketSearchDTO ticketSearchDTO,
                                       Integer page, Integer size) {
        Integer fromCity = cityRepository.findById(ticketSearchDTO.getFrom_id())
                .orElseThrow(() -> new NotFoundException("From city" + NOT_FOUND)).getId();
        Integer toCity = cityRepository.findById(ticketSearchDTO.getTo_id())
                .orElseThrow(() -> new NotFoundException("To city" + NOT_FOUND)).getId();
        Date back = ticketSearchDTO.getBack();
        Date when = ticketSearchDTO.getWhen();
        List<Ticket> tickets;
        if (back == null) {
            tickets = ticketRepository
                    .fromBy(fromCity, toCity, when, page, size);
        } else {
            tickets = ticketRepository
                    .fromAndBackBy(fromCity, toCity, when, back, Util
                            .getExtraPageable(page, size, "creation_timestamp", false));
        }
        return new ResultMessage(true, tickets);
    }

    private PurchasedTicket makerPurchasedTicket(Ticket ticket, PurchasedTicket purchasedTicket) {
        purchasedTicket.setPrice(ticket.getPrice());
        purchasedTicket.setFrom(ticket.getFrom());
        purchasedTicket.setBack(ticket.getBack());
        purchasedTicket.setName(ticket.getName());
        purchasedTicket.setFromSeatNumber(ticket.getFromSeatNumber());
        purchasedTicket.setBackSeatNumber(ticket.getBackSeatNumber());
        purchasedTicket.setUser(baseService.getUser());
        purchasedTicket.setTicket(ticket);
        return purchasedTicket;
    }

}
