package uz.pdp.appaviauz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.appaviauz.entity.base.AbsEntity;
import uz.pdp.appaviauz.entity.enums.TicketStatus;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class PurchasedTicket extends AbsEntity {
    @ManyToOne
    private FlightSchedule from;
    @ManyToOne
    private FlightSchedule back;
    private Integer fromSeatNumber;
    private Integer backSeatNumber;
    private Double price;
    @ManyToOne
    private User user;
    @Enumerated(value = EnumType.STRING)
    private TicketStatus ticketStatus;
    @JsonIgnore
    @ManyToOne
    private Ticket ticket;

    {
        ticketStatus = TicketStatus.ACTIVE;
    }

}
