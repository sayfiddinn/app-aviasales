package uz.pdp.appaviauz.payload;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaneDTO {
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @Min(value = 0, message = "seats is not negative")
    @Max(value = 10_000, message = "seats is very big")
    private Integer seats;
    @NotEmpty(message = "plane Number cannot be empty")
    private String planeNumber;
    @NotEmpty(message = "plane Company cannot be empty")
    private String planeCompany;
    @NotEmpty(message = "aerodrome Id cannot be empty")
    private UUID aerodromeId;
}
