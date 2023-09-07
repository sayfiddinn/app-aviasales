package uz.pdp.appaviauz.service;

import uz.pdp.appaviauz.payload.ResultMessage;

import java.util.Date;
import java.util.UUID;

public interface AdminService {
    ResultMessage addModerator(UUID userId);

    ResultMessage deleteModerator(UUID id);

    ResultMessage showModerators(Integer page, Integer size);

    ResultMessage showModerator(UUID id);

    ResultMessage checkModerator(UUID id, String email);


    ResultMessage showSoldTicketsByFlight(UUID flightId,
                                          Integer page, Integer size);

    ResultMessage showSoldTicketsByDate(Date time,
                                        Integer page, Integer size);

    ResultMessage showSoldTicketsByDateBetween(Date start, Date end,
                                               Integer page, Integer size);

    ResultMessage showSoldTicketsByAeroAndDate(UUID aerodromeId, Date time,
                                               Integer page, Integer size);


    ResultMessage showSoldTicketsByAeroAndDateBetween(UUID aerodromeId,
                                                      Date start, Date end,
                                                      Integer page, Integer size);

    ResultMessage showUsers(Integer page, Integer size);
    ResultMessage showUser(UUID user_id);

}
