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
public class AeroFlightsHistoryDTO {
    @NotEmpty(message = "comment cannot be empty")
    private String comment;
    @NotEmpty(message = "aerodrome Id cannot be empty")
    private UUID aerodromeId;
    @NotEmpty(message = "plane Id cannot be empty")
    private UUID planeId;
    @NotEmpty(message = "date cannot be empty")
    private Timestamp date;
    private boolean isArrived;
    private boolean hasLeft;
}
