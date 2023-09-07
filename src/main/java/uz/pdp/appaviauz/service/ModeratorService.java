package uz.pdp.appaviauz.service;


import uz.pdp.appaviauz.payload.AeroFlightsHistoryDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.TicketDTO;

import java.util.UUID;

public interface ModeratorService {

    ResultMessage addTicket(TicketDTO ticketDTO);

    ResultMessage addAerodromeFlightHistory(AeroFlightsHistoryDTO aeroFlightsHistoryDTO);

    ResultMessage showHistoryAll(Integer page, Integer size, boolean dateAsc);

    ResultMessage showHistoryByAerodrome(UUID aero_id, Integer page, Integer size,
                                         boolean dataAsc);

    ResultMessage showHistoryByPlane(UUID plane_id, Integer page, Integer size,
                                     boolean dateAsc);

    ResultMessage editAerodromeFlightHistory(UUID history_id,
                                             boolean has_left,
                                             boolean is_arrived);

    ResultMessage deletedTicket(UUID id);

    ResultMessage showTickets(Integer page, Integer size, boolean Asc);

    ResultMessage showTicketByNotLeft(Integer page, Integer size, boolean Asc);

    ResultMessage deletedTicketByNotLeft();
}
