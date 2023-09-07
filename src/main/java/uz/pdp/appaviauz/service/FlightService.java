package uz.pdp.appaviauz.service;

import uz.pdp.appaviauz.entity.enums.FlightStatus;
import uz.pdp.appaviauz.payload.FlightDTO;
import uz.pdp.appaviauz.payload.ResultMessage;

import java.util.Date;
import java.util.UUID;

public interface FlightService {
    ResultMessage addFlight(FlightDTO flightDTO);

    ResultMessage editFlight(UUID id, FlightDTO flightDTO);

    ResultMessage showFlight(UUID id);

    ResultMessage showFlightsByStatus(FlightStatus status,
                                      Integer page, Integer size);

    ResultMessage showFlights(Integer page, Integer size);

    ResultMessage cancelFlight(UUID id);

    ResultMessage showFlightByAerodrome(UUID aero_id,
                                        Integer page, Integer size);

    ResultMessage showFlightByAerodromeTime(UUID aeroId, Date time,
                                            Integer page, Integer size);

    ResultMessage showFlightByAerodromeTimeBetween(UUID aeroId, Date start, Date end,
                                                   Integer page, Integer size);

    ResultMessage showFlightByPlane(UUID planeId, Integer page, Integer size);

    ResultMessage showFlightByPlaneTime(UUID planeId, Date time,
                                        Integer page, Integer size);

    ResultMessage showFlightByPlaneTimeBetween(UUID planeId, Date start, Date end,
                                               Integer page, Integer size);

}
