package uz.pdp.appaviauz.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appaviauz.entity.FlightSchedule;
import uz.pdp.appaviauz.entity.enums.TicketStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchasedTicketDTO implements Serializable {
    private UUID id;
    private String name;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private FlightSchedule from;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private FlightSchedule back;
    private Integer fromSeatNumber;
    private Integer backSeatNumber;
    private Double price;
    private UUID userId;
    private TicketStatus ticketStatus;
    private UUID ticketId;
    private Timestamp creationTimestamp;
    private Timestamp updateTimestamp;
    private UUID createdBy;
    private UUID updatedBy;
}
