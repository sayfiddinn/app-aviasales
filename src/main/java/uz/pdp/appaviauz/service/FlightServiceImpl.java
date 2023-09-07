package uz.pdp.appaviauz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appaviauz.entity.FlightSchedule;
import uz.pdp.appaviauz.entity.enums.FlightStatus;
import uz.pdp.appaviauz.exception.ConflictException;
import uz.pdp.appaviauz.exception.NotFoundException;
import uz.pdp.appaviauz.mapper.MyMapper;
import uz.pdp.appaviauz.payload.FlightDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.repository.AerodromeRepository;
import uz.pdp.appaviauz.repository.FlightScheduleRepository;
import uz.pdp.appaviauz.repository.PlaneRepository;
import uz.pdp.appaviauz.util.Util;
import uz.pdp.appaviauz.util.Validation;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static uz.pdp.appaviauz.util.Path.*;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightScheduleRepository flightScheduleRepository;
    private final AerodromeRepository aerodromeRepository;
    private final PlaneRepository planeRepository;
    private final MyMapper mapper;


    @Override
    public ResultMessage addFlight(FlightDTO flightDTO) {
        checkAndSave(flightDTO, new FlightSchedule());
        return new ResultMessage(true, SAVED);
    }

    @Override
    public ResultMessage editFlight(UUID id, FlightDTO flightDTO) {

        FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flight" + NOT_FOUND));
        checkAndSave(flightDTO, flightSchedule);
        return new ResultMessage(true, UPDATED);
    }

    @Override
    public ResultMessage showFlight(UUID id) {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flight" + NOT_FOUND));
        return new ResultMessage(true, flightSchedule);
    }

    @Override
    public ResultMessage showFlightsByStatus(FlightStatus status,
                                             Integer page, Integer size) {
        Validation.checkPage(page, size, flightScheduleRepository
                .countByFlightStatus(status));
        List<FlightSchedule> all = flightScheduleRepository
                .findAllByFlightStatus(status, Util.getPageable(page, size));
        return new ResultMessage(true, all);
    }

    @Override
    public ResultMessage showFlights(Integer page, Integer size) {
        Validation.checkPage(page, size, flightScheduleRepository.count());
        List<FlightSchedule> all = flightScheduleRepository
                .findAll(Util.getPageable(page, size)).getContent();
        return new ResultMessage(true, all);
    }

    @Override
    public ResultMessage cancelFlight(UUID id) {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flight" + NOT_FOUND));
        if (Objects.equals(flightSchedule.getFlightStatus(), FlightStatus.CANCELED))
            return new ResultMessage(false, "This flight already canceled");
        flightSchedule.setFlightStatus(FlightStatus.CANCELED);
        flightScheduleRepository.save(flightSchedule);
        return new ResultMessage(true, "This flight canceled");
    }


    @Override
    public ResultMessage showFlightByAerodrome(UUID aero_id, Integer page, Integer size) {
        aerodromeRepository.findById(aero_id)
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND));
        Validation.checkPage(page, size, flightScheduleRepository
                .countByFrom_IdOrTo_Id(aero_id, aero_id));
        List<FlightSchedule> allByAerodrome = flightScheduleRepository
                .findAllByFrom_IdOrTo_Id(aero_id, aero_id,
                        Util.getExtraPageable(page, size, "flightTime", false));
        return new ResultMessage(true, allByAerodrome);
    }

    @Override
    public ResultMessage showFlightByAerodromeTime(UUID aeroId, Date time,
                                                   Integer page, Integer size) {
        aerodromeRepository.findById(aeroId)
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND));
        Validation.checkPage(page, size, flightScheduleRepository
                .countByAeroIdAndFlightTime(aeroId, time));
        List<FlightSchedule> all = flightScheduleRepository
                .findAllByAeroIdAndFlightTime(aeroId, time, page, size);
        return new ResultMessage(true, all);
    }

    @Override
    public ResultMessage showFlightByAerodromeTimeBetween(UUID aeroId, Date start, Date end,
                                                          Integer page, Integer size) {
        aerodromeRepository.findById(aeroId)
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND));
        Validation.checkPage(page, size, flightScheduleRepository
                .countByAeroIdAndFlightTimeBetween(aeroId, start, end));
        List<FlightSchedule> all = flightScheduleRepository
                .findAllByAeroIdAndFlightTimeBetween(aeroId, start, end, page, size);
        return new ResultMessage(true, all);
    }

    @Override
    public ResultMessage showFlightByPlane(UUID planeId, Integer page, Integer size) {
        planeRepository.findById(planeId)
                .orElseThrow(() -> new NotFoundException("Plane" + NOT_FOUND));
        Validation.checkPage(page, size, flightScheduleRepository.countByPlane_Id(planeId));
        List<FlightSchedule> all = flightScheduleRepository
                .findAllByPlane_Id(planeId,
                        Util.getExtraPageable(page, size, "flightTime", false));
        return new ResultMessage(true, all);
    }

    @Override
    public ResultMessage showFlightByPlaneTime(UUID planeId, Date time,
                                               Integer page, Integer size) {
        planeRepository.findById(planeId)
                .orElseThrow(() -> new NotFoundException("Plane" + NOT_FOUND));
        Validation.checkPage(page, size, flightScheduleRepository
                .countByPlane_IdAndFlightTime(planeId, time));
        List<FlightSchedule> all = flightScheduleRepository
                .findAllByPlaneIdAndFlightTime(planeId, time, page, size);
        return new ResultMessage(true, all);
    }

    @Override
    public ResultMessage showFlightByPlaneTimeBetween(UUID planeId, Date start, Date end,
                                                      Integer page, Integer size) {
        planeRepository.findById(planeId)
                .orElseThrow(() -> new NotFoundException("Plane" + NOT_FOUND));
        Validation.checkPage(page, size, flightScheduleRepository
                .countByPlaneIdAndFlightTimeBetween(planeId, start, end));
        List<FlightSchedule> all = flightScheduleRepository
                .findAllByPlaneIdAndFlightTimeBetween(planeId, start, end, page, size);
        return new ResultMessage(true, all);
    }

    private void checkAndSave(FlightDTO flightDTO, FlightSchedule flightSchedule) {
        if (Objects.equals(flightDTO.getToAero(), flightDTO.getFromAero()))
            throw new ConflictException("From aerodrome cannot be equal To aerodrome");
        if (flightScheduleRepository.findByNew(flightDTO.getPlaneId(), flightDTO.getFlightTime()))
            throw new ConflictException("During this flight time there is a flight on the plane");
        mapper.mappingFlight(flightDTO, flightSchedule);
        flightSchedule.setFrom(aerodromeRepository
                .findById(flightDTO.getFromAero())
                .orElseThrow(() -> new NotFoundException("From aerodrome" + NOT_FOUND)));
        flightSchedule.setTo(aerodromeRepository
                .findById(flightDTO.getToAero())
                .orElseThrow(() -> new NotFoundException("To aerodrome" + NOT_FOUND)));
        flightSchedule.setPlane(planeRepository
                .findById(flightDTO.getPlaneId())
                .orElseThrow(() -> new NotFoundException("Plane" + NOT_FOUND)));

        flightScheduleRepository.save(flightSchedule);
    }

}
