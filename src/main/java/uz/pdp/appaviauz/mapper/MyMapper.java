package uz.pdp.appaviauz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import uz.pdp.appaviauz.entity.*;
import uz.pdp.appaviauz.payload.*;

@Mapper(componentModel = "spring")
public interface MyMapper {
    MyMapper INSTANCE = Mappers.getMapper(MyMapper.class);

    Aerodrome aerodromeDtoToaerodrome(AerodromeDTO aerodromeDTO);

    void updateAerodrome(AerodromeDTO aerodromeDTO,
                         @MappingTarget Aerodrome aerodrome);

    AerodromeFlightsHistory historyDtoToHistory(AeroFlightsHistoryDTO historyDTO);

    void mappingFlight(FlightDTO flightDTO,
                       @MappingTarget FlightSchedule flightSchedule);

    Plane planeDtoToPlane(PlaneDTO planeDTO);

    void updatePlane(PlaneDTO planeDTO,
                     @MappingTarget Plane plane);

    PurchasedTicketDTO mappingPurTicketDTO(PurchasedTicket ticket);

    Ticket ticketDtoToTicket(TicketDTO ticketDTO);

    void userDtoToUser(UserDTO userDTO, @MappingTarget User user);

    void mappingRole(RoleDTO roleDTO, @MappingTarget Role role);

}
