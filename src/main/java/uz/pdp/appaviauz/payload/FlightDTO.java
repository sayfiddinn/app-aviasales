package uz.pdp.appaviauz.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {

    private String name;
    @NotEmpty(message = "from Aero cannot be empty")
    private UUID fromAero;
    @NotEmpty(message = "to Aero cannot be empty")
    private UUID toAero;
    @NotEmpty(message = "flight Time cannot be empty")
    private Timestamp flightTime;
    @NotEmpty(message = "plane Id cannot be empty")
    private UUID planeId;
}
