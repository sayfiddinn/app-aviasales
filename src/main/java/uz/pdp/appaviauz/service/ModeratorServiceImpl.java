package uz.pdp.appaviauz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appaviauz.entity.AerodromeFlightsHistory;
import uz.pdp.appaviauz.entity.FlightSchedule;
import uz.pdp.appaviauz.entity.Ticket;
import uz.pdp.appaviauz.exception.ConflictException;
import uz.pdp.appaviauz.exception.NotFoundException;
import uz.pdp.appaviauz.mapper.MyMapper;
import uz.pdp.appaviauz.payload.AeroFlightsHistoryDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.TicketDTO;
import uz.pdp.appaviauz.repository.*;
import uz.pdp.appaviauz.util.Util;
import uz.pdp.appaviauz.util.Validation;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static uz.pdp.appaviauz.util.Path.*;

@Service
@RequiredArgsConstructor
public class ModeratorServiceImpl implements ModeratorService {
    private final PlaneRepository planeRepository;
    private final AerodromeRepository aerodromeRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final HistoryFlightAeroRepo historyFlightAeroRepo;
    private final TicketRepository ticketRepository;
    private final MyMapper mapper;


    @Override
    public ResultMessage addTicket(TicketDTO ticketDTO) {
        checkAndSave(ticketDTO);
        return new ResultMessage(true, "Ticket saved");
    }

    private void checkAndSave(TicketDTO ticketDTO) {
        if (Objects.equals(ticketDTO.getFromId(), ticketDTO.getBackId()))
            throw new ConflictException("From flight cannot be equal Back flight");
        Ticket ticket = mapper.ticketDtoToTicket(ticketDTO);
        if (ticketDTO.getBackId() != null) {
            FlightSchedule back = flightScheduleRepository.findById(ticketDTO.getBackId())
                    .orElseThrow(() -> new NotFoundException("Back flight" + NOT_FOUND));
            ticket.setBack(back);
            ticket.setBackSeatNumber(back.getPlane().getSeats());
        }
        FlightSchedule from = flightScheduleRepository.findById(ticketDTO.getFromId())
                .orElseThrow(() -> new NotFoundException("From flight" + NOT_FOUND));
        ticket.setFrom(from);
        ticket.setFromSeatNumber(from.getPlane().getSeats());
        ticketRepository.save(ticket);
    }


    @Override
    public ResultMessage deletedTicket(UUID id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket" + NOT_FOUND));
        if (ticket.isDeleted())
            return new ResultMessage(false, "Ticket already deleted");
        ticket.setDeleted(true);
        ticketRepository.save(ticket);
        return new ResultMessage(true, DELETED);
    }

    @Override
    public ResultMessage showTickets(Integer page, Integer size,
                                     boolean Asc) {
        Validation.checkPage(page, size, ticketRepository.count());
        List<Ticket> all = ticketRepository
                .findAll(Util.getPageableDesc(page, size, Asc))
                .getContent();
        return new ResultMessage(true, all);
    }

    @Override
    public ResultMessage showTicketByNotLeft(Integer page, Integer size,
                                             boolean Asc) {
        Validation.checkPage(page, size, ticketRepository.countAllByCount(0));
        List<Ticket> all = ticketRepository.findAllByCount(0);
        return new ResultMessage(true, all);
    }

    @Override
    public ResultMessage deletedTicketByNotLeft() {
        long l = ticketRepository.countAllByCount(0);
        if (l == 0)
            return new ResultMessage(false, "Not left tickets don't available");
        List<Ticket> all = ticketRepository.findAllByCount(0);
        for (Ticket ticket : all) {
            ticket.setDeleted(true);
        }
        ticketRepository.saveAll(all);
        return new ResultMessage(true, DELETED);
    }

    //History
    @Override
    public ResultMessage addAerodromeFlightHistory(AeroFlightsHistoryDTO historyDTO) {
        AerodromeFlightsHistory history = mapper.historyDtoToHistory(historyDTO);
        history.setAerodrome(aerodromeRepository
                .findById(historyDTO.getAerodromeId())
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND)));
        history.setPlane(planeRepository
                .findById(historyDTO.getPlaneId())
                .orElseThrow(() -> new NotFoundException("Plane" + NOT_FOUND)));
        historyFlightAeroRepo.save(history);
        return new ResultMessage(true, SAVED);
    }

    @Override
    public ResultMessage showHistoryAll(Integer page, Integer size, boolean dateAsc) {
        Validation.checkPage(page, size, historyFlightAeroRepo.count());
        List<AerodromeFlightsHistory> all = historyFlightAeroRepo
                .findAll(Util.getExtraPageable(page, size, "date", dateAsc)).getContent();
        return new ResultMessage(true, all);
    }

    @Override
    public ResultMessage showHistoryByAerodrome(UUID aero_id,
                                                Integer page, Integer size,
                                                boolean dataAsc) {
        Validation.checkPage(page, size, historyFlightAeroRepo.countByAerodrome_Id(aero_id));
        aerodromeRepository.findById(aero_id)
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND));
        List<AerodromeFlightsHistory> all = historyFlightAeroRepo
                .findAllByAerodrome_Id(aero_id, Util
                        .getExtraPageable(page, size, "date", dataAsc));
        return new ResultMessage(true, all);
    }

    @Override
    public ResultMessage showHistoryByPlane(UUID plane_id,
                                            Integer page, Integer size,
                                            boolean dateAsc) {
        Validation.checkPage(page, size, historyFlightAeroRepo.countByPlane_Id(plane_id));
        planeRepository.findById(plane_id)
                .orElseThrow(() -> new NotFoundException("Plane" + NOT_FOUND));
        List<AerodromeFlightsHistory> all = historyFlightAeroRepo
                .findAllByPlane_Id(plane_id,
                        Util.getExtraPageable(page, size, "date", dateAsc));
        return new ResultMessage(true, all);
    }

    @Override
    public ResultMessage editAerodromeFlightHistory(UUID history_id,
                                                    boolean has_left,
                                                    boolean is_arrived
    ) {
        AerodromeFlightsHistory history = historyFlightAeroRepo.findById(history_id)
                .orElseThrow(() -> new NotFoundException("History" + NOT_FOUND));
        if (has_left == is_arrived)
            return new ResultMessage(false, "request params wrong");
        else if (has_left)
            history.setHasLeft(true);
        else history.setArrived(true);
        historyFlightAeroRepo.save(history);
        return new ResultMessage(true, UPDATED);
    }

}
