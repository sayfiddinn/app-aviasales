package uz.pdp.appaviauz.payload;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotEmpty(message = "from Id cannot be empty")
    private UUID fromId;

    private UUID backId;
    @Min(value = 1, message = "count can be at least 1")
    private Integer count;
    @Min(value = 100000, message = "count can be at least 100000")
    private Double price;
}
